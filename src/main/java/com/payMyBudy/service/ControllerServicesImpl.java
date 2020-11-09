package com.payMyBudy.service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payMyBudy.dao.HolderDao;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.model.Holder;

@Service
public class ControllerServicesImpl implements ControllerServices {

	@Autowired
	private HolderDao holderDao;

	private static final Logger logger = LogManager.getLogger("ControllerServices");

	@Override
	public Holder createHolder(String email) throws ServiceEmailException {
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(email);
		boolean matchFound = m.matches();
		if (matchFound) {
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
			return newHolder;
		} else {
			throw new ServiceEmailException("String provided is not an email");
		}
	}

}
