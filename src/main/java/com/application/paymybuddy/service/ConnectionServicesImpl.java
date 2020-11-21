package com.application.paymybuddy.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.paymybuddy.dao.ConnectionsDao;
import com.application.paymybuddy.dto.FriendList;
import com.application.paymybuddy.exception.ConnectionsException;
import com.application.paymybuddy.exception.ServiceEmailException;
import com.application.paymybuddy.exception.ServiceHolderException;
import com.application.paymybuddy.interfaces.ConnectionServices;
import com.application.paymybuddy.interfaces.Verification;
import com.application.paymybuddy.model.Connections;
import com.application.paymybuddy.model.Holder;

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
			if (connectionFriend.isActive()) {
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
		Holder mainHolder = verification.verificationOfData(email);
		Holder friend = verification.verificationOfData(emailFriend);
		Connections pastConnection = connectionDao.findConnection(mainHolder, friend);
		verification.validateMail(email);
		verification.validateMail(emailFriend);
		if (email.equals(emailFriend)) {
			logger.error("Both email provided are the same: {} and {}", email, emailFriend);
			throw new ServiceEmailException("Emails provided are the same");
		} else if ((pastConnection != null) && (pastConnection.isActive())) {
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
	public void deleteConnection(String email, String emailFriend)
			throws ServiceEmailException, ConnectionsException, ServiceHolderException {
		Holder mainHolder = verification.verificationOfData(email);
		Holder friend = verification.verificationOfData(emailFriend);
		Connections connection = mainHolder.getHolderFriendship().stream()
				.filter(str -> str.getFriendId().equals(friend)).findAny().orElse(null);
		if (connection != null) {
			connection.setActive(false);
			connectionDao.save(connection);
		} else {
			logger.error("No connection has been found");
			throw new ConnectionsException("No connection to delete");
		}
	}

}
