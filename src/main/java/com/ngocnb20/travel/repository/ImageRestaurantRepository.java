package com.ngocnb20.travel.repository;

import com.ngocnb20.travel.model.entity.ImagePlace;
import com.ngocnb20.travel.model.entity.ImageRestaurant;
import com.ngocnb20.travel.model.entity.Place;
import com.ngocnb20.travel.model.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRestaurantRepository extends BaseRepository<ImageRestaurant,Long> , JpaSpecificationExecutor<ImageRestaurant> {
    List<ImageRestaurant> findAll();
    void deleteImageRestaurantByRestaurant(Restaurant restaurant);
}
