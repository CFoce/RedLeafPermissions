package com.akaiha.perms.data;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class PlayersCache {

	public static volatile ConcurrentHashMap<String, List<String>> playerGroups = new ConcurrentHashMap<String, List<String>>();
}
