package com.cudo.mediabusiness.mediasite.domain;

import com.cudo.mediabusiness.mediasite.domain.enumpackage.Exposure;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.management.relation.Role;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
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
    private LocalDate createdDate;

    //메인 페이지에 노출 여부
    @Enumerated(EnumType.STRING)
    private Exposure show_check = Exposure.TRUE;

    private int show_sequence = 0;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id")
    private File file;

    /*@OneToMany(mappedBy = "portfolio")
    private List<File> file = new ArrayList<>();*/

    @Builder
    public Portfolio(Long id, String writer, String title, String content, String otherForm, String flatform,
                     String orderingCompany, String launchingDate, LocalDate createdDate, File file, int show_sequence){
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
    }

   /* public class Post {
        private String title;
        private String content;
        private Set<String> flatform;
        // constructor and getter
    }*/

   /* public class Flatform {


        @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
        @JoinTable(
                name = "flaforms_roles",
                joinColumns = @JoinColumn(name = "flatform_id"),
                inverseJoinColumns = @JoinColumn(name = "flat_id")
        )
        private Set<Flatform> flatforms = new HashSet<>();

    }
*/
    protected Portfolio() {

    }
}
