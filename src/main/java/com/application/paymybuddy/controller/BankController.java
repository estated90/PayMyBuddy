package com.application.paymybuddy.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.application.paymybuddy.dto.BankList;
import com.application.paymybuddy.dto.CreationBank;
import com.application.paymybuddy.exception.ConnectionsException;
import com.application.paymybuddy.exception.ServiceBankException;
import com.application.paymybuddy.exception.ServiceEmailException;
import com.application.paymybuddy.exception.ServiceHolderException;
import com.application.paymybuddy.interfaces.BankService;
import com.application.paymybuddy.model.Bank;

/**
 * @author nicolas
 *
 */
@RestController
public class BankController {

	/**
	 * 
	 */
	@Autowired
	private BankService bankService;

	private static final Logger logger = LogManager.getLogger("BankController");

	/**
	 * @param email
	 * @return List
	 * @throws ServiceEmailException
	 * @throws ServiceHolderException
	 */
	@GetMapping(value = "/Bank", params = "email")
	public List<BankList> getBanks(@RequestParam String email) throws ServiceEmailException, ServiceHolderException {
		logger.info("Get all banks for {}", email);
		return bankService.getBanks(email);
	}

	/**
	 * @param email
	 * @param bank
	 * @return ResponseEntity
	 * @throws ServiceEmailException
	 * @throws ServiceHolderException
	 * @throws ServiceBankException
	 */
	@PostMapping(value = "/Bank/create", params = "email")
	public ResponseEntity<Object> createBank(@RequestParam String email, @Valid @RequestBody CreationBank bank)
			throws ServiceEmailException, ServiceHolderException, ServiceBankException {
		logger.info("{} create a bank with : {}", email, bank);
		Bank createBank = bankService.postBank(email, bank);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.buildAndExpand(createBank.getHolderId().getEmail()).toUri();
		logger.info("{} was created", createBank);
		return ResponseEntity.created(location).build();
	}

	/**
	 * @param email
	 * @param bank
	 * @return ResponseEntity
	 * @throws ServiceEmailException
	 * @throws ServiceHolderException
	 * @throws ServiceBankException
	 */
	@PutMapping(value = "/Bank/update", params = "email")
	public ResponseEntity<Object> updateBank(@RequestParam String email, @Valid @RequestBody CreationBank bank)
			throws ServiceEmailException, ServiceHolderException, ServiceBankException {
		logger.info("{} update a bank with : {}", email, bank);
		Bank updateank = bankService.updateBank(email, bank);
		logger.info("{} was updated", updateank);
		return ResponseEntity.noContent().build();
	}

	/**
	 * @param email
	 * @param bank
	 * @return ResponseEntity
	 * @throws ServiceEmailException
	 * @throws ConnectionsException
	 * @throws ServiceHolderException
	 * @throws ServiceBankException
	 */
	@DeleteMapping(value = "/Bank/delete", params = "email")
	public ResponseEntity<Object> deleteConnection(@RequestParam String email, @Valid @RequestBody CreationBank bank)
			throws ServiceEmailException, ServiceHolderException, ServiceBankException {
		logger.info("{} delete a bank with : {}", email, bank);
		bankService.deleteBank(email, bank);
		logger.info("Bank deleted");
		return ResponseEntity.ok().build();
	}
}
