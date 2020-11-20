package com.application.paymybuddy.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.paymybuddy.dao.BankDao;
import com.application.paymybuddy.dao.ConnectionsDao;
import com.application.paymybuddy.dao.HolderDao;
import com.application.paymybuddy.exception.ServiceConnectionException;
import com.application.paymybuddy.exception.ServiceEmailException;
import com.application.paymybuddy.exception.ServiceHolderException;
import com.application.paymybuddy.interfaces.HolderServices;
import com.application.paymybuddy.interfaces.ProfileService;
import com.application.paymybuddy.model.Bank;
import com.application.paymybuddy.model.Connections;
import com.application.paymybuddy.model.Holder;

/**
 * @author nicolas
 *
 */
@Service
public class HolderServicesImpl implements HolderServices {

	@Autowired
	private HolderDao holderDao;
	@Autowired
	private ConnectionsDao connectionDao;
	@Autowired
	private BankDao bankDao;
	@Autowired
	private ProfileService profileService;
	@Autowired
	private PasswordManager passwordManager;

	@Autowired
	private Verification verification;

	private static final Logger logger = LogManager.getLogger("HolderServicesImpl");

	@Override
	public Holder createHolder(String email) throws ServiceEmailException, ServiceHolderException {
		verification.validateMail(email);
		Holder newHolder = new Holder();
		if (holderDao.findByEmail(email) != null) {
			logger.error("Email already in DB: {}", email);
			throw new ServiceHolderException("Email already used");
		}
		UUID uuid = UUID.randomUUID();
		newHolder.setHolderId(uuid);
		newHolder.setEmail(email);
		newHolder.setActive(true);
		newHolder.setCreatedAt(LocalDateTime.now());
		holderDao.save(newHolder);
		profileService.createProfile(email);
		return newHolder;
	}

	@Override
	public Holder connection(String email, String password)
			throws ServiceConnectionException, ServiceEmailException, ServiceHolderException {
		Holder holder = verification.verificationOfData(email);
		boolean validation = passwordManager.passwordDecoder(password, holder.getPassword());
		if (holder == null || !validation) {
			logger.error("Email is not in DB: {}", email);
			throw new ServiceConnectionException("Unknown email or/and password");
		}
		return holder;
	}

	@Override
	public void deleteHolder(String email) throws ServiceEmailException, ServiceHolderException {
		LocalDateTime now = LocalDateTime.now();
		Holder holder = verification.verificationOfData(email);
		holder.setActive(false);
		holder.setUpdatedAt(now);
		holderDao.save(holder);
		List<Bank> banks = holder.getBankId();
		for (Bank bank : banks) {
			bank.setActive(false);
			bank.setUpdate(now);
			bankDao.save(bank);
		}
		List<Connections> connections = holder.getHolderFriendship();
		for (Connections connection : connections) {
			connection.setActive(false);
			connectionDao.save(connection);
		}
		connections = holder.getHolderAsFriend();
		for (Connections connection : connections) {
			connection.setActive(false);
			connectionDao.save(connection);
		}
	}
}
