package com.ngocnb20.travel.controller;

import com.ngocnb20.travel.constant.StatusRespData;
import com.ngocnb20.travel.model.dto.param.BlogPramDto;
import com.ngocnb20.travel.model.dto.resp.BaseRespDto;
import com.ngocnb20.travel.model.dto.resp.BlogRespDto;
import com.ngocnb20.travel.model.dto.resp.PageRespDto;
import com.ngocnb20.travel.model.entity.Blog;
import com.ngocnb20.travel.repository.BlogRepository;
import com.ngocnb20.travel.service.BlogService;
import com.ngocnb20.travel.service.FileStorageService;
import com.ngocnb20.travel.service.impl.UserDetailServiceImpl;
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
@RequestMapping(path = "/blogs",produces = "application/json;charset=UTF-8")
public class BlogController extends BaseController {

    @Autowired
    BlogService blogService;
    @Autowired
    BlogRepository blogRepository;
    @Autowired
    FileStorageService fileStorageService;
    @Autowired
    UserDetailServiceImpl userDetailService;

    @Value("${app.file.location-blog}")
    String fileLocation;



    @GetMapping()
    public ResponseEntity<BaseRespDto> getAllBlog(){
        List<Blog> blogs=blogService.findAll();
        List<BlogRespDto> blogRespDtos=blogs.stream().map(b->blogService.convertDto(b)).collect(Collectors.toList());

        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,blogRespDtos)
        );
    }


    @GetMapping("/page/{page}")
    public ResponseEntity<BaseRespDto> getAllBlogByPage(@PathVariable("page") String page,@RequestParam String search){
        if(Objects.isNull(page)){
            page="0";
        }
        int numberPage=Integer.parseInt(page)-1;
        Page<Blog> blogs=blogService.getBlogs(numberPage,"",search);
        int totalPage=blogs.getTotalPages();
        System.out.println("total item"+blogs.getTotalElements());
        System.out.println("total page"+blogs.getTotalPages());
        List<BlogRespDto> blogRespDtos=blogs.getContent().stream()
                                            .map(b->blogService.convertDto(b))
                                            .collect(Collectors.toList());
        PageRespDto<BlogRespDto> pageData=new PageRespDto(totalPage,blogRespDtos);

        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,pageData)
        );
    }


    @GetMapping(path = "/detail/{id}")
    public ResponseEntity<BaseRespDto> getBlogById(@PathVariable Long id){
        Optional<Blog> blog=blogService.findBlogById(id);

        if(blog.isPresent()){
            return ResponseEntity.ok(
                    BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,blog.get())
            );
        }else {
            return ResponseEntity.ok(
                    BaseRespDto.error(StatusRespData.GET_BY_ID_FAIL)
            );
        }
    }


    @GetMapping(path = "/top/{id}")
    public ResponseEntity<BaseRespDto> getTopBlogNew(@PathVariable Long id){
        List<Blog> blogs= blogRepository.getBlogTop(id);
        List<BlogRespDto> blogRespDtos=blogs.stream().map(b ->blogService.convertDto(b)).collect(Collectors.toList());
        return ResponseEntity.ok(
                    BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,blogRespDtos)
            );
        }
    @GetMapping(path = "/default/{id}")
    public ResponseEntity<BaseRespDto> getTopDefaultBlog(@PathVariable Long id){
        boolean check=false;
        if (id==0){
            check=false;
        }else if (id==1){
            check=true;
        }
        List<Blog> blogs=  blogService.findByStatusBlog(check);
        List<BlogRespDto> blogRespDtos=blogs.stream().map(b ->blogService.convertDto(b)).collect(Collectors.toList());
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,blogRespDtos)
        );
    }

    @PostMapping
    public ResponseEntity<?> save(@ModelAttribute  BlogPramDto blogDto) throws IOException {

        //get userId Login
        String username;
        Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal). getUsername();
        } else {
            username = principal. toString();
        }

        Blog blog = new Blog();
        BeanUtils.copyProperties(blogDto,blog);
        System.out.println(blog.getNumberView());
        String url = fileStorageService.saveFile(blogDto.getFile(),
                "user_"+username,fileLocation );
        String unixPath = FilenameUtils.separatorsToUnix(url);//convert url //
        blog.setImage(unixPath);
        Blog blogResp =  blogService.save(blog);
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,blogResp)
        );
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id , @ModelAttribute  BlogPramDto blogDto) throws Exception {
        System.out.println("ID UPDATE"+id);
        String username;
        Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal). getUsername();
        } else {
            username = principal. toString();
        }

        Blog blog = blogService
                .findBlogById(id)
                .orElseThrow(() -> new Exception("User not found on :: " + id));
        BeanUtils.copyProperties(blogDto,blog);
         if(blogDto.getFile()!=null){
             String url = fileStorageService.saveFile(blogDto.getFile(),
                     "user_"+username,fileLocation );
             String unixPath = FilenameUtils.separatorsToUnix(url);//convert url //
             blog.setImage(unixPath);
         }

        Blog blogResp =  blogService.save(blog);

        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,blogResp)
        );

    }

    @PutMapping("/view/{id}")
    @Transactional
    public ResponseEntity<?> updateView(@PathVariable Long id ,@RequestParam int numberView) throws Exception {
         blogRepository.updateView(id,numberView);
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,numberView)
        );

    }
    @PutMapping("/like/{id}")
    @Transactional
    public ResponseEntity<?> updateLike(@PathVariable Long id ,@RequestParam int numberLike) throws Exception {
        blogRepository.updateLike(id,numberLike);
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,numberLike)
        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        try {
            blogService.deleteById(id);
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



