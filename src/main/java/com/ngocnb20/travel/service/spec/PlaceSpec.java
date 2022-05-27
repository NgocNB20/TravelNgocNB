package com.ngocnb20.travel.service.spec;

import com.ngocnb20.travel.model.entity.Blog;
import com.ngocnb20.travel.model.entity.Place;
import org.springframework.data.jpa.domain.Specification;

public class PlaceSpec {
    public static Specification<Place> searchByKeyword(String keyword){
        String searchLike="%"+keyword+"%" ;
        System.out.println(searchLike);

        return (root, query, criteriaBuilder) -> criteriaBuilder.or(criteriaBuilder.like(root.get("name"),searchLike)
        );
    }
}
