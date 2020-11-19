package com.application.paymybuddy.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
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
	private double amount;
	@Column(name="fees")
	private double fees;
	@Column(name="created_at")
	private LocalDateTime created;
	@Column(name="updated_at")
	private LocalDateTime update;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "connection_fk", foreignKey=@ForeignKey(name="connection_fk"), referencedColumnName="connection_id")
	private Connections connection;
	@OneToMany(mappedBy = "transaction", cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Movement> movement = new ArrayList<>();

	public Transactions() {
		super();
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
	 * @return the fees
	 */
	public double getFees() {
		return fees;
	}

	/**
	 * @param fees the fees to set
	 */
	public void setFees(double fees) {
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
	 * @return the connection
	 */
	public Connections getConnection() {
		return connection;
	}

	/**
	 * @param connection the connection to set
	 */
	public void setConnection(Connections connection) {
		this.connection = connection;
	}
}
