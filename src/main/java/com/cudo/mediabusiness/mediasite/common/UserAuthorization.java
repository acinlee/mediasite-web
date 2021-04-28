package com.cudo.mediabusiness.mediasite.common;

import com.cudo.mediabusiness.mediasite.dto.MemberDto;
import com.cudo.mediabusiness.mediasite.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
public class UserAuthorization {
    private final MemberService memberService;

    public UserAuthorization(MemberService memberService) {
        this.memberService = memberService;
    }

    //현재 인증된 사용자 가져오기
    public String getSessionUser(){
        //현재 인증된 사용자 가져오는 코드
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        MemberDto memberDto = memberService.findByIdMemberDto(username);

        return memberDto.getName();
    }
}
