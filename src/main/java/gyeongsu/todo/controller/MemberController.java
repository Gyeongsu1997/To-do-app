package gyeongsu.todo.controller;

import gyeongsu.todo.domain.Member;
import gyeongsu.todo.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members/new")
    public String create(@RequestParam("name") String name) {
        Member member = new Member(name);
        memberService.join(member);
        return "redirect:/";
    }
}