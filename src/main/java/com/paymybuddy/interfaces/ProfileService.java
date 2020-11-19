package com.paymybuddy.interfaces;

import com.paymybuddy.dto.EditProfile;
import com.paymybuddy.exception.ServiceEmailException;
import com.paymybuddy.exception.ServiceHolderException;
import com.paymybuddy.model.Profiles;

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