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

@Entity
@Table(name = "connections")
public class Connections {

	@Id
	@GeneratedValue(generator = "connection_uuid")
	@GenericGenerator(name = "connection_uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "connection_id", unique = true)
	private UUID connectionsId;
	@Column(name="is_active")
	private boolean active;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "holder_fk", foreignKey=@ForeignKey(name="holder_fk"))
	private Holder holderId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "friend_fk", foreignKey=@ForeignKey(name="friend_fk"), referencedColumnName="holder_Id")
	private Holder friendId;

	public Connections() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the connectionId
	 */
	public UUID getConnectionId() {
		return connectionsId;
	}

	/**
	 * @param connectionId the connectionId to set
	 */
	public void setConnectionId(UUID connectionsId) {
		this.connectionsId = connectionsId;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
}
