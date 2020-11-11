package com.payMyBudy.service;

import org.springframework.stereotype.Service;

import com.payMyBudy.exception.ServiceConnctionException;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.model.Holder;

@Service
public interface ControllerServices {

	public Holder createHolder(String email) throws ServiceEmailException;

	public Holder connection(String email, String password) throws ServiceEmailException, ServiceConnctionException;
	
}