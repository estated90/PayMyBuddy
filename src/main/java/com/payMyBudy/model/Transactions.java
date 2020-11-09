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

@Entity
@Table(name = "transactions")
public class Transactions {

	@Id
	@GeneratedValue(generator = "transaction_uuid")
	@GenericGenerator(name = "transaction_uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "transaction_id", unique = true)
	private UUID transactionId;
	@Column(name="description")
	private String description;
	@Column(name="amount")
	private long amount;
	@Column(name="fees")
	private long fees;
	@Column(name="created_at")
	private LocalDateTime created;
	@Column(name="updated_at")
	private LocalDateTime update;
	@OneToOne
	@JoinColumn(name = "connection_fk", foreignKey=@ForeignKey(name="connections_fk"))
	private Connections connectionsId;

	public Transactions() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the transactionId
	 */
	public UUID getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(UUID transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the fees
	 */
	public long getFees() {
		return fees;
	}

	/**
	 * @param fees the fees to set
	 */
	public void setFees(long fees) {
		this.fees = fees;
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
	 * @return the connectionId
	 */
	public Connections getConnectionId() {
		return connectionsId;
	}

	/**
	 * @param connectionId the connectionId to set
	 */
	public void setConnectionId(Connections connectionsId) {
		this.connectionsId = connectionsId;
	}

}
