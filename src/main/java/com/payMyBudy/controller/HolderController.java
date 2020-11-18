package com.payMyBudy.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.payMyBudy.exception.ServiceConnectionException;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.exception.ServiceHolderException;
import com.payMyBudy.interfaces.HolderServices;
import com.payMyBudy.model.Holder;

import io.swagger.annotations.Api;

/**
 * @author nicolas
 *
 */
@RestController
@Api(description = "API pour for the CRUD operation on users (holders).")
public class HolderController {

	private final Logger logger = LoggerFactory.getLogger(HolderController.class);
	
	@Autowired
	private HolderServices controllerServices;

	// Récupérer la liste des produits
	@GetMapping(value = "/Holder")
	public ResponseEntity<List<Holder>> getHolders() {
		logger.info("Getting all users");
		List<Holder> holder = controllerServices.getAllUsers();
		return ResponseEntity.ok(holder);
	}

	/**
	 * @param email
	 * @return ResponseEntity
	 * @throws ServiceEmailException
	 * @throws ServiceHolderException
	 */
	@PostMapping(value = "/Holder/create", params="email")
	public ResponseEntity<Holder> createUser(@RequestParam String email) throws ServiceEmailException, ServiceHolderException {
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
	

}
