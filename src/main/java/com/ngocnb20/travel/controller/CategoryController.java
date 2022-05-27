package com.ngocnb20.travel.controller;

import com.ngocnb20.travel.constant.StatusRespData;
import com.ngocnb20.travel.model.dto.resp.BaseRespDto;
import com.ngocnb20.travel.model.dto.resp.CategoryDto;
import com.ngocnb20.travel.model.entity.Category;
import com.ngocnb20.travel.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
@CrossOrigin("*")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public ResponseEntity<BaseRespDto> getAll(){
        List<Category> categories=categoryService.findAll();
        List<CategoryDto> categoryDtos=categories.stream()
                .map(c->categoryService.convertDto(c))
                .collect(Collectors.toList());
        return  ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,categoryDtos)
        );

    }
}
