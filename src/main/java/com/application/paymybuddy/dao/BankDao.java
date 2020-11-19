package com.application.paymybuddy.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.paymybuddy.model.Bank;

/**
 * @author nicolas
 *
 */
public interface BankDao extends JpaRepository<Bank, UUID>{

}
