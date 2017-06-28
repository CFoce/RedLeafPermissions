package com.akaiha.redleaf.entity;

public class Perm {

	private int id;
	private String groupName;
	private String perm;
	
	public String getPerm() {
		return perm;
	}
	
	public void setPerm(String perm) {
		this.perm = perm;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
