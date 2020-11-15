package com.payMyBudy.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payMyBudy.dao.ConnectionsDao;
import com.payMyBudy.dao.HolderDao;
import com.payMyBudy.dto.FriendList;
import com.payMyBudy.exception.ConnectionsException;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.interfaces.ConnectionServices;
import com.payMyBudy.model.Connections;
import com.payMyBudy.model.Holder;

@Service
public class ConnectionServicesImpl implements ConnectionServices {

	@Autowired
	private EmailChecker emailChecker;
	@Autowired
	private HolderDao holderDao;
	@Autowired
	private ConnectionsDao connectionDao;

	private static final Logger logger = LogManager.getLogger("ConnectionServicesImpl");

	public Connections createConnection(String email, String emailFriend)
			throws ServiceEmailException, ConnectionsException {
		Holder mainHolder = holderDao.findByEmail(email);
		Holder friend = holderDao.findByEmail(emailFriend);
		Connections pastConnection = connectionDao.findConnection(mainHolder, friend);
		emailChecker.validateMail(email);
		emailChecker.validateMail(emailFriend);
		if (email == emailFriend) {
			logger.error("Both email provided are the same: {} and {}", email, emailFriend);
			throw new ServiceEmailException("Emails provided are the same");
		} else if ((mainHolder == null) || (friend == null)) {
			logger.error("email has not been found in db: {} and/or {}", email, emailFriend);
			throw new ServiceEmailException("Email not found");
		} else if ((pastConnection != null) && (pastConnection.isActive() == true)) {
			logger.error("This connection already exists in DB");
			throw new ConnectionsException("Connections already exists");
		} else if (pastConnection == null) {
			Connections connection = new Connections();
			connection.setActive(true);
			connection.setFriendId(friend);
			connection.setHolderId(mainHolder);
			connection.setActive(true);
			connectionDao.save(connection);
			return connection;
		} else {
			pastConnection.setActive(true);
			return pastConnection;
		}
	}

	@Override
	public List<FriendList> getConnection(String email) throws ServiceEmailException {
		emailChecker.validateMail(email);
		Holder holder = holderDao.findByEmail(email);
		List<Connections> connectionFriends = holder.getFriendHolder();
		List<FriendList> friendList = new ArrayList<>();
		for (Connections connectionFriend:connectionFriends) {
			FriendList friend = new FriendList();
			friend.setEmail(connectionFriend.getHolderId().getEmail());
			friend.setFirstName(connectionFriend.getHolderId().getProfiles().getFirstName());
			friend.setLastName(connectionFriend.getHolderId().getProfiles().getLastName());
			friendList.add(friend);
		}
		return friendList;
	}
}
