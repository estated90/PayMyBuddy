package com.payMyBudy.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payMyBudy.dao.HolderDao;
import com.payMyBudy.dao.ProfileDao;
import com.payMyBudy.exception.ServiceConnectionException;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.interfaces.HolderServices;
import com.payMyBudy.interfaces.ProfileService;
import com.payMyBudy.model.Holder;
import com.payMyBudy.model.Profiles;

@Service
public class HolderServicesImpl implements HolderServices {

	@Autowired
	private HolderDao holderDao;
	@Autowired
	private ProfileDao profileDeo;
	@Autowired
	private ProfileService profileService;

	@Autowired
	private EmailChecker emailChecker;

	private static final Logger logger = LogManager.getLogger("HolderServices");

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

	@Override
	public Profiles updateProfile(String email, Profiles profile) throws ServiceEmailException {
		emailChecker.validateMail(email);
		Holder holder = holderDao.findByEmail(email);
		if (holder == null) {
			logger.error("No user found for: " + email);
			throw new ServiceEmailException("Email was not found");
		}
		Profiles profileModified = profileDeo.findByFk(holder);
		if (profile.getAddress()!=null) profileModified.setAddress(profile.getAddress());
		if (profile.getFirstName()!=null)profileModified.setFirstName(profile.getFirstName());
		if (profile.getLastName()!=null)profileModified.setLastName(profile.getLastName());
		if (profile.getPhone()!=null)profileModified.setPhone(profile.getPhone());
		profileModified.setUpdate(LocalDateTime.now());
		profileDeo.save(profileModified);
		
		
		return null;
	}
}
