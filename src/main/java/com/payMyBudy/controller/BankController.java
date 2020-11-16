package com.payMyBudy.controller;

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

import com.payMyBudy.dto.BankList;
import com.payMyBudy.dto.CreationBank;
import com.payMyBudy.exception.ConnectionsException;
import com.payMyBudy.exception.ServiceBankException;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.exception.ServiceHolderException;
import com.payMyBudy.interfaces.BankService;
import com.payMyBudy.model.Bank;

import io.swagger.annotations.Api;

@RestController
@Api(description = "API pour for the CRUD operation link to banks.")
public class BankController {

	@Autowired
	private BankService bankService;

	private static final Logger logger = LogManager.getLogger("BankController");

	@GetMapping(value = "/Bank", params = "email")
	public List<BankList> getBanks(@RequestParam String email) throws ServiceEmailException, ServiceHolderException {
		logger.info("Get all banks for {}", email);
		List<BankList> getConnection = bankService.getBanks(email);
		return getConnection;
	}

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

	@PutMapping(value = "/Bank/update", params = "email")
	public ResponseEntity<Object> updateBank(@RequestParam String email, @Valid @RequestBody CreationBank bank)
			throws ServiceEmailException, ServiceHolderException, ServiceBankException {
		logger.info("{} update a bank with : {}", email, bank);
		Bank updateank = bankService.updateBank(email, bank);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.buildAndExpand(updateank.getHolderId().getEmail()).toUri();
		logger.info("{} was updated", updateank);
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping(value = "/Bank/delete", params = "email")
	public ResponseEntity<Object> deleteConnection(@RequestParam String email, @Valid @RequestBody CreationBank bank)
			throws ServiceEmailException, ConnectionsException, ServiceHolderException, ServiceBankException {
		logger.info("{} delete a bank with : {}", email, bank);
		bankService.deleteBank(email, bank);
		logger.info("Bank deleted");
		return ResponseEntity.ok().build();
	}
}
