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

public class Autocomplete implements Completer{
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
		List<Taxonomy> taxonomies = mongo.getAllCollectionable(Collection.TAXONOMY, Taxonomy.class);
		for(Taxonomy t : taxonomies){
			completers.put("taxonomy."+t.getName(), t);
		}
	}
	
	public Set<String> listCompleters(){
		return completers.keySet();
	}

	/**
	 * @param prefix
	 * @return
	 * @see org.vidad.autocomplete.Completer#autocompEx(java.lang.String)
	 */
	@Override
	public List<NamedId> autocompEx(String prefix) {
		return null;
	}

	/**
	 * @param prefix
	 * @return
	 * @see org.vidad.autocomplete.Completer#autocomp(java.lang.String)
	 */
	@Override
	public List<String> autocomp(String prefix) {
		return null;
	}

	/**
	 * @return
	 * @see org.vidad.autocomplete.Completer#name()
	 */
	@Override
	public String name() {
		return null;
	}

	/**
	 * @param newone
	 * @see org.vidad.autocomplete.Completer#update(org.vidad.data.NamedId)
	 */
	@Override
	public void update(NamedId newone) {
	}
	
}
