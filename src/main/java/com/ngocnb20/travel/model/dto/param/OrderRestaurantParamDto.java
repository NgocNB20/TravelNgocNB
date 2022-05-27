package com.ngocnb20.travel.model.dto.param;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderRestaurantParamDto {
    private Long id;
    private String nameCustomer;
    private String email;
    private String phone;
    private String note;
    private String nameRestaurant;
}
