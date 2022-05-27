package com.ngocnb20.travel.model.dto.resp;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelRespDto {


    private Long id;
    private String image;
    private int numberView;
    private int rate;
    private int numberLike;
    private String address;
    private String type;
    private String email;
    private String urlWeb;
    private String phone;
    private String detail;
    private double price;
    private String service;
    private String name;

    protected LocalDateTime createDate;
    protected LocalDateTime updateTime;
    protected Long createById;
    protected Long lastModifiedBy;
}
