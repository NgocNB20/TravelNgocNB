package com.ngocnb20.travel.service;

import com.ngocnb20.travel.model.entity.OrderHotel;
import com.ngocnb20.travel.model.entity.OrderRestaurant;
import com.ngocnb20.travel.model.entity.Place;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface OrderHotelService extends BaseService {
    List<OrderHotel> findAll();
    Optional<OrderHotel> findById(Long id);
    Page<OrderHotel> getDataByPage(Integer page, String sort, String keyWord);
    OrderHotel save(OrderHotel orderHotel);
    void deleteById(Long id);
}
