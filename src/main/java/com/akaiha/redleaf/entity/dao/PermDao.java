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
		try {
			Statement stm = data.connect().createStatement();
			stm.executeQuery("INSERT INTO perm (name_group, name_perm) VALUES ('" + group + "','" + perm + "')");
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
	
	public void delete(String group, String perm) {
		try {
			Statement stm = data.connect().createStatement();
			stm.executeQuery("DELETE FROM perm WHERE name_perm = '" + perm + "' AND name_group = '" + group + "'");
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
	
	public void deleteByGroup(String group) {
		try {
			Statement stm = data.connect().createStatement();
			stm.executeQuery("DELETE FROM perm WHERE name_group = '" + group + "'");
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
	
	public void deleteByPerm(String perm) {
		try {
			Statement stm = data.connect().createStatement();
			stm.executeQuery("DELETE FROM perm WHERE name_perm = '" + perm + "'");
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
	
	public boolean has(String group, String perm) {
		boolean result = false;
		try {
			Statement stm = data.connect().createStatement();
			ResultSet rs = stm.executeQuery("Select * from perm where name_group = '" + group + "' AND name_perm = '" + perm + "'");
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
}
