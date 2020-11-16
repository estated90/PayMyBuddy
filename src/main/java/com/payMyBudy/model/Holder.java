package com.payMyBudy.model;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "holder", uniqueConstraints = { @UniqueConstraint(columnNames = "email") }, indexes = {
		@Index(columnList = "email", name = "email_index") })
public class Holder {

	@Id
	@GeneratedValue(generator = "holder_uuid")
	@GenericGenerator(name = "holder_uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "holder_id", unique = true)
	@JsonIgnore
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
	@JsonIgnore
	@OneToMany(mappedBy = "holderId",fetch=FetchType.LAZY)
	private List<Bank> bankId = new ArrayList<Bank>();
	@JsonIgnore
	
	@OneToMany(mappedBy = "holderId",fetch=FetchType.EAGER)
	private List<Connections> mainHolder = new ArrayList<Connections>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "friendId",fetch=FetchType.EAGER)
	private List<Connections> friendHolder = new ArrayList<Connections>();
	
    @OneToOne(mappedBy = "holderId", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    
    @JoinColumn(name = "profiles_fk", referencedColumnName = "profileId")
    private Profiles profiles;
	/**
	 * 
	 */
	public Holder() {
	}



	/**
	 * @param email
	 * @param password
	 */
	public Holder(@Size(max = 80) @Email @NotNull String email, @NotNull String password) {
		super();
		this.email = email;
		this.password = password;
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
		return bankId;
	}

	/**
	 * @param bankId the bankId to set
	 */
	public void setBankId(List<Bank> bankId) {
		this.bankId = bankId;
	}

	/**
	 * @return the mainHolder
	 */
	public List<Connections> getMainHolder() {
		return mainHolder;
	}

	/**
	 * @param mainHolder the mainHolder to set
	 */
	public void setMainHolder(List<Connections> mainHolder) {
		this.mainHolder = mainHolder;
	}

	/**
	 * @return the friendHolder
	 */
	public List<Connections> getFriendHolder() {
		return friendHolder;
	}

	/**
	 * @param friendHolder the friendHolder to set
	 */
	public void setFriendHolder(List<Connections> friendHolder) {
		this.friendHolder = friendHolder;
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
	
	

}
