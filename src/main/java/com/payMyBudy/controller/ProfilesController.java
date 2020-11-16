package com.payMyBudy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.payMyBudy.dto.EditProfile;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.interfaces.ProfileService;
import com.payMyBudy.model.Profiles;

import io.swagger.annotations.Api;

@RestController
@Api(description = "API pour for the CRUD operation on users (profiles).")
public class ProfilesController {
	
	private final Logger logger = LoggerFactory.getLogger(ProfilesController.class);
	
	@Autowired
	private ProfileService controllerServices;
	
	@PutMapping(value = "/Profile/update", params="email")
	public ResponseEntity<String> updateProfile(@RequestParam String email, @RequestBody EditProfile profile) throws ServiceEmailException {
		logger.info("update user : {}", email);
		Profiles updateProfile= controllerServices.updateProfile(email, profile);
		logger.info("{} was updated", updateProfile);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/Profile", params="email")
	public ResponseEntity<EditProfile> findProfile(@RequestParam String email) throws ServiceEmailException {
		logger.info("get user : {}", email);
		EditProfile getProfile= controllerServices.getProfiles(email);
		logger.info("{} was updated", getProfile);
		return ResponseEntity.ok().body(getProfile);
	}
	
	//TODO missing logic to update and delete
	
}
