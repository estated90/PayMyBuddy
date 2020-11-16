package com.payMyBudy.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.payMyBudy.dto.BankList;
import com.payMyBudy.exception.ServiceEmailException;

@Service
public interface BankService {

	List<BankList> getBanks(String email) throws ServiceEmailException;

}