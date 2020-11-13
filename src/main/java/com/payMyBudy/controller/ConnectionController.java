package com.payMyBudy.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.payMyBudy.exception.ConnectionsException;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.interfaces.ConnectionServices;
import com.payMyBudy.model.Connections;
import com.payMyBudy.model.Holder;

import io.swagger.annotations.Api;

@RestController
@Api(description = "API pour for the CRUD operation to register friendship.")
public class ConnectionController {
	
	@Autowired
	private ConnectionServices connectionServices;
	
	private final Logger logger = LoggerFactory.getLogger(HolderController.class);

	@PostMapping(value = "/Connection/create", params={"email", "emailFriend"})
	public ResponseEntity<Holder> createUser(@RequestParam String email, @RequestParam String emailFriend) throws ServiceEmailException, ConnectionsException {
		logger.info("create a relationship between {} and {}", email, emailFriend);
		Connections createConnection = connectionServices.createConnection(email, emailFriend);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(createConnection.getFriendId().getEmail()).toUri();
		logger.info("relationship created : {}", createConnection);
		return ResponseEntity.created(location).build();
	}
	
}
