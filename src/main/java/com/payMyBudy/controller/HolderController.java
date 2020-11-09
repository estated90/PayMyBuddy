package com.payMyBudy.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.payMyBudy.dao.HolderDao;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.model.Holder;
import com.payMyBudy.service.ControllerServices;

import io.swagger.annotations.Api;

@RestController
@Api(description = "API pour for the CRUD operation on users (holders).")
public class HolderController {

	private final Logger logger = LoggerFactory.getLogger(HolderController.class);
	
	@Autowired
	private HolderDao holderDao;
	
	@Autowired
	private ControllerServices controllerServices;

	// Récupérer la liste des produits
	@RequestMapping(value = "/Holder", method = RequestMethod.GET)
	public List<Holder> getHolders() {

		return holderDao.findAll();
	}

	@PostMapping(value = "/holderCreation", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Holder> createUser(@Valid @RequestBody Holder holder) throws ServiceEmailException {
		
		logger.info("create a user with : {}", holder);
		Holder createHolder = controllerServices.createHolder(holder);
		if (createHolder == null) {
			logger.info("The account has not been created");
			return ResponseEntity.noContent().build();
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(createHolder.getEmail()).toUri();
		logger.info("{} was created", createHolder);
		return ResponseEntity.created(location).build();
	}

}
