package com.ngocnb20.travel.service.spec;

import com.ngocnb20.travel.model.entity.Hotel;
import com.ngocnb20.travel.model.entity.Place;
import org.springframework.data.jpa.domain.Specification;

public class HotelSpec {
    public static Specification<Hotel> searchByKeyword(String keyword){
        String searchLike="%"+keyword+"%";
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(criteriaBuilder.like(root.get("name"),searchLike),
                criteriaBuilder.like(root.get("address"),searchLike)
        );
    }
}
