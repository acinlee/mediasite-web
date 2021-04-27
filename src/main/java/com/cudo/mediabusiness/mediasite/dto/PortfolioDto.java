package com.cudo.mediabusiness.mediasite.dto;

import com.cudo.mediabusiness.mediasite.controller.PortfolioController;
import com.cudo.mediabusiness.mediasite.domain.File;
import com.cudo.mediabusiness.mediasite.domain.Portfolio;
import com.cudo.mediabusiness.mediasite.domain.enumpackage.Exposure;
import com.cudo.mediabusiness.mediasite.service.PortfolioService;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
public class PortfolioDto {
    private Long id;
  /*  private String writer;*/

    @NotEmpty(message = "서비스명은 필수 입니다.")
    private String title;

    @NotEmpty(message = "서비스 설명은 필수 입니다.")
    private String content;

    @NotEmpty(message = "플랫폼은 필수 입니다.")
    private List<String> flatform;

    private String otherForm;

    @NotEmpty(message = "발주사는 필수 입니다.")
    private String orderingCompany;

    @NotEmpty(message = "런칭일은 필수 입니다.")
    private String launchingDate;
/*    private LocalDate createdDate;*/
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
                .title(title)
                .content(content)
                .build();
        return build;
    }

    @Builder
    public PortfolioDto(Long id, String title, String content, List<String> flatform, String otherForm,
                        String orderingCompany, String launchingDate, Exposure show_check, int show_sequence, String noticeYn, String secretYn, File file) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.flatform.addAll(flatform);
        /*this.flatform = flatform;*/
        this.otherForm = otherForm;
        this.orderingCompany = orderingCompany;
        this.launchingDate = launchingDate;
        this.show_check = show_check;
        this.show_sequence = show_sequence;
    /*    this.noticeYn = noticeYn;
        this.secretYn = secretYn;*/
        this.file = file;
    }

    public void setShow_sequence(int show_sequence) {
        this.show_sequence = show_sequence;
        if(show_sequence == 0){
            this.show_check = Exposure.FALSE;
        }else{
            this.show_check = Exposure.TRUE;
        }
    }
}
