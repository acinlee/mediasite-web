package com.cudo.mediabusiness.mediasite.dto;

import com.cudo.mediabusiness.mediasite.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
@NoArgsConstructor
public class MemberDto {
    @NotEmpty(message = "회원 아이디는 필수 입니다.")
    private String id;

    @NotEmpty(message = "회원 비밀번호는 필수 입니다.")
    private String password;

    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String name;

    private String groups_name;

    private String number;

    @NotEmpty(message = "회원 이메일은 필수 입니다.")
    private String email;

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .password(password)
                .name(name)
                .email(email)
                .groups_name(groups_name)
                .number(number)
                .build();
    }

    @Builder
    public MemberDto(String id, String password) {
        this.id = id;
        this.password = password;
    }

}
