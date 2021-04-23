package com.cudo.mediabusiness.mediasite.domain;

import com.cudo.mediabusiness.mediasite.domain.enumpackage.Exposure;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class MainPage {

    @Id @GeneratedValue
    @Column(name = "mainpage_id")
    private Long id;

    private String title;

    private String flatForm;

    private String ordering_company;

    private String launching_date;

    private String writer;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id")
    private File file;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime register_date;

    @Enumerated(EnumType.STRING)
    private Exposure exposure_check = Exposure.TRUE;

    private int exposure_sequence = 0;

    @Builder
    public MainPage(Long id, String title, String flatForm, String ordering_company,
                    String launching_date, String writer,
                    File file, int exposure_sequence){
        this.id = id;
        this.title = title;
        this.flatForm = flatForm;
        this.ordering_company = ordering_company;
        this.launching_date = launching_date;
        this.writer = writer;
        this.file = file;
        this.exposure_sequence = exposure_sequence;
    }

    public void update(File file) {
        this.file = file;
    }

    //메인 편집
    public void editPost(String title, String flatForm, String ordering_company,
                         String launching_date, int exposure_sequence){
        this.title = title;
        this.flatForm = flatForm;
        this.ordering_company = ordering_company;
        this.launching_date = launching_date;
        this.exposure_sequence = exposure_sequence;
    }

    //노출 변경
    public void changeExposure(Exposure exposure_check, int exposure_sequence){
        this.exposure_check = exposure_check;
        this.exposure_sequence = exposure_sequence;
    }

    //노출 순서 변경
    public void changeSequence(int exposure_sequence){
        this.exposure_sequence = exposure_sequence;
    }

    protected MainPage(){}
}
