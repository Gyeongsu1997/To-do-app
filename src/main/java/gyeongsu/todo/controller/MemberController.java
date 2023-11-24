package gyeongsu.todo.controller;

import gyeongsu.todo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

//    @GetMapping("/member/join")
//    public String memberJoin() {
//
//    }

//    @PostMapping("/member/join")
//    public String memberJoin() {
//        memberService.join(member);
//    }
}
