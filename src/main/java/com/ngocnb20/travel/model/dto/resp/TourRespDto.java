package com.ngocnb20.travel.model.dto.resp;

import com.ngocnb20.travel.model.entity.ImageTour;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TourRespDto {

    private Long id;

    private String title;
    private String image;
    private int numberView;
    private int numberLike;
    private int rate;    //đánh giá mấy sao
    private String detail;
    private String subDetail;
    private double price;
    private String totalDay;  //5 ngày 4 đêm
    private int totalPerson;
    private String departure;   //địa điểm khởi hành
    private Set<ImageTourDto> imageTours=new HashSet<>();

    protected LocalDateTime createDate;
    protected LocalDateTime updateTime;
    protected Long createById;
    protected Long lastModifiedBy;
    //@JsonManagedReference(value = "imageTours")

}
