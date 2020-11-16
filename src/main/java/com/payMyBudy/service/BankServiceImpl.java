package com.payMyBudy.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payMyBudy.dao.BankDao;
import com.payMyBudy.dao.HolderDao;
import com.payMyBudy.dto.BankList;
import com.payMyBudy.dto.CreationBank;
import com.payMyBudy.exception.ServiceBankException;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.exception.ServiceHolderException;
import com.payMyBudy.interfaces.BankService;
import com.payMyBudy.model.Bank;
import com.payMyBudy.model.Holder;

@Service
public class BankServiceImpl implements BankService {
	@Autowired
	private EmailChecker emailChecker;
	@Autowired
	private HolderDao holderDao;
	@Autowired
	private BankDao bankDao;
	private static final Logger logger = LogManager.getLogger("BankServiceImpl");

	@Override
	public List<BankList> getBanks(String email) throws ServiceEmailException, ServiceHolderException {
		logger.info("Creating list of banks for holder {}", email);
		emailChecker.validateMail(email);
		Holder holder = holderDao.findByEmail(email);
		if ((holder == null)) {
			logger.error("email has not been found in db: {}", email);
			throw new ServiceHolderException("Email of bank holder not found");
		}
		List<Bank> banks = holder.getBankId();
		List<BankList> bankList = new ArrayList<>();
		for (Bank bank : banks) {
			if (bank.isActive() == true) {
				BankList item = new BankList();
				item.setDomiciliation(bank.getDomiciliation());
				item.setIban(bank.getIban());
				item.setName(bank.getName());
				item.setRib(bank.getRib());
				bankList.add(item);
			}
		}
		logger.info("List created : {}", bankList);
		return bankList;
	}

	@Override
	public Bank postBank(String email, CreationBank bankDetail)
			throws ServiceEmailException, ServiceHolderException, ServiceBankException {
		logger.info("Creating bank for holder {}", email);
		emailChecker.validateMail(email);
		Holder holder = holderDao.findByEmail(email);
		if ((holder == null)) {
			logger.error("email has not been found in db: {}", email);
			throw new ServiceHolderException("Email of bank holder not found");
		}
		Bank existingBank = holder.getBankId().stream()
				.filter(str -> str.getIban().equals(bankDetail.getIban()) & str.getRib().equals(bankDetail.getRib()))
				.findAny().orElse(null);
		if (existingBank != null && existingBank.isActive() == true) {
			logger.error("The bank account already exist in db");
			throw new ServiceBankException("This bank is already attach to the user");
		} else if (existingBank == null) {
			Bank item = new Bank();
			item.setDomiciliation(bankDetail.getDomiciliation());
			item.setIban(bankDetail.getIban());
			item.setName(bankDetail.getName());
			item.setRib(bankDetail.getRib());
			item.setCreated(LocalDateTime.now());
			item.setActive(true);
			item.setHolderId(holder);
			bankDao.save(item);
			return item;
		} else {
			existingBank.setActive(true);
			bankDao.save(existingBank);
			return existingBank;
		}
	}

	@Override
	public Bank updateBank(String email, CreationBank bankDetail)
			throws ServiceEmailException, ServiceHolderException, ServiceBankException {
		logger.info("Updating bank for holder {}", email);
		emailChecker.validateMail(email);
		Holder holder = holderDao.findByEmail(email);
		if ((holder == null)) {
			logger.error("email has not been found in db: {}", email);
			throw new ServiceHolderException("Email of bank holder not found");
		}
		Bank existingBank = holder.getBankId().stream()
				.filter(str -> str.getIban().equals(bankDetail.getIban()) & str.getRib().equals(bankDetail.getRib()))
				.findAny().orElse(null);
		if (existingBank == null) {
			logger.error("The bank account do not exist in db");
			throw new ServiceBankException("This bank do not exist");
		}
		if (bankDetail.getName() != null)
			existingBank.setName(bankDetail.getName());
		if (bankDetail.getDomiciliation() != null)
			existingBank.setDomiciliation(bankDetail.getDomiciliation());
		if (bankDetail.getIban() != null)
			existingBank.setIban(bankDetail.getIban());
		if (bankDetail.getRib() != null)
			existingBank.setRib(bankDetail.getRib());
		existingBank.setUpdate(LocalDateTime.now());
		bankDao.save(existingBank);
		return existingBank;
	}

	@Override
	public void deleteBank(String email, CreationBank bankDetail)
			throws ServiceHolderException, ServiceEmailException, ServiceBankException {
		logger.info("Deleting bank for holder {}", email);
		emailChecker.validateMail(email);
		Holder holder = holderDao.findByEmail(email);
		if ((holder == null)) {
			logger.error("email has not been found in db: {}", email);
			throw new ServiceHolderException("Email of bank holder not found");
		}
		Bank existingBank = holder.getBankId().stream()
				.filter(str -> str.getIban().equals(bankDetail.getIban()) & str.getRib().equals(bankDetail.getRib()))
				.findAny().orElse(null);
		if (existingBank == null) {
			logger.error("The bank account do not exist in db");
			throw new ServiceBankException("This bank do not exist");
		}
		existingBank.setUpdate(LocalDateTime.now());
		existingBank.setActive(false);
		bankDao.save(existingBank);
	}

}
