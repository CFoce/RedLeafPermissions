package com.akaiha.redleaf.database;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import com.akaiha.redleaf.RedLeaf;
import com.akaiha.redleaf.config.Configs;

import net.md_5.bungee.api.plugin.Plugin;

public class DatabaseConfig {

	private Plugin plugin;
	private static String url = null;
	private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	
	public DatabaseConfig(Plugin plugin) {
		this.plugin = plugin;
		databaseSetup();
		tableSetup();
	}
	
	private void databaseSetup() {
		Connection conn = null;
		Statement stm = null;

	       try {
	           url = "jdbc:mysql://" + (String) RedLeaf.config.getConfig(Configs.IP) + ":" + (String) RedLeaf.config.getConfig(Configs.PORT) + "/";
	           Class.forName(DRIVER_NAME);
	           conn = DriverManager.getConnection(url,(String) RedLeaf.config.getConfig(Configs.USER),(String) RedLeaf.config.getConfig(Configs.PASSWORD));
	           try {
	        	   stm = conn.createStatement();
		           stm.executeUpdate("CREATE DATABASE " + (String) RedLeaf.config.getConfig(Configs.DATABASE)); 
	           } catch (Exception e) {
	        	   plugin.getLogger().warning("Warn: Database Creation Issue.");
	           }
	       } catch (Exception e) {
	    	   plugin.getLogger().severe("Error: MySQL Connection Issue.");
	       } finally {
	               try {
	            	   if (stm != null) {
	            		   stm.close();
	            	   }
	            	   if (conn != null) {
	            		   conn.close ();
	            	   }
	               } catch (Exception e) {
	            	   plugin.getLogger().severe("Error: MySQL Dsconnection Issue.");
	               }
	       }
	}
	
	private void tableSetup() {
		Connection conn = null;
		Statement stm = null;

	       try {
	           url = "jdbc:mysql://" + (String) RedLeaf.config.getConfig(Configs.IP) + ":" + (String) RedLeaf.config.getConfig(Configs.PORT) + "/" + (String) RedLeaf.config.getConfig(Configs.DATABASE);
	           Class.forName(DRIVER_NAME);
	           conn = DriverManager.getConnection(url,(String) RedLeaf.config.getConfig(Configs.USER),(String) RedLeaf.config.getConfig(Configs.PASSWORD));
	           stm = conn.createStatement();
	           sqlExecutor("/sql/init.sql", stm);
	       } catch (Exception e) {
	    	   plugin.getLogger().severe("Error: Database Connection Issue.");
	       } finally {
	               try {
	            	   if (stm != null) {
	            		   stm.close();
	            	   }
	            	   if (conn != null) {
	            		   conn.close ();
	            	   }
	               } catch (Exception e) {
	            	   plugin.getLogger().severe("Error: Database Dsconnection Issue.");
	               }
	       }
	}
	
	private void sqlExecutor(String fileName, Statement st) {
		String s = new String(); 
		StringBuffer sb = new StringBuffer(); 
		try {
			Reader r = new InputStreamReader( getClass().getResourceAsStream(fileName));
			BufferedReader br = new BufferedReader(r); 
			while((s = br.readLine()) != null) {
				sb.append(s); 
			} 
			br.close(); 
			String[] inst = sb.toString().split(";");
			for(int i = 0; i<inst.length; i++) {
				if(!inst[i].trim().equals("")) {
					st.executeUpdate(inst[i]); 
				} 
			} 
		} catch (Exception e){
			e.printStackTrace();
			plugin.getLogger().severe("Error: SQL Script Execution Failure At " + fileName);
		}
	}
}
