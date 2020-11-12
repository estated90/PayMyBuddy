package com.payMyBudy.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payMyBudy.model.Connections;
import com.payMyBudy.model.Holder;

public interface ConnectionsDao extends JpaRepository<Connections, UUID> {

}
