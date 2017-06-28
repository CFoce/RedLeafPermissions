package com.akaiha.redleaf.entity.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.akaiha.redleaf.database.Database;
import com.akaiha.redleaf.entity.Child;

public class ChildDao {

	private Database data = new Database();

	public List<Child> getChildListByChild(String name){
		List<Child> results = new ArrayList<Child>();
		Child child;
		try {
			Statement stm = data.connect().createStatement();
			ResultSet rs = stm.executeQuery("Select * from child where name_child like '" + name + "'");
			while (rs.next()) {
				  child = new Child();
				  child.setGroupName(rs.getString("name_group"));
				  child.setId(rs.getInt("id"));
				  child.setChildName(name);
				  results.add(child);
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
	
	public List<Child> getChildListByGroup(String name){
		List<Child> results = new ArrayList<Child>();
		Child child;
		try {
			Statement stm = data.connect().createStatement();
			ResultSet rs = stm.executeQuery("Select * from child where name_group like '" + name + "'");
			while (rs.next()) {
				  child = new Child();
				  child.setGroupName(name);
				  child.setId(rs.getInt("id"));
				  child.setChildName(rs.getString("name_child"));
				  results.add(child);
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
}
