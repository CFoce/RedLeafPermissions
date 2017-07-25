package com.akaiha.redleaf.util;

public class UtilPlayer {
	
	public static String insertDashUUID(String uuid) {
    	StringBuffer sb = new StringBuffer(uuid);
    	sb.insert(8, "-");
    	 
    	sb = new StringBuffer(sb.toString());
    	sb.insert(13, "-");
    	 
    	sb = new StringBuffer(sb.toString());
    	sb.insert(18, "-");
    	 
    	sb = new StringBuffer(sb.toString());
    	sb.insert(23, "-");
    	 
    	return sb.toString();
    }
}
