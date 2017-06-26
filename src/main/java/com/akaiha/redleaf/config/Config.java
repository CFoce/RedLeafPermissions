package com.akaiha.redleaf.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.EnumMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Config {

	private File configFile;
	private EnumMap<Configs, Object> configEnum;
	
	public Config(Plugin plugin) {
		if (!plugin.getDataFolder().exists()) {
    		plugin.getDataFolder().mkdir();
    	}
		this.configFile = new File(plugin.getDataFolder() + File.separator + "config.json");
		loadConfigFile();
	}
	
	@SuppressWarnings({ "unchecked", "resource" })
	private void loadConfigFile() {
		JSONObject jObj = new JSONObject();
		if (!configFile.exists()) {
			try {
				FileWriter file = new FileWriter(configFile);
		        file.write(jObj.toJSONString());
		        file.flush();
			} catch (IOException e) {Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error: Creating config.json");}
		}
		
		JSONParser parser = new JSONParser();
		this.configEnum = new EnumMap<Configs, Object>(Configs.class);
		
		try {
            Object obj = parser.parse(new FileReader(configFile));
            jObj = (JSONObject) obj;
            
            if (!jObj.containsKey("port")) {
            	jObj.put("port", "3306");
            	configEnum.put(Configs.PORT, "3306");
            } else {
            	configEnum.put(Configs.PORT, jObj.get("port"));
            }
            if (!jObj.containsKey("ip")) {
            	jObj.put("ip", "localhost");
            	configEnum.put(Configs.IP, "localhost");
            } else {
            	configEnum.put(Configs.IP, jObj.get("ip"));
            }
            if (!jObj.containsKey("password")) {
            	jObj.put("password", "Password");
            	configEnum.put(Configs.PASSWORD, "Password");
            } else {
            	configEnum.put(Configs.PASSWORD, jObj.get("password"));
            }
            if (!jObj.containsKey("user")) {
            	jObj.put("user", "admin");
            	configEnum.put(Configs.USER, "admin");
            } else {
            	configEnum.put(Configs.USER, jObj.get("user"));
            }
            if (!jObj.containsKey("database")) {
            	jObj.put("database", "REDLEAFPERMISSIONS");
            	configEnum.put(Configs.DATABASE, "REDLEAFPERMISSIONS");
            } else {
            	configEnum.put(Configs.DATABASE, jObj.get("database"));
            }
            
        } catch (ParseException | IOException e) {Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error: Reading config.json");}
		
		try {
			FileWriter file = new FileWriter(configFile);
	        file.write(jObj.toJSONString());
	        file.flush();
		} catch (IOException e) {Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error: Saving config.json");}
	}
	
	public Object getConfig(Configs key) {
		return configEnum.get(key);
	}
}
