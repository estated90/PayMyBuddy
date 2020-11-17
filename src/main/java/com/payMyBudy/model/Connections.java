package com.payMyBudy.model;

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

	public Connections() {
		super();
		// TODO Auto-generated constructor stub
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

}
