package com.payMyBudy.service;

import org.springframework.stereotype.Service;

import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.model.Holder;

@Service
public interface ControllerServices {

	public Holder createHolder(Holder holder) throws ServiceEmailException;
	
}