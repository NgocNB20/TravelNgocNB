package com.ngocnb20.travel.service;

import com.ngocnb20.travel.model.entity.ImagePlace;
import com.ngocnb20.travel.model.entity.Place;
import org.springframework.stereotype.Service;

import java.util.Set;


public interface ImagePlaceService extends BaseService {
    void  deleteByPlace(Place place);
}
