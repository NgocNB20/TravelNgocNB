package com.ngocnb20.travel.service.impl;

import com.ngocnb20.travel.constant.DefaultSort;
import com.ngocnb20.travel.model.dto.resp.ImageTourDto;
import com.ngocnb20.travel.model.dto.resp.TourRespDto;
import com.ngocnb20.travel.model.entity.Place;
import com.ngocnb20.travel.model.entity.Tour;
import com.ngocnb20.travel.repository.TourRepository;
import com.ngocnb20.travel.service.TourService;

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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TourServiceImpl implements TourService {
    @Autowired
    TourRepository  tourRepository;
    @Value("${app.page.size}")
    private Integer pageSize;

    @Override
    public List<Tour> findAll() {
        return tourRepository.findAll();
    }

    @Override
    public Tour save(Tour tour) {
        return tourRepository.save(tour);
    }

    @Override
    public Optional<Tour> findTourById(Long id) {
        return tourRepository.findById(id);
    }

    @Override
    public TourRespDto ConvertDto(Tour tour) {
        TourRespDto tourRespDto = new TourRespDto();
        tourRespDto.setDeparture(tour.getDeparture());
        tourRespDto.setDetail(tour.getDetail());
        tourRespDto.setSubDetail(tour.getSubDetail());
        tourRespDto.setId(tour.getId());
        tourRespDto.setNumberLike(tour.getNumberLike());
        tourRespDto.setNumberView(tour.getNumberView());
        tourRespDto.setPrice(tour.getPrice());
        tourRespDto.setRate(tour.getRate());
        tourRespDto.setTitle(tour.getTitle());
        tourRespDto.setTotalDay(tour.getTotalDay());
        tourRespDto.setTotalPerson(tour.getTotalPerson());

        tourRespDto.setImageTours(
                tour.getImageTours().stream()
                    .map(img->new ImageTourDto(img.getId(),img.getUrl()))
                    .collect(Collectors.toSet()));

        tourRespDto.setCreateDate(tour.getCreateDate());
        tourRespDto.setUpdateTime(tour.getUpdateTime());
        tourRespDto.setCreateById(tour.getCreateById());
        tourRespDto.setLastModifiedBy(tour.getLastModifiedBy());

        return tourRespDto;
    }

    @Override
    public void deleteById(Long id) {
        tourRepository.deleteById(id);
    }


    @Override
    public Page<Tour> getDataByPage(Integer page, String sort, String keyWord) {

        Pageable pageable = PageRequest.of(page,pageSize,generateSort(sort));
        Specification<Tour> tourSpec;

        if(StringUtils.isNotBlank(keyWord)){
            tourSpec = TourSpec.searchByKeyword(keyWord);
            return tourRepository.findAll(tourSpec,pageable);
        }
        return tourRepository.findAll(pageable);
    }


    private Sort generateSort(String sort){
        String defaultSort = DefaultSort.SORT_TOUR;
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
