package com.payMyBudy.dao;
import org.springframework.data.jpa.repository.JpaRepository;

import com.payMyBudy.model.Role;


public interface RoleRepository extends JpaRepository<Role, String> {

}
