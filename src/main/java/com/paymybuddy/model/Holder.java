package com.paymybuddy.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author nicolas
 *
 */
@Entity
@Table(name = "holder", uniqueConstraints = { @UniqueConstraint(columnNames = "email") }, indexes = {
		@Index(columnList = "email", name = "email_index") })
public class Holder {

	@Id
	@GeneratedValue(generator = "holder_uuid")
	@GenericGenerator(name = "holder_uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "holder_id", unique = true)
	//@JsonIgnore
	private UUID holderId;
	@Column(name = "email")
	@Size(max = 80)
	@Email
	private String email;
	@Column(name = "password")
	@JsonIgnore
	private String password;
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	@Column(name = "is_active")
	private boolean isActive;
	@OneToMany(mappedBy = "holder")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Bank> bank = new ArrayList<>();
	@JsonIgnore
	@OneToMany(mappedBy = "holderId")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Connections> holderFriendship = new ArrayList<>();
	@JsonIgnore
	@OneToMany(mappedBy = "friendId")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Connections> holderAsFriend = new ArrayList<>();
	@JsonIgnore
	@OneToOne(mappedBy = "holderId", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "profiles_fk", referencedColumnName = "profileId")
    private Profiles profiles;
	@JsonIgnore
	@OneToMany(mappedBy = "holder")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Movement> movement = new ArrayList<>();
    
	/**
	 * 
	 */
	public Holder() {
		//Default constructor
	}

	/**
	 * @return the holderId
	 */
	public UUID getHolderId() {
		return holderId;
	}

	/**
	 * @param holderId the holderId to set
	 */
	public void setHolderId(UUID holderId) {
		this.holderId = holderId;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the createdAt
	 */
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the updatedAt
	 */
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt the updatedAt to set
	 */
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
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
	 * @return the bankId
	 */
	public List<Bank> getBankId() {
		return bank;
	}

	/**
	 * @param bankId the bankId to set
	 */
	public void setBankId(List<Bank> bankId) {
		this.bank = bankId;
	}

	/**
	 * @return the holderFriendship
	 */
	public List<Connections> getHolderFriendship() {
		return holderFriendship;
	}

	/**
	 * @param holderFriendship the holderFriendship to set
	 */
	public void setHolderFriendship(List<Connections> holderFriendship) {
		this.holderFriendship = holderFriendship;
	}

	/**
	 * @return the holderAsFriend
	 */
	public List<Connections> getHolderAsFriend() {
		return holderAsFriend;
	}

	/**
	 * @param holderAsFriend the holderAsFriend to set
	 */
	public void setHolderAsFriend(List<Connections> holderAsFriend) {
		this.holderAsFriend = holderAsFriend;
	}

	/**
	 * @return the profiles
	 */
	public Profiles getProfiles() {
		return profiles;
	}

	/**
	 * @param profiles the profiles to set
	 */
	public void setProfiles(Profiles profiles) {
		this.profiles = profiles;
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
