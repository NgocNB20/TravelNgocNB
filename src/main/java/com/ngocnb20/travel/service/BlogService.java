package com.ngocnb20.travel.service;

import com.ngocnb20.travel.model.dto.resp.BlogRespDto;
import com.ngocnb20.travel.model.entity.Blog;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface BlogService extends BaseService {
    List<Blog> findAll();
    Blog save(Blog blog);
    Optional<Blog> findBlogById(Long id);
    Page<Blog> getBlogs(Integer page, String sort, String keyWord);//search xong rồi chia page
    BlogRespDto convertDto(Blog blog);
    void deleteById(Long id);

    List<Blog> findByStatusBlog(boolean status);


}
