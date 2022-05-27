package com.ngocnb20.travel.repository;

import com.ngocnb20.travel.model.entity.ImagePlace;
import com.ngocnb20.travel.model.entity.ImageTour;
import com.ngocnb20.travel.model.entity.Place;
import com.ngocnb20.travel.model.entity.Tour;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageTourRepository extends BaseRepository<ImageTour,Long> , JpaSpecificationExecutor<ImageTour> {
    List<ImageTour> findAll();
    void deleteImageTourByTour(Tour tour);
}
