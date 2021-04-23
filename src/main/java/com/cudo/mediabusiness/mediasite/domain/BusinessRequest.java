package com.cudo.mediabusiness.mediasite.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class BusinessRequest {

    @Id @GeneratedValue
    @Column(name = "businessrequest_id")
    private Long id;

    private String title;

    private String company_name;

    private String number;

    //담당자명
    private String manager;

    private String email;

    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime register_date;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id")
    private File file;

    @Builder
    public BusinessRequest(Long id, String title, String company_name,
                           String number, String manager, String email, String content) {
        this.id = id;
        this.title = title;
        this.company_name = company_name;
        this.number = number;
        this.manager = manager;
        this.email = email;
        this.content = content;
    }

    public void updateFile(File file) {
        this.file = file;
    }

    protected BusinessRequest() {}
}
