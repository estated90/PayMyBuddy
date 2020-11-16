package com.payMyBudy.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.jmapper.JMapper;
import com.payMyBudy.dao.HolderDao;
import com.payMyBudy.dto.BankList;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.interfaces.BankService;
import com.payMyBudy.model.Bank;
import com.payMyBudy.model.Holder;

@Service
public class BankServiceImpl implements BankService {
	@Autowired
	private EmailChecker emailChecker;
	@Autowired
	private HolderDao holderDao;
	private static final Logger logger = LogManager.getLogger("BankServiceImpl");

	@Override
	public List<BankList> getBanks(String email) throws ServiceEmailException {
		logger.info("Creating list of banks for holder {}", email);
		emailChecker.validateMail(email);
		Holder holder = holderDao.findByEmail(email);
		if ((holder == null)) {
			logger.error("email has not been found in db: {}", email);
			throw new ServiceEmailException("Email of bank holder not found");
		}
		List<Bank> banks = holder.getBankId();
		List<BankList> bankList = new ArrayList<>();
		for (Bank bank : banks) {
			JMapper<BankList, Bank> bankMapper = new JMapper<>(BankList.class, Bank.class);
			BankList resultBank = bankMapper.getDestination(bank);
			bankList.add(resultBank);
		}
		logger.info("List created : {}", bankList);
		return bankList;
	}
}
