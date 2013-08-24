package org.vidad.tag.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

@Path("/video")
@Consumes("application/json")
@Produces("application/json")
public class VideoRest extends org.vidad.tools.service.VideoRest{
	transient Logger log = Logger.getLogger(VideoRest.class);

	public VideoRest() {
		super();
	}
}
