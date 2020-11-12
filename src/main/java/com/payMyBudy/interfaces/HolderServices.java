package com.payMyBudy.interfaces;

import org.springframework.stereotype.Service;

import com.payMyBudy.exception.ServiceConnectionException;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.model.Holder;

@Service
public interface HolderServices {

	public Holder createHolder(String email) throws ServiceEmailException;

	public Holder connection(String email, String password) throws ServiceEmailException, ServiceConnectionException;
	
}