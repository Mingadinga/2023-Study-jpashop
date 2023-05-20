package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test @Transactional // em을 통한 모든 데이터 변경은 트랜잭션 안에서 이루어져야함, 자동으로 롤백
    @Rollback(value = false)
    public void testMember() throws Exception {
        // given
        Member member = new Member();
        member.setName("A");

        // when
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        // then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getName()).isEqualTo(member.getName());
        assertThat(findMember).isEqualTo(member); // 같은 영속성 컨텍스트 안에서는 id가 같으면 같은 엔티티로 인식. 1차 캐시에서 찾아옴.
    }

}