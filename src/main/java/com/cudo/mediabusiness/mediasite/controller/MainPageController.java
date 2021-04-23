package com.cudo.mediabusiness.mediasite.controller;

import com.cudo.mediabusiness.mediasite.dto.FileDto;
import com.cudo.mediabusiness.mediasite.dto.MainPageDto;
import com.cudo.mediabusiness.mediasite.dto.MainPageListDto;
import com.cudo.mediabusiness.mediasite.service.FileService;
import com.cudo.mediabusiness.mediasite.service.MainPageService;
import com.cudo.mediabusiness.mediasite.util.FileSave;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainPageController {
    private final MainPageService mainPageService;
    private final FileService fileService;

    //노출 페이지
    @GetMapping("/admin/mainpage")
    public String ExposureList(Model model) {
        //노출, 미노출 리스트
        List<MainPageDto> mainPageDtoListTrue = mainPageService.getMainPageListExposureTrue();
        List<MainPageDto> mainPageDtoListFalse = mainPageService.getMainPageListExposureFalse();
        model.addAttribute("mainPageDtoListTrue", mainPageDtoListTrue);
        model.addAttribute("mainPageDtoListFalse", mainPageDtoListFalse);
        model.addAttribute("userName", mainPageService.getSessionUser());
        model.addAttribute("mainPageDto", new MainPageDto());
        model.addAttribute("MainPageListDto", new MainPageListDto());
        return "/admin/main_set";
    }

    //메인 페이지 신규 등록
    @PostMapping("/admin/mainpage/post")
    public String mainPost(@RequestParam("file") MultipartFile files, @Valid MainPageDto mainPageDto, BindingResult result) {
        Long mainDtoId = mainPageService.saveMain(mainPageDto);
        //파일 변환 및 저장
        if (mainDtoId != null) {
            //파일 저장 클래스
            FileSave fileSave = new FileSave();
            fileSave.fileInfo(files);

            //dto에 파일 저장
            FileDto fileDto = new FileDto();
            fileDto.setOrigin_file_name(fileSave.getOriginFilename());
            fileDto.setFilesize(fileSave.getFileSize());
            fileDto.setFilename(fileSave.getConversionFilename());
            fileDto.setFilepath(fileSave.getFilePath());

            //파일 id 값
            Long send_id = fileService.saveFile(fileDto);
            if (send_id != null) {
                mainPageService.updateMain(mainDtoId, send_id);
            }
        }
        return "redirect:/admin/mainpage";
    }
    //이렇게 하면 동적으로 파일 + 객체를 묶어서 가지고 올 수 있음
    @PostMapping("/admin/mainpage/post_edit")
    public String updatePost(MainPageListDto mainPageListDto) {
        for(MainPageDto mainPageDto : mainPageListDto.getMain()){
            mainPageService.editMain(mainPageDto);
            if (!mainPageDto.getMultipartFile().isEmpty()) {
                FileSave fileSave = new FileSave();
                fileSave.fileInfo(mainPageDto.getMultipartFile());

                //dto에 파일 저장
                FileDto fileDto = new FileDto();
                fileDto.setOrigin_file_name(fileSave.getOriginFilename());
                fileDto.setFilesize(fileSave.getFileSize());
                fileDto.setFilename(fileSave.getConversionFilename());
                fileDto.setFilepath(fileSave.getFilePath());

                //파일 id 값
                Long send_id = fileService.saveFile(fileDto);
                if (send_id != null) {
                    mainPageService.updateMain(mainPageDto.getId(), send_id);
                }
            }
        }
        return "redirect:/admin/mainpage";
    }


    //노출 상태 변경 노출 -> 미노출, 미노출 -> 노출
    @PostMapping("/change/mainExposure")
    public String mainChangeExposure(@RequestParam("mainDtoId") Long id){
        mainPageService.changeExposure(id);
        return "redirect:/admin/mainpage";
    }
}
