package com.ngocnb20.travel.service;

import com.ngocnb20.travel.model.dto.resp.BlogRespDto;
import com.ngocnb20.travel.model.dto.resp.RestaurantRespDto;
import com.ngocnb20.travel.model.entity.Blog;
import com.ngocnb20.travel.model.entity.Hotel;
import com.ngocnb20.travel.model.entity.Place;
import com.ngocnb20.travel.model.entity.Restaurant;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;

public interface RestaurantService extends BaseService {
    List<Restaurant> findAll();
    Optional<Restaurant> findRestaurantById(Long id);
    Page<Restaurant> getDataByPage(Integer page, String sort, String keyWord);
    List<Restaurant> getRestaurantTop(Long id);
    RestaurantRespDto ConvertDto(Restaurant restaurant);
    Restaurant save(Restaurant restaurant);
    void deleteById(Long id);

}
