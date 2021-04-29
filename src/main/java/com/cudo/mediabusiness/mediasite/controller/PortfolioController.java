package com.cudo.mediabusiness.mediasite.controller;

import com.cudo.mediabusiness.mediasite.common.UserAuthorization;
import com.cudo.mediabusiness.mediasite.dto.FileDto;
import com.cudo.mediabusiness.mediasite.dto.PortfolioDto;
import com.cudo.mediabusiness.mediasite.repository.FileRepository;
import com.cudo.mediabusiness.mediasite.service.FileService;
import com.cudo.mediabusiness.mediasite.service.PortfolioService;
import com.cudo.mediabusiness.mediasite.util.FileSave;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PortfolioController {
    private final PortfolioService portfolioService;
    private final FileService fileService;
    private final UserAuthorization userAuthorization;
    private final FileRepository fileRepository;

    /* 목록 */
    @GetMapping("/admin/portfolio/list")
    public String list(Model model) {
        List<PortfolioDto> portfolioDtoList = portfolioService.getPortfolioList();
        model.addAttribute("postList" , portfolioDtoList);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("index.html");
        mav.addObject("flatformobj", getflatformlist());
        mav.addObject("predefinedhobbylist", getflatformlist());

        return "/admin/portfolio";
    }

    @GetMapping("/admin/portfolio/post")
    public String post(Model model) {
        boolean portfoliolist = false;
        model.addAttribute("portfolioDto", new PortfolioDto());
        model.addAttribute("portfoliolist", getflatformlist());
        List<PortfolioDto> portfolioDtoListTrue = portfolioService.getPortfolioListExposureTrue();
        List<PortfolioDto> portfolioDtoListFalse = portfolioService.getPortfolioListExposureFalse();
        return "/admin/portfolio_write";
    }

    /* 글쓰기 버튼 누르면 /portfolio.write로 Post요청*/
    /*@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, )*/
    @PostMapping("/admin/portfolio/post")
    public String write(HttpServletRequest request, @RequestParam("img_01") MultipartFile pcFile,
                        @RequestParam("img_03") MultipartFile mFile,
                        @Valid PortfolioDto portfolioDto, BindingResult result) {
        portfolioDto.setFlatform(portfolioDto.getFlatform());
        portfolioDto.setWriter(userAuthorization.getSessionUser());
        Long portfolioId = portfolioService.savePost(portfolioDto);
        //파일 변환 및 저장
        if (portfolioId != null) {
            if(pcFile != null && mFile != null){
                //파일 저장 클래스
                //추후에 함수 따로 빼서 만들 예정
                FileSave fileSavePc = new FileSave();
                fileSavePc.fileInfo(pcFile);

                //dto에 파일 저장
                FileDto fileDtoPc = new FileDto();
                fileDtoPc.setOrigin_file_name(fileSavePc.getOriginFilename());
                fileDtoPc.setFilesize(fileSavePc.getFileSize());
                fileDtoPc.setFilename(fileSavePc.getConversionFilename());
                fileDtoPc.setFilepath(fileSavePc.getFilePath());

                //파일 id 값
                Long send_id = fileService.saveFile(fileDtoPc);
                if (send_id != null) {
                    portfolioService.updatePost(portfolioId, send_id);
                }

                FileSave fileSaveM = new FileSave();
                fileSaveM.fileInfo(mFile);

                //dto에 파일 저장
                FileDto fileDtoM = new FileDto();
                fileDtoM.setOrigin_file_name(fileSaveM.getOriginFilename());
                fileDtoM.setFilesize(fileSaveM.getFileSize());
                fileDtoM.setFilename(fileSaveM.getConversionFilename());
                fileDtoM.setFilepath(fileSaveM.getFilePath());

                //파일 id 값
                send_id = fileService.saveFile(fileDtoM);
                if (send_id != null) {
                    portfolioService.updatePost(portfolioId, send_id);
                }
            }
            else if(pcFile != null && mFile == null){
                FileSave fileSave = new FileSave();
                fileSave.fileInfo(pcFile);

                //dto에 파일 저장
                FileDto fileDto = new FileDto();
                fileDto.setOrigin_file_name(fileSave.getOriginFilename());
                fileDto.setFilesize(fileSave.getFileSize());
                fileDto.setFilename(fileSave.getConversionFilename());
                fileDto.setFilepath(fileSave.getFilePath());

                //파일 id 값
                Long send_id = fileService.saveFile(fileDto);
                if (send_id != null) {
                    portfolioService.updatePost(portfolioId, send_id);
                }
            }
            else if(pcFile != null && mFile == null){
                FileSave fileSave = new FileSave();
                fileSave.fileInfo(mFile);

                //dto에 파일 저장
                FileDto fileDto = new FileDto();
                fileDto.setOrigin_file_name(fileSave.getOriginFilename());
                fileDto.setFilesize(fileSave.getFileSize());
                fileDto.setFilename(fileSave.getConversionFilename());
                fileDto.setFilepath(fileSave.getFilePath());

                //파일 id 값
                Long send_id = fileService.saveFile(fileDto);
                if (send_id != null) {
                    portfolioService.updatePost(portfolioId, send_id);
                }
            }

        }
        return "redirect:/admin/portfolio_write";
    }

    //플랫폼
    public List<String> getflatformlist() {
        List<String> flatformlist = new ArrayList<String>();
        flatformlist.add("PC");
        flatformlist.add("mobile web");
        flatformlist.add("app");
        flatformlist.add("PC Client");
        flatformlist.add("glass");
        flatformlist.add("기타");

        return flatformlist;
    }

    //노출여부
    @GetMapping("/admin/portfolio/showcheck")
    public String showList(Model model) {
        //노출, 미노출
        List<PortfolioDto> portfolioDtoListTrue = portfolioService.getPortfolioListExposureTrue();
        List<PortfolioDto> portfolioDtoListFalse = portfolioService.getPortfolioListExposureFalse();
        model.addAttribute("portfolioDtoListTrue", portfolioDtoListTrue);
        model.addAttribute("portfolioDtoListFlase", portfolioDtoListFalse);
        model.addAttribute("portfolioDto", new PortfolioDto());
        return "/admin/portfolio";
    }

    /* 글 수정 */
    @GetMapping("/admin/portfolio_write/portfolio_modify/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        PortfolioDto portfolioDto = portfolioService.getPost(id);
        model.addAttribute("post", portfolioDto );
        return "admin/portfolio_modify";
    }

    @PutMapping("/admin/portfolio_write/portfolio_modify/{id}")
    public String update(PortfolioDto portfolioDto) {
        portfolioService.savePost(portfolioDto);
        return "redirect:/portfolio";
    }

    /* 페이징 처리 */
/*    @GetMapping("/portfolio")
    public String boardView(@PageableDefault Pageable pageable, Model model){
        Page<Portfolio> portfolioList = portfolioService.getPortfolioList(pageable);
        model.addAttribute("portfolioList", portfolioList);
        return "portfolio";
    }*/

}
