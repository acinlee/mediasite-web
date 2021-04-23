package com.cudo.mediabusiness.mediasite.service;

import com.cudo.mediabusiness.mediasite.domain.Portfolio;
import com.cudo.mediabusiness.mediasite.dto.PortfolioDto;
import com.cudo.mediabusiness.mediasite.repository.FileRepository;
import com.cudo.mediabusiness.mediasite.repository.PortfolioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;


@Service
public class PortfolioService {
    private PortfolioRepository portfolioRepository;
    private FileRepository fileRepository;

    public PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    /* 게시글 등록 */
    @Transactional
    public Long savePost(PortfolioDto portfolioDto) {
        Portfolio portfolio = portfolioRepository.save(portfolioDto.toEntity());
        Long id = portfolio.getId();
        return id;
    }

    /*@Transactional
    public void savePost(Long portfolioId, Long fileId){
        Portfolio portfolio = portfolioRepository.findById(portfolioId);
        portfolio.updateFile(portfolioRepository.findFile(fileId));
    }*/

    /* 게시글 목록 구현 */
    @Transactional
    public List<PortfolioDto> getPortfolioList() {
        List<Portfolio> portfolioList = portfolioRepository.findAll();
        List<PortfolioDto> portfolioDtoList = new ArrayList<>();

        for(Portfolio port : portfolioList) {
            PortfolioDto portfolioDto = PortfolioDto.builder()
                    .id(port.getId())
                    .title(port.getTitle())
                    .createdDate(port.getCreatedDate())
                    .writer(port.getWriter())
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
        Portfolio portfolio = portfolioRepository.findById(id).get();
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

    //플랫폼
    public class Flatform {
        private List<String> flatformlist;

        public List<String> getFlatformlist() {
            return flatformlist;
        }

        public void setFlatformlist(List<String> flatformList) {
            this.flatformlist = flatformList;
        }
    }


   /* 플랫폼 */
//    public List<String> getFlatform(String flat){
//        return portfolioRepository.getflatform(flat);
//    }

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

    /* 페이징 처리 */
    public Page<Portfolio> getPortfolioList(Pageable pageable){
        int page = (pageable.getNumberOfPages() == 0) ? 0 : (pageable.getNumberOfPages() - 1); // page는 index 처럼 0부터 시작
        pageable = (Pageable) PageRequest.of(page, 10);

        return portfolioRepository.findAll((org.springframework.data.domain.Pageable) pageable);
    }

    public void savePost(Long portfolioId, Long send_id) {
    }
    /*@Transactional
    public void showExposure(Long id){
        Portfolio portfolio = portfolioRepository.findById(id);
        List<Portfolio> portfolioList = portfolioRepository.findAllExposure();


        int sequence = portfolio.getShow_sequence();

        //미노출
        if(portfolio.getShow_check() == Exposure.TRUE){
            if(getPortfolioList.size() != sequence){
                for(Portfolio portObject : portFolioList){
                    if(portObject.getShow_sequence() > sequence){
                        portObject.getShow_sequence(portObject.getShow_sequence() - 1);
                    }
                }
            }

            portfolio.showExposure(Exposure.FALSE, 0);
        }
        //노출
        else{
            portfolio.showExposure(Exposure.TRUE, portfolioList.size()+1);
        }*/
    }



