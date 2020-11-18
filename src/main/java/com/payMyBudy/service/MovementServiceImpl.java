package com.payMyBudy.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payMyBudy.dao.MovementDao;
import com.payMyBudy.dto.BankList;
import com.payMyBudy.dto.ReturnMovement;
import com.payMyBudy.exception.ServiceBankException;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.exception.ServiceHolderException;
import com.payMyBudy.exception.ServiceMovementException;
import com.payMyBudy.interfaces.MovementService;
import com.payMyBudy.model.Bank;
import com.payMyBudy.model.Holder;
import com.payMyBudy.model.Movement;
import com.payMyBudy.model.Transactions;

/**
 * @author nicolas
 *
 */
@Service
public class MovementServiceImpl implements MovementService {

	@Autowired
	private MovementDao movementDao;

	@Autowired
	private Verification verification;

	private static final Logger logger = LogManager.getLogger("MovementServiceImpl");

	@Override
	public List<ReturnMovement> getMovement(String email) throws ServiceEmailException, ServiceHolderException {
		logger.info("getting all movement of user : {}", email);
		Holder holder = verification.verificationOfData(email);
		List<Movement> movements = holder.getMovement();
		List<ReturnMovement> returnedMovement = new ArrayList<>();
		for (Movement movement : movements) {
			ReturnMovement returnMovement = new ReturnMovement();
			returnMovement.setAmount(movement.getAmount());
			if (movement.getBank() != null)
				returnMovement.setBankName(movement.getBank().getName());
			returnMovement.setCreatedAt(movement.getCreated());
			if (movement.getTransactions() != null)
				returnMovement.setTransactionDescription(movement.getTransactions().getDescription());
			returnedMovement.add(returnMovement);
		}
		logger.info("returning all movement : {}", returnedMovement);
		return returnedMovement;
	}

	@Override
	public double calculateSoldeAccount(String email) throws ServiceEmailException, ServiceHolderException {
		logger.info("calculating all movement of user : {}", email);
		Holder holder = verification.verificationOfData(email);
		double amount = (double) Math.round(movementDao.sumAmounts(holder) * 100) / 100;
		logger.info("all movement of user are: {}", amount);
		return amount;
	}

	@Override
	public Movement createMovement(String email, BankList bank, double amount)
			throws ServiceEmailException, ServiceHolderException, ServiceBankException, ServiceMovementException {
		logger.info("Creating new movement for user : {}", email);
		Holder holder = verification.verificationOfData(email);
		Bank existingBank = holder.getBankId().stream()
				.filter(str -> str.getIban().equals(bank.getIban()) & str.getRib().equals(bank.getRib())).findAny()
				.orElse(null);
		if (existingBank == null) {
			logger.error("The bank account do not exist in db");
			throw new ServiceBankException("This bank do not exist");
		}
		verifyMovementAuthorized(holder, amount);
		Movement movement = new Movement();
		movement.setAmount(amount);
		movement.setBank(existingBank);
		movement.setCreated(LocalDateTime.now());
		movement.setHolder(holder);
		movementDao.save(movement);
		return movement;
	}
	
	@Override
	public Movement createMovement(String email, Transactions transaction, double amount)
			throws ServiceEmailException, ServiceHolderException, ServiceBankException, ServiceMovementException {
		logger.info("Creating new movement for user : {}", email);
		Holder holder = verification.verificationOfData(email);
		verifyMovementAuthorized(holder, amount);
		Movement movement = new Movement();
		movement.setAmount(amount);
		movement.setTransactions(transaction);;
		movement.setCreated(LocalDateTime.now());
		movement.setHolder(holder);
		movementDao.save(movement);
		return movement;
	}

	private void verifyMovementAuthorized(Holder holder, double amount) throws ServiceMovementException {
		double solde = (double) Math.round(movementDao.sumAmounts(holder) * 100) / 100;
		if ((amount < 0) && ((solde + amount) < 0)) {
			logger.error("The amount to sent ({}) is too high for the solde of the account ({})", amount, solde);
			throw new ServiceMovementException("Amount to sent is higher than the solde of the account");
		}
	}

}
