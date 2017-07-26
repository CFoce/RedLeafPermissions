package com.akaiha.redleaf.entity.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.akaiha.redleaf.database.Database;
import com.akaiha.redleaf.entity.Player;

public class PlayerDao {
	
	private Database data = new Database();
	
	private String name() {
		return "PlayerDao ";
	}
	
	public List<Player> getByUUID(String uuid){
		List<Player> results = new ArrayList<Player>();
		Player player;
		try {
			Statement stm = data.connect().createStatement();
			ResultSet rs = stm.executeQuery("Select * from player where uuid like '" + uuid + "'");
			while (rs.next()) {
				  player = new Player();
				  player.setGroupName(rs.getString("name_group"));
				  player.setName(rs.getString("name_player"));
				  player.setUuid(uuid);
				  results.add(player);
				}
		} catch (ClassNotFoundException | SQLException e) {
			data.error(name() + "getByUUID");
		} finally {
			try {
				data.disconnect();
			} catch (SQLException e) {
				data.error();
			}
		}
		return results;
	}
	
	public List<Player> getByGroup(String group){
		List<Player> results = new ArrayList<Player>();
		Player player;
		try {
			Statement stm = data.connect().createStatement();
			ResultSet rs = stm.executeQuery("Select * from player where name_group like '" + group + "'");
			while (rs.next()) {
				  player = new Player();
				  player.setGroupName(group);
				  player.setName(rs.getString("name_player"));
				  player.setUuid(rs.getString("uuid"));
				  results.add(player);
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
	
	public void create(String UUID, String name, String group) {
		try {
			Statement stm = data.connect().createStatement();
			stm.executeUpdate("INSERT INTO player (name_player, name_group, uuid) VALUES ('" + name + "','" + group + "','" + UUID + "')");
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
	
	public void delete(String UUID, String group) {
		try {
			Statement stm = data.connect().createStatement();
			stm.executeUpdate("DELETE FROM player WHERE uuid = '" + UUID + "' AND name_group = '" + group + "'");
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
			stm.executeUpdate("DELETE FROM player WHERE name_group = '" + group + "'");
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
	
	public void deleteByUUID(String UUID) {
		try {
			Statement stm = data.connect().createStatement();
			stm.executeUpdate("DELETE FROM player WHERE uuid = '" + UUID + "'");
		} catch (ClassNotFoundException | SQLException e) {
			data.error(name() + "deleteByUUID");
		} finally {
			try {
				data.disconnect();
			} catch (SQLException e) {
				data.error();
			}
		}
	}
	
	public boolean has(String UUID, String group) {
		boolean result = false;
		try {
			Statement stm = data.connect().createStatement();
			ResultSet rs = stm.executeQuery("Select * from player where name_group = '" + group + "' AND uuid = '" + UUID + "'");
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
	
	public boolean has(String UUID) {
		boolean result = false;
		try {
			Statement stm = data.connect().createStatement();
			ResultSet rs = stm.executeQuery("Select * from player where uuid = '" + UUID + "'");
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
	
	public void changeName(String UUID, String name) {
		try {
			Statement stm = data.connect().createStatement();
			stm.executeUpdate("UPDATE player SET name_player = " + name + " WHERE uuid = '" + UUID + "'");
		} catch (ClassNotFoundException | SQLException e) {
			data.error(name() + "changeName");
		} finally {
			try {
				data.disconnect();
			} catch (SQLException e) {
				data.error();
			}
		}
	}
}