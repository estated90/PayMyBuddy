package com.payMyBudy.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.googlecode.jmapper.annotations.JGlobalMap;

/**
 * @author nicolas
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JGlobalMap
public class BankList {
	
	private String name;
	private String domiciliation;
	private String iban;
	private String rib;
	/**
	 * 
	 */
	public BankList() {
	}
	
	/**
	 * @param iban
	 * @param rib
	 */
	public BankList(String iban, String rib) {
		super();
		this.iban = iban;
		this.rib = rib;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the domiciliation
	 */
	public String getDomiciliation() {
		return domiciliation;
	}
	/**
	 * @param domiciliation the domiciliation to set
	 */
	public void setDomiciliation(String domiciliation) {
		this.domiciliation = domiciliation;
	}
	/**
	 * @return the iban
	 */
	public String getIban() {
		return iban;
	}
	/**
	 * @param iban the iban to set
	 */
	public void setIban(String iban) {
		this.iban = iban;
	}
	/**
	 * @return the rib
	 */
	public String getRib() {
		return rib;
	}
	/**
	 * @param rib the rib to set
	 */
	public void setRib(String rib) {
		this.rib = rib;
	}
	
	
}
