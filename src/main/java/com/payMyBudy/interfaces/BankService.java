package com.payMyBudy.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.payMyBudy.dto.BankList;
import com.payMyBudy.dto.CreationBank;
import com.payMyBudy.exception.ServiceBankException;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.exception.ServiceHolderException;
import com.payMyBudy.model.Bank;

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