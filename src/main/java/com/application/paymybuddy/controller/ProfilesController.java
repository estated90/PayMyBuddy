package com.application.paymybuddy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.paymybuddy.dto.EditProfile;
import com.application.paymybuddy.exception.ServiceEmailException;
import com.application.paymybuddy.exception.ServiceHolderException;
import com.application.paymybuddy.interfaces.ProfileService;
import com.application.paymybuddy.model.Profiles;

/**
 * @author nicolas
 *
 */
@RestController
public class ProfilesController {
	
	private final Logger logger = LoggerFactory.getLogger(ProfilesController.class);
	
	@Autowired
	private ProfileService controllerServices;
	
	/**
	 * @param email
	 * @param profile
	 * @return ResponseEntity
	 * @throws ServiceEmailException
	 * @throws ServiceHolderException 
	 */
	@PutMapping(value = "/Profile/update", params="email")
	public ResponseEntity<Object> updateProfile(@RequestParam String email, @RequestBody EditProfile profile) throws ServiceEmailException, ServiceHolderException {
		logger.info("update user : {}", email);
		Profiles updateProfile= controllerServices.updateProfile(email, profile);
		logger.info("{} was updated", updateProfile);
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * @param email
	 * @return ResponseEntity
	 * @throws ServiceEmailException
	 * @throws ServiceHolderException 
	 */
	@GetMapping(value = "/Profile", params="email")
	public ResponseEntity<EditProfile> findProfile(@RequestParam String email) throws ServiceEmailException, ServiceHolderException {
		logger.info("get user : {}", email);
		EditProfile getProfile= controllerServices.getProfiles(email);
		logger.info("{} was updated", getProfile);
		return ResponseEntity.ok().body(getProfile);
	}
}
