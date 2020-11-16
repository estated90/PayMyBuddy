package com.payMyBudy.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.payMyBudy.dto.BankList;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.interfaces.BankService;

import io.swagger.annotations.Api;

@RestController
@Api(description = "API pour for the CRUD operation link to banks.")
public class BankController {
	
	@Autowired
	private BankService bankService;
	
	private static final Logger logger = LogManager.getLogger("BankController");
	
	@GetMapping(value="/Bank", params= "email")
	public List<BankList> getBanks(@RequestParam String email) throws ServiceEmailException{
		logger.info("Get all banks for {}", email);
		List<BankList> getConnection = bankService.getBanks(email);
		return getConnection;
	}
}
