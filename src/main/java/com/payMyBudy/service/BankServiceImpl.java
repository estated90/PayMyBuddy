package com.payMyBudy.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.payMyBudy.dao.ConnectionsDao;
import com.payMyBudy.dao.HolderDao;
import com.payMyBudy.interfaces.BankService;
import com.payMyBudy.model.Bank;

public class BankServiceImpl implements BankService {
	@Autowired
	private EmailChecker emailChecker;
	@Autowired
	private HolderDao holderDao;
	@Autowired
	private ConnectionsDao connectionDao;

	private static final Logger logger = LogManager.getLogger("BankServiceImpl");
	
	@Override
	public List<Bank> getBanks (String email){
		return null;
		
	}
}
