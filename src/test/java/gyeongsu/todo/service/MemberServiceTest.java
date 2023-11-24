package gyeongsu.todo.service;

import gyeongsu.todo.domain.Member;
import gyeongsu.todo.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired MemberService memberService;
    @Test
    void 회원가입() throws Exception {
        //Given
        Member member = new Member("gyeongsu");

        //When
        Long saveId = memberService.join(member);

        //Then
        assertEquals(member, memberService.findOne(saveId));
    }

    @Test
    void 회원목록조회() throws Exception {
        //Given
        Member member1 = new Member("gyeongsu1");
        Member member2 = new Member("gyeongsu2");
        Member member3 = new Member("gyeongsu3");
        memberService.join(member1);
        memberService.join(member2);
        memberService.join(member3);

        //When
        List<Member> memberList = memberService.findMembers();

        //Then
        assertEquals(3, memberList.size());
        assertTrue(memberList.contains(member1));
        assertTrue(memberList.contains(member2));
        assertTrue(memberList.contains(member3));
    }
}
