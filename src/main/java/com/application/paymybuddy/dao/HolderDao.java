package com.application.paymybuddy.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.application.paymybuddy.model.Holder;

/**
 * @author nicolas
 *
 */
@Repository
public interface HolderDao extends JpaRepository<Holder, UUID> {

	List<Holder> findAll();

	Holder findByEmail(String email);
	
	@Query("SELECT h FROM Holder h WHERE h.email = :email AND h.password = :password")
	Holder findByEmailAndPassword(@Param("email")String email, @Param("password") String password);
}
