package com.payMyBudy.interfaces;

import com.payMyBudy.dto.EditProfile;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.model.Profiles;

public interface ProfileService {

	void createProfile(String email) throws ServiceEmailException;

	Profiles updateProfile(String email, EditProfile profile) throws ServiceEmailException;

	EditProfile getProfiles(String email);

}