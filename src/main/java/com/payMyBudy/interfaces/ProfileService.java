package com.payMyBudy.interfaces;

import com.payMyBudy.dto.EditProfile;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.exception.ServiceHolderException;
import com.payMyBudy.model.Profiles;

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