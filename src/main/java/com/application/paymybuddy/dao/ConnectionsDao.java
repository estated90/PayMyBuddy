package com.application.paymybuddy.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.application.paymybuddy.model.Connections;
import com.application.paymybuddy.model.Holder;

/**
 * @author nicolas
 *
 */
public interface ConnectionsDao extends JpaRepository<Connections, UUID> {

	@Query("SELECT c FROM Connections c WHERE holderId = :mainHolder AND friendId=:friend")
	Connections findConnection(@Param("mainHolder")Holder mainHolder, @Param("friend") Holder friend);

}