package com.ngocnb20.travel.service.spec;

import com.ngocnb20.travel.model.entity.Blog;
import org.springframework.data.jpa.domain.Specification;

public class BlogSpec {
    public static Specification<Blog> searchByKeyword(String keyword){
        String searchLike="%"+keyword+"%";
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(criteriaBuilder.like(root.get("title"),searchLike)
        );
    }


}
