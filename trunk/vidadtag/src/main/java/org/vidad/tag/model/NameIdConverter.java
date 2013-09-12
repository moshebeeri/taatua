/**
 * 
 */
package org.vidad.tag.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import org.apache.log4j.Logger;
import org.vidad.data.NamedId;

import javax.faces.convert.Converter;

/**
 * @author moshe
 *
 */
@FacesConverter("NameIdConverter")
public class NameIdConverter implements Converter {
	transient Logger log = Logger.getLogger(NameIdConverter.class);
	Map<String,NamedId> map = new HashMap<>();

	/**
	 * 
	 */
	public NameIdConverter() {
		List<NamedId> res = new ArrayList<>();
	    res.add(new NamedId("Israel"   , "88"));
        res.add(new NamedId("England"  , "77"));
        res.add(new NamedId("France"   , "67"));
        res.add(new NamedId("Germany"  , "57"));
        res.add(new NamedId("Italy"    , "47"));
        res.add(new NamedId("Spain"    , "37"));
        for( NamedId n : res)
        map.put(n.getId(), n);
	}

	/**
	 * @param context
	 * @param component
	 * @param value
	 * @return
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	@Override
	public Object getAsObject(FacesContext context,	UIComponent component, String value) {
        System.out.println("getAsObject=" +value);
        return map.get(value);
	}


	/**
	 * @param context
	 * @param component
	 * @param value
	 * @return
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	@Override
	public String getAsString(FacesContext context,	UIComponent component, Object value) {
        System.out.println("getAsString=" +(NamedId)value);
        return (String)value;
	}
}
