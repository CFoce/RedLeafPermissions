package com.akaiha.perms.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import com.akaiha.perms.Perms;
import com.akaiha.perms.config.Configs;

import net.md_5.bungee.api.plugin.Plugin;

public class Database {

	private volatile static String url;
	private volatile static Plugin plugin;
	private volatile Connection conn;

	public synchronized static void setUrl(String url) {
		Database.url = url;
	}
	
	public Connection connect() throws ClassNotFoundException, SQLException {
		Class.forName(DatabaseConfig.DRIVER_NAME);
		this.conn = DriverManager.getConnection(url,(String) Perms.config.getConfig(Configs.USER),(String) Perms.config.getConfig(Configs.PASSWORD));
		return conn;
	}
	
	public void disconnect() throws SQLException {
		conn.close();
	}

	public synchronized static void setPlugin(Plugin plugin) {
		Database.plugin = plugin;
	}
	
	public void error(final String message) {
		plugin.getProxy().getScheduler().schedule(plugin, new Runnable() {
            @Override
            public void run() {
            	Database.plugin.getLogger().severe("Error: Database Query Issue: " + message);
            }
		}, 1L, TimeUnit.MILLISECONDS);
	}
	
	public void error() {
		plugin.getProxy().getScheduler().schedule(plugin, new Runnable() {
            @Override
            public void run() {
            	Database.plugin.getLogger().severe("Error: Database Query Issue");
            }
		}, 1L, TimeUnit.MILLISECONDS);
	}
}
