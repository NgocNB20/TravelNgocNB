package com.ngocnb20.travel.repository;

import com.ngocnb20.travel.model.entity.Member;
import com.ngocnb20.travel.model.entity.Place;
import com.ngocnb20.travel.model.entity.Role;
import com.ngocnb20.travel.model.entity.RoleMember;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RoleMemberRepository extends CrudRepository<RoleMember,Long> , JpaSpecificationExecutor<RoleMember> {
    List<RoleMember> findByMember(Member member);
    List<RoleMember> findByRole(Role role);
    void deleteRoleMemberByMember(Member member);

}
