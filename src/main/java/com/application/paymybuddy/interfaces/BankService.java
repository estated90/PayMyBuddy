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
 *
 */
@Service
public interface BankService {

	/**
	 * @param email
	 * @return List
	 * @throws ServiceEmailException
	 * @throws ServiceHolderException
	 */
	List<BankList> getBanks(String email) throws ServiceEmailException, ServiceHolderException;

	/**
	 * @param email
	 * @param bank
	 * @return Bank
	 * @throws ServiceEmailException
	 * @throws ServiceHolderException
	 * @throws ServiceBankException
	 */
	Bank postBank(String email, CreationBank bank) throws ServiceEmailException, ServiceHolderException, ServiceBankException;

	/**
	 * @param email
	 * @param bankDetail
	 * @return Bank
	 * @throws ServiceEmailException
	 * @throws ServiceHolderException
	 * @throws ServiceBankException
	 */
	Bank updateBank(String email, CreationBank bankDetail)
			throws ServiceEmailException, ServiceHolderException, ServiceBankException;

	/**
	 * @param email
	 * @param bankDetail
	 * @throws ServiceHolderException
	 * @throws ServiceEmailException
	 * @throws ServiceBankException
	 */
	void deleteBank(String email, CreationBank bankDetail) throws ServiceHolderException, ServiceEmailException, ServiceBankException;

}