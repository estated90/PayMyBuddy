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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author nicolas
 *
 */
@Entity
@Table(name = "profiles")
public class Profiles {

	@Id
	@GeneratedValue(generator = "profile_uuid")
	@GenericGenerator(name = "profile_uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "profiles_id", unique = true)
	@JsonIgnore
	private UUID profileId;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	@Column(name="address")
	private String address;
	@Column(name="phone")
	private String phone;
	@Column(name="created_at")
	private LocalDateTime created;
	@Column(name="updated_at")
	private LocalDateTime update;
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "holder_fk", foreignKey=@ForeignKey(name="holder_fk"), nullable = false)
	private Holder holderId;

	public Profiles() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Profiles(String firstName, String lastName, String address, String phone) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
	}
	
	
	/**
	 * @param firstName
	 * @param lastName
	 * @param address
	 */
	public Profiles(String firstName, String lastName, String address) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	/**
	 * @param phone
	 */
	public Profiles(String phone) {
		super();
		this.phone = phone;
	}

	/**
	 * @return the profileId
	 */
	public UUID getProfileId() {
		return profileId;
	}

	/**
	 * @param profileId the profileId to set
	 */
	public void setProfileId(UUID profileId) {
		this.profileId = profileId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
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
	
}
