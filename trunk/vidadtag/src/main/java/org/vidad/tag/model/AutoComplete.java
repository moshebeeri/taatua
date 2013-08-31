/**
 * 
 */
package org.vidad.tag.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

/**
 * @author moshe
 * 
 */
@Named
@ManagedBean
@ApplicationScoped
public class AutoComplete implements Serializable {
	private static final long serialVersionUID = 9216166934125971050L;
	transient Logger log = Logger.getLogger(AutoComplete.class);
	
	public static class Data{
		String name;
		public Data(String name, String objid) {
			super();
			this.name = name;
			this.objid = objid;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getObjid() {
			return objid;
		}
		public void setObjid(String objid) {
			this.objid = objid;
		}
		String objid;
	}
	
	public AutoComplete() {
		
	}
	List<Data> locations;
	List<String> names;
	public List<Data> autocomplete(String prefix) {
		List<Data> res = new ArrayList<>();
		int i=0;
    	System.out.println("AutoComplete.autocomplete prefix="+prefix);
        List<String> locations = Arrays.asList(genLocations());
        for(String l: locations)
        	res.add(new Data(l,""+i++));
        this.locations=res;
        return this.locations;
    }

	public void setLocations(List<Data> locations) {
		this.locations = locations;
	}

	public List<Data> getLocations() {
    	System.out.println("AutoComplete.getLocations");
		return locations;
	}

	public List<String> getNames() {
		return Arrays.asList(genLocations());
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public String[] genLocations() {
		return new String[] { "Abari", "Absurdsvanj", "Adjikistan"};
	}
	
	public String[] genLocations2() {
		return new String[] { "Abari", "Absurdsvanj", "Adjikistan",
				"Afromacoland", "Agrabah", "Agaria", "Aijina", "Ajir",
				"Al-Alemand", "Al Amarja", "Alaine", "Albenistan", "Aldestan",
				"Al Hari", "Alpine Emirates", "Altruria",
				"Allied States of America", "BabaKiueria", "Babalstan",
				"Babar's Kingdom", "Backhairistan", "Bacteria", "Bahar",
				"Bahavia", "Bahkan", "Bakaslavia", "Balamkadar", "Baki",
				"Balinderry", "Balochistan", "Baltish", "Baltonia",
				"Bataniland, Republic of", "Bayview", "Banania, Republica de",
				"Bandrika", "Bangalia", "Bangstoff", "Bapetikosweti", "Baracq",
				"Baraza", "Barataria", "Barclay Islands", "Barringtonia",
				"Bay View", "Basenji", };
	}
}
