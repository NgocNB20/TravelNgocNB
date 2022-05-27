package com.ngocnb20.travel.service.impl;

import com.ngocnb20.travel.model.dto.resp.CategoryDto;
import com.ngocnb20.travel.model.entity.Category;
import com.ngocnb20.travel.repository.CategoryRepository;
import com.ngocnb20.travel.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryDto convertDto(Category category) {
        CategoryDto categoryDto=new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }


}
