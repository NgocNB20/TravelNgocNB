package com.ngocnb20.travel.service;

import com.ngocnb20.travel.model.entity.Member;
import com.ngocnb20.travel.model.entity.Place;
import com.ngocnb20.travel.model.entity.Role;
import com.ngocnb20.travel.model.entity.RoleMember;

import java.util.List;

public interface RoleMemberService extends BaseService{
    List<RoleMember>  getMemberRole(Member member);
    List<RoleMember>  getMemberRoleByRole(Role role);
    void  deleteByMember(Member member);
}
