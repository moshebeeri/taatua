package org.vidad.tools.rest;

import com.sun.jersey.api.client.AsyncWebResource;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.concurrent.Future;

public class RestProxy<C> implements Serializable {

	private static final long serialVersionUID = 7751804428194361573L;
	String hostUrl;
    ClientConfig clientConfig;
    Client client;
    final Class<?> aClass;
    String path;
    GenericType<List<C>> listGenericType;
    protected Class<? extends C> clazz;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public RestProxy(String hostUrl) {
        super();
        this.hostUrl = hostUrl;
        ParameterizedType superclass = (ParameterizedType) getClass()
                .getGenericSuperclass();
        aClass = (Class<?>) ((ParameterizedType) superclass)
                .getActualTypeArguments()[0];
        path = aClass.getSimpleName();
        clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,
                Boolean.TRUE);
        client = Client.create(clientConfig);
        listGenericType = new GenericType<List<C>>() {};
        clazz = ((Class) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    protected Client getClient() {
        return client;
    }

    protected String getHostUrl() {
        return hostUrl;
    }

    protected WebResource getWebResource(String of) {
        return client.resource(getHostUrl() + "/r/" + of.toLowerCase());
    }

    protected AsyncWebResource getAsyncWebResource(String of) {
        return client.asyncResource(getHostUrl() + "/r/" + of.toLowerCase());
    }

    protected <T> T put(String key, WebResource resource, Class<T> c, Object requestEntity) {
        return resource.
                type(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON).
                header("key", key).
                put(c, requestEntity);
    }

    protected <T> T get(String key, WebResource resource, Class<T> c) {
        return resource.
                type(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON).
                header("key", key).
                get(c);
    }

    private List<C> get(String key, WebResource resource,
                        GenericType<List<C>> listGenericType) {
        return resource.
                type(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON).
                header("key", key).
                get(listGenericType);
    }

    protected <T> T post(String key, WebResource resource, Class<T> c, Object requestEntity) {
        return resource.
                type(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON).
                header("key", key).
                post(c, requestEntity);
    }

    protected <T> T postForm(String key, WebResource resource, Class<T> c, Object form) {
        return resource.
                type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).
                accept(MediaType.APPLICATION_JSON).
                header("key", key).
                post(c, form);
    }

    protected <T> Future<T> delete(String key, AsyncWebResource resource, Class<T> c, Object requestEntity) {
        return resource.
                header("key", key).
                delete(c, requestEntity);//(genericType, requestEntity);
    }

    public C create(String key, C c) {
        WebResource resource = getWebResource(path + "/create");
        return put(key, resource, clazz, c);
    }

    public C read(String key, String id) {
        WebResource resource = getWebResource(path + "/read/" + id);
        return get(key, resource, clazz);
    }

    public C read(String key, Long id) {
        WebResource resource = getWebResource(path + "/read/" + id);
        return get(key, resource, clazz);
    }

    public List<C> query(String key) {
        WebResource resource = getWebResource(path + "/query/");
        return get(key, resource, listGenericType);
    }

    public C updateExisting(String key, C c) {
        WebResource resource = getWebResource(path + "/update/");
        return put(key, resource, clazz, c);
    }

    public C update(String key, C c) {
        WebResource resource = getWebResource(path + "/update/");
        return post(key, resource, clazz, c);
    }

    public Future<? extends C> delete(String key, C c) {
        AsyncWebResource resource = getAsyncWebResource(path + "/delete");
        return delete(key, resource, clazz, c);
    }
}