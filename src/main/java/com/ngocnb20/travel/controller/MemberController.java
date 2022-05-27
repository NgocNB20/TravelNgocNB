package com.ngocnb20.travel.controller;

import com.ngocnb20.travel.constant.StatusRespData;
import com.ngocnb20.travel.model.dto.param.MemberParamDto;
import com.ngocnb20.travel.model.dto.param.PlaceParamDto;
import com.ngocnb20.travel.model.dto.resp.BaseRespDto;
import com.ngocnb20.travel.model.dto.resp.PageRespDto;
import com.ngocnb20.travel.model.dto.resp.PlaceRespDto;
import com.ngocnb20.travel.model.entity.*;
import com.ngocnb20.travel.service.MemberService;
import com.ngocnb20.travel.service.RoleMemberService;
import com.ngocnb20.travel.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/members")
@CrossOrigin("*")
public class MemberController {
    @Autowired
    MemberService memberService;

    @Autowired
    RoleService roleService;

    @Autowired
    RoleMemberService roleMemberService;


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        try {
            memberService.deleteById(id);
            return ResponseEntity.ok(
                    BaseRespDto.success(StatusRespData.REMOVE_DATA_SUCCESS+id,id)
            );
        }catch (Exception e){
            return ResponseEntity.ok(
                    BaseRespDto.success(StatusRespData.REMOVE_FAIL+id,id)
            );
        }
    }

    @GetMapping({"/page/{page}"})
    public ResponseEntity<BaseRespDto> getAllBlogByPage(@PathVariable("page") String page,@RequestParam String search){
        if(Objects.isNull(page)){
            page="0";
        }
        int numberPage=Integer.parseInt(page)-1;
        Page<Member> members = memberService.getDataByPage(numberPage,"",search);
        int totalPage = members.getTotalPages();
        System.out.println("total item"+members.getTotalElements());
        System.out.println("total page"+members.getTotalPages());

        PageRespDto<Member> pageData=new PageRespDto(totalPage,members.getContent(),members.getTotalElements());

        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.GET_DATA_SUCCESS,pageData)

        );
    }

    @PostMapping
    public ResponseEntity<?> save(@ModelAttribute MemberParamDto memberParamDto) throws IOException {
        Member member = new Member();
        BeanUtils.copyProperties(memberParamDto,member);
        member.setPassword(new BCryptPasswordEncoder().encode(memberParamDto.getPassword()));

        List<RoleMember> list = memberParamDto.getIdRole().stream().map(id->{
            Role role = roleService.findById(id).get();
           return new RoleMember(role,member);
        }).collect(Collectors.toList());
        member.setRoleMembers(list);
        Member memberResp = memberService.save(member);

        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,memberResp)
        );
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable Long id , @ModelAttribute MemberParamDto memberParamDto) throws Exception {
        Member member = memberService.getMemberId(id).orElseThrow(() -> new Exception("User not found on :: " + id));
        BeanUtils.copyProperties(memberParamDto,member);
//        List<RoleMember> list = memberParamDto.getIdRole().stream().map(idMember->{
//            Role role = roleService.findById(idMember).get();
//            return new RoleMember(role,member);
//        }).collect(Collectors.toList());
//        roleMemberService.deleteByMember(member);
//        member.setRoleMembers(list);
        Member memberResp = memberService.save(member);
        return ResponseEntity.ok(
                BaseRespDto.success(StatusRespData.UPDATE_DATA_SUCCESS,memberResp)
        );

    }

}
