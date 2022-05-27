package com.ngocnb20.travel.controller;

import com.ngocnb20.travel.constant.StatusRespData;
import com.ngocnb20.travel.model.dto.resp.BaseRespDto;
import com.ngocnb20.travel.model.dto.resp.MenuRespDto;
import com.ngocnb20.travel.model.entity.Blog;
import com.ngocnb20.travel.service.BlogService;
import com.ngocnb20.travel.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping( path= "/home")
public class HomeController extends BaseController{

    @Autowired
    MenuService menuService;




    @GetMapping(path = "/menu",produces = "application/json;charset=UTF-8")
    public ResponseEntity<BaseRespDto> getMenu(){
        System.out.println(menuService.getMenuDto().get().get(1).getName());
        List<MenuRespDto> menus=menuService.getMenuDto().get();

        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,menus)
        );
    }



}
