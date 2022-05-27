package com.ngocnb20.travel.controller;

import com.ngocnb20.travel.constant.StatusRespData;
import com.ngocnb20.travel.model.dto.param.OrderTourParamDto;
import com.ngocnb20.travel.model.dto.resp.BaseRespDto;
import com.ngocnb20.travel.model.dto.resp.PageRespDto;
import com.ngocnb20.travel.model.entity.OrderRestaurant;
import com.ngocnb20.travel.model.entity.OrderTour;
import com.ngocnb20.travel.repository.OrderTourRepository;
import com.ngocnb20.travel.service.OrderTourService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(path = "/order-tour",produces = "application/json;charset=UTF-8")
public class OrderTourController {
    @Autowired
    OrderTourRepository orderTourRepository;
    @Autowired
    OrderTourService oderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@ModelAttribute OrderTourParamDto orderDto){
        OrderTour orderTour = new OrderTour();
        BeanUtils.copyProperties(orderDto,orderTour);
        orderTour.setDateOrder(LocalDateTime.now());
        OrderTour orderTourResp = orderTourRepository.save(orderTour);
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,orderTourResp)
        );
    }
    @GetMapping("/page/{page}")
    public ResponseEntity<BaseRespDto> getAllByPage(@PathVariable("page") String page, @RequestParam String search){
        if(Objects.isNull(page)){
            page="0";
        }
        int numberPage  = Integer.parseInt(page)-1;
        Page<OrderTour> tours = oderService.getDataByPage(numberPage,"",search);
        int totalPage   =   tours.getTotalPages();
        long totalItem  =   tours.getTotalElements();
        PageRespDto<OrderTour> pageData = new PageRespDto(totalPage,tours.getContent(),totalItem );
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,pageData)
        );
    }

    @GetMapping(path = "/detail/{id}")
    public ResponseEntity<BaseRespDto> getById(@PathVariable Long id){
        Optional<OrderTour> orderTour = oderService.findById(id);

        if(orderTour.isPresent()){
            return ResponseEntity.ok(
                    BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,orderTour.get())
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
        OrderTour order= oderService.findById(id).get();
        order.setStatus(true);
        oderService.save(order);
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,order)
        );
    }
}
