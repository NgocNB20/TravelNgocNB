package com.ngocnb20.travel.service.spec;

import com.ngocnb20.travel.model.entity.Member;
import com.ngocnb20.travel.model.entity.OrderHotel;
import org.springframework.data.jpa.domain.Specification;

public class MemberSpec {
    public static Specification<Member> searchByKeyword(String keyword){
        String searchLike="%"+keyword+"%";
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(criteriaBuilder.like(root.get("email"),searchLike)
        );
    }
}
