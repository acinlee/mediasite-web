package com.cudo.mediabusiness.mediasite.controller;
import com.cudo.mediabusiness.mediasite.domain.Member;
import com.cudo.mediabusiness.mediasite.dto.MemberDto;
import com.cudo.mediabusiness.mediasite.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberDto memberDto, BindingResult result) { //@Valid를 하면 @NotEmpty 등을 자동으로 작업해줌
        if(result.hasErrors()) {
            return "members/createMemberForm";
        }
        memberService.join(memberDto);

        return "redirect:/login";
    }

    @GetMapping("/")
    public String homeView() {
        return "members/login";
    }

    @GetMapping("/login")
    public String loginView(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "exception", required = false) String exception,
                            Model model) {
        model.addAttribute("error",error);
        model.addAttribute("exception",exception);
        return "members/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminView() {
        return "admin/admin";
    }

    //member list
    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findAllMember();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
