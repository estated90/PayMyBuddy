package com.paymybuddy.controller;

import java.net.URI;
import java.util.List;

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

import com.paymybuddy.dto.FriendList;
import com.paymybuddy.exception.ConnectionsException;
import com.paymybuddy.exception.ServiceEmailException;
import com.paymybuddy.exception.ServiceHolderException;
import com.paymybuddy.interfaces.ConnectionServices;
import com.paymybuddy.model.Connections;

/**
 * @author nicolas
 *
 */

@RestController
public class ConnectionController {
	
	@Autowired
	private ConnectionServices connectionServices;
	
	private final Logger logger = LoggerFactory.getLogger(ConnectionController.class);

	/**
	 * @param email
	 * @return List
	 * @throws ServiceEmailException
	 * @throws ServiceHolderException 
	 */
	@GetMapping(value="/Connection", params= "email")
	public List<FriendList> getConnection(@RequestParam String email) throws ServiceEmailException, ServiceHolderException{
		logger.info("Get all relationship for {}", email);
		return connectionServices.getConnection(email);
	}
	
	/**
	 * @param email
	 * @param emailFriend
	 * @return ResponseEntity
	 * @throws ServiceEmailException
	 * @throws ConnectionsException
	 * @throws ServiceHolderException 
	 */
	@PostMapping(value = "/Connection/create", params={"email", "emailFriend"})
	public ResponseEntity<Connections> createConnectiion(@RequestParam String email, @RequestParam String emailFriend) throws ServiceEmailException, ConnectionsException, ServiceHolderException {
		logger.info("create a relationship between {} and {}", email, emailFriend);
		Connections createConnection = connectionServices.createConnection(email, emailFriend);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(createConnection.getFriendId().getEmail()).toUri();
		logger.info("relationship created : {}", createConnection);
		return ResponseEntity.created(location).build();
	}

	
	/**
	 * @param email
	 * @param emailFriend
	 * @return ResponseEntity
	 * @throws ServiceEmailException
	 * @throws ConnectionsException
	 * @throws ServiceHolderException 
	 */
	@DeleteMapping(value = "/Connection", params={"email", "emailFriend"})
	public ResponseEntity<Object> deleteConnection(@RequestParam String email, @RequestParam String emailFriend) throws ServiceEmailException, ConnectionsException, ServiceHolderException {
		logger.info("delete a relationship between {} and {}", email, emailFriend);
		connectionServices.deleteConnection(email, emailFriend);
		logger.info("Relationship deleted");
		return ResponseEntity.ok().build();
	}
	
}
