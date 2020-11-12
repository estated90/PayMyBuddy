package com.payMyBudy.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payMyBudy.dao.ConnectionsDao;
import com.payMyBudy.dao.HolderDao;
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

	public Connections createConnection(String email, String emailFriend) throws ServiceEmailException {
		emailChecker.validateMail(email);
		emailChecker.validateMail(emailFriend);
		Holder mainHolder = holderDao.findByEmail(email);
		Holder friend = holderDao.findByEmail(email);
		if (mainHolder == null || friend == null) {
			logger.error("email has not been found in db: {} and/or {}", email, emailFriend);
			throw new ServiceEmailException("Email not found");
		}
		
		//TODO create a check if relationship exist to avoid duplicate
		
		Connections connection = new Connections();
		connection.setActive(true);
		connection.setFriendId(friend);
		connection.setHolderId(mainHolder);
		connectionDao.save(connection);
		return connection;
	}

}
