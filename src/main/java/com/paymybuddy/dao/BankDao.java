package com.paymybuddy.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.model.Bank;

/**
 * @author nicolas
 *
 */
public interface BankDao extends JpaRepository<Bank, UUID>{

}
