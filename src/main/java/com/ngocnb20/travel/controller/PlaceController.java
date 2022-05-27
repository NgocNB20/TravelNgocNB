package com.ngocnb20.travel.controller;

import com.ngocnb20.travel.constant.StatusRespData;
import com.ngocnb20.travel.model.dto.param.BlogPramDto;
import com.ngocnb20.travel.model.dto.param.PlaceParamDto;
import com.ngocnb20.travel.model.dto.resp.BaseRespDto;
import com.ngocnb20.travel.model.dto.resp.BlogRespDto;
import com.ngocnb20.travel.model.dto.resp.PageRespDto;
import com.ngocnb20.travel.model.dto.resp.PlaceRespDto;
import com.ngocnb20.travel.model.entity.Blog;
import com.ngocnb20.travel.model.entity.ImagePlace;
import com.ngocnb20.travel.model.entity.Place;
import com.ngocnb20.travel.repository.PlaceRepository;
import com.ngocnb20.travel.service.FileStorageService;
import com.ngocnb20.travel.service.ImagePlaceService;
import com.ngocnb20.travel.service.PlaceService;
import com.ngocnb20.travel.service.impl.UserDetailServiceImpl;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/places",produces = "application/json;charset=UTF-8")
@CrossOrigin("*")
public class PlaceController extends BaseController {

    @Autowired
    PlaceService placeService;
    @Autowired
    FileStorageService fileStorageService;
    @Autowired
    UserDetailServiceImpl userDetailService;
    @Autowired
    ImagePlaceService imagePlaceService;
    @Autowired
    PlaceRepository placeRepository;

    @Value("${app.file.location-place}")
    String fileLocation;


    @GetMapping()
    public ResponseEntity<BaseRespDto> getAllPlace(){
        List<Place> places = placeService.findAll();
        List<PlaceRespDto> placeRespDtos = places.stream().map(p->placeService.convertDto(p))
                                        .collect(Collectors.toList());
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS, placeRespDtos)
        );
    }
    @GetMapping(path = "/top/{id}")
    public ResponseEntity<BaseRespDto> getTopPlaceNew(@PathVariable Long id){
        List<Place> places = placeRepository.getPlaceTop(id);
        List<PlaceRespDto> placeRespDtos = places.stream().map(p ->placeService.convertDto(p)).collect(Collectors.toList());
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,placeRespDtos)
        );
    }


    @GetMapping(path = "/detail/{id}")
    public ResponseEntity<BaseRespDto> getBlogByPlaceId(@PathVariable Long id){
        Optional<Place> place = placeService.findPlaceById(id);
        if(place.isPresent()){
            PlaceRespDto placeRespDto =placeService.convertDto(place.get());
            return ResponseEntity.ok(
                    BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS, placeRespDto)
            );
        }else
        {
            return ResponseEntity.ok(
                    BaseRespDto.error(StatusRespData.GET_BY_ID_FAIL)
            );
        }
    }


    @GetMapping(path = "/top-six")
    public ResponseEntity<BaseRespDto> getTopPlaceByNumberView(){
        List<Place> places= placeService.getTopByNumberView();
        List<PlaceRespDto> placeRespDtos = places.stream().map(p->placeService.convertDto(p)).collect(Collectors.toList());
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,placeRespDtos)
        );

    }

    @GetMapping({"/page/{page}"})
    public ResponseEntity<BaseRespDto> getAllBlogByPage(@PathVariable("page") String page,@RequestParam String search){
        if(Objects.isNull(page)){
            page="0";
        }
        int numberPage=Integer.parseInt(page)-1;
        Page<Place> places = placeService.getDataByPage(numberPage,"",search);
        int totalPage = places.getTotalPages();
        System.out.println("total item"+places.getTotalElements());
        System.out.println("total page"+places.getTotalPages());
        List<PlaceRespDto> placeRespDtos = places.getContent().stream()
                                                .map(p->placeService.convertDto(p))
                                                .collect(Collectors.toList());
        PageRespDto<PlaceRespDto> pageData=new PageRespDto(totalPage,placeRespDtos,places.getTotalElements());

        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,pageData)

        );
    }
    @PostMapping
    public ResponseEntity<?> save(@ModelAttribute PlaceParamDto placeParamDto) throws IOException {
        //get userId Login
        String username;
        Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal). getUsername();
        } else {
            username = principal. toString();
        }

         List<String> nameImages = fileStorageService.saveMultipartFile(placeParamDto.getFiles(),
                 "user_"+username,fileLocation);
         Place place = new Place();
        BeanUtils.copyProperties(placeParamDto,place);
        Set<ImagePlace> imagePlaces = nameImages.stream().map(nameImage->new ImagePlace(FilenameUtils.separatorsToUnix(nameImage),place)).collect(Collectors.toSet());
        place.setImagePlaces(imagePlaces);
        Place placeResp = placeService.save(place);
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,placeResp)
        );
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable Long id , @ModelAttribute PlaceParamDto placeDto) throws Exception {
        String username;
        Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal). getUsername();
        } else {
            username = principal. toString();
        }

        Place place = placeService
                .findPlaceById(id)
                .orElseThrow(() -> new Exception("User not found on :: " + id));
        BeanUtils.copyProperties(placeDto,place);

        if(placeDto.getFiles()!=null){
            List<String> nameImages = fileStorageService.saveMultipartFile(placeDto.getFiles(),
                    "user_"+username,fileLocation);
            Set<ImagePlace> imagePlaces = nameImages.stream().map(nameImage->new ImagePlace(FilenameUtils.separatorsToUnix(nameImage),place)).collect(Collectors.toSet());
            imagePlaceService.deleteByPlace(place);
            place.setImagePlaces(imagePlaces);
        }
        Place placeResp = placeService.save(place);

        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,placeResp)
        );

    }

    @PutMapping("/view/{id}")
    @javax.transaction.Transactional
    public ResponseEntity<?> updateView(@PathVariable Long id ,@RequestParam int numberView) throws Exception {
        placeRepository.updateView(id,numberView);
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,numberView)
        );

    }

    @PutMapping("/like/{id}")
    @javax.transaction.Transactional
    public ResponseEntity<?> updateLike(@PathVariable Long id ,@RequestParam int numberLike) throws Exception {
        placeRepository.updateLike(id,numberLike);
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,numberLike)
        );

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        try {
            placeService.deleteById(id);
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
