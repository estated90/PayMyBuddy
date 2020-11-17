package com.payMyBudy.interfaces;

import java.util.List;

import com.payMyBudy.dto.BankList;
import com.payMyBudy.dto.ReturnMovement;
import com.payMyBudy.exception.ServiceBankException;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.exception.ServiceHolderException;
import com.payMyBudy.exception.ServiceMovementException;
import com.payMyBudy.model.Movement;

public interface MovementService {

	List<ReturnMovement> getMovement(String email) throws ServiceEmailException, ServiceHolderException;

	double calculateSoldeAccount(String email) throws ServiceEmailException, ServiceHolderException;

	Movement createMovement(String email, BankList bank, double amount) throws ServiceEmailException, ServiceHolderException, ServiceBankException, ServiceMovementException;

}