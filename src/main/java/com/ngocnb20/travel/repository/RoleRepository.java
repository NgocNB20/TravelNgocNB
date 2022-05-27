package com.ngocnb20.travel.repository;

import com.ngocnb20.travel.model.entity.Role;
import com.ngocnb20.travel.model.entity.RoleMember;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long> , JpaSpecificationExecutor<Role> {
    Optional<Role> findRoleById(Long id);
    Optional<Role> findByRoleName(String roleName);

}
