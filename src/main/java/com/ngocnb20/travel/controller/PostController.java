package com.ngocnb20.travel.controller;

import com.ngocnb20.travel.constant.StatusRespData;
import com.ngocnb20.travel.model.dto.param.PostParamDto;
import com.ngocnb20.travel.model.dto.resp.BaseRespDto;
import com.ngocnb20.travel.model.dto.resp.PageRespDto;
import com.ngocnb20.travel.model.dto.resp.PostRespDto;
import com.ngocnb20.travel.model.entity.Post;
import com.ngocnb20.travel.repository.PostRepository;
import com.ngocnb20.travel.service.FileStorageService;
import com.ngocnb20.travel.service.PostService;
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
@RequestMapping(path = "/posts",produces = "application/json;charset=UTF-8")
public class PostController extends  BaseController{

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    FileStorageService fileStorageService;
    @Autowired
    UserDetailServiceImpl userDetailService;

    @Value("${app.file.location-post}")
    String fileLocation;

    @GetMapping()
    public ResponseEntity<BaseRespDto> getAll(){
        List<Post> posts = postService.findAll();
        List<PostRespDto> postRespDtos = posts.stream().map(p->postService.convertDto(p)).collect(Collectors.toList());

        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,postRespDtos)
        );
    }


    @GetMapping("/page/{page}")
    public ResponseEntity<BaseRespDto> getAllByPage(@PathVariable("page") String page, @RequestParam String search){
        if(Objects.isNull(page)){
            page="0";
        }
        int numberPage=Integer.parseInt(page)-1;
        Page<Post> posts = postService.getPosts(numberPage,"",search);
        int totalPage = posts.getTotalPages();
        System.out.println("total item"+posts.getTotalElements());
        System.out.println("total page"+posts.getTotalPages());
        List<PostRespDto> postRespDtos = posts.getContent().stream()
                .map(p->postService.convertDto(p))
                .collect(Collectors.toList());
        PageRespDto<PostRespDto> pageData = new PageRespDto(totalPage,postRespDtos);

        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,pageData)
        );
    }


    @GetMapping(path = "/detail/{id}")
    public ResponseEntity<BaseRespDto> getBlogById(@PathVariable Long id){
        Optional<Post> post = postService.findPostById(id);

        if(post.isPresent()){
            return ResponseEntity.ok(
                    BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,post.get())
            );
        }else {
            return ResponseEntity.ok(
                    BaseRespDto.error(StatusRespData.GET_BY_ID_FAIL)
            );
        }
    }


    @GetMapping(path = "/top/{id}")
    public ResponseEntity<BaseRespDto> getTopBlogNew(@PathVariable Long id){
        List<Post> posts = postRepository.getPostTop(id);
        List<PostRespDto> postRespDtos = posts.stream().map(p -> postService.convertDto(p)).collect(Collectors.toList());
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,postRespDtos)
        );
    }



    @PostMapping
    public ResponseEntity<?> save(@ModelAttribute PostParamDto postDto) throws IOException {

        //get userId Login
        String username;
        Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal). getUsername();
        } else {
            username = principal. toString();
        }

        Post post = new Post();
        BeanUtils.copyProperties(postDto, post);
        System.out.println(post.getNumberView());
        String url = fileStorageService.saveFile(postDto.getFile(),
                "user_"+username,fileLocation );
        String unixPath = FilenameUtils.separatorsToUnix(url);//convert url //
        post.setImage(unixPath);
        Post postResp =  postService.save(post);
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,postResp)
        );
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id , @ModelAttribute  PostParamDto postDto) throws Exception {
        System.out.println("ID UPDATE"+id);
        String username;
        Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal). getUsername();
        } else {
            username = principal. toString();
        }

        Post post = postService
                .findPostById(id)
                .orElseThrow(() -> new Exception("Post not found on :: " + id));
        BeanUtils.copyProperties(postDto, post);
        if (postDto.getFile()!=null){
            String url = fileStorageService.saveFile(postDto.getFile(),
                    "user_"+username,fileLocation );
            String unixPath = FilenameUtils.separatorsToUnix(url);//convert url //
            post.setImage(unixPath);
        }
        Post postResp =  postService.save(post);

        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS, postResp)
        );

    }

    @PutMapping("/view/{id}")
    @Transactional
    public ResponseEntity<?> updateView(@PathVariable Long id ,@RequestParam int numberView) throws Exception {
        postRepository.updateView(id,numberView);
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,numberView)
        );

    }

    @PutMapping("/like/{id}")
    @Transactional
    public ResponseEntity<?> updateLike(@PathVariable Long id ,@RequestParam int numberLike) throws Exception {
        postRepository.updateLike(id,numberLike);
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,numberLike)
        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        try {
            postService.deleteById(id);
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
