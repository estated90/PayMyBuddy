package com.payMyBudy.controller;

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

import com.payMyBudy.dto.FriendList;
import com.payMyBudy.exception.ConnectionsException;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.interfaces.ConnectionServices;
import com.payMyBudy.model.Connections;
import io.swagger.annotations.Api;

@RestController
@Api(description = "API pour for the CRUD operation to register friendship.")
public class ConnectionController {
	
	@Autowired
	private ConnectionServices connectionServices;
	
	private final Logger logger = LoggerFactory.getLogger(HolderController.class);

	@PostMapping(value = "/Connection/create", params={"email", "emailFriend"})
	public ResponseEntity<Connections> createConnectiion(@RequestParam String email, @RequestParam String emailFriend) throws ServiceEmailException, ConnectionsException {
		logger.info("create a relationship between {} and {}", email, emailFriend);
		Connections createConnection = connectionServices.createConnection(email, emailFriend);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(createConnection.getFriendId().getEmail()).toUri();
		logger.info("relationship created : {}", createConnection);
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping(value="/Connection", params= "email")
	public List<FriendList> getConnection(@RequestParam String email) throws ServiceEmailException{
		logger.info("Get all relationship for {}", email);
		List<FriendList> getConnection = connectionServices.getConnection(email);
		return getConnection;
	}
	
	@DeleteMapping(value = "/Connection", params={"email", "emailFriend"})
	public ResponseEntity<Object> deleteConnection(@RequestParam String email, @RequestParam String emailFriend) throws ServiceEmailException, ConnectionsException {
		logger.info("delete a relationship between {} and {}", email, emailFriend);
		connectionServices.deleteConnection(email, emailFriend);
		logger.info("Relationship deleted");
		return ResponseEntity.ok().build();
	}
	
}
