package com.ngocnb20.travel.service.impl;


import com.ngocnb20.travel.constant.DefaultSort;
import com.ngocnb20.travel.model.dto.resp.*;
import com.ngocnb20.travel.model.entity.Hotel;
import com.ngocnb20.travel.model.entity.Place;
import com.ngocnb20.travel.model.entity.Restaurant;
import com.ngocnb20.travel.repository.RestaurantRepository;
import com.ngocnb20.travel.service.RestaurantService;
import com.ngocnb20.travel.service.spec.PlaceSpec;
import com.ngocnb20.travel.service.spec.RestaurantSpec;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    @Autowired
    RestaurantRepository restaurantRepository;
    @Value("${app.page.size}")
    private Integer pageSize;

    @Override
    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    @Override
    public Optional<Restaurant> findRestaurantById(Long id) {
        return restaurantRepository.findById(id);
    }

    @Override
    public Page<Restaurant> getDataByPage(Integer page, String sort, String keyWord) {

        Pageable pageable = PageRequest.of(page,pageSize,generateSort(sort));
        Specification<Restaurant> restaurantSpec;

        if(StringUtils.isNotBlank(keyWord)){
            restaurantSpec = RestaurantSpec.searchByKeyword(keyWord);
            return restaurantRepository.findAll(restaurantSpec,pageable);
        }
        return restaurantRepository.findAll(pageable);
    }

    @Override
    public List<Restaurant> getRestaurantTop(Long id) {
        List<Restaurant> restaurants = restaurantRepository.getRestaurantTop(id);
        return restaurants ;
    }

    @Override
    public RestaurantRespDto ConvertDto(Restaurant restaurant) {
        RestaurantRespDto restaurantDto = new RestaurantRespDto();
        restaurantDto.setId(restaurant.getId());
        restaurantDto.setDetail(restaurant.getDetail());
        restaurantDto.setAddress(restaurant.getAddress());
        restaurantDto.setName(restaurant.getName());
        restaurantDto.setNumberLike(restaurant.getNumberLike());
        restaurantDto.setPhone(restaurant.getPhone());
        restaurantDto.setNumberView(restaurant.getNumberView());
        restaurantDto.setImageRestaurants(restaurant.getImageRestaurants()
                .stream().map(img->new ImageRestaurantDto(img.getId()   , img.getUrl())).collect(Collectors.toSet())
        );
        restaurantDto.setRestaurantFits(restaurant.getRestaurantFits()
                .stream().map(f->new RestaurantFitDto(f.getId(),f.getName())).collect(Collectors.toSet())
        );
        restaurantDto.setRestaurantFoods(restaurant.getRestaurantFoods()
                .stream().map(f->new RestaurantFoodDto(f.getId(),f.getName())).collect(Collectors.toSet())
        );
        restaurantDto.setRestaurantTypes(restaurant.getRestaurantTypes()
                .stream().map(t->new RestaurantTypeDto(t.getId(),t.getName())).collect(Collectors.toSet())
        );

        restaurantDto.setCreateDate(restaurant.getCreateDate());
        restaurantDto.setUpdateTime(restaurant.getUpdateTime());
        restaurantDto.setCreateById(restaurant.getCreateById());
        restaurantDto.setLastModifiedBy(restaurant.getLastModifiedBy());

        return  restaurantDto;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteById(Long id) {
          restaurantRepository.deleteById(id);
    }

    private Sort generateSort(String sort){
        String defaultSort = DefaultSort.SORT_RESTAURANT;
        Sort.Order orderByDefault = Sort.Order.desc(defaultSort);
        Sort.Order orderBySortField = null;

        if(StringUtils.isBlank(sort)){
            return Sort.by(orderByDefault);
        }

        if(sort.startsWith("-")){
            orderBySortField=Sort.Order.desc(sort.substring(1));//substring(1) cắt bỏ kí tự "-"
        }else {
            orderBySortField=Sort.Order.asc(sort);
        }

        return Sort.by(orderBySortField,orderByDefault);
    }




}
