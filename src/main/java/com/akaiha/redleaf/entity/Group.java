package com.akaiha.redleaf.entity;

import java.util.List;

public class Group {
	
	private int id;
	private String name;
	private String server;
	private String world;
	private List<Child> children;
	private List<Perm> perms;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
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

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getWorld() {
		return world;
	}

	public void setWorld(String world) {
		this.world = world;
	}

}
