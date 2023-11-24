package gyeongsu.todo.service;

import gyeongsu.todo.domain.Member;
import gyeongsu.todo.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired MemberService memberService;
    @Test
    public void 회원가입() throws Exception {
        //Given
        Member member = new Member();
        member.setName("gyeongsu");

        //When
        Long saveId = memberService.join(member);

        //Then
        Assertions.assertEquals(member, memberService.findOne(saveId));
    }

    @Test
    public void 회원목록조회() throws Exception {
        //Given
        Member member1 = new Member();
        member1.setName("gyeongsu1");
        Member member2 = new Member();
        member2.setName("gyeongsu");
        Member member3 = new Member();
        member3.setName("gyeongsu");
        memberService.join(member1);
        memberService.join(member2);
        memberService.join(member3);

        //When
        List<Member> memberList = memberService.findMembers();

        //Then
        Assertions.assertEquals(3, memberList.size());
        Assertions.assertEquals(true, memberList.contains(member1));
        Assertions.assertEquals(true, memberList.contains(member1));
        Assertions.assertEquals(true, memberList.contains(member1));
    }
}
