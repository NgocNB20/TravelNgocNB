package com.ngocnb20.travel.model.dto.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberParamDto {
    private Long id;
    private String email;
    private String name;
    private String password;
    private String resetPassword;
    private List<Long> idRole;


    public MemberParamDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public MemberParamDto(String email, String name, String password, String resetPassword) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.resetPassword = resetPassword;
    }

    public MemberParamDto(Long id, String email, String name, String password, String resetPassword) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.resetPassword = resetPassword;
    }
}
