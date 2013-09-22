package org.vidad.data;

import org.apache.log4j.Logger;

public class NamedId {
	transient Logger log = Logger.getLogger(NamedId.class);

	String id;
	String completer;
	String name;
	
	public NamedId(String name) {
		this(name,name);
	}
	public NamedId(String name, String id) {
		super();
		this.name = name;
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCompleter() {
		return completer;
	}
	public void setCompleter(String completer) {
		this.completer = completer;
	}
	@Override
	public String toString() {
		return "NamedId [id=" + id + ", name=" + name + "]";
	}
}
