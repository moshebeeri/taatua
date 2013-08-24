/**
 * This file was generated by Moshe Beeri
 * Aug 11, 2013
 * org.vidad.tag.service
 */
package org.vidad.tag.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

/**
 * @author Moshe Beeri
 *
 */
@Path("/test")
@Consumes("application/json")
@Produces("application/json")
public class Test {
	Logger log = Logger.getLogger(Test.class);
	
		public Test() {
		super();
	}

	@GET
	@Path("ping")
	@Produces("text/html")
	public String ping() {
		return "Ping result at server time - " + new DateTime().toString();
	}
}
