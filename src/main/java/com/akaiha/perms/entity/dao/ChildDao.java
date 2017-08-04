package com.akaiha.perms.entity.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.akaiha.perms.database.Database;
import com.akaiha.perms.entity.Child;

public class ChildDao {

	private Database data = new Database();
	
	private String name() {
		return "ChildDao ";
	}

	public List<Child> getByChild(String name){
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
			data.error(name() + "getByChild");
		} finally {
			try {
				data.disconnect();
			} catch (SQLException e) {
				data.error();
			}
		}
		return results;
	}
	
	public List<Child> getByGroup(String name){
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
			data.error(name() + "getByGroup");
		} finally {
			try {
				data.disconnect();
			} catch (SQLException e) {
				data.error();
			}
		}
		return results;
	}
	
	public boolean has(String group, String child) {
		boolean result = false;
		try {
			Statement stm = data.connect().createStatement();
			ResultSet rs = stm.executeQuery("Select * from child where name_group = '" + group + "' AND name_child = '" + child + "'");
			result = rs.first();
		} catch (ClassNotFoundException | SQLException e) {
			data.error(name() + "has");
		} finally {
			try {
				data.disconnect();
			} catch (SQLException e) {
				data.error();
			}
		}
		return result;
	}
	
	public void create(String group, String child) {
		try {
			Statement stm = data.connect().createStatement();
			stm.executeUpdate("INSERT INTO child (name_child, name_group) VALUES ('" + child + "','" + group + "')");
		} catch (ClassNotFoundException | SQLException e) {
			data.error(name() + "create");
		} finally {
			try {
				data.disconnect();
			} catch (SQLException e) {
				data.error();
			}
		}
	}
	
	public void delete(String group, String child) {
		try {
			Statement stm = data.connect().createStatement();
			stm.executeUpdate("DELETE FROM child WHERE name_child = '" + child + "' AND name_group = '" + group + "'");
		} catch (ClassNotFoundException | SQLException e) {
			data.error(name() + "delete");
		} finally {
			try {
				data.disconnect();
			} catch (SQLException e) {
				data.error();
			}
		}
	}
	
	public void deleteByGroup(String group) {
		try {
			Statement stm = data.connect().createStatement();
			stm.executeUpdate("DELETE FROM child WHERE name_group = '" + group + "'");
		} catch (ClassNotFoundException | SQLException e) {
			data.error(name() + "deleteByGroup");
		} finally {
			try {
				data.disconnect();
			} catch (SQLException e) {
				data.error();
			}
		}
	}

	public void deleteByChild(String child) {
		try {
			Statement stm = data.connect().createStatement();
			stm.executeUpdate("DELETE FROM child WHERE name_child = '" + child + "'");
		} catch (ClassNotFoundException | SQLException e) {
			data.error(name() + "deleteByChild");
		} finally {
			try {
				data.disconnect();
			} catch (SQLException e) {
				data.error();
			}
		}
	}
}