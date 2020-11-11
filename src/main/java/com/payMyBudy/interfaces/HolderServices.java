package com.payMyBudy.interfaces;

import org.springframework.stereotype.Service;

import com.payMyBudy.exception.ServiceConnectionException;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.model.Holder;
import com.payMyBudy.model.Profiles;

@Service
public interface HolderServices {

	public Holder createHolder(String email) throws ServiceEmailException;

	public Holder connection(String email, String password) throws ServiceEmailException, ServiceConnectionException;

	public Profiles updateProfile(String email, Profiles profile) throws ServiceEmailException;
	
}