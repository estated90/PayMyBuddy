package com.payMyBudy.interfaces;

import java.util.List;

import com.payMyBudy.dto.CreateTransaction;
import com.payMyBudy.dto.ReturnTransaction;
import com.payMyBudy.exception.ConnectionsException;
import com.payMyBudy.exception.ServiceBankException;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.exception.ServiceHolderException;
import com.payMyBudy.exception.ServiceMovementException;
import com.payMyBudy.model.Transactions;


public interface TransactionsService {

	List<ReturnTransaction> getAllTransaction(String email) throws ServiceEmailException, ServiceHolderException;

	List<ReturnTransaction> getTransactionToFriends(String email) throws ServiceEmailException, ServiceHolderException;

	List<ReturnTransaction> getTransactionFromFriends(String email)
			throws ServiceEmailException, ServiceHolderException;

	Transactions createTransaction(String email, CreateTransaction newTransaction)
			throws ServiceEmailException, ServiceHolderException, ConnectionsException, ServiceBankException, ServiceMovementException;

}