package com.akaiha.redleaf.entity.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.akaiha.redleaf.database.Database;
import com.akaiha.redleaf.entity.Group;

public class GroupDao {

	private Database data = new Database();

	public boolean has(String name) {
		boolean result = false;
		try {
			Statement stm = data.connect().createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM perm_group WHERE name_group LIKE '" + name + "'");
			result = rs.first();
		} catch (ClassNotFoundException | SQLException e) {
			data.error();
		} finally {
			try {
				data.disconnect();
			} catch (SQLException e) {
				data.error();
			}
		}
		return result;
	}
	
	public void create(String name) {
		try {
			Statement stm = data.connect().createStatement();
			stm.executeUpdate("INSERT INTO perm_group (name_group) VALUES ('" + name + "')");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			data.error();
		} finally {
			try {
				data.disconnect();
			} catch (SQLException e) {
				e.printStackTrace();
				data.error();
			}
		}
	}
	
	public void delete(String name) {
		try {
			Statement stm = data.connect().createStatement();
			stm.executeUpdate("DELETE FROM perm_group WHERE name_group = '" + name + "'");
		} catch (ClassNotFoundException | SQLException e) {
			data.error();
		} finally {
			try {
				data.disconnect();
			} catch (SQLException e) {
				data.error();
			}
		}
	}
	
	public List<Group> getAll() {
		return new ArrayList<Group>();
	}
}
