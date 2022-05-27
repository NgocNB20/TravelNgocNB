package com.ngocnb20.travel.service.spec;

import com.ngocnb20.travel.model.entity.Agency;
import org.springframework.data.jpa.domain.Specification;

public class AgencySpec {
    public static Specification<Agency> searchByKeyword(String keyword) {
        String searchLike = "%" + keyword + "%";
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(criteriaBuilder.like(root.get("name"), searchLike)
        );

    }
}