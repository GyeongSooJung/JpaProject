package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() {
        //g
        Member member = new Member();
        member.setName("kim");

        //w
        Long savedId = memberService.join(member);

        //t
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    void 중복_회원_예외() throws Exception {

        Member member = new Member();
        member.setName("kim");

        Member member1 = new Member();
        member1.setName("kim");

        try {
            memberService.join(member);
            memberService.join(member1);
        } catch (IllegalStateException e) {
            return;
        }

        fail("예외가 발생해야 한다.");

    }
}