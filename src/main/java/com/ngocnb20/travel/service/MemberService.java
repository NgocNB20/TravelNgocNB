package com.ngocnb20.travel.service;


import com.ngocnb20.travel.model.entity.Member;
import com.nimbusds.jose.JOSEException;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface MemberService extends BaseService {
    List<Member> findAll();
    Optional<Member> getMemberId(Long id);
    Optional<Member> getMemberByEmail(String email);
    boolean getByEmail(String email) throws ParseException, JOSEException;
    Member save (Member member);
    void deleteById(Long id);
    Page<Member> getDataByPage(Integer page, String sort, String keyWord);

}
