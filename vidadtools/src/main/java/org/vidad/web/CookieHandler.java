package org.vidad.web;

import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieHandler {
	Random random = new Random();
	
	private String generateID(){ 
		return Long.toHexString(random.nextLong()).toUpperCase();
	}
	
	public String handle(HttpServletRequest request,
			HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		for(Cookie c : cookies){
			if(c.getName().equals("id"))
				return c.getValue();
		}
		
		String id;
		Cookie c = new Cookie("id", (id=generateID()));
		c.setPath("/");
		c.setMaxAge(Integer.MAX_VALUE);
	    response.addCookie(c);
		return id;
	}
}
