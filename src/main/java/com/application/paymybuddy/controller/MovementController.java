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

import com.application.paymybuddy.dto.BankList;
import com.application.paymybuddy.dto.ReturnMovement;
import com.application.paymybuddy.dto.Solde;
import com.application.paymybuddy.exception.ServiceBankException;
import com.application.paymybuddy.exception.ServiceEmailException;
import com.application.paymybuddy.exception.ServiceHolderException;
import com.application.paymybuddy.exception.ServiceMovementException;
import com.application.paymybuddy.interfaces.MovementService;
import com.application.paymybuddy.model.Movement;

/**
 * @author nicolas
 *
 */
@RestController
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
