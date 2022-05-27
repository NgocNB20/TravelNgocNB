package com.ngocnb20.travel.model.dto.param;

import com.ngocnb20.travel.model.entity.CategoryPlace;
import com.ngocnb20.travel.model.entity.ImagePlace;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HotelPramDto {
    private int numberView;
    private int numberLike;
    private String name;
    private String address;
    private double price;
    private String detail;
    private MultipartFile file;
    private int rate;
    private String email;
    private String urlWeb;
    private String phone;
    private String service;



}
