package org.vidad.data.taxomony;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.vidad.autocomplete.Completer;
import org.vidad.data.Collectionable;
import org.vidad.data.NamedId;
import org.vidad.data.Trie;
import org.vidad.tools.conf.Collection;
import org.vidad.tools.nosql.Mongodb;

public abstract class Taxonomy extends Collectionable<Taxonomy> implements Completer{
	transient Logger log = Logger.getLogger(Taxonomy.class);
	
	String name;
	Term root;
	Trie trie = new Trie();
	List<Term> all = new ArrayList<Term>();
			
	abstract public Taxonomy fromJson(String json);
	
	public Taxonomy(String name) {
		super();
		this.name = name;
		this.root = new Term();
		this.root.name = name;
	}

	public void update(){
		Mongodb.getInstance().updateCollectionable(this);
	}
	
	public Map<String,String> id2Name(){
		Map<String,String> ret = new HashMap<>();
		for(Term t : all)
			ret.put(t.getObjectId().toString(), t.name);
		return ret;
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
		return getName();
	}

	/**
	 * @param newone
	 * @see org.vidad.autocomplete.Completer#update(org.vidad.data.NamedId)
	 */
	@Override
	public void update(NamedId newone) {
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Term getRoot() {
		return root;
	}

	@Override
	public Collection getCollection(){
		return Collection.TAXONOMY;
	}
	
	public Term addChlidByName(Term parent, String chlidname){
		return parent.addChlidByName(chlidname, all, trie);
	}
	
	public Term addChlid(Term parent, Term term){
		return parent.addChlid(term, all, trie);
	}
	
	public Term addChlidByName(String chlidname){
		return root.addChlidByName(chlidname, all, trie);
	}
	
	public Term addChlid(Term term){
		return root.addChlid(term, all, trie);
	}
			
	public static class Term extends Collectionable<Term>{
		String name;
		String parentName;
		ObjectId parentId;
		Map<String, Term> childrenss = new HashMap<String, Term>();
		
		@Override
		public Collection getCollection() {
			return Collection.TERM;
		}
		
		public Term addChlidByName(String chlidname, List<Term> all, Trie trie){
			if(name==null)
				throw new IllegalArgumentException("null name");
			
			if(childrenss.containsKey(chlidname))
				return childrenss.get(chlidname);
			
			Term term = new Term();
			term.name=chlidname;
			term.parentName=this.name;
			term.parentId = parentId;
			
			insertTerm(all, trie, term);
			return term;
		}

		public Term addChlid(Term term, List<Term> all, Trie trie){
			if(name==null)
				throw new IllegalArgumentException("null name");
			
			if(childrenss.containsKey(term.name))
				return childrenss.get(term.name);
			
			insertTerm(all, trie, term);
			return term;
		}

		private void insertTerm(List<Term> all, Trie trie, Term term) {
			all.add(term);
			trie.insert(term.name);
			Mongodb.getInstance().insertCollectionable(term);
			childrenss.put(term.name, term);
			Mongodb.getInstance().updateCollectionable(this);
		}
		
		public String getName() {
			return name;
		}

		public String getParentName() {
			return parentName;
		}

		public ObjectId getParentId() {
			return parentId;
		}

		public Map<String, Term> getChildrenss() {
			return childrenss;
		}

		@Override
		public String toString() {
			return "Taxonomy [name=" + name + ", parentName=" + parentName + "]";
		}

		public void print(Integer tabs) {
			for(int i=0;i<tabs;i++)
				System.out.print('\t');
			System.out.println(this);
			for(Entry<String, Term> child : childrenss.entrySet()){
				child.getValue().print(tabs+1);
			}
		}
	}	
}
