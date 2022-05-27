package com.ngocnb20.travel.service.impl;

import com.ngocnb20.travel.constant.DefaultSort;
import com.ngocnb20.travel.model.dto.resp.BlogRespDto;
import com.ngocnb20.travel.model.dto.resp.PostRespDto;
import com.ngocnb20.travel.model.entity.Blog;
import com.ngocnb20.travel.model.entity.Post;
import com.ngocnb20.travel.repository.PostRepository;
import com.ngocnb20.travel.service.PostService;
import com.ngocnb20.travel.service.spec.BlogSpec;
import com.ngocnb20.travel.service.spec.PostSpec;
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
public class PostServiceImpl implements PostService {


    @Autowired
    PostRepository postRepository;
    @Value("${app.page.size}")
    private Integer pageSize;


    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Optional<Post> findPostById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public Page<Post> getPosts(Integer page, String sort, String keyWord) {
        Pageable pageable = PageRequest.of(page,pageSize,generateSort(sort));
        Specification<Post> postSpec;
        if(StringUtils.isNotBlank(keyWord)){
            postSpec = PostSpec.searchByKeyword(keyWord);
            return postRepository.findAll(postSpec,pageable);
        }
        return postRepository.findAll(pageable);
    }


    @Override
    public PostRespDto convertDto(Post post) {
        PostRespDto postRespDto = new PostRespDto();
        postRespDto.setId(post.getId());
        postRespDto.setDate(post.getDate());
        postRespDto.setDetail(post.getDetail());
        postRespDto.setTitle(post.getTitle());
        postRespDto.setImage(post.getImage());
//        BeanUtils.copyProperties(blogRespDtoDto,blog);
        postRespDto.setNumberComment(post.getNumberComment());
        postRespDto.setNumberView(post.getNumberView());
        postRespDto.setNumberLike(post.getNumberLike());
        postRespDto.setDetailSummary(post.getDetailSummary());


        postRespDto.setCreateDate(post.getCreateDate());
        postRespDto.setUpdateTime(post.getUpdateTime());
        postRespDto.setCreateById(post.getCreateById());
        postRespDto.setLastModifiedBy(post.getLastModifiedBy());
        return postRespDto;
    }

    @Override
    public void deleteById(Long id) {
        postRepository.deleteById(id);

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
}
