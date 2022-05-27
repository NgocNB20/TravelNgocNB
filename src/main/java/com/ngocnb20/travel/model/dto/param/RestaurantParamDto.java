package com.ngocnb20.travel.model.dto.param;

import com.ngocnb20.travel.model.entity.ImageRestaurant;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RestaurantParamDto implements Serializable {
    private int numberView;
    private int numberComment;
    private int numberLike;
    private String name;
    private String address;
    private String phone;
    private String detail;
    private MultipartFile[] files;
    private Set<ImageRestaurant> imageRestaurants = new HashSet<>();
}
