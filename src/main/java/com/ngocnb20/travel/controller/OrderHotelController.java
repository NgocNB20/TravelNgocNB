package com.ngocnb20.travel.controller;

import com.ngocnb20.travel.constant.StatusRespData;
import com.ngocnb20.travel.model.dto.param.OrderHotelParamDto;
import com.ngocnb20.travel.model.dto.resp.BaseRespDto;
import com.ngocnb20.travel.model.dto.resp.PageRespDto;
import com.ngocnb20.travel.model.dto.resp.RestaurantRespDto;
import com.ngocnb20.travel.model.entity.OrderHotel;
import com.ngocnb20.travel.repository.OrderHotelRepository;
import com.ngocnb20.travel.service.OrderHotelService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(path = "/order-hotel",produces = "application/json;charset=UTF-8")
public class OrderHotelController {
    @Autowired
    OrderHotelRepository orderHotelRepository;

    @Autowired
    OrderHotelService oderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@ModelAttribute OrderHotelParamDto orderDto){
        OrderHotel orderHotel = new OrderHotel();
        BeanUtils.copyProperties(orderDto,orderHotel);
        orderHotel.setDateOrder(LocalDateTime.now());
        OrderHotel orderResp = orderHotelRepository.save(orderHotel);
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,orderResp)
        );
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id ){
        OrderHotel orderHotel = oderService.findById(id).get();
        orderHotel.setStatus(true);
        oderService.save(orderHotel);
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,orderHotel)
        );
    }
    @GetMapping("/page/{page}")
    public ResponseEntity<BaseRespDto> getAllByPage(@PathVariable("page") String page, @RequestParam String search){
        if(Objects.isNull(page)){
            page="0";
        }
        int numberPage  = Integer.parseInt(page)-1;
        Page<OrderHotel> hotels = oderService.getDataByPage(numberPage,"",search);
        int totalPage   =   hotels.getTotalPages();
        long totalItem  = hotels.getTotalElements();
        PageRespDto<OrderHotel> pageData = new PageRespDto(totalPage,hotels.getContent(),totalItem );
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,pageData)
        );
    }

    @GetMapping(path = "/detail/{id}")
    public ResponseEntity<BaseRespDto> getById(@PathVariable Long id){
        Optional<OrderHotel> hotel = oderService.findById(id);

        if(hotel.isPresent()){
            return ResponseEntity.ok(
                    BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,hotel.get())
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

    public static void main(String[] args) {

    }

}
