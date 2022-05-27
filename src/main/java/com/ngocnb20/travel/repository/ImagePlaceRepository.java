package com.ngocnb20.travel.repository;


import com.ngocnb20.travel.model.entity.ImagePlace;

import com.ngocnb20.travel.model.entity.Place;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagePlaceRepository extends BaseRepository<ImagePlace,Long> , JpaSpecificationExecutor<ImagePlace> {
    List<ImagePlace> findAll();
    void deleteImagePlaceByPlace(Place place);
}
