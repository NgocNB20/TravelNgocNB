package com.ngocnb20.travel.service.spec;

import com.ngocnb20.travel.model.entity.OrderHotel;
import org.springframework.data.jpa.domain.Specification;

public class OrderHotelSpec {
    public static Specification<OrderHotel> searchByKeyword(String keyword){
        String searchLike="%"+keyword+"%";
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(criteriaBuilder.like(root.get("nameCustomer"),searchLike),
                criteriaBuilder.like(root.get("nameHotel"),searchLike)
        );
    }
}
