package com.ngocnb20.travel.service;

import com.ngocnb20.travel.model.dto.resp.PlaceRespDto;
import com.ngocnb20.travel.model.entity.OrderRestaurant;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface OrderRestaurantService extends BaseService {
    List<OrderRestaurant> findAll();
    Optional<OrderRestaurant> findById(Long id);
    Page<OrderRestaurant> getDataByPage(Integer page, String sort, String keyWord);
    OrderRestaurant save(OrderRestaurant orderRestaurant);
    void deleteById(Long id);
}
