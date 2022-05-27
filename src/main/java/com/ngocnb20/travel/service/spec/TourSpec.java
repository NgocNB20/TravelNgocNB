package com.ngocnb20.travel.service.spec;

import com.ngocnb20.travel.model.entity.Place;
import com.ngocnb20.travel.model.entity.Tour;
import org.springframework.data.jpa.domain.Specification;

public class TourSpec {
    public static Specification<Tour> searchByKeyword(String keyword){
        String searchLike="%"+keyword+"%";
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(criteriaBuilder.like(root.get("title"),searchLike)
        );
    }
}
