package com.ngocnb20.travel.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberDto {
    private Long id;
    private String email;
    private String name;
    private String password;
    private String resetPassword;

}
