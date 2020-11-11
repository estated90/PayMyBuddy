package com.payMyBudy.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Role {
	@Id
	private String name;

	@ManyToMany(mappedBy = "role")
	private List<Holder> members;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Holder> getMembers() {
		return members;
	}

	public void setMembers(List<Holder> members) {
		this.members = members;
	}

	public Role(String name) {
		this.name = name;
	}

	public Role() {
	}
}
