package com.ngocnb20.travel.service;

import com.ngocnb20.travel.model.dto.resp.HotelRespDto;
import com.ngocnb20.travel.model.dto.resp.PlaceRespDto;
import com.ngocnb20.travel.model.entity.Hotel;
import com.ngocnb20.travel.model.entity.Place;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface HotelService {
    List<Hotel> fillAll();
    Optional<Hotel> findHotelById(Long id);
    Page<Hotel> getDataByPage(Integer page, String sort, String keyWord);
    List<Hotel> getHotelTop(Long id);
    HotelRespDto convertDto(Hotel hotel);
    Hotel save(Hotel hotel);
    void deleteById(Long id);


}
