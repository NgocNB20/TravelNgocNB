package com.ngocnb20.travel.model.dto.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgencyParamDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String name;
    private String image;
    private String address;
    private String email;
    private String urlWeb;
    private String phone;
    private String detail;
    private int numberView;
    private int numberLike;
    private MultipartFile file;
}
