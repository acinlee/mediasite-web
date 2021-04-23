package com.cudo.mediabusiness.mediasite.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class CustomFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String msg = "Invaild Username or Password";

        if(exception instanceof BadCredentialsException){

        }else if(exception instanceof InsufficientAuthenticationException){
            msg = "Invalid Secret Key";
        }

        setDefaultFailureUrl("/login?error=true&exception="+msg);

        super.onAuthenticationFailure(request,response,exception);
    }
}
