package org.vidad.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestData {
	private CookieHandler cookieHandler = new CookieHandler();
	Locator	locator	= new Locator();
	
	private String	id 			= "";
	private String	url 		= "";
	private String	ip 			= "";
	private String	location 	= "";
	private String	userAgent 	= "";
	private String	publisher 	= "";
	private String 	cookie 		= "";
	private String	domain 		= "";
	private String	lang 		= "";
	private String	title 		= "";
	
	public RequestData(HttpServletRequest request, HttpServletResponse response) {
		if (request != null) {
			url = request.getParameter("url");
			userAgent = request.getHeader("User-Agent");
			publisher = request.getParameter("publisher"); 
			title = request.getParameter("title");
			String ip = request.getHeader("X-FORWARDED-FOR");
			cookie = cookieHandler.handle(request, response);
			location = locator.getInfoFromGeoPlugin(ip);
			lang = request.getParameter("lang");
			title = request.getParameter("title");
		}
	}

	public CookieHandler getCookieHandler() {
		return cookieHandler;
	}

	public Locator getLocator() {
		return locator;
	}

	public String getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}

	public String getIp() {
		return ip;
	}

	public String getLocation() {
		return location;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public String getPublisher() {
		return publisher;
	}

	public String getCookie() {
		return cookie;
	}

	public String getDomain() {
		return domain;
	}

	public String getLang() {
		return lang;
	}

	public String getTitle() {
		return title;
	}
}
