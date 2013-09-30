package org.vidad.data.taxomony;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.log4j.Logger;
import org.vidad.data.taxomony.Taxonomy.Term;
import org.vidad.tools.conf.Collection;
import org.vidad.tools.nosql.Mongodb;

import au.com.bytecode.opencsv.CSVReader;

public class GoogleTaxonomy  {
	transient Logger log = Logger.getLogger(GoogleTaxonomy.class);
	transient public static final String NAME = "GoogleProducts";
	Taxonomy taxonomy = new Taxonomy(NAME);
	
	public GoogleTaxonomy() {
	}

	public void fromFile(File file) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(file));
	    String line[] ;
	    while ((line = reader.readNext()) != null) {
	    	Term cursor = taxonomy.getRoot();
	    	for( String part : line){
	    		if(!part.isEmpty()){
	    			cursor = taxonomy.addChlidByName(cursor,part);
	    		}
	    	}
	    }
	    Term root = taxonomy.getRoot();
	    root.print(0);
	    System.out.println(root.childrenss.size() + " top level categories " + root.childrenss.keySet());
	    System.out.println("all=" + taxonomy.getAll().size());
	    Mongodb.getInstance().insertCollectionable(taxonomy);
	    reader.close();
	}
	
	public static void main(String[] args) throws IOException {
		Mongodb.getInstance().dropCollection(Collection.TAXONOMY);
		Mongodb.getInstance().dropCollection(Collection.TERM);
		GoogleTaxonomy gm = new GoogleTaxonomy();
		gm.fromFile(Paths.get("/home/moshe/data/vidad/taxonomy/taxonomy.en-US-google.csv").toFile());
		String trieQuery = "Camera";
		List<String> res = gm.taxonomy.getTrie().search(trieQuery);
		System.out.println(trieQuery + " result value="+res);
	}
}
