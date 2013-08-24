package org.vidad.tag.model;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
	
	public SelectVideoToTag() {
		super();
	}
	public void select(AjaxBehaviorEvent e){
		Mongodb mongo = Mongodb.getInstance();
		videos = mongo.getAllCollectionable(Collection.VIDEO, Video.class);
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
