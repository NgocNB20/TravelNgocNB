package com.ngocnb20.travel.service;


import com.ngocnb20.travel.model.dto.resp.BlogRespDto;
import com.ngocnb20.travel.model.dto.resp.CategoryDto;
import com.ngocnb20.travel.model.entity.Blog;
import com.ngocnb20.travel.model.entity.Category;
import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    CategoryDto convertDto(Category category);

}
