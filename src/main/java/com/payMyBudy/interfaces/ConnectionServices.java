package com.payMyBudy.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.payMyBudy.dto.FriendList;
import com.payMyBudy.exception.ConnectionsException;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.model.Connections;

/**
 * @author nicolas
 *
 */
@Service
public interface ConnectionServices {

	/**
	 * @param email
	 * @param emailFriend
	 * @return Connections
	 * @throws ServiceEmailException
	 * @throws ConnectionsException
	 */
	public Connections createConnection(String email, String emailFriend) throws ServiceEmailException, ConnectionsException;

	/**
	 * @param email
	 * @return List<FriendList>
	 * @throws ServiceEmailException
	 */
	public List<FriendList> getConnection(String email) throws ServiceEmailException;

	/**
	 * @param email
	 * @param emailFriend
	 * @throws ServiceEmailException
	 * @throws ConnectionsException
	 */
	void deleteConnection(String email, String emailFriend) throws ServiceEmailException, ConnectionsException;

}
