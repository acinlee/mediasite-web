package com.cudo.mediabusiness.mediasite.service;

import com.cudo.mediabusiness.mediasite.domain.Introduction;
import com.cudo.mediabusiness.mediasite.domain.MainPage;
import com.cudo.mediabusiness.mediasite.dto.IntroductionDto;
import com.cudo.mediabusiness.mediasite.dto.MainPageDto;
import com.cudo.mediabusiness.mediasite.repository.FileRepository;
import com.cudo.mediabusiness.mediasite.repository.IntroductionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IntroductionService {
    private final IntroductionRepository introductionRepository;
    private final FileRepository fileRepository;

    //등록
    @Transactional
    public Long saveIntroduction(IntroductionDto introductionDto) {
        introductionDto.setWriter(getSessionUser());
        return introductionRepository.save(introductionDto.toEntity());
    }

    @Transactional
    public void updateFile(Long introductionDto, Long fileId) {
        Introduction introduction = introductionRepository.findById(introductionDto);
        introduction.update(fileRepository.findFile(fileId));
    }

    //전체 소개서
    public List<IntroductionDto> getAllIntroduction() {
        List<Introduction> introductionList = introductionRepository.findAllList();
        List<IntroductionDto> introductionDtoList = new ArrayList<>();
        for(Introduction introduction : introductionList){
            IntroductionDto introductionDto = IntroductionDto.builder()
                    .id(introduction.getId())
                    .content(introduction.getContent())
                    .file(introduction.getFile())
                    .register_date(introduction.getRegister_date())
                    .writer(introduction.getWriter())
                    .build();
            introductionDtoList.add(introductionDto);
        }
        return introductionDtoList;
    }

    //최신 소개서
    public IntroductionDto getRecentlyIntroduction() {
        Introduction introduction = introductionRepository.findRecentlyist();
        IntroductionDto introductionDto = IntroductionDto.builder()
                .id(introduction.getId())
                .content(introduction.getContent())
                .file(introduction.getFile())
                .register_date(introduction.getRegister_date())
                .writer(introduction.getWriter())
                .build();

        return introductionDto;
    }

    //현재 인증된 사용자 가져오기
    public String getSessionUser(){
        //현재 인증된 사용자 가져오는 코드
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();

        return username;
    }
}
