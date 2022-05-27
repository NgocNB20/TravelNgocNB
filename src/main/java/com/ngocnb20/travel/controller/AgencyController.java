package com.ngocnb20.travel.controller;

import com.ngocnb20.travel.constant.StatusRespData;
import com.ngocnb20.travel.model.dto.param.AgencyParamDto;
import com.ngocnb20.travel.model.dto.param.BlogPramDto;
import com.ngocnb20.travel.model.dto.resp.AgencyRespDto;
import com.ngocnb20.travel.model.dto.resp.BaseRespDto;
import com.ngocnb20.travel.model.dto.resp.BlogRespDto;
import com.ngocnb20.travel.model.dto.resp.PageRespDto;
import com.ngocnb20.travel.model.entity.Agency;
import com.ngocnb20.travel.model.entity.Blog;
import com.ngocnb20.travel.repository.AgencyRepository;
import com.ngocnb20.travel.service.AgencyService;
import com.ngocnb20.travel.service.FileStorageService;
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
@RequestMapping(path = "/agencies",produces = "application/json;charset=UTF-8")
@CrossOrigin("*")
public class AgencyController {
    @Autowired
    AgencyRepository agencyRepository;
    @Autowired
    AgencyService agencyService;
    @Autowired
    FileStorageService fileStorageService;

    @Value("${app.file.location-agency}")
    String fileLocation;

    @GetMapping()
    public ResponseEntity<BaseRespDto> getAllAgency(){
        List<Agency> agencies=agencyService.findAll();
        List<AgencyRespDto> agencyDtos  =   agencies.stream().map(a->agencyService.ConvertDto(a)).collect(Collectors.toList());

        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,agencyDtos)
        );
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<BaseRespDto> getByPage(@PathVariable("page") String page, @RequestParam String search){
        if(Objects.isNull(page)){
            page="0";
        }
        int numberPage=Integer.parseInt(page)-1;
        Page<Agency> agencies=agencyService.getDataByPage(numberPage,"",search);
        int totalPage=agencies.getTotalPages();
        System.out.println("total item"+agencies.getTotalElements());
        System.out.println("total page"+agencies.getTotalPages());
        List<AgencyRespDto> agencyDtos=agencies.getContent().stream()
                .map(a->agencyService.ConvertDto(a))
                .collect(Collectors.toList());
        PageRespDto<AgencyRespDto> pageData=new PageRespDto(totalPage,agencyDtos);

        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,pageData)

        );
    }

    @PostMapping
    public ResponseEntity<?> save(@ModelAttribute AgencyParamDto agencyDto) throws IOException {

        //get userId Login
        String username;
        Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal). getUsername();
        } else {
            username = principal. toString();
        }

        Agency agency = new Agency();
        BeanUtils.copyProperties(agencyDto,agency);

        String url = fileStorageService.saveFile(agencyDto.getFile(),
                "user_"+username,fileLocation );
        String unixPath = FilenameUtils.separatorsToUnix(url);//convert url //
        agency.setImage(unixPath);
        Agency agencyResp =  agencyService.save(agency);
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.SAVE_DATA_SUCCESS,agencyResp)
        );
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id , @ModelAttribute  AgencyParamDto agencyDto) throws Exception {
        System.out.println("a"+agencyDto.getDetail());
        System.out.println(agencyDto.getFile());
        String username;
        Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal). getUsername();
        } else {
            username = principal. toString();
        }

        Agency agency = agencyService
                .findById(id)
                .orElseThrow(() -> new Exception("User not found on :: " + id));
        BeanUtils.copyProperties(agencyDto,agency);
        if (agencyDto.getFile()!=null){
            String url = fileStorageService.saveFile(agencyDto.getFile(),
                    "user_"+username,fileLocation );
            String unixPath = FilenameUtils.separatorsToUnix(url);//convert url //
            agency.setImage(unixPath);
        }
        Agency  agencyResp =  agencyService.save(agency);

        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,agencyResp)
        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        try {
            agencyService.deleteById(id);
            return ResponseEntity.ok(
                    BaseRespDto.success(StatusRespData.REMOVE_DATA_SUCCESS+id,id)
            );
        }catch (Exception e){
            return ResponseEntity.ok(
                    BaseRespDto.success(StatusRespData.REMOVE_FAIL+id,id)
            );
        }
    }

    @PutMapping("/view/{id}")
    @Transactional
    public ResponseEntity<?> updateView(@PathVariable Long id ,@RequestParam int numberView) throws Exception {
        agencyRepository.updateView(id,numberView);
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,numberView)
        );

    }
    @PutMapping("/like/{id}")
    @Transactional
    public ResponseEntity<?> updateLike(@PathVariable Long id ,@RequestParam int numberLike) throws Exception {
        agencyRepository.updateLike(id,numberLike);
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,numberLike)
        );

    }

    @GetMapping(path = "/detail/{id}")
    public ResponseEntity<BaseRespDto> getBlogById(@PathVariable Long id){
        Optional<Agency> agency =   agencyService.findById(id);

        if(agency.isPresent()){
            return ResponseEntity.ok(
                    BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,agency.get())
            );
        }else {
            return ResponseEntity.ok(
                    BaseRespDto.error(StatusRespData.GET_BY_ID_FAIL)
            );
        }
    }

}
