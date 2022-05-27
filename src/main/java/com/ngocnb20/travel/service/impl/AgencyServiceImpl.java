package com.ngocnb20.travel.service.impl;

import com.ngocnb20.travel.constant.DefaultSort;
import com.ngocnb20.travel.model.dto.resp.AgencyRespDto;
import com.ngocnb20.travel.model.entity.Agency;
import com.ngocnb20.travel.model.entity.Blog;
import com.ngocnb20.travel.repository.AgencyRepository;
import com.ngocnb20.travel.service.AgencyService;
import com.ngocnb20.travel.service.spec.AgencySpec;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AgencyServiceImpl implements AgencyService {

    @Autowired
    AgencyRepository agencyRepository;

    @Value("${app.page.size}")
    private Integer pageSize;


    @Override
    public List<Agency> findAll() {
        return agencyRepository.findAll();
    }

    @Override
    public Optional<Agency> findById(Long id) {
        return agencyRepository.findById(id);
    }

    @Override
    public Page<Agency> getDataByPage(Integer page, String sort, String keyWord) {
        Pageable pageable = PageRequest.of(page,pageSize,generateSort(sort));
        Specification<Agency> agencySpec;
        if(StringUtils.isNotBlank(keyWord)){
            agencySpec = AgencySpec.searchByKeyword(keyWord);
            return agencyRepository.findAll(agencySpec,pageable);
        }
        return agencyRepository.findAll(pageable);
    }


    private Sort generateSort(String sort){
        String defaultSort = DefaultSort.SORT_AGENCY;
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
    public AgencyRespDto ConvertDto(Agency agency) {
        AgencyRespDto agencyDto = new AgencyRespDto();
        agencyDto.setId(agency.getId());
        agencyDto.setDetail(agency.getDetail());
        agencyDto.setImage(agency.getImage());
        agencyDto.setAddress(agency.getAddress());
        agencyDto.setEmail(agency.getEmail());
        agencyDto.setPhone(agency.getPhone());
        agencyDto.setNumberLike(agency.getNumberLike());
        agencyDto.setNumberView(agency.getNumberView());
        agencyDto.setName(agency.getName());
        agencyDto.setUrlWeb(agency.getUrlWeb());

        agencyDto.setCreateDate(agency.getCreateDate());
        agencyDto.setUpdateTime(agency.getUpdateTime());
        agencyDto.setCreateById(agency.getCreateById());
        agencyDto.setLastModifiedBy(agency.getLastModifiedBy());





        return agencyDto;
    }

    @Override
    public Agency save(Agency agency) {
        return agencyRepository.save(agency);
    }

    @Override
    public void deleteById(Long id) {
        agencyRepository.deleteById(id);
    }

}
