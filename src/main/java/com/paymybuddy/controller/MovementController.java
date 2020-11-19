package com.paymybuddy.controller;

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

import com.paymybuddy.dto.BankList;
import com.paymybuddy.dto.ReturnMovement;
import com.paymybuddy.dto.Solde;
import com.paymybuddy.exception.ServiceBankException;
import com.paymybuddy.exception.ServiceEmailException;
import com.paymybuddy.exception.ServiceHolderException;
import com.paymybuddy.exception.ServiceMovementException;
import com.paymybuddy.interfaces.MovementService;
import com.paymybuddy.model.Movement;

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
