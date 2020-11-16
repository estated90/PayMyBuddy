package com.payMyBudy.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payMyBudy.dao.HolderDao;
import com.payMyBudy.exception.ServiceConnectionException;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.interfaces.HolderServices;
import com.payMyBudy.interfaces.ProfileService;
import com.payMyBudy.model.Holder;

@Service
public class HolderServicesImpl implements HolderServices {

	@Autowired
	private HolderDao holderDao;
	@Autowired
	private ProfileService profileService;

	@Autowired
	private EmailChecker emailChecker;

	private static final Logger logger = LogManager.getLogger("HolderServicesImpl");

	@Override
	public Holder createHolder(String email) throws ServiceEmailException {
		emailChecker.validateMail(email);
		Holder newHolder = new Holder();
		if (holderDao.findByEmail(email) != null) {
			logger.error("Email already in DB: " + email);
			throw new ServiceEmailException("Email already used");
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
	public Holder connection(String email, String password) throws ServiceConnectionException, ServiceEmailException {
		emailChecker.validateMail(email);
		Holder holderTest = holderDao.findByEmailAndPassword(email, password);
		if (holderTest == null) {
			logger.error("Email is not in DB: " + email);
			throw new ServiceConnectionException("Unknown email or/and password");
		}
		return holderTest;
	}


}
