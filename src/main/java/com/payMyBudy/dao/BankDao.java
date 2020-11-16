package com.payMyBudy.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payMyBudy.model.Bank;

public interface BankDao extends JpaRepository<Bank, UUID>{

}
