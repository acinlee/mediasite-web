package com.cudo.mediabusiness.mediasite.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class File {

    @Id @GeneratedValue
    private Long id;

    //원본 파일명
    private String origin_file_name;
    private String filename;
    private String filepath;
    private Long filesize;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime register_date;

    //mapping relation
    //Main
    @OneToOne(mappedBy = "file", fetch = FetchType.LAZY)
    private MainPage mainPage;

    //introduction
    @OneToOne(mappedBy = "file", fetch = FetchType.LAZY)
    private Introduction introduction;

    //portfolio
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    //business_request
    @OneToOne(mappedBy = "file", fetch = FetchType.LAZY)
    private BusinessRequest businessRequest;

    @Builder
    public File (Long id, String origin_file_name, String filename, String filepath,
                 Long filesize, Portfolio portfolio){
        this.id = id;
        this.origin_file_name = origin_file_name;
        this.filename = filename;
        this.filepath = filepath;
        this.filesize = filesize;
        this.portfolio = portfolio;
    }

    protected File(){}

}
