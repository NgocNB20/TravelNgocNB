package com.ngocnb20.travel.service;

import com.ngocnb20.travel.model.dto.resp.AgencyRespDto;
import com.ngocnb20.travel.model.entity.Agency;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface AgencyService {
    List<Agency> findAll();
    Optional<Agency> findById(Long id);
    Page<Agency> getDataByPage(Integer page, String sort, String keyWord);
    AgencyRespDto ConvertDto(Agency agency);
    Agency save(Agency agency);
    void deleteById(Long id);

}
