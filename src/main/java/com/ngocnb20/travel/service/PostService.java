package com.ngocnb20.travel.service;

import com.ngocnb20.travel.model.dto.resp.BlogRespDto;
import com.ngocnb20.travel.model.dto.resp.PostRespDto;
import com.ngocnb20.travel.model.entity.Blog;
import com.ngocnb20.travel.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface PostService extends BaseService {
    List<Post> findAll();
    Post save(Post post);
    Optional<Post> findPostById(Long id);
    Page<Post> getPosts(Integer page, String sort, String keyWord);//search xong rồi chia page
    PostRespDto convertDto(Post post);
    void deleteById(Long id);

}
