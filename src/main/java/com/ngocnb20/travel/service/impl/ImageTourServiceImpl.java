package com.ngocnb20.travel.service.impl;

import com.ngocnb20.travel.model.entity.Tour;
import com.ngocnb20.travel.repository.ImageTourRepository;
import com.ngocnb20.travel.service.ImageTourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageTourServiceImpl implements ImageTourService {

    @Autowired
    ImageTourRepository imageTourRepository;

    @Override
    public void deleteByTour(Tour tour) {
        imageTourRepository.deleteImageTourByTour(tour);
    }
}
