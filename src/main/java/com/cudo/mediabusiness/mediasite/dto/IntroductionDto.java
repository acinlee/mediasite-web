package com.cudo.mediabusiness.mediasite.dto;

import com.cudo.mediabusiness.mediasite.domain.File;
import com.cudo.mediabusiness.mediasite.domain.Introduction;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class IntroductionDto {
    private Long id;
    private LocalDateTime register_date;
    private String writer;
    private String content;
    private File file;

    public Introduction toEntity() {
        return Introduction.builder()
                .writer(writer)
                .content(content)
                .file(file)
                .build();
    }

    @Builder
    public IntroductionDto(Long id, LocalDateTime register_date, String writer,
                        String content, File file) {
        this.id = id;
        this.register_date = register_date;
        this.writer = writer;
        this.content = content;
        this.file = file;
    }
}
