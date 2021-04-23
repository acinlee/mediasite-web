package com.cudo.mediabusiness.mediasite.controller;

import com.cudo.mediabusiness.mediasite.domain.Portfolio;
import com.cudo.mediabusiness.mediasite.dto.FileDto;
import com.cudo.mediabusiness.mediasite.dto.PortfolioDto;
import com.cudo.mediabusiness.mediasite.repository.PortfolioRepository;
import com.cudo.mediabusiness.mediasite.service.FileService;
import com.cudo.mediabusiness.mediasite.service.PortfolioService;
import com.cudo.mediabusiness.mediasite.util.FileSave;
import lombok.RequiredArgsConstructor;
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
        return "/admin/portfolio";
    }

    @GetMapping("/admin/portfolio/post")
    public String post(Model model) {
        model.addAttribute("portfolioDto", new PortfolioDto());
        return "/admin/portfolio_write";
    }

    /* 글쓰기 버튼 누르면 /portfolio.write로 Post요청*/
   /*@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, )*/
    @PostMapping("/admin/portfolio/post")
    public String write(@RequestParam("file") MultipartFile files, @Valid PortfolioDto portfolioDto, BindingResult result) {
        Long portfolioId = portfolioService.savePost(portfolioDto);
        if(portfolioId != null){
            FileSave fileSave = new FileSave();
            fileSave.fileInfo(files);

            FileDto fileDto = new FileDto();
            fileDto.setOrigin_file_name(fileSave.getOriginFilename());
            fileDto.setFilesize(fileSave.getFileSize());
            fileDto.setFilename(fileSave.getConversionFilename());
            fileDto.setFilepath(fileSave.getFilePath());

            Long send_id = fileService.saveFile(fileDto);
            if (send_id != null) {
                portfolioService.savePost(portfolioId, send_id);
            }
            return "redirect:/";
        } else {
            return "portfolio_write";
        }
    }

/*    @GetMapping("/admin/portfolio/post")
    public String writePost{

    }*/

    //플랫폼
    @ModelAttribute("flatformlist")
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
        return "html/portfolio_modify";
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
