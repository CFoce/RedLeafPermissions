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
	
	public void create(String UUID, String group) {
		
	}
	
	public void delete(String UUID, String group) {
		
	}
	
	public void deleteByGroup(String group) {
		
	}
	
	public void deleteByUUID(String UUID) {
		
	}
	
	public boolean has(String UUID, String group) {
		return false;
	}
}