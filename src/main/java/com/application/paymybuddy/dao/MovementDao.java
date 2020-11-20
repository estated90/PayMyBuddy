package com.application.paymybuddy.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.application.paymybuddy.model.Holder;
import com.application.paymybuddy.model.Movement;

/**
 * @author nicolas
 *
 */
@Repository
public interface MovementDao extends JpaRepository<Movement, UUID> {

	@Query("SELECT COALESCE(SUM(amount),0) FROM Movement where holder=:holder")
	double sumAmounts(@Param("holder")Holder holder);
}
