package com.ngocnb20.travel.model.dto.param;

import com.ngocnb20.travel.model.entity.ImageTour;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TourParamDto {
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
    private Set<ImageTour> imageTours=new HashSet<>();
    private MultipartFile[] files;

}
