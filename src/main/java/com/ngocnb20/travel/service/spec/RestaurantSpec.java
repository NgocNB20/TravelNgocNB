package com.ngocnb20.travel.service.spec;

import com.ngocnb20.travel.model.entity.Place;
import com.ngocnb20.travel.model.entity.Restaurant;
import org.springframework.data.jpa.domain.Specification;

public class RestaurantSpec {
    public static Specification<Restaurant> searchByKeyword(String keyword){
        String searchLike="%"+keyword+"%" ;
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(criteriaBuilder.like(root.get("name"),searchLike),
                criteriaBuilder.like(root.get("address"),searchLike)
        );
        //            return (root, query, criteriaBuilder) -> criteriaBuilder.or(criteriaBuilder.like(root.get("title"),searchLike),
//            criteriaBuilder.like(root.get("content"),searchLike)
//            );
    }
}
