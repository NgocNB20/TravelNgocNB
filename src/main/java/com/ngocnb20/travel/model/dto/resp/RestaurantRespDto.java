package com.ngocnb20.travel.model.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantRespDto {
    private Long id;
    private String name;
    private String address;
    private int numberLike;
    private int numberView;
    String phone;
    private String detail;
    private Set<ImageRestaurantDto> imageRestaurants;
    private Set<RestaurantFitDto> restaurantFits;
    private Set<RestaurantFoodDto> restaurantFoods;
    private Set<RestaurantTypeDto> restaurantTypes;

    protected LocalDateTime createDate;
    protected LocalDateTime updateTime;
    protected Long createById;
    protected Long lastModifiedBy;
}
