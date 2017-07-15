package com.akaiha.redleaf.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.akaiha.redleaf.RedLeaf;
import com.akaiha.redleaf.config.Configs;

import net.md_5.bungee.api.plugin.Plugin;

public class Database {

	private static String url;
	private static Plugin plugin;
	private Connection conn;

	public static void setUrl(String url) {
		Database.url = url;
	}
	
	public Connection connect() throws ClassNotFoundException, SQLException {
		Class.forName(DatabaseConfig.DRIVER_NAME);
		this.conn = DriverManager.getConnection(url,(String) RedLeaf.config.getConfig(Configs.USER),(String) RedLeaf.config.getConfig(Configs.PASSWORD));
		return conn;
	}
	
	public void disconnect() throws SQLException {
		conn.close();
	}

	public static void setPlugin(Plugin plugin) {
		Database.plugin = plugin;
	}
	
	public void error() {
		Database.plugin.getLogger().severe("Error: Database Query Issue.");
	}
}
