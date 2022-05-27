package com.ngocnb20.travel.repository;



import com.ngocnb20.travel.model.entity.OrderRestaurant;
import com.ngocnb20.travel.model.entity.OrderTour;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderTourRepository extends BaseRepository<OrderTour,Long>, JpaSpecificationExecutor<OrderTour> {
    List<OrderTour> findAll();
    Optional<OrderTour> findById(Long id);
    void deleteById(Long id);


}
