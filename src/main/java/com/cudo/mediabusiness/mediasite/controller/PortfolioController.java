package com.cudo.mediabusiness.mediasite.controller;

import com.cudo.mediabusiness.mediasite.domain.Portfolio;
import com.cudo.mediabusiness.mediasite.dto.FileDto;
import com.cudo.mediabusiness.mediasite.dto.PortfolioDto;
import com.cudo.mediabusiness.mediasite.repository.PortfolioRepository;
import com.cudo.mediabusiness.mediasite.service.FileService;
import com.cudo.mediabusiness.mediasite.service.PortfolioService;
import com.cudo.mediabusiness.mediasite.util.FileSave;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final FileService fileService;

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
    public String write(HttpServletRequest request, @RequestPart("img_01") MultipartFile files,
                        @RequestPart("img_03") MultipartFile mfiles,
                        @Valid PortfolioDto portfolioDto, BindingResult result) {
        portfolioDto.getFlatform();
        portfolioDto.getOtherForm();
        portfolioDto.getFile();

        ModelAndView mav = new ModelAndView();
        Long portfolioId = portfolioService.savePost(portfolioDto);
        /*mav.addObject("flatformsselected", flatform.getFlatformlist());*/
        //파일 변환 및 저장
        if (portfolioId != null) {
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
                portfolioService.savePost(portfolioId, send_id);
            }
        }
        return "redirect:/admin/portfolio_write";
    }

/*    @GetMapping("/admin/portfolio/post")
    public String writePost{

    }*/

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


    /*@RequestMapping(value = "/test/modelMap/")
    public String getMultiRowToModel(@ModelAttribute String string) {

    }*/
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
    @GetMapping("/portfolio")
    public String boardView(@PageableDefault Pageable pageable, Model model){
        Page<Portfolio> portfolioList = portfolioService.getPortfolioList(pageable);
        model.addAttribute("portfolioList", portfolioList);
        return "portfolio";
    }

}
