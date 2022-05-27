package com.ngocnb20.travel.controller;

import com.ngocnb20.travel.constant.StatusRespData;
import com.ngocnb20.travel.model.dto.param.BlogPramDto;
import com.ngocnb20.travel.model.dto.param.HotelPramDto;
import com.ngocnb20.travel.model.dto.resp.*;
import com.ngocnb20.travel.model.entity.Blog;
import com.ngocnb20.travel.model.entity.Hotel;
import com.ngocnb20.travel.model.entity.Restaurant;
import com.ngocnb20.travel.repository.HotelRepository;
import com.ngocnb20.travel.service.FileStorageService;
import com.ngocnb20.travel.service.HotelService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/hotels",produces = "application/json;charset=UTF-8")
@CrossOrigin("*")
public class HotelController {

    @Autowired
    HotelService hotelService;

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    FileStorageService fileStorageService;

    @Value("${app.file.location-hotel}")
    String fileLocation;


    @GetMapping
    public ResponseEntity<BaseRespDto> getAllHotel(){
        List<Hotel> hotels = hotelService.fillAll();
        System.out.println(hotels.size());
        List<HotelRespDto> hotelDtos = hotels.stream()
                .map(h->hotelService.convertDto(h))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,hotelDtos)
        );

    }

    @GetMapping("/page/{page}")
    public ResponseEntity<BaseRespDto> getAllHotelByPage(@PathVariable("page") String page, @RequestParam String search){
        if(Objects.isNull(page)){
            page="0";
        }
        int numberPage  = Integer.parseInt(page)-1;
        Page<Hotel> hotels = hotelService.getDataByPage(numberPage,"",search);
        int totalPage   =   hotels.getTotalPages();
        long totalItem  = hotels.getTotalElements();

        List<HotelRespDto> hotelDtos  =   hotels.getContent().stream()
                .map(h->hotelService.convertDto(h))
                .collect(Collectors.toList());
        PageRespDto<RestaurantRespDto> pageData=new PageRespDto(totalPage,hotelDtos,totalItem   );

        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,pageData)

        );
    }


    @GetMapping(path = "/detail/{id}")
    public ResponseEntity<BaseRespDto> getRestaurantById(@PathVariable Long id){
        Optional<Hotel> hotel = hotelService.findHotelById(id);

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

    @GetMapping(path = "/top/{id}")
    public ResponseEntity<BaseRespDto> getTopBlogNew(@PathVariable Long id){
        List<Hotel> hotels = hotelService.getHotelTop(id);
        List<HotelRespDto> hotelRespDtos=hotels.stream().map(h ->hotelService.convertDto(h)).collect(Collectors.toList());
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,hotelRespDtos)
        );
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        try {
            hotelService.deleteById(id);
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
    public ResponseEntity<?> update(@PathVariable Long id , @ModelAttribute HotelPramDto hotelDto) throws Exception {
        System.out.println("ID UPDATE"+id);
        String username;
        Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal). getUsername();
        } else {
            username = principal. toString();
        }

        Hotel hotel = hotelService
                .findHotelById(id)
                .orElseThrow(() -> new Exception("User not found on :: " + id));
        BeanUtils.copyProperties(hotelDto,hotel);
        if (hotelDto.getFile()!=null){
            String url = fileStorageService.saveFile(hotelDto.getFile(),
                "user_"+username,fileLocation );
            String unixPath = FilenameUtils.separatorsToUnix(url);//convert url //
            hotel.setImage(unixPath);
        }
        Hotel hotelResp =  hotelService.save(hotel);

        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,hotel)
        );

    }

    @PutMapping("/view/{id}")
    @Transactional
    public ResponseEntity<?> updateView(@PathVariable Long id ,@RequestParam int numberView) throws Exception {
        hotelRepository.updateView(id,numberView);
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,numberView)
        );

    }

    @PutMapping("/like/{id}")
    @Transactional
    public ResponseEntity<?> updateLike(@PathVariable Long id ,@RequestParam int numberLike) throws Exception {
        hotelRepository.updateLike(id,numberLike);
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,numberLike)
        );

    }

    @PostMapping
    public ResponseEntity<?> save(@ModelAttribute  HotelPramDto hotelDto) throws IOException {

        //get userId Login
        String username;
        Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal). getUsername();
        } else {
            username = principal. toString();
        }

        Hotel hotel = new Hotel();
        BeanUtils.copyProperties(hotelDto,hotel);
        System.out.println(hotel.getNumberView());
        String url = fileStorageService.saveFile(hotelDto.getFile(),
                "user_"+username,fileLocation );
        String unixPath = FilenameUtils.separatorsToUnix(url);//convert url //
        hotel.setImage(unixPath);
        Hotel hotelResp =  hotelService.save(hotel);
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,hotelResp)
        );
    }

}
