package com.cudo.mediabusiness.mediasite.dto;

import com.cudo.mediabusiness.mediasite.domain.File;
import com.cudo.mediabusiness.mediasite.domain.Portfolio;
import com.cudo.mediabusiness.mediasite.domain.enumpackage.Exposure;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter @Setter
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
    private LocalDateTime createdDate;
    private Exposure show_check;
    private int show_sequence;
    private List<File> file;
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
                .flatform(flatform)
                .otherForm(otherForm)
                .orderingCompany(orderingCompany)
                .launchingDate(launchingDate)

                .build();
        return build;
    }

    @Builder
    public PortfolioDto(Long id, String writer, String title, String content, String flatform,
                        String otherForm, String orderingCompany, String launchingDate, LocalDateTime createdDate,
                        Exposure show_check, int show_sequence, List<File> file) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.flatform = flatform;
        this.otherForm = otherForm;
        this.orderingCompany = orderingCompany;
        this.launchingDate = launchingDate;
        this.createdDate = createdDate;
        this.show_check = show_check;
        this.show_sequence = show_sequence;
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
