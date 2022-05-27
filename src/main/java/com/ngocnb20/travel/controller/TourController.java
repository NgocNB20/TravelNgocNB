package com.ngocnb20.travel.controller;

import com.ngocnb20.travel.constant.StatusRespData;
import com.ngocnb20.travel.model.dto.param.PlaceParamDto;
import com.ngocnb20.travel.model.dto.param.TourParamDto;
import com.ngocnb20.travel.model.dto.resp.BaseRespDto;
import com.ngocnb20.travel.model.dto.resp.PageRespDto;
import com.ngocnb20.travel.model.dto.resp.TourRespDto;
import com.ngocnb20.travel.model.entity.ImagePlace;
import com.ngocnb20.travel.model.entity.ImageTour;
import com.ngocnb20.travel.model.entity.Place;
import com.ngocnb20.travel.model.entity.Tour;
import com.ngocnb20.travel.repository.TourRepository;
import com.ngocnb20.travel.service.FileStorageService;
import com.ngocnb20.travel.service.ImageTourService;
import com.ngocnb20.travel.service.TourService;
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
@RequestMapping(path = "/tours",produces = "application/json;charset=UTF-8")
@CrossOrigin("*")
public class TourController {

    @Autowired
    TourRepository tourRepository;

    @Autowired
    TourService tourService;
    @Autowired
    FileStorageService fileStorageService;
    @Value("${app.file.location-tour}")
    String fileLocation;
    @Autowired
    ImageTourService imageTourService;


    @GetMapping()
    public ResponseEntity<BaseRespDto>  getAllTour(){
        List<Tour> tours = tourService.findAll();
        List<TourRespDto> tourRespDtos=tours.stream().map(t->tourService.ConvertDto(t))
                                                      .collect(Collectors.toList());
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,tourRespDtos)
        );
    }

    @GetMapping(path = "/detail/{id}")
    public ResponseEntity<BaseRespDto> getTourById(@PathVariable Long id){
        Optional<Tour> tour=tourService.findTourById(id);

        if(tour.isPresent()){
            return ResponseEntity.ok(
                    BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,tour.get())
            );
        }
        else {
            return ResponseEntity.ok(
                    BaseRespDto.error(StatusRespData.GET_BY_ID_FAIL)
            );
        }
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<BaseRespDto> getAllBlogByPage(@PathVariable("page") String page,@RequestParam String search){
        if(Objects.isNull(page)){
            page="0";
        }
        int numberPage=Integer.parseInt(page)-1;
        Page<Tour> tours = tourService.getDataByPage(numberPage,"",search);
        int totalPage = tours.getTotalPages();
        System.out.println("total item"+tours.getTotalElements());
        System.out.println("total page"+tours.getTotalPages());

        List<TourRespDto> tourRespDtos = tours.getContent().stream()
                .map(t->tourService.ConvertDto(t))
                .collect(Collectors.toList());
        PageRespDto<TourRespDto> pageData=new PageRespDto(totalPage,tourRespDtos);

        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,pageData)

        );
    }

    @PostMapping
    public ResponseEntity<?> save(@ModelAttribute TourParamDto tourParamDto) throws IOException {
        //get userId Login
        String username;
        Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal). getUsername();
        } else {
            username = principal. toString();
        }

        List<String> nameImages = fileStorageService.saveMultipartFile(tourParamDto.getFiles(),
                "user_"+username,fileLocation);
        Tour tour = new Tour();
        BeanUtils.copyProperties(tourParamDto,tour);
        Set<ImageTour> imageTours = nameImages.stream().map(nameImage->new ImageTour(FilenameUtils.separatorsToUnix(nameImage),tour)).collect(Collectors.toSet());
        tour.setImageTours(imageTours);
        Tour tourResp = tourService.save(tour);
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,tourResp)
        );
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable Long id , @ModelAttribute TourParamDto tourParamDto) throws Exception {
        System.out.println("ID UPDATE"+id);
        String username;
        Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal). getUsername();
        } else {
            username = principal. toString();
        }

        Tour tour = tourService
                .findTourById(id)
                .orElseThrow(() -> new Exception("Tour not found on :: " + id));
        BeanUtils.copyProperties(tourParamDto,tour);
        if(tourParamDto.getFiles()!=null){
            List<String> nameImages = fileStorageService.saveMultipartFile(tourParamDto.getFiles(),
                    "user_"+username,fileLocation);
            Set<ImageTour> imageTours = nameImages.stream().map(nameImage->new ImageTour(FilenameUtils.separatorsToUnix(nameImage),tour)).collect(Collectors.toSet());
            imageTourService.deleteByTour(tour);
            tour.setImageTours(imageTours);
        }
        Tour tourResp = tourService.save(tour);

        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,tourResp)
        );

    }

    @PutMapping("/view/{id}")
    @javax.transaction.Transactional
    public ResponseEntity<?> updateView(@PathVariable Long id ,@RequestParam int numberView) throws Exception {
        tourRepository.updateView(id,numberView);
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,numberView)
        );

    }

    @PutMapping("/like/{id}")
    @javax.transaction.Transactional
    public ResponseEntity<?> updateLike(@PathVariable Long id ,@RequestParam int numberLike) throws Exception {
        tourRepository.updateLike(id,numberLike);
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,numberLike)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        try {
            tourService.deleteById(id);
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
