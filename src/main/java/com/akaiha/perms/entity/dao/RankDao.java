package com.akaiha.perms.entity.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.akaiha.perms.database.Database;
import com.akaiha.perms.entity.Group;
import com.akaiha.perms.entity.Rank;

public class RankDao {

	private Database data = new Database();
	
	private String name() {
		return "RankDao ";
	}

	public boolean has(String name) {
		boolean result = false;
		try {
			Statement stm = data.connect().createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM rank WHERE rank = '" + name + "'");
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
	
	public Rank get(String name) {
		Rank r = new Rank();
		try {
			Statement stm = data.connect().createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM rank WHERE rank LIKE '" + name + "'");
			while (rs.next()) {
				r.setRank(name);
			}
		} catch (ClassNotFoundException | SQLException e) {
			data.error(name() + "get");
		} finally {
			try {
				data.disconnect();
			} catch (SQLException e) {
				data.error();
			}
		}
		return r;
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
	
	public void createWithPrefix(String name, String prefix) {
		try {
			Statement stm = data.connect().createStatement();
			stm.executeUpdate("INSERT INTO perm_group (name_group,prefix) VALUES ('" + name + "','" + prefix+ "')");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			data.error(name() + "createWithPrefix");
		} finally {
			try {
				data.disconnect();
			} catch (SQLException e) {
				e.printStackTrace();
				data.error();
			}
		}
	}
	
	public void addPrefix(String name, String prefix) {
		try {
			Statement stm = data.connect().createStatement();
			stm.executeUpdate("UPDATE perm_group SET prefix = '" + prefix + "' WHERE name_group LIKE '" + name + "'");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			data.error(name() + "createWithPrefix");
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
		List<Group> list = new ArrayList<Group>();
		try {
			Statement stm = data.connect().createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM perm_group");
			while (rs.next()) {
				Group g = new Group();
				g.setName(rs.getString("name_group"));
				g.setPrefix(rs.getString("prefix"));
				list.add(g);
			}
		} catch (ClassNotFoundException | SQLException e) {
			data.error(name() + "getAll");
		} finally {
			try {
				data.disconnect();
			} catch (SQLException e) {
				data.error();
			}
		}
		return list;
	}
}