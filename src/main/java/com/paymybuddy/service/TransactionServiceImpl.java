package com.paymybuddy.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.constants.Costs;
import com.paymybuddy.dao.TransactionDao;
import com.paymybuddy.dto.CreateTransaction;
import com.paymybuddy.dto.ReturnTransaction;
import com.paymybuddy.exception.ConnectionsException;
import com.paymybuddy.exception.ServiceBankException;
import com.paymybuddy.exception.ServiceEmailException;
import com.paymybuddy.exception.ServiceHolderException;
import com.paymybuddy.exception.ServiceMovementException;
import com.paymybuddy.interfaces.MovementService;
import com.paymybuddy.interfaces.TransactionsService;
import com.paymybuddy.model.Connections;
import com.paymybuddy.model.Holder;
import com.paymybuddy.model.Transactions;

@Service
public class TransactionServiceImpl implements TransactionsService {

	@Autowired
	private Verification verification;
	@Autowired
	private MovementService movementService;
	@Autowired
	private TransactionDao transactionDao;

	private static final Logger logger = LogManager.getLogger("TransactionServiceImpl");

	private static final String MESSAGE1 = "Result : {}";

	@Override
	public List<ReturnTransaction> getAllTransaction(String email)
			throws ServiceEmailException, ServiceHolderException {
		logger.info("Get all the transaction for user {}", email);
		Holder holder = verification.verificationOfData(email);
		List<ReturnTransaction> returnTransaction = new ArrayList<>();
		List<Connections> mainConnections = holder.getHolderFriendship();
		setTransactionInList(returnTransaction, mainConnections);
		List<Connections> friendConnections = holder.getHolderAsFriend();
		setTransactionInList(returnTransaction, friendConnections);
		logger.info(MESSAGE1, returnTransaction);
		return returnTransaction;
	}

	@Override
	public List<ReturnTransaction> getTransactionToFriends(String email)
			throws ServiceEmailException, ServiceHolderException {
		logger.info("Get the transaction to friends for user {}", email);
		Holder holder = verification.verificationOfData(email);
		List<ReturnTransaction> returnTransaction = new ArrayList<>();
		List<Connections> mainConnections = holder.getHolderFriendship();
		setTransactionInList(returnTransaction, mainConnections);
		logger.info(MESSAGE1, returnTransaction);
		return returnTransaction;
	}

	@Override
	public List<ReturnTransaction> getTransactionFromFriends(String email)
			throws ServiceEmailException, ServiceHolderException {
		logger.info("Get the transaction from friends for user {}", email);
		Holder holder = verification.verificationOfData(email);
		List<ReturnTransaction> returnTransaction = new ArrayList<>();
		List<Connections> friendConnections = holder.getHolderAsFriend();
		setTransactionInList(returnTransaction, friendConnections);
		logger.info(MESSAGE1, returnTransaction);
		return returnTransaction;
	}

	@Override
	public Transactions createTransaction(String email, CreateTransaction newTransaction) throws ServiceEmailException,
			ServiceHolderException, ConnectionsException, ServiceBankException, ServiceMovementException {
		logger.info("Create transaction to friends {} from user {} of {}", newTransaction.getFriendEmail(), email,
				newTransaction.getAmount());
		Double amount = newTransaction.getAmount();
		Holder holder = verification.verificationOfData(email);
		Holder holderFriend = verification.verificationOfData(newTransaction.getFriendEmail());
		Double amountToPay = (double) Math.round(amount * -1 * (1 + Costs.FEES) * 100) / 100;
		verification.verifyMovementAuthorized(holder, amountToPay);
		Transactions transaction = new Transactions();
		Connections connection = holder.getHolderFriendship().stream()
				.filter(str -> str.getFriendId().equals(holderFriend)).findAny().orElse(null);
		if (connection != null) {
			transaction.setAmount(amount);
			transaction.setConnection(connection);
			transaction.setCreated(LocalDateTime.now());
			transaction.setDescription(newTransaction.getDescription());
			transaction.setFees((double) Math.round(amount * Costs.FEES * 100) / 100);
			transactionDao.save(transaction);
		} else {
			logger.error("No connection has been found");
			throw new ConnectionsException("No connection with user found");
		}
		logger.info("Create movements in user account for{}", transaction.getAmount() + transaction.getFees());
		movementService.createMovement(email, transaction, amountToPay);
		logger.info("Create movements in friend account for {}", transaction.getAmount());
		movementService.createMovement(holderFriend.getEmail(), transaction, amount);

		return transaction;
	}

	private List<ReturnTransaction> setTransactionInList(List<ReturnTransaction> returnTransaction,
			List<Connections> connections) {
		for (Connections connection : connections) {
			List<Transactions> transactions = connection.getTransactions();
			for (Transactions transaction : transactions) {
				ReturnTransaction detailTransaction = new ReturnTransaction();
				detailTransaction.setAmount(transaction.getAmount());
				detailTransaction.setCreatedAt(transaction.getCreated());
				detailTransaction.setDescription(transaction.getDescription());
				detailTransaction.setFees(transaction.getFees());
				detailTransaction.setToUser(transaction.getConnection().getFriendId().getEmail());
				detailTransaction.setUser(transaction.getConnection().getHolderId().getEmail());
				returnTransaction.add(detailTransaction);
			}
		}
		return returnTransaction;
	}

}
