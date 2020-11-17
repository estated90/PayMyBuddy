package com.payMyBudy.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author nicolas
 *
 */
@Entity
@Table(name = "movement")
public class Movement {

	@Id
	@GeneratedValue(generator = "movement_uuid")
	@GenericGenerator(name = "movement_uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "movement_id", unique = true)
	private UUID id;
	@Column(name="amount")
	private long amount;
	@Column(name="created_at", updatable=false)
	private LocalDateTime created;
	@Column(name="updated_at")
	private LocalDateTime update;
	@OneToOne
	@JoinColumn(name = "bank_fk", foreignKey=@ForeignKey(name="bank_fk"))
	private Bank bankId;
	@OneToOne
	@JoinColumn(name = "holder_fk", foreignKey=@ForeignKey(name="holder_fk"))
	private Holder holderId;

	public Movement() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * @return the amount
	 */
	public long getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(long amount) {
		this.amount = amount;
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
	 * @return the bankId
	 */
	public Bank getBankId() {
		return bankId;
	}

	/**
	 * @param bankId the bankId to set
	 */
	public void setBankId(Bank bankId) {
		this.bankId = bankId;
	}

	/**
	 * @return the personId
	 */
	public Holder getPersonId() {
		return holderId;
	}

	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(Holder personId) {
		this.holderId = personId;
	}
	
}
