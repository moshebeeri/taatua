package org.vidad.data.taxomony;

import org.apache.log4j.Logger;

public class TagTaxonomy extends Taxonomy {
	transient public static final String NAME = "Tag";
	transient Logger log = Logger.getLogger(TagTaxonomy.class);
	
	public TagTaxonomy() {
		super(NAME);
	}

	public Term add(String tag){
		return addChlidByName(tag);
	}

	@Override
	public TagTaxonomy fromJson(String json) {
		return gson.fromJson(json, TagTaxonomy.class);
	}
}
