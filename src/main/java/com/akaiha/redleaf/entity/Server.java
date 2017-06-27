package com.akaiha.redleaf.entity;

import java.util.List;

public class Server {

	private int id;
	private String name;
	private int groupId;
	private boolean state = false;
	private List<World> world;
	
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

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public List<World> getWorld() {
		return world;
	}

	public void setWorld(List<World> world) {
		this.world = world;
	}
}
