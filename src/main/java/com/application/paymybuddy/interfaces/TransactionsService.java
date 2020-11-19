package com.application.paymybuddy.interfaces;

import java.util.List;

import com.application.paymybuddy.dto.CreateTransaction;
import com.application.paymybuddy.dto.ReturnTransaction;
import com.application.paymybuddy.exception.ConnectionsException;
import com.application.paymybuddy.exception.ServiceBankException;
import com.application.paymybuddy.exception.ServiceEmailException;
import com.application.paymybuddy.exception.ServiceHolderException;
import com.application.paymybuddy.exception.ServiceMovementException;
import com.application.paymybuddy.model.Transactions;


public interface TransactionsService {

	List<ReturnTransaction> getAllTransaction(String email) throws ServiceEmailException, ServiceHolderException;

	List<ReturnTransaction> getTransactionToFriends(String email) throws ServiceEmailException, ServiceHolderException;

	List<ReturnTransaction> getTransactionFromFriends(String email)
			throws ServiceEmailException, ServiceHolderException;

	Transactions createTransaction(String email, CreateTransaction newTransaction)
			throws ServiceEmailException, ServiceHolderException, ConnectionsException, ServiceBankException, ServiceMovementException;

}