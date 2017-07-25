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
	
	private String name() {
		return "GroupDao ";
	}

	public boolean has(String name) {
		boolean result = false;
		try {
			Statement stm = data.connect().createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM perm_group WHERE name_group = '" + name + "'");
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
	
	public Group get(String name) {
		Group g = new Group();
		try {
			Statement stm = data.connect().createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM perm_group WHERE name_group LIKE '" + name + "'");
			while (rs.next()) {
				  g.setName(name);
				  g.setPrefix(rs.getString("prefix"));
				}
		} catch (ClassNotFoundException | SQLException e) {
			data.error(name() + "has");
		} finally {
			try {
				data.disconnect();
			} catch (SQLException e) {
				data.error();
			}
		}
		return g;
	}
	
	public void create(String name) {
		try {
			Statement stm = data.connect().createStatement();
			stm.executeUpdate("INSERT INTO perm_group (name_group) VALUES ('" + name + "')");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			data.error(name() + "create");
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
			data.error(name() + "delete");
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
