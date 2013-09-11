package org.vidad.autocomplete;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.vidad.data.Dictionary;
import org.vidad.tools.conf.Collection;
import org.vidad.tools.nosql.Mongodb;

public class Autocomplete {
	transient Logger log = Logger.getLogger(Autocomplete.class);
	private Mongodb mongo;
	private static Autocomplete instance;
	private Map<String,Completer> completers;
	
	private Autocomplete() {
		initialize();
	}
	

	public static Autocomplete getInstance() {
		if (null == instance) {
			instance = new Autocomplete();
		}
		return instance;
	}
	
	private void initialize(){
		mongo = Mongodb.getInstance();
		List<Dictionary> dictionaries = mongo.getAllCollectionable(Collection.DICTIONARY, Dictionary.class);
		completers = new HashMap<String,Completer>();
		for(Dictionary d : dictionaries){
			completers.put("dictionary."+d.name(), d);
		}
	}
	
	public Set<String> listCompleters(){
		return completers.keySet();
	}
	
}
