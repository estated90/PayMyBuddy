package com.payMyBudy.interfaces;

import java.util.List;

import com.payMyBudy.model.Bank;

public interface BankService {

	List<Bank> getBanks(String email);

}