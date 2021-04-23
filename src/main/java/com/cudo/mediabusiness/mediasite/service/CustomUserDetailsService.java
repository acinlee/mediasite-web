package com.cudo.mediabusiness.mediasite.service;

import com.cudo.mediabusiness.mediasite.domain.Member;
import com.cudo.mediabusiness.mediasite.domain.enumpackage.UserAuthority;
import com.cudo.mediabusiness.mediasite.exception.validateException;
import com.cudo.mediabusiness.mediasite.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Optional<Member> check_member = Optional.ofNullable(memberRepository.findMemberById(id));
        if(check_member.isEmpty()) {
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        if(check_member.get().getAuthority().equals("ADMIN")){
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }
        else if(check_member.get().getAuthority().equals("SUPER_ADMIN")){
            authorities.add(new SimpleGrantedAuthority("SUPER_ADMIN"));
        }
        return new User(check_member.get().getId(), check_member.get().getPassword(), authorities);
    }
}
