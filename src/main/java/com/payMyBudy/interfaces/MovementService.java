package com.payMyBudy.interfaces;

import java.util.List;

import com.payMyBudy.dto.BankList;
import com.payMyBudy.dto.ReturnMovement;
import com.payMyBudy.dto.Solde;
import com.payMyBudy.exception.ServiceBankException;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.exception.ServiceHolderException;
import com.payMyBudy.exception.ServiceMovementException;
import com.payMyBudy.model.Movement;
import com.payMyBudy.model.Transactions;

public interface MovementService {

	List<ReturnMovement> getMovement(String email) throws ServiceEmailException, ServiceHolderException;

	Solde calculateSoldeAccount(String email) throws ServiceEmailException, ServiceHolderException;

	Movement createMovement(String email, BankList bank, double amount)
			throws ServiceEmailException, ServiceHolderException, ServiceBankException, ServiceMovementException;

	Movement createMovement(String email, Transactions transaction, double amount)
			throws ServiceEmailException, ServiceHolderException, ServiceBankException, ServiceMovementException;

}