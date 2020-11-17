package com.payMyBudy.interfaces;

import com.payMyBudy.dto.EditProfile;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.model.Profiles;

/**
 * @author nicolas
 *
 */
public interface ProfileService {

	/**
	 * @param email
	 * @throws ServiceEmailException
	 */
	void createProfile(String email) throws ServiceEmailException;

	/**
	 * @param email
	 * @param profile
	 * @return Profiles
	 * @throws ServiceEmailException
	 */
	Profiles updateProfile(String email, EditProfile profile) throws ServiceEmailException;

	/**
	 * @param email
	 * @return EditProfile
	 */
	EditProfile getProfiles(String email);

}