package com.application.paymybuddy.interfaces;

import com.application.paymybuddy.dto.EditProfile;
import com.application.paymybuddy.exception.ServiceEmailException;
import com.application.paymybuddy.exception.ServiceHolderException;
import com.application.paymybuddy.model.Profiles;

/**
 * @author nicolas
 *
 */
public interface ProfileService {

	/**
	 * @param email
	 * @throws ServiceEmailException
	 * @throws ServiceHolderException 
	 */
	void createProfile(String email) throws ServiceEmailException, ServiceHolderException;

	/**
	 * @param email
	 * @param profile
	 * @return Profiles
	 * @throws ServiceEmailException
	 * @throws ServiceHolderException 
	 */
	Profiles updateProfile(String email, EditProfile profile) throws ServiceEmailException, ServiceHolderException;

	/**
	 * @param email
	 * @return EditProfile
	 * @throws ServiceHolderException 
	 * @throws ServiceEmailException 
	 */
	EditProfile getProfiles(String email) throws ServiceEmailException, ServiceHolderException;

}