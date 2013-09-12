/**
 * 
 */
package org.vidad.tag.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.openfaces.util.Faces;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.vidad.data.NamedId;

/**
 * @author moshe
 * 
 */
@ManagedBean
@ApplicationScoped
public class AutoComplete implements Serializable {
	private static final long serialVersionUID = 9216166934125971050L;
	transient Logger log = Logger.getLogger(AutoComplete.class);
	
	public interface Autocomplete{
		public List<NamedId> autocomplete(String prefix);
	}
	
	public AutoComplete() {
		
	}

	public List<NamedId> autocomplete(String prefix) {
		FacesContext con = FacesContext.getCurrentInstance();
		ExternalContext ec = con.getExternalContext();
		String resource = ec.getRequestParameterMap().get("javax.faces.source");
//		for( Entry<String, String> entry :  ec.getRequestParameterMap().entrySet()){
//	    	System.out.println("ParameterMap:    " + entry.getKey() + "=" + entry.getValue());
//		}
    	System.out.println("prefix="+prefix + " resource=" + resource);
		con = null;ec=null;
		List<NamedId> res = new ArrayList<>();
    	System.out.println("prefix="+prefix);
        if(prefix.equals("Is"))
	        res.add(new NamedId("Israel", "88"));
        res.add(new NamedId("England"  , "77"));
        res.add(new NamedId("France"   , "67"));
        res.add(new NamedId("Germany"  , "57"));
        res.add(new NamedId("Italy"    , "47"));
        res.add(new NamedId("Spain"    , "37"));
        return res;
    }

	public List<String> getSuggestedTags(){
		String searchString = Faces.var("searchString", String.class);
		System.out.println("searchString="+ searchString);
		
		if(searchString==null)
			return new ArrayList<String>();
		List<String> suggestedTags = Arrays.asList(new String[]{"ford", "mazda", "fiat"});
			
		return suggestedTags;	
	}

	public String[] testDataShort() {
		return new String[] { "Abari", "Absurdsvanj", "Adjikistan"};
	}
	
	public String[] testDataLong() {
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
