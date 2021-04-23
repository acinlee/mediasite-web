package com.cudo.mediabusiness.mediasite.domain;

import com.cudo.mediabusiness.mediasite.domain.enumpackage.UserAuthority;
import com.cudo.mediabusiness.mediasite.domain.enumpackage.UserPermission;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
public class Member {

    @Id
    @Column(name = "member_id")
    private String id;

    @NotEmpty
    private String password;

    @NotEmpty
    private String name;

    //소속명
    private String groups_name;

    private String number;

    private String email;

    //유저 권한
    @Enumerated(EnumType.STRING)
    private UserAuthority authority = UserAuthority.ADMIN;

    //가입 및 사용 승인 여부
    @Enumerated(EnumType.STRING)
    private UserPermission permission = UserPermission.WAIT;

    @Builder
    public Member(String id, String password, String name, String groups_name, String number, String email){
        this.id = id;
        this.password = password;
        this.name = name;
        this.groups_name = groups_name;
        this.number = number;
        this.email = email;
    }

    protected Member() { }
}
