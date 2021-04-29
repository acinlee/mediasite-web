package com.cudo.mediabusiness.mediasite.domain;

import com.cudo.mediabusiness.mediasite.domain.enumpackage.Exposure;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Portfolio {

    @Id @GeneratedValue
    @Column(name = "portfolio_id")
    //id 값 db에서 자동 생성
    private Long id;

    //작성자
    private String writer;

    //제목
    @Column(length = 40, nullable = false)
    private String title;

    //내용
    @Column(length = 200, nullable = false)
    private String content;

    //플랫폼 종류
    private String flatform;

    //플랫폼 종류 기타
    @Column(length = 20)
    private String otherForm;

    //발주사
    @Column(length = 40)
    private String orderingCompany;

    //런칭 날짜
    private String launchingDate;

    //등록 날짜
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    //메인 페이지에 노출 여부
    @Enumerated(EnumType.STRING)
    private Exposure show_check = Exposure.TRUE;

    private int show_sequence = 0;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id")
    private List<File> file = new ArrayList<>();


    @Builder
    public Portfolio(Long id, String writer, String title, String content, String otherForm, String flatform, String orderingCompany,
                     String launchingDate, LocalDateTime createdDate, List<File> file, Exposure show_check ,int show_sequence){
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.otherForm = otherForm;
        this.flatform = flatform;
        this.orderingCompany = orderingCompany;
        this.launchingDate = launchingDate;
        this.createdDate = createdDate;
        this.file = file;
        this.show_sequence = show_sequence;
        this.show_check = show_check;
    }

    public void uploadFile(File file) {
        this.file = getFile();
    }

    protected Portfolio() { }
}
