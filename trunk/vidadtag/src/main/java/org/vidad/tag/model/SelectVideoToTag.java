package org.vidad.tag.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.log4j.Logger;
import org.vidad.data.Video;
import org.vidad.tools.conf.Collection;
import org.vidad.tools.nosql.Mongodb;

@ManagedBean	
@SessionScoped
public class SelectVideoToTag implements Serializable{
	private static final long serialVersionUID = -4146661290221256647L;
	transient Logger log = Logger.getLogger(SelectVideoToTag.class);
	
	List<Video> videos;
	double	duraion;
	boolean initial;
	String	videoId;
	
	public SelectVideoToTag() {
		super();
	}
	public void select(AjaxBehaviorEvent e){
		Mongodb mongo = Mongodb.getInstance();
		videos = mongo.getAllCollectionable(Collection.VIDEO, Video.class);
	}
	
	public String tag() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		sessionMap.put("videoId", videoId);
		return "ok";
	}

	public List<Video> getVideos() {
		return videos;
	}
	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}
	public double getDuraion() {
		return duraion;
	}
	public void setDuraion(double duraion) {
		this.duraion = duraion;
	}
	public boolean isInitial() {
		return initial;
	}
	public void setInitial(boolean initial) {
		this.initial = initial;
	}
}
