package com.ngocnb20.travel.service.impl;


import com.ngocnb20.travel.model.entity.Member;
import com.ngocnb20.travel.model.entity.RoleMember;
import com.ngocnb20.travel.service.MemberService;
import com.ngocnb20.travel.service.RoleMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    MemberService memberService;
    @Autowired
    RoleMemberService roleMemberService;
    //check Login như nào
    @Transactional//khi quan hệ 1-n bị đóng session gọi cái này giúp các hàm call jpa đc gọi chạy cùng session
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> memberOpt = memberService.getMemberByEmail(username);
        System.out.println("hi");
        if (!memberOpt.isPresent()){
            throw new UsernameNotFoundException("The user name do not exits"+username);
        }


        Member member = memberOpt.get();
        //Fix ME: Get From database getRole
        List<RoleMember> list = roleMemberService.getMemberRole(member);
        //Hibernate.initialize(); quan hệ nhiều-nhiều bị đóng section
        List<GrantedAuthority> authorities = list.stream()
                .map(role->new SimpleGrantedAuthority(role.getRole().getRoleName()))
                .collect(Collectors.toList());

        //add in securityContext
        return new User(String.valueOf(member.getId()),member.getPassword(),authorities);
    }
}
