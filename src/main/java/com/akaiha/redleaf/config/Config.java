package com.akaiha.redleaf.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.EnumMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.md_5.bungee.api.plugin.Plugin;

public class Config {

	private Plugin plugin;
	private File configFile;
	private EnumMap<Configs, Object> configEnum;
	
	public Config(Plugin plugin) {
		this.plugin = plugin;
		if (!plugin.getDataFolder().exists()) {
    		plugin.getDataFolder().mkdir();
    	}
		this.configFile = new File(plugin.getDataFolder() + File.separator + "config.json");
		init();
		load();
	}
	
	public void init() {
		JsonObject jObj = new JsonObject();
		Gson gson = new GsonBuilder().create();
		try {
			if (!configFile.exists()) {
				Writer writer = new FileWriter(configFile);
			    gson.toJson(jObj, writer);
			    writer.close();
			}
		} catch (IOException e) {
			plugin.getLogger().severe("Error: Creating config.json");
		}
	}
	
	public void save() {
		JsonObject jObj = new JsonObject();
		Gson gson = new GsonBuilder().create();
		
		jObj.addProperty("port", (String) getConfig(Configs.PORT));
		jObj.addProperty("ip", (String) getConfig(Configs.IP));
		jObj.addProperty("password", (String) getConfig(Configs.PASSWORD));
		jObj.addProperty("user", (String) getConfig(Configs.USER));
		jObj.addProperty("database", (String) getConfig(Configs.DATABASE));
		jObj.addProperty("init", (boolean) getConfig(Configs.INIT));
		
		try {
			Writer writer = new FileWriter(configFile);
		    gson.toJson(jObj, writer);
		    writer.close();
		} catch (IOException e) {
			plugin.getLogger().severe("Error: Saving config.json");
		}
	}
	
	private void load() {
		JsonObject jObj = new JsonObject();
		JsonParser parser = new JsonParser();
		this.configEnum = new EnumMap<Configs, Object>(Configs.class);
		
		try {
			JsonElement jsonElement = parser.parse(new FileReader(configFile));
	        jObj = jsonElement.getAsJsonObject();
            
            if (!jObj.has("port")) {
            	jObj.addProperty("port", "3306");
            	configEnum.put(Configs.PORT, "3306");
            } else {
            	configEnum.put(Configs.PORT, jObj.get("port").getAsString());
            }
            if (!jObj.has("ip")) {
            	jObj.addProperty("ip", "localhost");
            	configEnum.put(Configs.IP, "localhost");
            } else {
            	configEnum.put(Configs.IP, jObj.get("ip").getAsString());
            }
            if (!jObj.has("password")) {
            	jObj.addProperty("password", "Password");
            	configEnum.put(Configs.PASSWORD, "Password");
            } else {
            	configEnum.put(Configs.PASSWORD, jObj.get("password").getAsString());
            }
            if (!jObj.has("user")) {
            	jObj.addProperty("user", "admin");
            	configEnum.put(Configs.USER, "admin");
            } else {
            	configEnum.put(Configs.USER, jObj.get("user").getAsString());
            }
            if (!jObj.has("database")) {
            	jObj.addProperty("database", "REDLEAFPERMISSIONS");
            	configEnum.put(Configs.DATABASE, "REDLEAFPERMISSIONS");
            } else {
            	configEnum.put(Configs.DATABASE, jObj.get("database").getAsString());
            }
            if (!jObj.has("init")) {
            	jObj.addProperty("init", false);
            	configEnum.put(Configs.INIT, false);
            } else {
            	configEnum.put(Configs.INIT, jObj.get("init").getAsBoolean());
            }
        } catch (Exception e) {
        	plugin.getLogger().severe("Error: Reading config.json");
        }
	}
	
	public Object getConfig(Configs key) {
		return configEnum.get(key);
	}
	
	public void setConfig(Configs key, Object value) {
		configEnum.put(key, value);
	}
}
