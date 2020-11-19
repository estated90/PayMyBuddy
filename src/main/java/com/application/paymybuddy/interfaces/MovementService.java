package com.application.paymybuddy.interfaces;

import java.util.List;

import com.application.paymybuddy.dto.BankList;
import com.application.paymybuddy.dto.ReturnMovement;
import com.application.paymybuddy.dto.Solde;
import com.application.paymybuddy.exception.ServiceBankException;
import com.application.paymybuddy.exception.ServiceEmailException;
import com.application.paymybuddy.exception.ServiceHolderException;
import com.application.paymybuddy.exception.ServiceMovementException;
import com.application.paymybuddy.model.Movement;
import com.application.paymybuddy.model.Transactions;

public interface MovementService {

	List<ReturnMovement> getMovement(String email) throws ServiceEmailException, ServiceHolderException;

	Solde calculateSoldeAccount(String email) throws ServiceEmailException, ServiceHolderException;

	Movement createMovement(String email, BankList bank, double amount)
			throws ServiceEmailException, ServiceHolderException, ServiceBankException, ServiceMovementException;

	Movement createMovement(String email, Transactions transaction, double amount)
			throws ServiceEmailException, ServiceHolderException, ServiceBankException, ServiceMovementException;

}