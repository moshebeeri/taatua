/**
 * This file was generated by Moshe Beeri
 * Jun 17, 2013
 * com.orbograph.rest
 */
package org.vidad.tools.rest;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.vidad.data.Collectionable;
import org.vidad.tools.nosql.Mongodb;

/**
 * @author Moshe Beeri
 * 
 */
public class MongoRestService<T extends Collectionable<T>> extends RestService<T> {
	Logger log = Logger.getLogger(MongoRestService.class);

	/**
	 * @throws Exception 
	 * 
	 */
	public MongoRestService() throws Exception {
		this(Mongodb.getInstance());
	}

	/**
	 * @param mongo
	 * @throws Exception
	 */
	public MongoRestService(Mongodb mongo) throws Exception {
		super(mongo);
		this.mongo = mongo;
	}

	/**
	 * @param key
	 * @param object
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @see com.orbograph.rest.RestService#create(java.lang.String,
	 *      java.lang.Object)
	 */
	@Override
	@POST
	@Path("create")
	@Produces("application/json")
	@Consumes("application/json")
	public T create(@HeaderParam("key") String key, T object) throws IOException, ClassNotFoundException {
		ObjectId id = mongo.insertCollectionable(object);
		object.setObjectId(id);
		return object;
	}

	/**
	 * @param key
	 * @param object
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @see com.orbograph.rest.RestService#update(java.lang.String,
	 *      java.lang.Object)
	 */
	@Override
	@PUT
	@Path("update")
	@Produces("application/json")
	@Consumes("application/json")
	public T update(@HeaderParam("key") String key, T object) throws ClassNotFoundException, IOException {
		ObjectId id = mongo.updateCollectionable(object);
		object.setObjectId(id);
		return object;
	}

	/**
	 * @param key
	 * @param id
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * @see com.orbograph.rest.RestService#read(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	@GET
	@Path("get/{id}")
	@Produces("application/json")
	public T read(@HeaderParam("key") String key, @PathParam("id") String id) throws ClassNotFoundException, InstantiationException, IllegalAccessException  {
		@SuppressWarnings("unchecked")
		Class<T> t = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
		return mongo.getCollectionable(new ObjectId(id), t.newInstance().getCollection(), t);
	}

	/**
	 * @param key
	 * @return list contains all  
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * @see com.orbograph.rest.RestService#read(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	@GET
	@Path("getall")
	@Produces("application/json")
	public List<T> readAll(@HeaderParam("key") String key) throws ClassNotFoundException, InstantiationException, IllegalAccessException  {
		@SuppressWarnings("unchecked")
		Class<T> t = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
		return mongo.getAllCollectionable(t.newInstance().getCollection(), t);
	}

	/**
	 * @param key
	 * @param timestamp - in milliseconds from 1/1/1970
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @see com.orbograph.rest.RestService#readFrom(java.lang.String, java.lang.Long)
	 */
	@Override
	@GET
	@Path("getfrom/{timestamp}")
	@Produces("application/json")
	public List<T> readFromTime(@HeaderParam("key") String key, @PathParam("timestamp") Long timestamp) throws ClassNotFoundException, InstantiationException, IllegalAccessException  {
		@SuppressWarnings("unchecked")
		Class<T> t = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
		DateTime from = new DateTime(timestamp);
		return mongo.getFromTimeCollectionable(from , t.newInstance().getCollection(), t);
	} 
	
	/**
	 * @param key
	 * @param name
	 * @return some hello world style of text.
	 */
	@GET
	@Path("ping")
	@Produces("text/html")
	public String ping() {
		return "Ping result at server time - " + new DateTime().toString();
	}
	
	/**
	 * @param key
	 * @param id
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IOException
	 * @see com.orbograph.rest.RestService#delete(java.lang.String, java.lang.String)
	 */
	@Override
	@DELETE
	@Path("delete/{id}")
	@Produces("application/json")
	public T delete(@HeaderParam("key") String key, @PathParam("id") String id) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
		@SuppressWarnings("unchecked")
		Class<T> t = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
		return mongo.deleteCollectionable(new ObjectId(id), t.newInstance().getCollection(), t);
	}
}