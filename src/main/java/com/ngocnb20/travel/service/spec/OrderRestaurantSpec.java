package com.ngocnb20.travel.service.spec;


import com.ngocnb20.travel.model.entity.OrderRestaurant;
import org.springframework.data.jpa.domain.Specification;

public class OrderRestaurantSpec {
    public static Specification<OrderRestaurant> searchByKeyword(String keyword){
        String searchLike="%"+keyword+"%";
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(criteriaBuilder.like(root.get("nameCustomer"),searchLike),
                criteriaBuilder.like(root.get("nameRestaurant"),searchLike)
        );
    }
}
