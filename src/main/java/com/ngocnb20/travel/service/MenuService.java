package com.ngocnb20.travel.service;

import com.ngocnb20.travel.model.dto.resp.MenuRespDto;

import java.util.List;
import java.util.Optional;

public interface MenuService {
    Optional<List<MenuRespDto>> getMenuDto();
}
