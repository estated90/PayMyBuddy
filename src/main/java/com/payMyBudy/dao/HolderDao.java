package com.payMyBudy.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.payMyBudy.model.Holder;

@Repository
public interface HolderDao extends JpaRepository<Holder, UUID> {

	List<Holder> findAll();

	Object findByEmail(String email);	
}
