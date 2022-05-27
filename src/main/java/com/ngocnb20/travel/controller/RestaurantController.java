package com.ngocnb20.travel.controller;

import com.ngocnb20.travel.constant.StatusRespData;
import com.ngocnb20.travel.model.dto.param.HotelPramDto;
import com.ngocnb20.travel.model.dto.param.PlaceParamDto;
import com.ngocnb20.travel.model.dto.param.RestaurantParamDto;
import com.ngocnb20.travel.model.dto.resp.BaseRespDto;
import com.ngocnb20.travel.model.dto.resp.HotelRespDto;
import com.ngocnb20.travel.model.dto.resp.PageRespDto;
import com.ngocnb20.travel.model.dto.resp.RestaurantRespDto;
import com.ngocnb20.travel.model.entity.*;
import com.ngocnb20.travel.repository.ImagePlaceRepository;
import com.ngocnb20.travel.repository.ImageRestaurantRepository;
import com.ngocnb20.travel.repository.RestaurantRepository;
import com.ngocnb20.travel.service.FileStorageService;
import com.ngocnb20.travel.service.RestaurantService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/restaurants",produces = "application/json;charset=UTF-8")
@CrossOrigin("*")
public class RestaurantController {
    @Autowired
    RestaurantService restaurantService;
    @Value("${app.file.location-restaurant}")
    String fileLocation;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    FileStorageService fileStorageService;
    @Autowired
    ImageRestaurantRepository imageRepository;

    @GetMapping
    public ResponseEntity<BaseRespDto> getAllRestaurant(){
        List<Restaurant> restaurants = restaurantService.findAll();
        System.out.println(restaurants.size());
        List<RestaurantRespDto> restaurantDtos = restaurants.stream()
                                                            .map(r->restaurantService.ConvertDto(r))
                                                            .collect(Collectors.toList());

        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,restaurants)
        );

    }

    @GetMapping("/page/{page}")
    public ResponseEntity<BaseRespDto> getAllBlogByPage(@PathVariable("page") String page, @RequestParam String search){
        if(Objects.isNull(page)){
            page="0";
        }
        int numberPage  = Integer.parseInt(page)-1;
        Page<Restaurant> restaurants = restaurantService.getDataByPage(numberPage,"",search);
        int totalPage   =   restaurants.getTotalPages();
        long totalItem   =   restaurants.getTotalElements();
        System.out.println("total item"+restaurants.getTotalElements());
        System.out.println("total page"+restaurants.getTotalPages());

        List<RestaurantRespDto> restaurantRespDtos  =   restaurants.getContent().stream()
                .map(r->restaurantService.ConvertDto(r))
                .collect(Collectors.toList());
        PageRespDto<RestaurantRespDto> pageData=new PageRespDto(totalPage,restaurantRespDtos,totalItem);

        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,pageData)

        );
    }

    @GetMapping(path = "/detail/{id}")
    public ResponseEntity<BaseRespDto> getRestaurantById(@PathVariable Long id){
        Optional<Restaurant> restaurant =   restaurantService.findRestaurantById(id);

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

    @GetMapping(path = "/top/{id}")
    public ResponseEntity<BaseRespDto> getTopBlogNew(@PathVariable Long id){
        List<Restaurant> restaurants = restaurantService.getRestaurantTop(id);
        List<RestaurantRespDto> restaurantDtos=restaurants.stream().map(r ->restaurantService.ConvertDto(r)).collect(Collectors.toList());
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,restaurantDtos)
        );
    }

    @PostMapping
    public ResponseEntity<?> save(@ModelAttribute RestaurantParamDto restaurantDto) throws IOException {

        //get userId Login
        String username;
        Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal). getUsername();
        } else {
            username = principal. toString();
        }
        List<String> nameImages = fileStorageService.saveMultipartFile(restaurantDto.getFiles(),
                "user_"+username,fileLocation);

        Restaurant restaurant = new Restaurant();
        BeanUtils.copyProperties(restaurantDto,restaurant);
        Set<ImageRestaurant> imageRestaurants = nameImages.stream()
                .map(nameImage->new ImageRestaurant(FilenameUtils.separatorsToUnix(nameImage),restaurant))
                .collect(Collectors.toSet());
        restaurant.setImageRestaurants(imageRestaurants);
        Restaurant restaurantResp = restaurantService.save(restaurant);
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,restaurantResp)
        );
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @ModelAttribute RestaurantParamDto restaurantDto) throws Exception {
        String username;
        Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal). getUsername();
        } else {
            username = principal. toString();
        }
        Restaurant restaurant = restaurantService
                .findRestaurantById(id)
                .orElseThrow(() -> new Exception("User not found on :: " + id));
        BeanUtils.copyProperties(restaurantDto,restaurant);
        if(restaurantDto.getFiles()!=null){
            List<String> nameImages = fileStorageService.saveMultipartFile(restaurantDto.getFiles(),
                    "user_"+username,fileLocation);
            Set<ImageRestaurant> imageRestaurants = nameImages.stream()
                    .map(nameImage->new ImageRestaurant(FilenameUtils.separatorsToUnix(nameImage),restaurant))
                    .collect(Collectors.toSet());
            imageRepository.deleteImageRestaurantByRestaurant(restaurant);
            restaurant.setImageRestaurants(imageRestaurants);
        }
        Restaurant restaurantResp = restaurantService.save(restaurant);
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,restaurantResp)
        );
    }

    @PutMapping("/view/{id}")
    @javax.transaction.Transactional
    public ResponseEntity<?> updateView(@PathVariable Long id ,@RequestParam int numberView) throws Exception {
        restaurantRepository.updateView(id,numberView);
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,numberView)
        );

    }

    @PutMapping("/like/{id}")
    @javax.transaction.Transactional
    public ResponseEntity<?> updateLike(@PathVariable Long id ,@RequestParam int numberLike) throws Exception {
        restaurantRepository.updateLike(id,numberLike);
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,numberLike)
        );

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        try {
            restaurantService.deleteById(id);
            return ResponseEntity.ok(
                    BaseRespDto.success(StatusRespData.REMOVE_DATA_SUCCESS+id,id)
            );
        }catch (Exception e){
            return ResponseEntity.ok(
                    BaseRespDto.success(StatusRespData.REMOVE_FAIL+id,id)
            );
        }
    }



}
