package com.cudo.mediabusiness.mediasite.dto;

import com.cudo.mediabusiness.mediasite.domain.File;
import com.cudo.mediabusiness.mediasite.domain.Portfolio;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class FileDto {

    private Long id;
    private String origin_file_name;
    private String filename;
    private String filepath;
    private Long filesize;
    private LocalDateTime register_date;
    private Portfolio portfolio;

    public File toEntity() {
        return File.builder()
                .id(id)
                .origin_file_name(origin_file_name)
                .filename(filename)
                .filepath(filepath)
                .filesize(filesize)
                .portfolio(portfolio)
                .build();
    }

    @Builder
    public FileDto (Long id, String origin_file_name, String filename, String filepath,
                    Long filesize, Portfolio portfolio){
        this.id = id;
        this.origin_file_name = origin_file_name;
        this.filename = filename;
        this.filepath = filepath;
        this.filesize = filesize;
        this.portfolio = portfolio;
    }
}
