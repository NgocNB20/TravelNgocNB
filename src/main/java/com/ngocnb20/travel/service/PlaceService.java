package com.ngocnb20.travel.service;

import com.ngocnb20.travel.model.dto.resp.PlaceRespDto;
import com.ngocnb20.travel.model.entity.Blog;
import com.ngocnb20.travel.model.entity.Place;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface PlaceService extends BaseService {
    List<Place> findAll();
    Optional<Place> findPlaceById(Long id);
    List<Place> getTopByNumberView();
    Page<Place> getDataByPage(Integer page, String sort, String keyWord);
    PlaceRespDto convertDto(Place place);
    Place save(Place place);
    void deleteById(Long id);


}
