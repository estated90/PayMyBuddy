package com.application.paymybuddy.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.application.paymybuddy.dto.BankList;
import com.application.paymybuddy.dto.CreationBank;
import com.application.paymybuddy.exception.ServiceBankException;
import com.application.paymybuddy.exception.ServiceEmailException;
import com.application.paymybuddy.exception.ServiceHolderException;
import com.application.paymybuddy.model.Bank;

/**
 * @author nicolas
 * Method to provide the services related to object bank in the controllers 
 */
@Service
public interface BankService {

	/**
	 * @param email
	 * @return List
	 * @throws ServiceEmailException
	 * @throws ServiceHolderException
	 * Getting all the banks as a List related to the user
	 */
	List<BankList> getBanks(String email) throws ServiceEmailException, ServiceHolderException;

	/**
	 * @param email
	 * @param bank
	 * @return Bank
	 * @throws ServiceEmailException
	 * @throws ServiceHolderException
	 * @throws ServiceBankException
	 * Create or reactivate a bank
	 */
	Bank postBank(String email, CreationBank bank) throws ServiceEmailException, ServiceHolderException, ServiceBankException;

	/**
	 * @param email
	 * @param bankDetail
	 * @return Bank
	 * @throws ServiceEmailException
	 * @throws ServiceHolderException
	 * @throws ServiceBankException
	 * update the information. Needs IBAN and BIC to be the one in DB
	 */
	Bank updateBank(String email, CreationBank bankDetail)
			throws ServiceEmailException, ServiceHolderException, ServiceBankException;

	/**
	 * @param email
	 * @param bankDetail
	 * @throws ServiceHolderException
	 * @throws ServiceEmailException
	 * @throws ServiceBankException
	 * Delete the information. Needs IBAN and BIC to be the one in DB
	 */
	void deleteBank(String email, CreationBank bankDetail) throws ServiceHolderException, ServiceEmailException, ServiceBankException;

}