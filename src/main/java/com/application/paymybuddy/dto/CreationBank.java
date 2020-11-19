package com.application.paymybuddy.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.googlecode.jmapper.annotations.JGlobalMap;

/**
 * @author nicolas
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JGlobalMap
public class CreationBank {

	@NotNull
	private String name;
	@NotNull
	private String domiciliation;
	@NotNull
	private String iban;
	@NotNull
	private String rib;

	/**
	 * 
	 */
	public CreationBank() {
	}


	/**
	 * @param name
	 * @param domiciliation
	 * @param iban
	 * @param rib
	 */
	public CreationBank(@NotNull String name, @NotNull String domiciliation, @NotNull String iban,
			@NotNull String rib) {
		super();
		this.name = name;
		this.domiciliation = domiciliation;
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
