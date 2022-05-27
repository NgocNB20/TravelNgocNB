package com.ngocnb20.travel.repository;

import com.ngocnb20.travel.model.dto.resp.MenuRespDto;
import com.ngocnb20.travel.model.entity.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface MenuRepository extends BaseRepository<Menu,Long> {
    @Query("SELECT new com.ngocnb20.travel.model.dto.resp.MenuRespDto(m.name,m.url) FROM Menu m where m.status=true ")
    Optional<List<MenuRespDto>> getMenuDto();
}
