package com.payMyBudy.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payMyBudy.dao.HolderDao;
import com.payMyBudy.dao.ProfileDao;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.interfaces.ProfileService;
import com.payMyBudy.model.Holder;
import com.payMyBudy.model.Profiles;

@Service
public class ProfilesServicesImpl implements ProfileService {

	@Autowired
	private ProfileDao profileDao;
	@Autowired
	private HolderDao holderDao;
	
	private static final Logger logger = LogManager.getLogger("ProfilesServicesImpl");

	@Override
	public void createProfile(String email) throws ServiceEmailException {
		logger.info("Creating profile for user {]: ", email);
		Holder holder = holderDao.findByEmail(email);
		Profiles newProfile = new Profiles();
		newProfile.setCreated(holder.getCreatedAt());
		newProfile.setHolderId(holder);
		profileDao.save(newProfile);
		logger.info("Profile created {]: ", newProfile);
	}
	
}
