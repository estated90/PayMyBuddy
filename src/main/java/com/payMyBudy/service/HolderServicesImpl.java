package com.payMyBudy.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payMyBudy.dao.HolderDao;
import com.payMyBudy.exception.ServiceConnectionException;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.exception.ServiceHolderException;
import com.payMyBudy.interfaces.HolderServices;
import com.payMyBudy.interfaces.ProfileService;
import com.payMyBudy.model.Holder;

/**
 * @author nicolas
 *
 */
@Service
public class HolderServicesImpl implements HolderServices {

	@Autowired
	private HolderDao holderDao;
	@Autowired
	private ProfileService profileService;

	@Autowired
	private Verification verification;

	private static final Logger logger = LogManager.getLogger("HolderServicesImpl");

	@Override
	public Holder createHolder(String email) throws ServiceEmailException, ServiceHolderException {
		verification.validateMail(email);
		Holder newHolder = new Holder();
		if (holderDao.findByEmail(email) != null) {
			logger.error("Email already in DB: " + email);
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
	public Holder connection(String email, String password) throws ServiceConnectionException, ServiceEmailException, ServiceHolderException {
		verification.validateMail(email);
		Holder holder = holderDao.findByEmail(email);
		if (holder == null || (holder.getPassword().equals(password)==false)) {
			logger.error("Email is not in DB: " + email);
			throw new ServiceConnectionException("Unknown email or/and password");
		}
		return holder;
	}

	@Override
	public List<Holder> getAllUsers() {
		List<Holder> holders = holderDao.findAll();
		return holders;
	}


}
