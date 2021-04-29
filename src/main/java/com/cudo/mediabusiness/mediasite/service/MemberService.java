package com.cudo.mediabusiness.mediasite.service;

import com.cudo.mediabusiness.mediasite.config.SecurityMemberRepository;
import com.cudo.mediabusiness.mediasite.domain.Member;
import com.cudo.mediabusiness.mediasite.dto.MemberDto;
import com.cudo.mediabusiness.mediasite.exception.validateException;
import com.cudo.mediabusiness.mediasite.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService{

    //유저 DB 활용 객체
    private final MemberRepository memberRepository;
    private final SecurityMemberRepository securityMemberRepository;

    //회원 가입
    @Transactional
    public void join(MemberDto memberDto){
        validateDuplicateUserId(memberDto.toEntity());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        memberRepository.save(memberDto.toEntity());
    }

    //ID 중복 검사
    public void validateDuplicateUserId(Member member) {
        Optional<Member> check_member = Optional.ofNullable(memberRepository.findMemberById(member.getId()));
        if(check_member.isPresent()) {
            throw new validateException("이미 존재하는 Id입니다.");
        }
    }

    //전체 계정 조회
    public List<Member> findAllMember(){
        List<Member> findAllMember  = memberRepository.findAllMember();
        if(!findAllMember.isEmpty()) {
            throw new IllegalStateException("회원이 존재하지 않습니다.");
        }
        return findAllMember;
    }

    //특정 유저 조회
    public MemberDto findByIdMemberDto(String id){
        Member member = memberRepository.findMemberById(id);
        MemberDto memberDto = MemberDto.builder()
                .id(member.getId())
                .password(member.getPassword())
                .name(member.getName())
                .build();
        return memberDto;
    }
    /*
    * admin
    * 1. 회원 가입 승인
    * 2. 유저 사용여부 변경(중지, 활성화)
    * 3. 유저 비밀번호 초기화
    */

    //로그인 유저 정보 불러오기
}
