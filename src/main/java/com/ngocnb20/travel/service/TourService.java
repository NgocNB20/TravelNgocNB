package com.ngocnb20.travel.service;

import com.ngocnb20.travel.model.dto.resp.TourRespDto;
import com.ngocnb20.travel.model.entity.Place;
import com.ngocnb20.travel.model.entity.Tour;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface TourService {
    List<Tour> findAll();
    Tour save(Tour tour);
    Optional<Tour> findTourById(Long id);
    Page<Tour> getDataByPage(Integer page, String sort, String keyWord);
    TourRespDto ConvertDto(Tour tour);
    void deleteById(Long id);
}
