package com.ngocnb20.travel.service.impl;

import com.ngocnb20.travel.model.dto.resp.MenuRespDto;
import com.ngocnb20.travel.repository.MenuRepository;
import com.ngocnb20.travel.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuRepository menuRepository;

    @Override
    public Optional<List<MenuRespDto>> getMenuDto() {
        return menuRepository.getMenuDto();
    }
}
