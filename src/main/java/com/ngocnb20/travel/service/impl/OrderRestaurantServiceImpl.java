package com.ngocnb20.travel.service.impl;

import com.ngocnb20.travel.constant.DefaultSort;
import com.ngocnb20.travel.model.entity.OrderRestaurant;
import com.ngocnb20.travel.model.entity.Place;
import com.ngocnb20.travel.repository.OrderRestaurantRepository;
import com.ngocnb20.travel.service.OrderRestaurantService;
import com.ngocnb20.travel.service.spec.OrderRestaurantSpec;
import com.ngocnb20.travel.service.spec.PlaceSpec;
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

@Service
public class OrderRestaurantServiceImpl implements OrderRestaurantService {
    @Autowired
    OrderRestaurantRepository orderRepository;

    @Value("${app.page.size}")
    private Integer pageSize;




    @Override
    public List<OrderRestaurant> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<OrderRestaurant> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Page<OrderRestaurant> getDataByPage(Integer page, String sort, String keyWord) {

        Pageable pageable = PageRequest.of(page,pageSize,generateSort(sort));
        Specification<OrderRestaurant> orderSpec;

        if(StringUtils.isNotBlank(keyWord)){
            orderSpec = OrderRestaurantSpec.searchByKeyword(keyWord);
            return orderRepository.findAll(orderSpec,pageable);
        }
        return orderRepository.findAll(pageable);
    }

    @Override
    public OrderRestaurant save(OrderRestaurant orderRestaurant) {
        return orderRepository.save(orderRestaurant);
    }


    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }


    private Sort generateSort(String sort){
        String defaultSort = DefaultSort.SORT_ORDER;
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
