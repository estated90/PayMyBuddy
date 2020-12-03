package com.application.paymybuddy.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.paymybuddy.model.Bank;

/**
 * @author nicolas
 *
 */
@Repository
public interface BankDao extends JpaRepository<Bank, UUID>{

}
