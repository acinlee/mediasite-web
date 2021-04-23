package com.cudo.mediabusiness.mediasite.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        setDefaultTargetUrl("/admin/mainpage");
        SavedRequest savedRequest = requestCache.getRequest(request,response);
        if(savedRequest != null){
            // 인증 받기 전 url로 이동하기
            String targetUrl = savedRequest.getRedirectUrl();
            redirectStrategy.sendRedirect(request,response,targetUrl);
        }else{
            // 기본 url로 가도록 함함
            redirectStrategy.sendRedirect(request,response,getDefaultTargetUrl());
        }
    }
}
