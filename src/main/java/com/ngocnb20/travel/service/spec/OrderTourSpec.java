package com.ngocnb20.travel.service.spec;

import com.ngocnb20.travel.model.entity.OrderRestaurant;
import com.ngocnb20.travel.model.entity.OrderTour;
import org.springframework.data.jpa.domain.Specification;

public class OrderTourSpec {
    public static Specification<OrderTour> searchByKeyword(String keyword){
        String searchLike="%"+keyword+"%";
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(criteriaBuilder.like(root.get("nameCustomer"),searchLike),
                criteriaBuilder.like(root.get("nameTour"),searchLike)
        );
    }
}
