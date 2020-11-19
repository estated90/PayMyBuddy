package com.application.paymybuddy.controller;

import java.net.URI;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.application.paymybuddy.dto.CreateTransaction;
import com.application.paymybuddy.dto.ReturnTransaction;
import com.application.paymybuddy.exception.ConnectionsException;
import com.application.paymybuddy.exception.ServiceBankException;
import com.application.paymybuddy.exception.ServiceEmailException;
import com.application.paymybuddy.exception.ServiceHolderException;
import com.application.paymybuddy.exception.ServiceMovementException;
import com.application.paymybuddy.interfaces.TransactionsService;
import com.application.paymybuddy.model.Transactions;

@RestController
public class TransactionsController {

	/**
	 * 
	 */
	@Autowired
	private TransactionsService transactionsService;

	private static final Logger logger = LogManager.getLogger("TransactionsController");
	private static final String MESSAGE = "Get all financial transactions for {}";

	/**
	 * @param email
	 * @return
	 * @throws ServiceEmailException
	 * @throws ServiceHolderException
	 */
	@GetMapping(value = "/Transaction/all", params = "email")
	public List<ReturnTransaction> returnAllTransaction(@RequestParam String email)
			throws ServiceEmailException, ServiceHolderException {
		logger.info(MESSAGE, email);
		return transactionsService.getAllTransaction(email);
	}

	/**
	 * @param email
	 * @return
	 * @throws ServiceEmailException
	 * @throws ServiceHolderException
	 */
	@GetMapping(value = "/Transaction/to", params = "email")
	public List<ReturnTransaction> returnTransactionToFriends(@RequestParam String email)
			throws ServiceEmailException, ServiceHolderException {
		logger.info(MESSAGE, email);
		return transactionsService.getTransactionToFriends(email);
	}

	/**
	 * @param email
	 * @return
	 * @throws ServiceEmailException
	 * @throws ServiceHolderException
	 */
	@GetMapping(value = "/Transaction/from", params = "email")
	public List<ReturnTransaction> returnTransactionFromFriends(@RequestParam String email)
			throws ServiceEmailException, ServiceHolderException {
		logger.info(MESSAGE, email);
		return transactionsService.getTransactionFromFriends(email);
	}

	/**
	 * @param email
	 * @param transaction
	 * @return
	 * @throws ServiceEmailException
	 * @throws ServiceHolderException
	 * @throws ConnectionsException
	 * @throws ServiceBankException
	 * @throws ServiceMovementException
	 */
	@PostMapping(value = "/Transaction/create", params = "email")
	public ResponseEntity<Object> createNewTransaction(@RequestParam String email,
			@RequestBody CreateTransaction transaction) throws ServiceEmailException, ServiceHolderException,
			ConnectionsException, ServiceBankException, ServiceMovementException {
		logger.info("Creating a new transaction for user {} : {}", email, transaction);
		Transactions transactions = transactionsService.createTransaction(email, transaction);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.buildAndExpand(transactions.getConnection().getHolderId().getEmail()).toUri();
		logger.info("{} was created", transactions);
		return ResponseEntity.created(location).build();
	}

}
