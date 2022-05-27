package com.ngocnb20.travel.service.impl;

import com.ngocnb20.travel.constant.DefaultSort;
import com.ngocnb20.travel.model.dto.resp.CategoryDto;
import com.ngocnb20.travel.model.dto.resp.ImagePlaceDto;
import com.ngocnb20.travel.model.dto.resp.PlaceRespDto;
import com.ngocnb20.travel.model.entity.Blog;
import com.ngocnb20.travel.model.entity.Category;
import com.ngocnb20.travel.model.entity.Place;
import com.ngocnb20.travel.repository.CategoryRepository;
import com.ngocnb20.travel.repository.PlaceRepository;
import com.ngocnb20.travel.service.PlaceService;
import com.ngocnb20.travel.service.spec.BlogSpec;
import com.ngocnb20.travel.service.spec.PlaceSpec;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlaceServiceImpl implements PlaceService {
    @Autowired
    PlaceRepository placeRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Value("${app.page.size}")
    private Integer pageSize;


    @Override
    public List<Place> findAll() {
        return placeRepository.findAll();
    }


    @Override
    public Optional<Place> findPlaceById(Long id) {
        return placeRepository.findById(id);
    }


    @Override
    public List<Place> getTopByNumberView() {
        return placeRepository.findTop6ByOrderByNumberViewDesc();
    }


    @Override
    public Page<Place> getDataByPage(Integer page, String sort, String keyWord) {

        Pageable pageable = PageRequest.of(page,pageSize,generateSort(sort));
        Specification<Place> placeSpec;

        if(StringUtils.isNotBlank(keyWord)){
            placeSpec = PlaceSpec.searchByKeyword(keyWord);
            return placeRepository.findAll(placeSpec,pageable);
        }
        return placeRepository.findAll(pageable);
    }


    private Sort generateSort(String sort){
        String defaultSort = DefaultSort.SORT_PLACE;
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


    @Override
    public PlaceRespDto convertDto(Place place) {
        Set<Category> categories=categoryRepository.getCategoryByPlace(place.getId());
        PlaceRespDto placeRespDto =new PlaceRespDto();
        placeRespDto.setId(place.getId());
        placeRespDto.setPrice(place.getPrice());

//      BeanUtils.copyProperties(placeRespDto,place);
        placeRespDto.setAddress(place.getAddress());
        placeRespDto.setName(place.getName());
        placeRespDto.setNumberLike(place.getNumberLike());
        placeRespDto.setNumberComment(place.getNumberComment());
        placeRespDto.setNumberView(place.getNumberView());
        placeRespDto.setDetail(place.getDetail());

        placeRespDto.setCategories(
                categories.stream()
                        .map(c -> new CategoryDto(c.getId(),c.getName()))
                        .collect(Collectors.toSet())
        );

        placeRespDto.setImagePlaces(
                place.getImagePlaces().stream()
                .map(img-> new ImagePlaceDto(img.getId(),img.getUrl()))
                .collect(Collectors.toSet())
        );

        placeRespDto.setCreateDate(place.getCreateDate());
        placeRespDto.setUpdateTime(place.getUpdateTime());
        placeRespDto.setCreateById(place.getCreateById());
        placeRespDto.setLastModifiedBy(place.getLastModifiedBy());


        return placeRespDto;
    }

    @Override
    public Place save(Place place) {
        return placeRepository.save(place);
    }

    @Override
    public void deleteById(Long id) {
        placeRepository.deleteById(id);
    }
}
