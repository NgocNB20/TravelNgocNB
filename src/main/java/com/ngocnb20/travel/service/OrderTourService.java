package com.ngocnb20.travel.service;

import com.ngocnb20.travel.model.entity.OrderRestaurant;
import com.ngocnb20.travel.model.entity.OrderTour;
import com.ngocnb20.travel.model.entity.Place;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface OrderTourService extends BaseService{
    List<OrderTour> findAll();
    Optional<OrderTour> findById(Long id);
    Page<OrderTour> getDataByPage(Integer page, String sort, String keyWord);
    OrderTour save(OrderTour orderTour);
    void deleteById(Long id);
}
