package com.ngocnb20.travel.repository;

import com.ngocnb20.travel.model.entity.Hotel;
import com.ngocnb20.travel.model.entity.Member;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MemberRepository extends BaseRepository<Member,Long>, JpaSpecificationExecutor<Member> {
    Optional<Member> findByEmail(String email);



}
