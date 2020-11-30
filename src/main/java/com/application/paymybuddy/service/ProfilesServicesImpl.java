package com.application.paymybuddy.service;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.paymybuddy.dao.HolderDao;
import com.application.paymybuddy.dao.ProfileDao;
import com.application.paymybuddy.dto.EditProfile;
import com.application.paymybuddy.exception.ServiceEmailException;
import com.application.paymybuddy.exception.ServiceHolderException;
import com.application.paymybuddy.interfaces.PasswordManager;
import com.application.paymybuddy.interfaces.ProfileService;
import com.application.paymybuddy.interfaces.Verification;
import com.application.paymybuddy.model.Holder;
import com.application.paymybuddy.model.Profiles;

/**
 * @author nicolas
 *
 */
@Service
@Transactional
public class ProfilesServicesImpl implements ProfileService {

	@Autowired
	private ProfileDao profileDao;
	@Autowired
	private HolderDao holderDao;
	@Autowired
	private Verification verification;
	@Autowired
	private PasswordManager passwordManager;

	private static final Logger logger = LogManager.getLogger("ProfilesServicesImpl");

	@Override
	public EditProfile getProfiles(String email) throws ServiceEmailException, ServiceHolderException {
		Holder holder = verification.verificationOfData(email);
		Profiles profile = holder.getProfiles();
		EditProfile resultProfile = new EditProfile();
		if (holder.isActive()) {
			resultProfile.setAddress(profile.getAddress());
			resultProfile.setFirstName(profile.getFirstName());
			resultProfile.setLastName(profile.getLastName());
			resultProfile.setPhone(profile.getPhone());
			resultProfile.setEmail(profile.getHolderId().getEmail());
		}
		return resultProfile;
	}

	@Override
	public void createProfile(String email) throws ServiceEmailException, ServiceHolderException {
		logger.info("Creating profile for user {}: ", email);
		Holder holder = verification.verificationOfData(email);
		Profiles newProfile = new Profiles();
		newProfile.setCreated(holder.getCreatedAt());
		newProfile.setHolderId(holder);
		profileDao.save(newProfile);
		logger.info("Profile created {}: ", newProfile);
	}

	@Override
	public Profiles updateProfile(String email, EditProfile profile)
			throws ServiceEmailException, ServiceHolderException {
		Holder holder = verification.verificationOfData(email);
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
		if (profile.getPassword() != null) {
			String encodedPassword = passwordManager.passwordEncoder(profile.getPassword());
			holder.setPassword(encodedPassword);
		}
		if (profile.getEmail() != null || profile.getPassword() != null)
			holder.setUpdatedAt(LocalDateTime.now());
		holderDao.save(holder);
		return profileModified;
	}
}
