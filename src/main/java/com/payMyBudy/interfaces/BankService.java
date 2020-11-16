package com.payMyBudy.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.payMyBudy.dto.BankList;
import com.payMyBudy.dto.CreationBank;
import com.payMyBudy.exception.ServiceBankException;
import com.payMyBudy.exception.ServiceEmailException;
import com.payMyBudy.exception.ServiceHolderException;
import com.payMyBudy.model.Bank;

@Service
public interface BankService {

	List<BankList> getBanks(String email) throws ServiceEmailException, ServiceHolderException;

	Bank postBank(String email, CreationBank bank) throws ServiceEmailException, ServiceHolderException, ServiceBankException;

	Bank updateBank(String email, CreationBank bankDetail)
			throws ServiceEmailException, ServiceHolderException, ServiceBankException;

	void deleteBank(String email, CreationBank bankDetail) throws ServiceHolderException, ServiceEmailException, ServiceBankException;

}