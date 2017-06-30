package com.akaiha.redleaf.entity.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.akaiha.redleaf.database.Database;
import com.akaiha.redleaf.entity.Perm;

public class PermDao {
	
	private Database data = new Database();

	public List<Perm> getByName(String name){
		List<Perm> results = new ArrayList<Perm>();
		Perm perm;
		try {
			Statement stm = data.connect().createStatement();
			ResultSet rs = stm.executeQuery("Select * from perm where name_perm like '" + name + "'");
			while (rs.next()) {
				  perm = new Perm();
				  perm.setGroupName(rs.getString("name_group"));
				  perm.setId(rs.getInt("id"));
				  perm.setPerm(name);
				  results.add(perm);
				}
		} catch (ClassNotFoundException | SQLException e) {
			data.error();
		} finally {
			try {
				data.disconnect();
			} catch (SQLException e) {
				data.error();
			}
		}
		return results;
	}
	
	public List<Perm> getByGroup(String group){
		List<Perm> results = new ArrayList<Perm>();
		Perm perm;
		try {
			Statement stm = data.connect().createStatement();
			ResultSet rs = stm.executeQuery("Select * from perm where name_group like '" + group + "'");
			while (rs.next()) {
				  perm = new Perm();
				  perm.setGroupName(group);
				  perm.setPerm(rs.getString("name_perm"));
				  perm.setId(rs.getInt("id"));
				  results.add(perm);
				}
		} catch (ClassNotFoundException | SQLException e) {
			data.error();
		} finally {
			try {
				data.disconnect();
			} catch (SQLException e) {
				data.error();
			}
		}
		return results;
	}
	
	public void create(String group, String perm) {
		
	}
	
	public void delete(String group, String perm) {
		
	}
	
	public void deleteByGroup(String group) {
		
	}
	
	public void deleteByPerm(String perm) {
		
	}
	
	public boolean has(String group, String perm) {
		return false;
	}
}
