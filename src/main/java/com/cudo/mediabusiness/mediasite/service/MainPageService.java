package com.cudo.mediabusiness.mediasite.service;

import com.cudo.mediabusiness.mediasite.domain.MainPage;
import com.cudo.mediabusiness.mediasite.domain.enumpackage.Exposure;
import com.cudo.mediabusiness.mediasite.dto.MainPageDto;
import com.cudo.mediabusiness.mediasite.repository.FileRepository;
import com.cudo.mediabusiness.mediasite.repository.MainPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MainPageService {
    private final MainPageRepository mainPageRepository;
    private final FileRepository fileRepository;

    //글 등록
    @Transactional
    public Long saveMain(MainPageDto mainPageDto) {
        //인증된 사용자 이름을 dto에 집어 넣음
        mainPageDto.setWriter(getSessionUser());

        //만약 노출중인 페이지가 하나도 없으면 순서에 1을 집어 넣음
        if(mainPageRepository.findAllExposure().isEmpty()) {
            mainPageDto.setExposure_sequence(1);
        }
        else {
            int sequence = mainPageRepository.findAllExposure().size();
            mainPageDto.setExposure_sequence(sequence + 1);
        }
        return mainPageRepository.save(mainPageDto.toEntity());
    }

    //파일 객체 생성 후 파일과 연결 하기 위한 메인 수정
    @Transactional
    public void updateMain(Long mainDtoId, Long fileId) {
        MainPage mainPage = mainPageRepository.findById(mainDtoId);
        mainPage.update(fileRepository.findFile(fileId));
    }

    //메인 수정
    @Transactional
    public void editMain(MainPageDto mainPageDto){
        MainPage mainPage = mainPageRepository.findById(mainPageDto.getId());
        if(mainPage.getExposure_check() == Exposure.TRUE){
            mainPage.editPost(mainPageDto.getTitle(), mainPageDto.getFlatForm(),
                    mainPageDto.getOrdering_company(), mainPageDto.getLaunching_date(), mainPageDto.getExposure_sequence());
        }
        else{
            mainPage.editPost(mainPageDto.getTitle(), mainPageDto.getFlatForm(),
                    mainPageDto.getOrdering_company(), mainPageDto.getLaunching_date(), 0);
        }
    }

    //노출 여부 변경
    @Transactional
    public void changeExposure(Long id){
        MainPage mainPage = mainPageRepository.findById(id);
        List<MainPage> mainPageList = mainPageRepository.findAllExposure();

        //변경하는 객체의 순서 기억
        int sequence = mainPage.getExposure_sequence();

        //노출 -> 미노출
        if(mainPage.getExposure_check() == Exposure.TRUE){
            //해당 순서가 노출 순서의 마지막인지 확인
            if(mainPageList.size() != sequence){
                for(MainPage mainObject : mainPageList){
                    if(mainObject.getExposure_sequence() > sequence){
                        mainObject.changeSequence(mainObject.getExposure_sequence() - 1);
                    }
                }
            }

            mainPage.changeExposure(Exposure.FALSE, 0);
        }
        //미노출 -> 노출
        else{
            mainPage.changeExposure(Exposure.TRUE, mainPageList.size()+1);
        }
    }
    //모든 메인페이지 리스트
    public List<MainPageDto> getMainPageList() {
        List<MainPage> mainPageList = mainPageRepository.findAllMain();
        List<MainPageDto> mainPageDtoList = new ArrayList<>();
        return getList(mainPageDtoList, mainPageList);
    }

    //노출 리스트
    public List<MainPageDto> getMainPageListExposureTrue() {
        List<MainPage> mainPageList = mainPageRepository.findAllExposure();
        List<MainPageDto> mainPageDtoList = new ArrayList<>();
        return getList(mainPageDtoList, mainPageList);
    }

    //미노출 리스트
    public List<MainPageDto> getMainPageListExposureFalse() {
        List<MainPage> mainPageList = mainPageRepository.findAllNotExposure();
        List<MainPageDto> mainPageDtoList = new ArrayList<>();
        return getList(mainPageDtoList, mainPageList);
    }

    //리스트 뿌리는 클래스
    public List<MainPageDto> getList(List<MainPageDto> mainPageDtoArrayList, List<MainPage> mainPageList){
        for(MainPage addList : mainPageList) {
            MainPageDto mainPageDto = MainPageDto.builder()
                    .id(addList.getId())
                    .title(addList.getTitle())
                    .flatForm(addList.getFlatForm())
                    .ordering_company(addList.getOrdering_company())
                    .launching_date(addList.getLaunching_date())
                    .register_date(addList.getRegister_date())
                    .writer(addList.getWriter())
                    .file(addList.getFile())
                    .exposure_check(addList.getExposure_check())
                    .exposure_sequence(addList.getExposure_sequence())
                    .build();
            mainPageDtoArrayList.add(mainPageDto);
        }
        return mainPageDtoArrayList;
    }

    //현재 인증된 사용자 가져오기
    public String getSessionUser(){
        //현재 인증된 사용자 가져오는 코드
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();

        return username;
    }
}
