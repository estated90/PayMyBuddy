package com.paymybuddy.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.model.Profiles;

/**
 * @author nicolas
 *
 */
@Repository
public interface ProfileDao extends JpaRepository<Profiles, UUID>{
}
