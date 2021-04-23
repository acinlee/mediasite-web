package com.cudo.mediabusiness.mediasite.controller;

import com.cudo.mediabusiness.mediasite.dto.FileDto;
import com.cudo.mediabusiness.mediasite.dto.IntroductionDto;
import com.cudo.mediabusiness.mediasite.service.FileService;
import com.cudo.mediabusiness.mediasite.service.IntroductionService;
import com.cudo.mediabusiness.mediasite.util.FileSave;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
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

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class IntroductionController {
    private final IntroductionService introductionService;
    private final FileService fileService;

    @GetMapping("/members/introduction/download")
    public String IntroductionDownload(Model model) {
        IntroductionDto introductionDto = introductionService.getRecentlyIntroduction();
        System.out.println(introductionDto.getId());
        model.addAttribute("introduceRecently", introductionDto);
        return "/members/introduceDownloadPage";
    }
    @GetMapping("/admin/introduction")
    public String IntroductionList(Model model) {
        List<IntroductionDto> introductionDtoList = introductionService.getAllIntroduction();
        model.addAttribute("introductionDtoList", introductionDtoList);
        model.addAttribute("introductionDto", new IntroductionDto());
        return "/admin/introduce";
    }

    @PostMapping("/admin/introduction/post")
    public String introductionPost(@RequestParam("file")MultipartFile files, @Valid IntroductionDto introductionDto, BindingResult result) {
        System.out.println("========================="+introductionDto.getContent()+"========================================");
        Long introDtoId = introductionService.saveIntroduction(introductionDto);
        if(introDtoId != null) {
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
                introductionService.updateFile(introDtoId, send_id);
            }
            return "redirect:/admin/introduction";
        } else {
            return "redirect:/admin/introduction";
        }
    }

    //파일 다운로드 기능
    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> fileDownload(HttpServletRequest request, @PathVariable("fileId") Long fileId) throws IOException {
        FileDto fileDto = fileService.getFile(fileId);
        Path path = Paths.get(fileDto.getFilepath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        String userAgent = request.getHeader("user-agent");
        String fileName = fileDto.getOrigin_file_name();

        //파일 확장자에 따라서 다운로드할지 바로 뷰로 볼지 여부 결정하기 위해서 확장자 받아옴
        String extension = FilenameUtils.getExtension(fileName);

        boolean ie = (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1);

        if(ie) {
            fileName = URLEncoder.encode( fileName, "utf-8" ).replaceAll("\\+", "%20");
        } else {
            fileName = new String( String.valueOf(fileName).getBytes("utf-8"), "iso-8859-1");
        }

        if(extension.equals("pdf")){
            //바로 보이게
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .contentType(MediaType.APPLICATION_PDF) //pdf 다운 가능
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                    .body(resource);
        }
        else{
            //다운로드 버전
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .contentType(MediaType.APPLICATION_PDF) //pdf 다운 가능
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        }
    }
}
