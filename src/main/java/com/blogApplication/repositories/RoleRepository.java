package com.blogApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogApplication.entities.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {

}
