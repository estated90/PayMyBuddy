package com.paymybuddy.interfaces;

import java.util.List;

import com.paymybuddy.dto.CreateTransaction;
import com.paymybuddy.dto.ReturnTransaction;
import com.paymybuddy.exception.ConnectionsException;
import com.paymybuddy.exception.ServiceBankException;
import com.paymybuddy.exception.ServiceEmailException;
import com.paymybuddy.exception.ServiceHolderException;
import com.paymybuddy.exception.ServiceMovementException;
import com.paymybuddy.model.Transactions;


public interface TransactionsService {

	List<ReturnTransaction> getAllTransaction(String email) throws ServiceEmailException, ServiceHolderException;

	List<ReturnTransaction> getTransactionToFriends(String email) throws ServiceEmailException, ServiceHolderException;

	List<ReturnTransaction> getTransactionFromFriends(String email)
			throws ServiceEmailException, ServiceHolderException;

	Transactions createTransaction(String email, CreateTransaction newTransaction)
			throws ServiceEmailException, ServiceHolderException, ConnectionsException, ServiceBankException, ServiceMovementException;

}