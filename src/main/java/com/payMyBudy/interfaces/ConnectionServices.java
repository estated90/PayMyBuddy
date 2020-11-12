package com.payMyBudy.interfaces;

import org.springframework.stereotype.Service;

import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.model.Connections;

@Service
public interface ConnectionServices {

	public Connections createConnection(String email, String emailFriend) throws ServiceEmailException;

}
