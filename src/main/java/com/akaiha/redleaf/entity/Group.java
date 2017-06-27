package com.akaiha.redleaf.entity;

import java.util.List;

public class Group {
	
	private String name;
	private List<Server> server;
	private List<Child> children;
	private List<Perm> perms;

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Child> getChildren() {
		return children;
	}
	
	public void setChildren(List<Child> children) {
		this.children = children;
	}
	
	public List<Perm> getPerms() {
		return perms;
	}
	
	public void setPerms(List<Perm> perms) {
		this.perms = perms;
	}

	public List<Server> getServer() {
		return server;
	}

	public void setServer(List<Server> server) {
		this.server = server;
	}
}
