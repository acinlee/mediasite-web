package com.cudo.mediabusiness.mediasite.dto;

import com.cudo.mediabusiness.mediasite.domain.BusinessRequest;
import com.cudo.mediabusiness.mediasite.domain.File;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class BusinessRequestDto {
    private Long id;
    @NotEmpty
    private String title;
    private String company_name;
    @NotEmpty
    private String number;
    @NotEmpty
    private String manager;
    @NotEmpty
    private String email;
    private String content;
    private LocalDateTime register_date;
    private File file;

    public BusinessRequest toEntity() {
        return BusinessRequest.builder()
                .title(title)
                .company_name(company_name)
                .number(number)
                .manager(manager)
                .email(email)
                .content(content)
                .build();
    }

    @Builder
    public BusinessRequestDto(Long id, String title, String company_name, String number
             ,String manager, String email, String content, LocalDateTime register_date, File file) {
        this.id = id;
        this.title = title;
        this.company_name = company_name;
        this.number = number;
        this.manager = manager;
        this.email = email;
        this.content = content;
        this.register_date= register_date;
        this.file = file;
    }
}
