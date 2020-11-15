package com.payMyBudy.service;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.jmapper.JMapper;
import com.payMyBudy.dao.HolderDao;
import com.payMyBudy.dao.ProfileDao;
import com.payMyBudy.dto.EditProfile;
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
	@Autowired
	private EmailChecker emailChecker;

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

	@Override
	public Profiles updateProfile(String email, EditProfile profile) throws ServiceEmailException {
		emailChecker.validateMail(email);
		Holder holder = holderDao.findByEmail(email);
		if (holder == null) {
			logger.error("No user found for: " + email);
			throw new ServiceEmailException("Email was not found");
		}
		Profiles profileModified = holder.getProfiles();
		if (profile.getAddress() != null)
			profileModified.setAddress(profile.getAddress());
		if (profile.getFirstName() != null)
			profileModified.setFirstName(profile.getFirstName());
		if (profile.getLastName() != null)
			profileModified.setLastName(profile.getLastName());
		if (profile.getPhone() != null)
			profileModified.setPhone(profile.getPhone());
		if (profile.getAddress() != null || profile.getFirstName() != null || profile.getLastName() != null
				|| profile.getPhone() != null)
			profileModified.setUpdate(LocalDateTime.now());
		profileDao.save(profileModified);
		if (profile.getEmail() != null)
			holder.setEmail(profile.getEmail());
		if (profile.getPassword() != null)
			holder.setPassword(profile.getPassword());
		if (profile.getEmail() != null || profile.getPassword() != null)
			holder.setUpdatedAt(LocalDateTime.now());
		holderDao.save(holder);
		return profileModified;
	}

	@Override
	public EditProfile getProfiles(String email) {
		Holder holder = holderDao.findByEmail(email);
		Profiles profile = holder.getProfiles();
		JMapper<EditProfile, Profiles> profileMapper = new JMapper<>(EditProfile.class, Profiles.class);
		EditProfile resultProfile = profileMapper.getDestination(profile);
		resultProfile.setEmail(profile.getHolderId().getEmail());
		resultProfile.setPassword(profile.getHolderId().getPassword());
		return resultProfile;
	}

}
