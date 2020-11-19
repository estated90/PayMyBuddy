package com.paymybuddy.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.dao.ConnectionsDao;
import com.paymybuddy.dto.FriendList;
import com.paymybuddy.exception.ConnectionsException;
import com.paymybuddy.exception.ServiceEmailException;
import com.paymybuddy.exception.ServiceHolderException;
import com.paymybuddy.interfaces.ConnectionServices;
import com.paymybuddy.model.Connections;
import com.paymybuddy.model.Holder;

/**
 * @author nicolas
 *
 */
@Service
public class ConnectionServicesImpl implements ConnectionServices {

	@Autowired
	private Verification verification;
	@Autowired
	private ConnectionsDao connectionDao;

	private static final Logger logger = LogManager.getLogger("ConnectionServicesImpl");

	@Override
	public List<FriendList> getConnection(String email) throws ServiceEmailException, ServiceHolderException {
		Holder holder = verification.verificationOfData(email);
		List<Connections> connectionFriends = holder.getHolderFriendship();
		List<FriendList> friendList = new ArrayList<>();
		for (Connections connectionFriend : connectionFriends) {
			if (connectionFriend.isActive()==true) {
				FriendList friend = new FriendList();
				friend.setEmail(connectionFriend.getFriendId().getEmail());
				friend.setFirstName(connectionFriend.getFriendId().getProfiles().getFirstName());
				friend.setLastName(connectionFriend.getFriendId().getProfiles().getLastName());
				friendList.add(friend);
			}
		}
		return friendList;
	}

	@Override
	public Connections createConnection(String email, String emailFriend)
			throws ServiceEmailException, ConnectionsException, ServiceHolderException {
		Holder mainHolder = verification.verificationOfData(email) ;
		Holder friend = verification.verificationOfData(emailFriend) ;
		Connections pastConnection = connectionDao.findConnection(mainHolder, friend);
		verification.validateMail(email);
		verification.validateMail(emailFriend);
		if (email.equals(emailFriend)) {
			logger.error("Both email provided are the same: {} and {}", email, emailFriend);
			throw new ServiceEmailException("Emails provided are the same");
		} else if ((pastConnection != null) && (pastConnection.isActive() == true)) {
			logger.error("This connection already exists in DB");
			throw new ConnectionsException("Connections already exists");
		} else if (pastConnection == null) {
			Connections connection = new Connections();
			connection.setActive(true);
			connection.setFriendId(friend);
			connection.setHolderId(mainHolder);
			connectionDao.save(connection);
			return connection;
		} else {
			pastConnection.setActive(true);
			connectionDao.save(pastConnection);
			return pastConnection;
		}
	}

	@Override
	public void deleteConnection(String email, String emailFriend) throws ServiceEmailException, ConnectionsException, ServiceHolderException {
		Holder mainHolder = verification.verificationOfData(email) ;
		Holder friend = verification.verificationOfData(emailFriend) ;
		List<Connections> connectionFriends = mainHolder.getHolderFriendship();
		boolean result = false;
		for (Connections connectionFriend : connectionFriends) {
			if (connectionFriend.getFriendId()==friend) {
				connectionFriend.setActive(false);
				connectionDao.save(connectionFriend);
				result = true;
				break;
			} else if (result == false) {
				logger.error("No connection has been found");
				throw new ConnectionsException("No connection to delete");
			}
		}
	}
}
