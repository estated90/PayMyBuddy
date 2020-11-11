package com.payMyBudy.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.payMyBudy.model.Holder;
import com.payMyBudy.model.Profiles;

@Repository
public interface ProfileDao extends JpaRepository<Profiles, UUID>{

	@Query("SELECT p FROM Profiles p WHERE p.holderId = :uuid")
	Profiles findByFk(@Param("uuid") Holder holder);
}
