package com.payMyBudy.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	private UUID movementId;
	@Column(name="amount")
	private double amount;
	@Column(name="created_at", updatable=false)
	private LocalDateTime created;
	@Column(name="updated_at")
	private LocalDateTime update;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "holder_fk", foreignKey=@ForeignKey(name="holder_fk"), referencedColumnName="holder_id")
	private Holder holder;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bank_fk", foreignKey=@ForeignKey(name="bank_fk"), referencedColumnName="bank_id")
	private Bank bank;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(foreignKey=@ForeignKey(name="transaction_fk"), referencedColumnName="transaction_id")
	private Transactions transaction;

	public Movement() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the movementId
	 */
	public UUID getMovementId() {
		return movementId;
	}

	/**
	 * @param movementId the movementId to set
	 */
	public void setMovementId(UUID movementId) {
		this.movementId = movementId;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
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
	 * @return the holder
	 */
	public Holder getHolder() {
		return holder;
	}

	/**
	 * @param holder the holder to set
	 */
	public void setHolder(Holder holder) {
		this.holder = holder;
	}

	/**
	 * @return the bank
	 */
	public Bank getBank() {
		return bank;
	}

	/**
	 * @param bank the bank to set
	 */
	public void setBank(Bank bank) {
		this.bank = bank;
	}

	/**
	 * @return the transaction
	 */
	public Transactions getTransaction() {
		return transaction;
	}

	/**
	 * @param transaction the transaction to set
	 */
	public void setTransaction(Transactions transaction) {
		this.transaction = transaction;
	}

}
