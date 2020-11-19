package com.paymybuddy.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.paymybuddy.model.Holder;
import com.paymybuddy.model.Movement;

/**
 * @author nicolas
 *
 */
@Repository
public interface MovementDao extends JpaRepository<Movement, UUID> {

	@Query("SELECT SUM(amount) FROM Movement where holder=:holder")
	double sumAmounts(@Param("holder")Holder holder);
}
