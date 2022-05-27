package com.ngocnb20.travel.service;

import com.ngocnb20.travel.model.entity.Tour;

public interface ImageTourService extends BaseService{
    void deleteByTour(Tour tour);
}
