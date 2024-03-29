/**
 * This file was generated by Moshe Beeri
 * Aug 7, 2013
 * com.orbograph.conf
 */
package org.vidad.tools.conf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Moshe Beeri
 *
 */
public enum Collection{
	TAG, VIDEO, EXCEPTION, TRIE, TAXONOMY, TERM, DICTIONARY,  
	;
	
	static List<Collection> all = new ArrayList<Collection>();
	static Map<String,Collection> map = new HashMap<String,Collection>();
	/**
	 * @param name
	 */
	private Collection() {
	}
	
	public static List<Collection> all(){
		if(!all.isEmpty())
			return all;
		all = new ArrayList<Collection>();
		for (Collection c : Collection.values())
			all.add(c);
		return all;
	}
	
	public static Collection fromString(String name){
		if(map.isEmpty())
			for (Collection c : Collection.values())
				map.put(c.name(), c);
		Collection c = map.get(name.toLowerCase());
		if(c==null)
			throw new IllegalArgumentException("enum: "+name+" is not part of enum Collection");
		return c;
	}	
}

