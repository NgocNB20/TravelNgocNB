package com.ngocnb20.travel.repository;

import com.ngocnb20.travel.model.entity.OrderRestaurant;
import com.ngocnb20.travel.model.entity.Place;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRestaurantRepository extends BaseRepository<OrderRestaurant,Long>, JpaSpecificationExecutor<OrderRestaurant> {
    List<OrderRestaurant> findAll();
    Optional<OrderRestaurant> findById(Long id);
    void deleteById(Long id);

}
