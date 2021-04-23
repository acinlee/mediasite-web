package com.cudo.mediabusiness.mediasite.dto;

import com.cudo.mediabusiness.mediasite.controller.PortfolioController;
import com.cudo.mediabusiness.mediasite.domain.File;
import com.cudo.mediabusiness.mediasite.domain.Portfolio;
import com.cudo.mediabusiness.mediasite.domain.enumpackage.Exposure;
import com.cudo.mediabusiness.mediasite.service.PortfolioService;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class PortfolioDto {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private String flatform;
    private String otherForm;
    private String orderingCompany;
    private String launchingDate;
    private LocalDate createdDate;
    private Exposure show_check;
    private int show_sequence;
   /* private String noticeYn;
    private String secretYn;*/
    private File file;

    private List<PortfolioDto> portfolioDtoList;

    public class Flatform {
        private List<String> flatformlist;

        public List<String> getFlatformlist() {
            return flatformlist;
        }

        public void setFlatformlist(List<String> flatformList) {
            this.flatformlist = flatformList;
        }
    }

    public Portfolio toEntity() {
            Portfolio build = Portfolio.builder()
                    .id(id)
                    .writer(writer)
                    .title(title)
                    .content(content)
                    .build();
            return build;
    }

    @Builder
    public PortfolioDto(Long id, String writer, String title, String content, String flatform,
                        String otherForm, String orderingCompany, String launchingDate, LocalDate createdDate,
                        Exposure show_check, int show_sequence, String noticeYn, String secretYn, File file) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        /*this.flatform.addAll(flatform);*/
        this.flatform = flatform;
        this.otherForm = otherForm;
        this.orderingCompany = orderingCompany;
        this.launchingDate = launchingDate;
        this.createdDate = createdDate;
        this.show_check = show_check;
        this.show_sequence = show_sequence;
    /*    this.noticeYn = noticeYn;
        this.secretYn = secretYn;*/
        this.file = file;
    }

}
