package com.application.paymybuddy.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.application.paymybuddy.dto.FriendList;
import com.application.paymybuddy.exception.ConnectionsException;
import com.application.paymybuddy.exception.ServiceEmailException;
import com.application.paymybuddy.exception.ServiceHolderException;
import com.application.paymybuddy.model.Connections;

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
	 * @throws ServiceHolderException 
	 * Create or reactivate a connection
	 */
	public Connections createConnection(String email, String emailFriend) throws ServiceEmailException, ConnectionsException, ServiceHolderException;

	/**
	 * @param email
	 * @return List
	 * @throws ServiceEmailException
	 * @throws ServiceHolderException 
	 * Get all connection link to the account
	 */
	public List<FriendList> getConnection(String email) throws ServiceEmailException, ServiceHolderException;

	/**
	 * @param email
	 * @param emailFriend
	 * @throws ServiceEmailException
	 * @throws ConnectionsException
	 * @throws ServiceHolderException 
	 * Delete a connection by setting false
	 */
	void deleteConnection(String email, String emailFriend) throws ServiceEmailException, ConnectionsException, ServiceHolderException;

}
