package com.ngocnb20.travel.repository;

import com.ngocnb20.travel.model.entity.OrderHotel;
import com.ngocnb20.travel.model.entity.OrderRestaurant;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderHotelRepository extends BaseRepository<OrderHotel,Long>, JpaSpecificationExecutor<OrderHotel> {
    List<OrderHotel> findAll();
    Optional<OrderHotel> findById(Long id);
    void deleteById(Long id);

}
