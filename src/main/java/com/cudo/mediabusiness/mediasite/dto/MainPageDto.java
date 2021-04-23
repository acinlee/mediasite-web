package com.cudo.mediabusiness.mediasite.dto;

import com.cudo.mediabusiness.mediasite.domain.File;
import com.cudo.mediabusiness.mediasite.domain.MainPage;
import com.cudo.mediabusiness.mediasite.domain.enumpackage.Exposure;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class MainPageDto {
    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String flatForm;
    @NotEmpty
    private String ordering_company;
    @NotEmpty
    private String launching_date;
    private String writer;
    private MultipartFile multipartFile;
    private File file;
    private LocalDateTime register_date;
    private Exposure exposure_check;
    private int exposure_sequence;

    public MainPage toEntity(){
        return MainPage.builder()
                .title(title)
                .flatForm(flatForm)
                .ordering_company(ordering_company)
                .launching_date(launching_date)
                .writer(writer)
                .exposure_sequence(exposure_sequence)
                .build();
        //.id(id)
    }

    @Builder
    public MainPageDto(Long id, String title, String flatForm, String ordering_company
            , String launching_date, String writer, File file, MultipartFile multipartFile
            , LocalDateTime register_date, Exposure exposure_check, int exposure_sequence){
        this.id = id;
        this.title = title;
        this.flatForm = flatForm;
        this.ordering_company = ordering_company;
        this.launching_date = launching_date;
        this.register_date = register_date;
        this.writer = writer;
        this.file = file;
        this.multipartFile = multipartFile;
        this.exposure_check = exposure_check;
        this.exposure_sequence = exposure_sequence;
    }
}
