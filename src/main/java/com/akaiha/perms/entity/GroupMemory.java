package com.akaiha.perms.entity;

import java.util.List;

public class GroupMemory {

	private String name = null;
	private String prefix = null;
	private List<String> children = null;
	private List<String> servers = null;
	private List<String> perms = null;
	private List<String> antiperms = null;
	private List<String> bungee = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getChildren() {
		return children;
	}

	public void setChildren(List<String> children) {
		this.children = children;
	}

	public List<String> getAntiperms() {
		return antiperms;
	}

	public void setAntiperms(List<String> antiperms) {
		this.antiperms = antiperms;
	}

	public List<String> getPerms() {
		return perms;
	}

	public void setPerms(List<String> perms) {
		this.perms = perms;
	}

	public List<String> getServers() {
		return servers;
	}

	public void setServers(List<String> servers) {
		this.servers = servers;
	}

	public List<String> getBungee() {
		return bungee;
	}

	public void setBungee(List<String> bungee) {
		this.bungee = bungee;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
