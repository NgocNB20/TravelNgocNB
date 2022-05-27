package com.ngocnb20.travel.service.impl;

import com.ngocnb20.travel.model.entity.Place;
import com.ngocnb20.travel.repository.ImagePlaceRepository;
import com.ngocnb20.travel.service.ImagePlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImagePlaceServiceImpl implements ImagePlaceService {
    @Autowired
    ImagePlaceRepository imgRepository;
    @Override
    public void deleteByPlace(Place place) {
        imgRepository.deleteImagePlaceByPlace(place);
    }
}
