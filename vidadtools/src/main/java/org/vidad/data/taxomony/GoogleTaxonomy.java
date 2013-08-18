package org.vidad.data.taxomony;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.text.html.parser.Entity;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.vidad.data.Collectionable;
import org.vidad.data.Trie;
import org.vidad.tools.conf.Collection;
import org.vidad.tools.nosql.Mongodb;

import au.com.bytecode.opencsv.CSVReader;

public class GoogleTaxonomy {
	transient Logger log = Logger.getLogger(GoogleTaxonomy.class);
	static Mongodb mongo = Mongodb.getInstance();
	static List<Taxonomy> all = new ArrayList<Taxonomy>(); 

	public static class Taxonomy extends Collectionable<Taxonomy>{
		String name;
		String parentName;
		ObjectId parentId = null;
		Set<ObjectId> childs = new HashSet<ObjectId>(); 
		transient Map<String, Taxonomy> childrenss = new HashMap<String, Taxonomy>();
		
		@Override
		public Collection getCollection() {
			return Collection.TEXANOMY;
		}
		
		public Taxonomy chlidByName(String chlidname){
			if(name==null)
				throw new IllegalArgumentException("null name");
			if( childrenss.containsKey(name))
				return childrenss.get(name);
			
			Taxonomy t = new Taxonomy();
			t.name=chlidname;
			t.parentName=this.name;
			t.parentId=this.getObjectId();
			mongo.insertCollectionable(t);
			childs.add(t.getObjectId());
			childrenss.put(chlidname, t);
			all.add(t);
			return t;
		}

		@Override
		public String toString() {
			return "Taxonomy [name=" + name + ", parentName=" + parentName + "]";
		}

		public void print(Integer tabs) {
			for(int i=0;i<tabs;i++)
				System.out.print('\t');
			System.out.println(this);
			for(Entry<String, Taxonomy> child : childrenss.entrySet()){
				child.getValue().print(tabs+1);
			}
		}
	}
	
	public static void fromFile(File file) throws IOException {
		Taxonomy root = new Taxonomy();
		root.name="GoogleTaxonomy";
		mongo.insertCollectionable(root);
		all.add(root);
		
		CSVReader reader = new CSVReader(new FileReader(file));
	    String line[] ;
	    while ((line = reader.readNext()) != null) {
	    	Taxonomy cursor = root;
	    	for( String part : line){
	    		if(!part.isEmpty())
	    			cursor = cursor.chlidByName(part);
	    	}
	    }
	    root.print(0);

	    System.out.println(root.childrenss.size() + " top level categories " + root.childrenss.keySet());
	    System.out.println(root.childs.size() + " id's ");
	    System.out.println(all.size() + " all ");
	    Trie trie = new Trie(Collection.TEXANOMY, root.getObjectId(), root.name);
	    for(Taxonomy t : all){
	    	mongo.updateCollectionable(t);
	    	trie.addWord(t.name);
	    }
	    mongo.insertCollectionable(trie);
	    reader.close();
	    
	}
	
	public static void main(String[] args) throws IOException {
		GoogleTaxonomy.fromFile(Paths.get("/home/moshe/data/taxonomy/taxonomy.en-US-google.csv").toFile());
	}
}
