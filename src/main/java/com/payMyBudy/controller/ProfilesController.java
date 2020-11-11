package com.payMyBudy.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.interfaces.HolderServices;
import com.payMyBudy.model.Holder;
import com.payMyBudy.model.Profiles;

import io.swagger.annotations.Api;

@RestController
@Api(description = "API pour for the CRUD operation on users (profiles).")
public class ProfilesController {
	
	private final Logger logger = LoggerFactory.getLogger(ProfilesController.class);
	
	@Autowired
	private HolderServices controllerServices;
	
	@PutMapping(value = "/Profile/update", params="email")
	public ResponseEntity<Holder> updateProfile(@RequestParam String email, @RequestBody Profiles profile) throws ServiceEmailException {
		logger.info("update user : {}", email);
		Profiles updateProfile= controllerServices.updateProfile(email, profile);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(email).toUri();
		logger.info("{} was updated", updateProfile);
		return ResponseEntity.created(location).build();
	}
	
}
