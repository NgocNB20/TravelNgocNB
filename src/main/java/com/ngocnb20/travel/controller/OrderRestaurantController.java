package com.ngocnb20.travel.controller;

import com.ngocnb20.travel.constant.StatusRespData;
import com.ngocnb20.travel.model.dto.param.OrderRestaurantParamDto;
import com.ngocnb20.travel.model.dto.resp.BaseRespDto;
import com.ngocnb20.travel.model.dto.resp.HotelRespDto;
import com.ngocnb20.travel.model.dto.resp.PageRespDto;
import com.ngocnb20.travel.model.dto.resp.RestaurantRespDto;
import com.ngocnb20.travel.model.entity.Hotel;
import com.ngocnb20.travel.model.entity.OrderHotel;
import com.ngocnb20.travel.model.entity.OrderRestaurant;
import com.ngocnb20.travel.model.entity.OrderTour;
import com.ngocnb20.travel.repository.OrderRestaurantRepository;
import com.ngocnb20.travel.service.OrderRestaurantService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping(path = "/order-restaurant",produces = "application/json;charset=UTF-8")
public class OrderRestaurantController {
    @Autowired
    OrderRestaurantRepository orderRestaurantRepository;
    @Autowired
    OrderRestaurantService oderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@ModelAttribute OrderRestaurantParamDto orderDto){
        OrderRestaurant orderRestaurant = new OrderRestaurant();
        BeanUtils.copyProperties(orderDto,orderRestaurant);
        orderRestaurant.setDateOrder(LocalDateTime.now());
        OrderRestaurant orderResp = orderRestaurantRepository.save(orderRestaurant);
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,orderResp)
        );
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<BaseRespDto> getAllByPage(@PathVariable("page") String page, @RequestParam String search){
        if(Objects.isNull(page)){
            page="0";
        }
        int numberPage  = Integer.parseInt(page)-1;
        Page<OrderRestaurant> restaurants = oderService.getDataByPage(numberPage,"",search);
        int totalPage   =   restaurants.getTotalPages();
        long totalItem  = restaurants.getTotalElements();
        PageRespDto<OrderRestaurant> pageData = new PageRespDto(totalPage,restaurants.getContent(),totalItem );
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,pageData)
        );
    }

    @GetMapping(path = "/detail/{id}")
    public ResponseEntity<BaseRespDto> getById(@PathVariable Long id){
        Optional<OrderRestaurant> restaurant = oderService.findById(id);

        if(restaurant.isPresent()){
            return ResponseEntity.ok(
                    BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,restaurant.get())
            );
        }else {
            return ResponseEntity.ok(
                    BaseRespDto.error(StatusRespData.GET_BY_ID_FAIL)
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        try {
            oderService.deleteById(id);
            return ResponseEntity.ok(
                    BaseRespDto.success(StatusRespData.REMOVE_DATA_SUCCESS+id,id)
            );
        }catch (Exception e){
            return ResponseEntity.ok(
                    BaseRespDto.success(StatusRespData.REMOVE_FAIL+id,id)
            );
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRestaurant(@PathVariable Long id ){
        OrderRestaurant order= oderService.findById(id).get();
        order.setStatus(true);
        oderService.save(order);
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,order)
        );
    }
}
