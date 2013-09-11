/**
 * This file was generated by Moshe Beeri
 * May 1, 2013
 * com.orbograph.data
 */
package org.vidad.data;

import java.lang.reflect.ParameterizedType;

import org.bson.types.ObjectId;
import org.vidad.tools.conf.Collection;
import org.vidad.tools.nosql.Mongodb;

import com.google.gson.Gson;


/**
 * @author Moshe Beeri
 *
 */
public abstract class Collectionable<T> {
	protected transient Gson gson = new Gson();
	protected transient Mongodb mongo = Mongodb.getInstance();
	protected ObjectId objectId;
	
	abstract public Collection getCollection();
	
	public ObjectId getObjectId(){
		return objectId;
	}
		
	public void setObjectId(ObjectId id){
		objectId = id;
	}

	public String toJson()
	{
		return gson.toJson(this);
	}
	
	public T fromJson(String json)
	{
		@SuppressWarnings("unchecked")
		Class<T> t = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
		return gson.fromJson(json, t);
	}

	public void lightweight() {}
	public void retrived() {}
	public void inserted() {}
	public void updated() {}
	public void deleted() {}

}
