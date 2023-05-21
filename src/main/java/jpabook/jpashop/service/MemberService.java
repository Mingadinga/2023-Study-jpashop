package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor // final 필드의 생성자만
@Transactional(readOnly = true) // 읽기용 트랜잭션. db 부하 덜함
@Service
public class MemberService {

    // @Autowired 단점 : 테스트하기 어렵고, 바꿔치기가 불가능
    // 수정자 주입 : mock 연결 가능, 하지만 중간에 변경될 수 있는 위험
    // 생성자 주입 : mock 가능, 생성 시점에만 초기화, 주입 까먹지 않으므로 npe 위험 덜함
    private final MemberRepository memberRepository;

    // 회원 가입
    @Transactional(readOnly = false)
    public Long join(Member member) {
        validateDuplicateMemberOrElseThrow(member);
        memberRepository.save(member);
        return member.getId();
    }

    // 멀티 스레드를 쓰면 validate를 통과할수도 있음
    // 최후의 방어 수단으로 member unique 제약조건을 두는 것이 좋다
    private void validateDuplicateMemberOrElseThrow(Member member) {
        memberRepository.findByName(member.getName()).stream().findFirst()
                .ifPresent((value) -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원 단건 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
