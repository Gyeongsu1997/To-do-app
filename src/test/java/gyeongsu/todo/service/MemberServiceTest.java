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

        //When
        Long saveId = memberService.join("gyeongsu");

        //Then
        assertEquals("gyeongsu", memberService.findOne(saveId).getName());
    }

    @Test
    void 회원목록조회() throws Exception {
        //Given
        Long saveId1 = memberService.join("gyeongsu1");
        Long saveId2 = memberService.join("gyeongsu2");
        Long saveId3 = memberService.join("gyeongsu3");

        Member member1 = memberService.findOne(saveId1);
        Member member2 = memberService.findOne(saveId2);
        Member member3 = memberService.findOne(saveId3);

        //When
        List<Member> memberList = memberService.findMembers();

        //Then
        assertEquals(3, memberList.size());
        assertTrue(memberList.contains(member1));
        assertTrue(memberList.contains(member2));
        assertTrue(memberList.contains(member3));
    }
}
