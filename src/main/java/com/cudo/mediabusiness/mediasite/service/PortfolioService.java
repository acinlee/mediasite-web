package com.cudo.mediabusiness.mediasite.service;

import com.cudo.mediabusiness.mediasite.domain.File;
import com.cudo.mediabusiness.mediasite.domain.Portfolio;
import com.cudo.mediabusiness.mediasite.dto.PortfolioDto;
import com.cudo.mediabusiness.mediasite.repository.FileRepository;
import com.cudo.mediabusiness.mediasite.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final FileRepository fileRepository;

    /* 게시글 등록 */
    @Transactional
    public Long savePost(PortfolioDto portfolioDto) {
        return portfolioRepository.save(portfolioDto.toEntity());
    }

    /* 게시글 목록 구현 */
    @Transactional
    public List<PortfolioDto> getPortfolioList() {
        List<Portfolio> portfolioList = portfolioRepository.findAll();
        List<PortfolioDto> portfolioDtoList = new ArrayList<>();

        for(Portfolio port : portfolioList) {
            PortfolioDto portfolioDto = PortfolioDto.builder()
                    .id(port.getId())
                    .title(port.getTitle())
                    .show_check(port.getShow_check())
                    .show_sequence(port.getShow_sequence())
                    .file(port.getFile())
                    .build();
            portfolioDtoList.add(portfolioDto);
        }
        return portfolioDtoList;
    }

    /* 게시글 수정 */
    @Transactional
    public PortfolioDto getPost(Long id) {
        Portfolio portfolio = portfolioRepository.findById(id);
        PortfolioDto portfolioDto = PortfolioDto.builder()
                .id(portfolio.getId())
                .title(portfolio.getTitle())
                .content(portfolio.getContent())
                /*.flatform(portfolio.getFlatform())*/
                .otherForm(portfolio.getOtherForm())
                .orderingCompany(portfolio.getOrderingCompany())
                .launchingDate(portfolio.getLaunchingDate())
                .show_check(portfolio.getShow_check())
                .file(portfolio.getFile())
                .show_sequence(portfolio.getShow_sequence())
                .build();
        return portfolioDto;
    }

    //노출
    public List<PortfolioDto> getPortfolioListExposureTrue() {
        List<Portfolio> portfolioList = portfolioRepository.findAllExposure();
        List<PortfolioDto> portfolioDtoList = new ArrayList<>();
        return getPortfolioList();
    }

    //미노출
    public List<PortfolioDto> getPortfolioListExposureFalse() {
        List<Portfolio> portfolioList = portfolioRepository.findAllNotExposure();
        List<PortfolioDto> portfolioDtoList = new ArrayList<>();
        return getPortfolioList();
    }

/*    *//* 페이징 처리 *//*
    public Page<Portfolio> getPortfolioList(Pageable pageable){
        int page = (pageable.getNumberOfPages() == 0) ? 0 : (pageable.getNumberOfPages() - 1); // page는 index 처럼 0부터 시작
        pageable = (Pageable) PageRequest.of(page, 10);

        return portfolioRepository.findAll((org.springframework.data.domain.Pageable) pageable);
    }*/

    @Transactional
    public void updatePost(Long portfolioId, Long send_id) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId);
        portfolio.uploadFile(fileRepository.findFile(send_id));
    }
}



