package com.ngocnb20.travel.service.impl;

import com.ngocnb20.travel.constant.DefaultSort;
import com.ngocnb20.travel.model.dto.resp.BlogRespDto;
import com.ngocnb20.travel.model.entity.Blog;
import com.ngocnb20.travel.repository.BlogRepository;
import com.ngocnb20.travel.service.BlogService;
import com.ngocnb20.travel.service.spec.BlogSpec;
import org.apache.commons.lang3.StringUtils;
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

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogRepository blogRepository;

    @Value("${app.page.size}")
    private Integer pageSize;

    @Override
    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    @Override
    public Blog save(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public Optional<Blog> findBlogById(Long id) {
        return blogRepository.findById(id);
    }

    @Override
    public Page<Blog> getBlogs(Integer page, String sort, String keyWord) {
        Pageable pageable = PageRequest.of(page,pageSize,generateSort(sort));
        Specification<Blog> blogSpec;
        if(StringUtils.isNotBlank(keyWord)){
            blogSpec = BlogSpec.searchByKeyword(keyWord);
            return blogRepository.findAll(blogSpec,pageable);
        }
        return blogRepository.findAll(pageable);
    }

    private Sort generateSort(String sort){
        String defaultSort = DefaultSort.SORT_BLOG;
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
    public BlogRespDto convertDto(Blog blog) {
        BlogRespDto blogRespDto=new BlogRespDto();
        blogRespDto.setId(blog.getId());
        blogRespDto.setDate(blog.getDate());
        blogRespDto.setDetail(blog.getDetail());
        blogRespDto.setTitle(blog.getTitle());
        blogRespDto.setImage(blog.getImage());
//        BeanUtils.copyProperties(blogRespDtoDto,blog);
        blogRespDto.setNumberComment(blog.getNumberComment());
        blogRespDto.setNumberView(blog.getNumberView());
        blogRespDto.setNumberLike(blog.getNumberLike());
        blogRespDto.setDetailSummary(blog.getDetailSummary());
        blogRespDto.setStatusBlog(blog.statusBlog);

        blogRespDto.setCreateDate(blog.getCreateDate());
        blogRespDto.setUpdateTime(blog.getUpdateTime());
        blogRespDto.setCreateById(blog.getCreateById());
        blogRespDto.setLastModifiedBy(blog.getLastModifiedBy());

        return blogRespDto;
    }

    @Override
    public void deleteById(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public List<Blog> findByStatusBlog(boolean status) {
        return blogRepository.findByStatusBlog(status);
    }


}
