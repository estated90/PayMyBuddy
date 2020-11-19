package com.payMyBudy.service;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payMyBudy.dao.HolderDao;
import com.payMyBudy.dao.ProfileDao;
import com.payMyBudy.dto.EditProfile;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.exception.ServiceHolderException;
import com.payMyBudy.interfaces.ProfileService;
import com.payMyBudy.model.Holder;
import com.payMyBudy.model.Profiles;

/**
 * @author nicolas
 *
 */
@Service
public class ProfilesServicesImpl implements ProfileService {

	@Autowired
	private ProfileDao profileDao;
	@Autowired
	private HolderDao holderDao;
	@Autowired
	private Verification verification;

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
			resultProfile.setPassword(profile.getHolderId().getPassword());
		}
		return resultProfile;
	}

	@Override
	public void createProfile(String email) throws ServiceEmailException, ServiceHolderException {
		logger.info("Creating profile for user {]: ", email);
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
		if (profile.getPassword() != null)
			holder.setPassword(profile.getPassword());
		if (profile.getEmail() != null || profile.getPassword() != null)
			holder.setUpdatedAt(LocalDateTime.now());
		holderDao.save(holder);
		return profileModified;
	}
}
