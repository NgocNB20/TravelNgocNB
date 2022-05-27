package com.ngocnb20.travel.service;

import com.ngocnb20.travel.model.entity.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findById(Long id);
    Optional<Role> findByRoleName(String roleName);
}
