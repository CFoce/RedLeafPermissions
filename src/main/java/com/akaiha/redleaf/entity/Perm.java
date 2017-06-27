package com.akaiha.redleaf.entity;

public class Perm {

	private int id;
	private int groupId;
	private String perm;
	
	public String getPerm() {
		return perm;
	}
	
	public void setPerm(String perm) {
		this.perm = perm;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
