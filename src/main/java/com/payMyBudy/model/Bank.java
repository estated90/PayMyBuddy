package com.payMyBudy.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.ForeignKey;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "bank")
public class Bank {

	@Id
	@GeneratedValue(generator = "bank_uuid")
	@GenericGenerator(name = "bank_uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "bank_id")
	private UUID bankId;
	@Column(name = "bank_iban")
	private String iban;
	@Column(name = "bank_bic")
	private String rib;
	@Column(name = "bank_created_at")
	private LocalDateTime created;
	@Column(name = "bank_updated_at")
	private LocalDateTime update;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="holder_fk", foreignKey=@ForeignKey(name="holder_fk"), referencedColumnName="holder_id", insertable = false, updatable = false)
	private Holder holderId;

	public Bank() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the bankId
	 */
	public UUID getBankId() {
		return bankId;
	}

	/**
	 * @param bankId the bankId to set
	 */
	public void setBankId(UUID bankId) {
		this.bankId = bankId;
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

	/**
	 * @return the created
	 */
	public LocalDateTime getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	/**
	 * @return the update
	 */
	public LocalDateTime getUpdate() {
		return update;
	}

	/**
	 * @param update the update to set
	 */
	public void setUpdate(LocalDateTime update) {
		this.update = update;
	}

}
