package org.vidad.tools.nosql.memcache;

import java.util.Map;

public interface Memcache {

	public long 	increment(String Key);
	public long 	getCounter(String Key);
	public String 	getString(String Key);
	public long 	get(String Key);
	public Boolean 	getBoolean(String Key);
	
	public void setValue(String key, String value, int param);
	public void setValue(String key, String data);
	public Map<String, Object> getMulti(String[] keys);
}