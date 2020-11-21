package com.application.paymybuddy.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.application.paymybuddy.exception.ServiceConnectionException;
import com.application.paymybuddy.exception.ServiceEmailException;
import com.application.paymybuddy.exception.ServiceHolderException;
import com.application.paymybuddy.interfaces.HolderServices;
import com.application.paymybuddy.model.Holder;

/**
 * @author nicolas
 *
 */
@RestController
public class HolderController {

	private final Logger logger = LoggerFactory.getLogger(HolderController.class);
	
	@Autowired
	private HolderServices controllerServices;

	/**
	 * @param email
	 * @return ResponseEntity
	 * @throws ServiceEmailException
	 * @throws ServiceHolderException
	 */
	@PostMapping(value = "/Holder/create", params="email")
	public ResponseEntity<Object> createUser(@RequestParam String email) throws ServiceEmailException, ServiceHolderException {
		logger.info("create a user with : {}", email);
		Holder createHolder = controllerServices.createHolder(email);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(createHolder.getEmail()).toUri();
		logger.info("{} was created", createHolder);
		return ResponseEntity.created(location).build();
	}
	
	/**
	 * @param email
	 * @param password
	 * @throws ServiceEmailException
	 * @throws ServiceConnectionException
	 * @throws ServiceHolderException 
	 */
	@GetMapping(value="/Holder/connection", params= {"email", "password"})
	public void connection(@RequestParam String email, @RequestParam String password) throws ServiceEmailException, ServiceConnectionException, ServiceHolderException {
		logger.info("User with email {} is trying to connect", email);
		Holder connectedHolder = controllerServices.connection(email, password);
		logger.info("{} is connected", connectedHolder);
	}
	
	/**
	 * @param email
	 * @return
	 * @throws ServiceEmailException
	 * @throws ServiceHolderException
	 */
	@DeleteMapping(value = "/Holder/delete", params="email")
	public ResponseEntity<Object> deleteHolderandRelated(@RequestParam String email) throws ServiceEmailException, ServiceHolderException {
		logger.info("delete user {}", email);
		controllerServices.deleteHolder(email);
		logger.info("Holder deleted");
		return ResponseEntity.ok().build();
	}
}
