package com.paymybuddy.model;

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
@Table(name = "connections")
public class Connections {

	@Id
	@GeneratedValue(generator = "connection_uuid")
	@GenericGenerator(name = "connection_uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "connection_id", unique = true)
	private UUID connectionsId;
	@Column(name="is_active")
	private boolean isActive;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "holder_fk", foreignKey=@ForeignKey(name="holder_fk"))
	private Holder holderId;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "friend_fk", foreignKey=@ForeignKey(name="friend_fk"), referencedColumnName="holder_id")
	private Holder friendId;
	@OneToMany(mappedBy = "connection")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Transactions> transactions = new ArrayList<>();
	
	public Connections() {
		super();
	}

	/**
	 * @return the connectionsId
	 */
	public UUID getConnectionsId() {
		return connectionsId;
	}

	/**
	 * @param connectionsId the connectionsId to set
	 */
	public void setConnectionsId(UUID connectionsId) {
		this.connectionsId = connectionsId;
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
	 * @return the holderId
	 */
	public Holder getHolderId() {
		return holderId;
	}

	/**
	 * @param holderId the holderId to set
	 */
	public void setHolderId(Holder holderId) {
		this.holderId = holderId;
	}

	/**
	 * @return the friendId
	 */
	public Holder getFriendId() {
		return friendId;
	}

	/**
	 * @param friendId the friendId to set
	 */
	public void setFriendId(Holder friendId) {
		this.friendId = friendId;
	}

	/**
	 * @return the transactions
	 */
	public List<Transactions> getTransactions() {
		return transactions;
	}

	/**
	 * @param transactions the transactions to set
	 */
	public void setTransactions(List<Transactions> transactions) {
		this.transactions = transactions;
	}

}
