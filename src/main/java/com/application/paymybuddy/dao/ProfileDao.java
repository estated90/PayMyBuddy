package com.application.paymybuddy.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.paymybuddy.model.Profiles;

/**
 * @author nicolas
 *
 */
@Repository
public interface ProfileDao extends JpaRepository<Profiles, UUID>{
}
