package com.akaiha.redleaf.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.akaiha.redleaf.RedLeaf;
import com.akaiha.redleaf.config.Configs;

public class DatabaseConfig {

	private static String url = null;
	
	public DatabaseConfig() {
		Connection conn = null;
		Statement stm = null;

	       try {
	           url = "jdbc:mysql://" + RedLeaf.config.getConfig(Configs.IP) + ":" + RedLeaf.config.getConfig(Configs.PORT) + "/";
	           Class.forName ("com.mysql.jdbc.Driver");
	           conn = DriverManager.getConnection(url,(String) RedLeaf.config.getConfig(Configs.USER),(String) RedLeaf.config.getConfig(Configs.PASSWORD));
	           try {
	        	   stm = conn.createStatement();
		           stm.executeUpdate("CREATE DATABASE " + RedLeaf.config.getConfig(Configs.DATABASE)); 
	           } catch (Exception e) {
	        	   Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Warn: Database Creation Issue.");
	           }
	       } catch (Exception e) {
	    	   Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error: Database Connection Issue.");
	       } finally {
	               try {
	            	   if (stm != null) {
	            		   stm.close();
	            	   }
	            	   if (conn != null) {
	            		   conn.close ();
	            	   }
	               } catch (Exception e) {
	            	   Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error: Database Dsconnection Issue.");
	               }
	       }
	}
}
