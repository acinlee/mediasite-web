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
public class Introduction {

    @Id @GeneratedValue
    @Column(name = "introduction_id")
    private Long id;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime register_date;

    private String writer;

    private String content;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id")
    private File file;

    @Builder
    public Introduction(Long id, String writer, String content, File file){
        this.id = id;
        this.writer = writer;
        this.content = content;
        this.file = file;
    }

    public void update(File file){
        this.file = file;
    }

    protected Introduction(){}
}
