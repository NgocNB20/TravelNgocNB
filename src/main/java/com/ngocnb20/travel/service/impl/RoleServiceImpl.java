package com.ngocnb20.travel.service.impl;

import com.ngocnb20.travel.model.entity.Role;
import com.ngocnb20.travel.repository.RoleRepository;
import com.ngocnb20.travel.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository repository;

    @Override
    public Optional<Role> findById(Long id) {
        return repository.findRoleById(id);
    }

    @Override
    public Optional<Role> findByRoleName(String roleName) {
        return repository.findByRoleName(roleName);
    }
}
