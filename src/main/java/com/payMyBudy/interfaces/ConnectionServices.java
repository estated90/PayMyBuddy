package com.payMyBudy.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.payMyBudy.dto.FriendList;
import com.payMyBudy.exception.ConnectionsException;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.model.Connections;

@Service
public interface ConnectionServices {

	public Connections createConnection(String email, String emailFriend) throws ServiceEmailException, ConnectionsException;

	public List<FriendList> getConnection(String email) throws ServiceEmailException;

}
