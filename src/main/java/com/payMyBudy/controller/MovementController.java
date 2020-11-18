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

import com.payMyBudy.dto.BankList;
import com.payMyBudy.dto.ReturnMovement;
import com.payMyBudy.dto.Solde;
import com.payMyBudy.exception.ServiceBankException;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.exception.ServiceHolderException;
import com.payMyBudy.exception.ServiceMovementException;
import com.payMyBudy.interfaces.MovementService;
import com.payMyBudy.model.Movement;

import io.swagger.annotations.Api;

/**
 * @author nicolas
 *
 */
@RestController
@Api(description = "API pour for the CRUD operation link to financial movement.")
public class MovementController {

	/**
	 * 
	 */
	@Autowired
	private MovementService movementService;

	private static final Logger logger = LogManager.getLogger("MovementController");

	/**
	 * @param email
	 * @return
	 * @throws ServiceEmailException
	 * @throws ServiceHolderException
	 */
	@GetMapping(value = "/Movement", params = "email")
	public List<ReturnMovement> getMovement(@RequestParam String email)
			throws ServiceEmailException, ServiceHolderException {
		logger.info("Get all financial movement for {}", email);
		return movementService.getMovement(email);
	}

	/**
	 * @param email
	 * @return
	 * @throws ServiceEmailException
	 * @throws ServiceHolderException
	 */
	@GetMapping(value = "/Movement/solde", params = "email")
	public Solde getSolde(@RequestParam String email) throws ServiceEmailException, ServiceHolderException {
		logger.info("Get solde of the account {}", email);
		return movementService.calculateSoldeAccount(email);
	}

	@PostMapping(value = "/Movement/create", params = { "email", "amount" })
	public ResponseEntity<Object> createMovement(@RequestParam String email, @RequestParam double amount,
			@RequestBody BankList bankList)
			throws ServiceEmailException, ServiceHolderException, ServiceBankException, ServiceMovementException {
		Movement movement = movementService.createMovement(email, bankList, amount);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(movement.getHolder().getEmail())
				.toUri();
		logger.info("{} was created", movement);
		return ResponseEntity.created(location).build();
	}
}
