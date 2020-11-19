package com.paymybuddy.interfaces;

import java.util.List;

import com.paymybuddy.dto.BankList;
import com.paymybuddy.dto.ReturnMovement;
import com.paymybuddy.dto.Solde;
import com.paymybuddy.exception.ServiceBankException;
import com.paymybuddy.exception.ServiceEmailException;
import com.paymybuddy.exception.ServiceHolderException;
import com.paymybuddy.exception.ServiceMovementException;
import com.paymybuddy.model.Movement;
import com.paymybuddy.model.Transactions;

public interface MovementService {

	List<ReturnMovement> getMovement(String email) throws ServiceEmailException, ServiceHolderException;

	Solde calculateSoldeAccount(String email) throws ServiceEmailException, ServiceHolderException;

	Movement createMovement(String email, BankList bank, double amount)
			throws ServiceEmailException, ServiceHolderException, ServiceBankException, ServiceMovementException;

	Movement createMovement(String email, Transactions transaction, double amount)
			throws ServiceEmailException, ServiceHolderException, ServiceBankException, ServiceMovementException;

}