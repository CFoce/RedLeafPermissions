package com.akaiha.redleaf.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.akaiha.redleaf.RedLeaf;
import com.akaiha.redleaf.config.Configs;

import net.md_5.bungee.api.plugin.Plugin;

public class DatabaseConfig {

	private Plugin plugin;
	private static String url;
	public static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	private static String sqlNew;
	private static String sqlUsed;
	
	public DatabaseConfig(Plugin plugin) {
		this.plugin = plugin;
		Database.setPlugin(plugin);
		Database.setUrl("jdbc:mysql://" + (String) RedLeaf.config.getConfig(Configs.IP) + ":" + (String) RedLeaf.config.getConfig(Configs.PORT) + "/" + (String) RedLeaf.config.getConfig(Configs.DATABASE));
	    File dir = new File((sqlNew = plugin.getDataFolder() + File.separator + "new sql"));
		if (!dir.exists()) {
    		dir.mkdir();
    	}
		dir = new File((sqlUsed = plugin.getDataFolder() + File.separator + "old sql"));
		if (!dir.exists()) {
    		dir.mkdir();
    	}
		databaseSetup();
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
	           } finally {
	        	   try {
	        		   conn.setCatalog((String) RedLeaf.config.getConfig(Configs.DATABASE));
		        	   stm = conn.createStatement();
		        	   if (!(boolean) RedLeaf.config.getConfig(Configs.INIT)) {
		        		   sqlExecutor("/sql/init.sql", stm);
		        		   RedLeaf.config.setConfig(Configs.INIT, true);
		        		   RedLeaf.config.save();
		        	   }
		           } catch (Exception e) {
		        	   plugin.getLogger().warning("Warn: Tables Creation Issue.");
		           } finally {
		        	   sqlChecker(stm);
		           }
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
	
	private void sqlExecutor(File file, Statement st) {
		String s = new String(); 
		StringBuffer sb = new StringBuffer(); 
		try {
			FileReader r = new FileReader(file);
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
			plugin.getLogger().severe("Error: SQL Script Execution Failure At " + file.getName());
		}
	}
	
	private void sqlChecker(Statement st) {
		int files = new File(sqlNew).list().length;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		String fileDate = df.format(today);
		File file = new File(sqlUsed + File.separator + fileDate);
		if (files > 0) {
			file.mkdir();
		}
		
		for (int i = 0; i < files; i++) {
			file = new File(sqlNew + File.separator + i + ".sql");
			sqlExecutor(file, st);
			file.renameTo(new File(sqlUsed + File.separator + fileDate + File.separator + i + ".sql"));
		}
	}
}
