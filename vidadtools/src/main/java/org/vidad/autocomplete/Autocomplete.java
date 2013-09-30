package org.vidad.autocomplete;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.vidad.data.Dictionary;
import org.vidad.data.NamedId;
import org.vidad.data.taxomony.Taxonomy;
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
			String key = "dictionary"+d.name();
			log.info("adding Autocomplete key="+key);
			completers.put(key, d);
			
		}
		List<Taxonomy> taxonomies = mongo.getAllCollectionable(Collection.TAXONOMY, Taxonomy.class);
		for(Taxonomy t : taxonomies){
			String key = "taxonomy"+t.getName();
			log.info("adding Autocomplete key="+key);
			completers.put(key, t);
		}
	}
	
	public Set<String> listCompleters(){
		return completers.keySet();
	}

	public List<NamedId> complete(String prefix, String completer) {
		String key = completer.substring(completer.lastIndexOf('_')+1);
		Completer from = completers.get(key);
		List<NamedId> autocompEx = from.autocompEx(prefix);
		log.debug(autocompEx.toArray(new NamedId[autocompEx.size()]));
		return autocompEx;
	}

	public List<String> completeStrings(String prefix, String completer) {
		return null;
	}

	public void update(NamedId newone, String completer) {
		
	}
	
}
