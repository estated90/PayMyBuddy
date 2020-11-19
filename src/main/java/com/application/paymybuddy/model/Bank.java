package com.application.paymybuddy.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * @author nicolas
 *
 */
@Entity
@Table(name = "bank")
public class Bank {

	@Id
	@GeneratedValue(generator = "bank_uuid")
	@GenericGenerator(name = "bank_uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "bank_id")
	private UUID bankId;
	@Column(name = "name")
	private String name;
	@Column(name = "domiciliation")
	private String domiciliation;
	@Column(name = "bank_iban")
	private String iban;
	@Column(name = "bank_bic")
	private String rib;
	@Column(name = "is_active")
	boolean isActive;
	@Column(name = "bank_created_at")
	private LocalDateTime created;
	@Column(name = "bank_updated_at")
	private LocalDateTime update;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "holder_fk", foreignKey = @ForeignKey(name = "holder_fk"))
	private Holder holder;
	@OneToMany(mappedBy = "bank")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Movement> movement = new ArrayList<>();
	
	public Bank() {
		super();
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
	 * @return the holderId
	 */
	public Holder getHolderId() {
		return holder;
	}

	/**
	 * @param holderId the holderId to set
	 */
	public void setHolderId(Holder holderId) {
		this.holder = holderId;
	}

	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the movement
	 */
	public List<Movement> getMovement() {
		return movement;
	}

	/**
	 * @param movement the movement to set
	 */
	public void setMovement(List<Movement> movement) {
		this.movement = movement;
	}

}
