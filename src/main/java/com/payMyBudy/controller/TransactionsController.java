package com.payMyBudy.controller;

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

import com.payMyBudy.dto.CreateTransaction;
import com.payMyBudy.dto.ReturnTransaction;
import com.payMyBudy.exception.ConnectionsException;
import com.payMyBudy.exception.ServiceBankException;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.exception.ServiceHolderException;
import com.payMyBudy.exception.ServiceMovementException;
import com.payMyBudy.interfaces.TransactionsService;
import com.payMyBudy.model.Transactions;

import io.swagger.annotations.Api;

@RestController
@Api(description = "API pour for the CRUD operation link to financial transactions.")
public class TransactionsController {

	/**
	 * 
	 */
	@Autowired
	private TransactionsService transactionsService;

	private static final Logger logger = LogManager.getLogger("TransactionsController");

	/**
	 * @param email
	 * @return
	 * @throws ServiceEmailException
	 * @throws ServiceHolderException
	 */
	@GetMapping(value = "/Transaction/all", params = "email")
	public List<ReturnTransaction> returnAllTransaction(@RequestParam String email)
			throws ServiceEmailException, ServiceHolderException {
		logger.info("Get all financial transactions for {}", email);
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
		logger.info("Get all financial transactions for {}", email);
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
		logger.info("Get all financial transactions for {}", email);
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
		logger.info("Creating a new transaction for user {] : {}", email, transaction);
		Transactions transactions = transactionsService.createTransaction(email, transaction);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.buildAndExpand(transactions.getConnection().getHolderId().getEmail()).toUri();
		logger.info("{} was created", transactions);
		return ResponseEntity.created(location).build();
	}

}
