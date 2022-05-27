package com.ngocnb20.travel.model.dto.resp;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgencyRespDto {
    private Long id;
    private String image;
    private String address;
    private String name;
    //private String typeAgency;
    private String email;
    private String urlWeb;
    private String phone;
    private String detail;
    private int numberView;
    private int numberLike;

    protected LocalDateTime createDate;
    protected LocalDateTime updateTime;
    protected Long createById;
    protected Long lastModifiedBy;

    public AgencyRespDto(Long id, String image, String address, String email, String urlWeb, String phone, String detail, int numberView, int numberLike, LocalDateTime createDate, LocalDateTime updateTime, Long createById, Long lastModifiedBy) {
        this.id = id;
        this.image = image;
        this.address = address;
        this.email = email;
        this.urlWeb = urlWeb;
        this.phone = phone;
        this.detail = detail;
        this.numberView = numberView;
        this.numberLike = numberLike;
        this.createDate = createDate;
        this.updateTime = updateTime;
        this.createById = createById;
        this.lastModifiedBy = lastModifiedBy;
    }
}
