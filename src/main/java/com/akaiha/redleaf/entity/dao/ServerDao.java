package com.akaiha.redleaf.entity.dao;

import java.util.ArrayList;
import java.util.List;

import com.akaiha.redleaf.database.Database;
import com.akaiha.redleaf.entity.Server;

public class ServerDao {

	private Database data = new Database();

	public List<Server> getByName(String name){
		return new ArrayList<Server>();
	}
	
	public List<Server> getByGroup(String group) {
		return new ArrayList<Server>();
	}
	
	public List<Server> getDefaults(String group) {
		return new ArrayList<Server>();
	}
	
	public List<Server> getDefaultsByServer(String group) {
		return new ArrayList<Server>();
	}
	
	public List<Server> getDefaultsByNames(String group) {
		return new ArrayList<Server>();
	}
	
	//state isnt required unless true it defaults to false
	public void create(String group, String name, boolean state) {
		
	}
	
	public void create(String group, String name) {
		
	}
	
	public void delete(String group, String name) {
		
	}
	
	public void deleteByGroup(String group) {
		
	}
	
	public void deleteByName(String name) {
		
	}
	
	public void has(String group, String name) {
		
	}
	
	public void changeState(String group, String name, boolean state) {
		
	}
}
