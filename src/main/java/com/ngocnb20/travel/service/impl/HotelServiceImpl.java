package com.ngocnb20.travel.service.impl;

import com.ngocnb20.travel.constant.DefaultSort;
import com.ngocnb20.travel.model.dto.resp.HotelRespDto;
import com.ngocnb20.travel.model.entity.Hotel;
import com.ngocnb20.travel.model.entity.Tour;
import com.ngocnb20.travel.repository.HotelRepository;
import com.ngocnb20.travel.service.HotelService;
import com.ngocnb20.travel.service.spec.HotelSpec;
import com.ngocnb20.travel.service.spec.TourSpec;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    HotelRepository hotelRepository;

    @Value("${app.page.size}")
    private Integer pageSize;


    @Override
    public List<Hotel> fillAll() {
        return hotelRepository.findAll();
    }


    @Override
    public Optional<Hotel> findHotelById(Long id) {
        return hotelRepository.findById(id);
    }


    @Override
    public Page<Hotel> getDataByPage(Integer page, String sort, String keyWord) {
        Pageable pageable = PageRequest.of(page,pageSize,generateSort(sort));
        Specification<Hotel> hotelSpec;

        if(StringUtils.isNotBlank(keyWord)){
            hotelSpec = HotelSpec.searchByKeyword(keyWord);
            return hotelRepository.findAll(hotelSpec,pageable);
        }
        return hotelRepository.findAll(pageable);
    }

    @Override
    public List<Hotel> getHotelTop(Long id) {
        List<Hotel> hotels = hotelRepository.getHotelTop(id);
        return hotels;
    }


    @Override
    public HotelRespDto convertDto(Hotel hotel) {
        HotelRespDto hotelDto  =  new HotelRespDto();
        hotelDto.setId(hotel.getId());
        hotelDto.setDetail(hotel.getDetail());
        hotelDto.setAddress(hotel.getAddress());
        hotelDto.setNumberLike(hotel.getNumberLike());
        hotelDto.setPrice(hotel.getPrice());
        hotelDto.setNumberView(hotel.getNumberView());
        hotelDto.setName(hotel.getName());
        hotelDto.setService(hotel.getService());
        hotelDto.setRate(hotel.getRate());
        hotelDto.setEmail(hotel.getEmail());
        hotelDto.setImage(hotel.getImage());
        hotelDto.setUrlWeb(hotel.getUrlWeb());
        hotelDto.setImage(hotel.getImage());
        hotelDto.setPhone(hotel.getPhone());

        hotelDto.setCreateDate(hotel.getCreateDate());
        hotelDto.setUpdateTime(hotel.getUpdateTime());
        hotelDto.setCreateById(hotel.getCreateById());
        hotelDto.setLastModifiedBy(hotel.getLastModifiedBy());
        return hotelDto;
    }

    @Transactional
    @Override
    public Hotel save(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public void deleteById(Long id) {
        hotelRepository.deleteById(id);
    }


    private Sort generateSort(String sort){
        String defaultSort = DefaultSort.SORT_HOTEL;
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
