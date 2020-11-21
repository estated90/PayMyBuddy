package com.application.paymybuddy.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.paymybuddy.dao.MovementDao;
import com.application.paymybuddy.dto.BankList;
import com.application.paymybuddy.dto.ReturnMovement;
import com.application.paymybuddy.dto.Solde;
import com.application.paymybuddy.exception.ServiceBankException;
import com.application.paymybuddy.exception.ServiceEmailException;
import com.application.paymybuddy.exception.ServiceHolderException;
import com.application.paymybuddy.exception.ServiceMovementException;
import com.application.paymybuddy.interfaces.MovementService;
import com.application.paymybuddy.interfaces.Verification;
import com.application.paymybuddy.model.Bank;
import com.application.paymybuddy.model.Holder;
import com.application.paymybuddy.model.Movement;
import com.application.paymybuddy.model.Transactions;

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
			if (movement.getTransaction() != null)
				returnMovement.setTransactionDescription(movement.getTransaction().getDescription());
			returnedMovement.add(returnMovement);
		}
		logger.info("returning all movement : {}", returnedMovement);
		return returnedMovement;
	}

	@Override
	public Solde calculateSoldeAccount(String email) throws ServiceEmailException, ServiceHolderException {
		logger.info("calculating all movement of user : {}", email);
		Holder holder = verification.verificationOfData(email);
		double amount = (double) Math.round(movementDao.sumAmounts(holder) * 100) / 100;
		Solde solde = new Solde();
		solde.setAccount(holder.getEmail());
		solde.setSolde(amount);
		logger.info("all movement of user are: {}", amount);
		return solde;
	}

	@Override
	public Movement createMovement(String email, BankList bank, double amount)
			throws ServiceEmailException, ServiceHolderException, ServiceBankException, ServiceMovementException {
		logger.info("Creating new movement for user : {}", email);
		Holder holder = verification.verificationOfData(email);
		Bank existingBank = holder.getBankId().stream()
				.filter(str -> str.getIban().equals(bank.getIban()) && str.getRib().equals(bank.getRib())).findAny()
				.orElse(null);
		if (existingBank == null) {
			logger.error("The bank account do not exist in db");
			throw new ServiceBankException("This bank do not exist");
		} else if (amount < 0 && !existingBank.isActive()) {
			logger.error("The bank account is not active");
			throw new ServiceBankException("This bank has been disabled, please enable it");
		} else {
			verification.verifyMovementAuthorized(holder, amount);
			Movement movement = new Movement();
			movement.setAmount(amount);
			movement.setBank(existingBank);
			movement.setCreated(LocalDateTime.now());
			movement.setHolder(holder);
			movementDao.save(movement);
			return movement;
		}
	}

	@Override
	public Movement createMovement(String email, Transactions transaction, double amount)
			throws ServiceEmailException, ServiceHolderException, ServiceBankException, ServiceMovementException {
		logger.info("Creating new movement for user : {}", email);
		Holder holder = verification.verificationOfData(email);
		Movement movement = new Movement();
		movement.setAmount(amount);
		movement.setTransaction(transaction);
		movement.setCreated(LocalDateTime.now());
		movement.setHolder(holder);
		movementDao.save(movement);
		return movement;
	}

}
