package com.payMyBudy.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payMyBudy.model.Transactions;

@Repository
public interface TransactionDao extends JpaRepository<Transactions, UUID>{

}
