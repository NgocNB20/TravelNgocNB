package com.ngocnb20.travel.controller;


import com.ngocnb20.travel.model.dto.MemberDto;
import com.ngocnb20.travel.model.entity.Member;
import com.ngocnb20.travel.model.entity.Role;
import com.ngocnb20.travel.model.entity.RoleMember;
import com.ngocnb20.travel.service.MemberService;
import com.ngocnb20.travel.service.RoleMemberService;
import com.ngocnb20.travel.service.RoleService;
import com.ngocnb20.travel.service.impl.UserDetailServiceImpl;
import com.ngocnb20.travel.util.TokenUtil;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class LoginResource {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserDetailServiceImpl userDetailsService;
    @Autowired
    MemberService memberService;
    @Autowired
    RoleMemberService roleMemberService;
    @Autowired
    RoleService roleService;

    @GetMapping
    public String getData(){
        System.out.println("get login");
        return "abcd";
    }


    @PostMapping("/register")
    public ResponseEntity<?> submitRegister(@ModelAttribute  MemberDto memberDto) {
        Member member = new Member();
        BeanUtils.copyProperties(memberDto,member);
        member.setPassword(new BCryptPasswordEncoder().encode(memberDto.getPassword()));
        Role role = roleService.findByRoleName("ROLE_USER").get();
        RoleMember roleMember = new RoleMember();
        roleMember.setMember(member);
        roleMember.setRole(role);
        List<RoleMember> list = new ArrayList<>();
        list.add(roleMember);
        member.setRoleMembers(list);
        Member memberResp = memberService.save(member);
        return ResponseEntity.ok(memberResp);

    }





    @PostMapping(path = "/login")
    public ResponseEntity<?> submitLogin(@ModelAttribute MemberDto member) throws ParseException, JOSEException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(member.getEmail());
        if (Objects.nonNull(userDetails)
                && passwordEncoder.matches(member.getPassword(),userDetails.getPassword())){

            Optional<Member> memberOpt = memberService.getMemberByEmail((member.getEmail()));
            Member memberLogin = memberOpt.get();
            List<RoleMember> list = roleMemberService.getMemberRole(memberLogin);
            List<String> authorities = list.stream()
                    .map(role->role.getRole().getRoleName())
                    .collect(Collectors.toList());

            Map<String, Object> objectMap = new HashMap<>();
            objectMap.put("userId",userDetails.getUsername());
            objectMap.put("role",authorities);

            String token = TokenUtil.encode(objectMap);
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }



    @PostMapping (path = "/exits-email")
    public ResponseEntity<?> checkEmail(@RequestBody @Valid MemberDto memberDto){

        Optional<Member> member = memberService.getMemberByEmail(memberDto.getEmail());
        if(member.isPresent()){
            return ResponseEntity.status(HttpStatus.FOUND).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

}
