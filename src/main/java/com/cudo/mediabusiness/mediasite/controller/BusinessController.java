package com.cudo.mediabusiness.mediasite.controller;

import com.cudo.mediabusiness.mediasite.dto.BusinessRequestDto;
import com.cudo.mediabusiness.mediasite.dto.FileDto;
import com.cudo.mediabusiness.mediasite.service.BusinessRequestService;
import com.cudo.mediabusiness.mediasite.service.FileService;
import com.cudo.mediabusiness.mediasite.util.FileSave;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BusinessController {
    private final BusinessRequestService businessRequestService;
    private final FileService fileService;

    @GetMapping("/members/business")
    public String registerBusinessPage(Model model){
        model.addAttribute("business", new BusinessRequestDto());
        return "/members/businessRequestPage";
    }

    @PostMapping("/members/business/post")
    public String registerBusiness(@RequestParam("file") MultipartFile files, @Valid BusinessRequestDto businessRequestDto, BindingResult result){
        Long businessId = businessRequestService.registerBusinessRequest(businessRequestDto);
        if(businessId != null){
            FileSave fileSave = new FileSave();
            fileSave.fileInfo(files);

            FileDto fileDto = new FileDto();
            fileDto.setOrigin_file_name(fileSave.getOriginFilename());
            fileDto.setFilesize(fileSave.getFileSize());
            fileDto.setFilename(fileSave.getConversionFilename());
            fileDto.setFilepath(fileSave.getFilePath());

            Long send_id = fileService.saveFile(fileDto);
            if (send_id != null) {
                businessRequestService.updateBusiness(businessId, send_id);
            }
            return "redirect:/";
        } else {
            return "/members/businessRequestPage";
        }
    }

    @GetMapping("/admin/businessList")
    public String businessList(Model model){
        List<BusinessRequestDto> businessRequestDtoList = businessRequestService.findAllBusinessRequest();
        model.addAttribute("businessList", businessRequestDtoList);
        return "/admin/alliance";
    }

    //상세 조회
    @GetMapping("/admin/businessList/{id}")
    public String detailBusiness(@PathVariable("id") Long id, Model model) {
        BusinessRequestDto businessRequestDto = businessRequestService.getBusiness(id);
        model.addAttribute("business", businessRequestDto);
        return "/admin/alliance_detail";
    }

}
