package com.paymybuddy.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.dao.BankDao;
import com.paymybuddy.dto.BankList;
import com.paymybuddy.dto.CreationBank;
import com.paymybuddy.exception.ServiceBankException;
import com.paymybuddy.exception.ServiceEmailException;
import com.paymybuddy.exception.ServiceHolderException;
import com.paymybuddy.interfaces.BankService;
import com.paymybuddy.model.Bank;
import com.paymybuddy.model.Holder;

/**
 * @author nicolas
 *
 */
@Service
public class BankServiceImpl implements BankService {
	@Autowired
	private Verification verification;
	@Autowired
	private BankDao bankDao;
	private static final Logger logger = LogManager.getLogger("BankServiceImpl");

	@Override
	public List<BankList> getBanks(String email) throws ServiceEmailException, ServiceHolderException {
		logger.info("Creating list of banks for holder {}", email);
		Holder holder = verification.verificationOfData(email) ;
		List<Bank> banks = holder.getBankId();
		List<BankList> bankList = new ArrayList<>();
		for (Bank bank : banks) {
			if (bank.isActive()) {
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
		Holder holder = verification.verificationOfData(email) ;
		Bank existingBank = holder.getBankId().stream()
				.filter(str -> str.getIban().equals(bankDetail.getIban()) && str.getRib().equals(bankDetail.getRib()))
				.findAny().orElse(null);
		if (existingBank != null && existingBank.isActive()) {
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
		Holder holder = verification.verificationOfData(email) ;
		Bank existingBank = holder.getBankId().stream()
				.filter(str -> str.getIban().equals(bankDetail.getIban()) && str.getRib().equals(bankDetail.getRib()))
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
		Holder holder = verification.verificationOfData(email) ;
		Bank existingBank = holder.getBankId().stream()
				.filter(str -> str.getIban().equals(bankDetail.getIban()) && str.getRib().equals(bankDetail.getRib()))
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
