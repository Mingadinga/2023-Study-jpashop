package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class MemberApiController {

    private final MemberService memberService;

    // bad case - 엔티티는 API 스펙과 결합되면 안된다
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    // good case - DTO로 엔티티와 API 스펙 결합 끊기
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Long id = memberService.join(request.toEntity());
        return new CreateMemberResponse(id);
    }

    // 명령 쿼리 분리 스타일
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id, @RequestBody @Valid UpdateMemberRequest request) {
        // 유지보수성 증가. 트래픽 크지 않다면 이런 스타일을 사용할 수 있다.
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    // bad case - 엔티티는 API 스펙과 결합되면 안된다
    // 컬렉션을 직접 반환하면 항후 API 스펙에 데이터를 추가하기 어렵다. 별도의 Result DTO를 반환해야한다.
    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    // good case - DTO로 엔티티와 API 스펙 결합 끊기
    @GetMapping("/api/v2/members")
    public Result membersV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }


    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    static class CreateMemberRequest {
        private String name;

        public CreateMemberRequest(String name) {
            this.name = name;
        }

        public Member toEntity() {
            Member member = new Member();
            member.setName(name);
            return member;
        }
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    static class UpdateMemberRequest {
        private String name;

        public UpdateMemberRequest(String name) {
            this.name = name;
        }

        // 이렇게 만든 member 필드에는 대부분의 값이 null!
        // 이때 JPA 병합하면 값이 다 날라간다..
        // 변경 감지로 값이 변경되도록 해야함
//        public Member toEntity() {
//            Member member = new Member();
//            member.setName(name);
//            return member;
//        }
    }

}
