package com.ngocnb20.travel.service.impl;

import com.ngocnb20.travel.constant.DefaultSort;
import com.ngocnb20.travel.model.entity.Member;
import com.ngocnb20.travel.model.entity.Place;
import com.ngocnb20.travel.repository.MemberRepository;
import com.ngocnb20.travel.service.MemberService;
import com.ngocnb20.travel.service.spec.MemberSpec;
import com.ngocnb20.travel.service.spec.PlaceSpec;
import com.ngocnb20.travel.util.TokenUtil;
import com.nimbusds.jose.JOSEException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberRepository memberRepository;
    @Value("${app.page.size}")
    private Integer pageSize;


    @Override
    public List<Member> findAll() {
        return null;
    }

    @Override
    public Optional<Member> getMemberId(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public Optional<Member> getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

   @Override
    public boolean getByEmail(String email)  {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()){
//           Member members = member.get();
//            Map<String,Object> param = new HashMap<>();
//            param.put("username",members.getEmail());
//            param.put("Date","19/11/2000");
//
//            param.put("Id",3L);
//            String token = null;
//            try {
//                token = TokenUtil.encode(param);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            members.setResetPassword(token);
//            Member member1 = new Member();
//            member1.setId(4L);
//            member1.setEmail(member.get().getEmail());
//            member1.setPassword(member.get().getPassword());
//            member1.setResetPassword(member.get().getResetPassword());
//            memberRepository.save(member1);
            //GỬI MAIL
            return true;
        }
        return false;

    }

    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }


    @Override
    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    @Override
    public Page<Member> getDataByPage(Integer page, String sort, String keyWord) {
        Pageable pageable = PageRequest.of(page,pageSize,generateSort(sort));
        Specification<Member> memberSpec;

        if(StringUtils.isNotBlank(keyWord)){
            memberSpec = MemberSpec.searchByKeyword(keyWord);
            return memberRepository.findAll(memberSpec,pageable);
        }
        return memberRepository.findAll(pageable);
    }

    private Sort generateSort(String sort){
        String defaultSort = DefaultSort.SORT_MEMBER;
        Sort.Order orderByDefault = Sort.Order.desc(defaultSort);
        Sort.Order orderBySortField = null;

        if(StringUtils.isBlank(sort)){
            return Sort.by(orderByDefault);
        }

        if(sort.startsWith("-")){
            orderBySortField=Sort.Order.desc(sort.substring(1)); //substring(1) cắt bỏ kí tự "-"
        }else {
            orderBySortField=Sort.Order.asc(sort);
        }
        return Sort.by(orderBySortField,orderByDefault);
    }

}
