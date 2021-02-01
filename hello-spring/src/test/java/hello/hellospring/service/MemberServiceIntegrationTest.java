package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    // 필드 레벨에서 Autowired로 의존성 주입
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memoryMemberRepository;


    // 다음 테스트를 위해 DB에 있던 데이터를 지우는 작업인데, 이제 필요없으므로 지우기
    /*
    @AfterEach
    public void afterEach() {
        memoryMemberRepository.clearStore();
    }
     */

    @Test
    void join() {
        // given
        Member member = new Member();
        member.setName("spring");
        // when
        Long savedId = memberService.join(member);

        // then
        // 저장한게 repository에 있는게 맞아?
        Member findMember = memberService.findOne(savedId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());

    }

    @Test
    public void validateDuplicatedCheck() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

//        try {
//            memberService.join(member2);
//            fail();
//        } catch (IllegalStateException e) {
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }

        // then
    }
}