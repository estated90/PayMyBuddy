package com.payMyBudy.interfaces;

import com.payMyBudy.exception.ServiceEmailException;

public interface ProfileService {

	void createProfile(String email) throws ServiceEmailException;

}