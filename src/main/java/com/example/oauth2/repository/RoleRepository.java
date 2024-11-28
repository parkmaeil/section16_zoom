package com.example.oauth2.repository;

import com.example.oauth2.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findByName(String name); // 1. USER, 2. MANAGER, 3. ADMIN
}
