package com.akaiha.redleaf.entity.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.akaiha.redleaf.database.Database;
import com.akaiha.redleaf.entity.Server;

public class ServerDao {

	private Database data = new Database();
	
	private String name() {
		return "ServerDao ";
	}

	public List<Server> getByName(String name){
		List<Server> results = new ArrayList<Server>();
		Server server;
		try {
			Statement stm = data.connect().createStatement();
			ResultSet rs = stm.executeQuery("Select * from perm_server where name_server like '" + name + "'");
			while (rs.next()) {
				  server = new Server();
				  server.setGroupName(rs.getString("name_group"));
				  server.setId(rs.getInt("id"));
				  server.setName(name);
				  server.setState(rs.getBoolean("state"));
				  results.add(server);
				}
		} catch (ClassNotFoundException | SQLException e) {
			data.error(name() + "getByName");
		} finally {
			try {
				data.disconnect();
			} catch (SQLException e) {
				data.error();
			}
		}
		return results;
	}
	
	public List<Server> getByGroup(String group) {
		List<Server> results = new ArrayList<Server>();
		Server server;
		try {
			Statement stm = data.connect().createStatement();
			ResultSet rs = stm.executeQuery("Select * from perm_server where name_group like '" + group + "'");
			while (rs.next()) {
				  server = new Server();
				  server.setName(rs.getString("name_server"));
				  server.setId(rs.getInt("id"));
				  server.setGroupName(group);
				  server.setState(rs.getBoolean("state"));
				  results.add(server);
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
	
	public List<Server> getDefaultsByServer(String server) {
		List<Server> results = new ArrayList<Server>();
		Server serv;
		try {
			Statement stm = data.connect().createStatement();
			ResultSet rs = stm.executeQuery("Select * from perm_server where name_server = '" + server + "' AND state = " + true);
			while (rs.next()) {
				  serv = new Server();
				  serv.setName(server);
				  serv.setId(rs.getInt("id"));
				  serv.setGroupName(rs.getString("name_group"));
				  serv.setState(true);
				  results.add(serv);
				}
		} catch (ClassNotFoundException | SQLException e) {
			data.error(name() + "getDefaultsByServer");
		} finally {
			try {
				data.disconnect();
			} catch (SQLException e) {
				data.error();
			}
		}
		return results;
	}
	
	public List<Server> getDefaultsByGroup(String group) {
		List<Server> results = new ArrayList<Server>();
		Server serv;
		try {
			Statement stm = data.connect().createStatement();
			ResultSet rs = stm.executeQuery("Select * from perm_server where name_group = '" + group + "' AND state = " + true);
			while (rs.next()) {
				  serv = new Server();
				  serv.setName(rs.getString("name_server"));
				  serv.setId(rs.getInt("id"));
				  serv.setGroupName(group);
				  serv.setState(true);
				  results.add(serv);
				}
		} catch (ClassNotFoundException | SQLException e) {
			data.error(name() + "getDefaultsByGroup");
		} finally {
			try {
				data.disconnect();
			} catch (SQLException e) {
				data.error();
			}
		}
		return results;
	}
	
	public void create(String group, String name, boolean state) {
		try {
			Statement stm = data.connect().createStatement();
			stm.executeUpdate("INSERT INTO perm_server (name_server, name_group, state) VALUES ('" + name + "','" + group + "'," + state + ")");
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
	
	public void create(String group, String name) {
		try {
			Statement stm = data.connect().createStatement();
			stm.executeUpdate("INSERT INTO perm_server (name_server, name_group) VALUES ('" + name + "','" + group + "')");
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
	
	public void delete(String group, String name) {
		try {
			Statement stm = data.connect().createStatement();
			stm.executeUpdate("DELETE FROM perm_server WHERE name_server = '" + name + "' AND name_group = '" + group + "'");
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
			stm.executeUpdate("DELETE FROM perm_server WHERE name_group = '" + group + "'");
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
	
	public void deleteByName(String name) {
		try {
			Statement stm = data.connect().createStatement();
			stm.executeUpdate("DELETE FROM perm_server WHERE name_server = '" + name + "'");
		} catch (ClassNotFoundException | SQLException e) {
			data.error(name() + "deleteByName");
		} finally {
			try {
				data.disconnect();
			} catch (SQLException e) {
				data.error();
			}
		}
	}
	
	public boolean has(String group, String name) {
		boolean result = false;
		try {
			Statement stm = data.connect().createStatement();
			ResultSet rs = stm.executeQuery("Select * from perm_server where name_group = '" + group + "' AND name_server = '" + name + "'");
			result = rs.first();
		} catch (ClassNotFoundException | SQLException e) {
			data.error(name() +  "has");
		} finally {
			try {
				data.disconnect();
			} catch (SQLException e) {
				data.error();
			}
		}
		return result;
	}
	
	public void changeState(String group, String name, boolean state) {
		try {
			Statement stm = data.connect().createStatement();
			stm.executeUpdate("UPDATE perm_server SET state = " + state + " WHERE name_group = '" + group + "' AND name_server = '" + name + "'");
		} catch (ClassNotFoundException | SQLException e) {
			data.error(name() + "changeState");
		} finally {
			try {
				data.disconnect();
			} catch (SQLException e) {
				data.error();
			}
		}
	}
}
