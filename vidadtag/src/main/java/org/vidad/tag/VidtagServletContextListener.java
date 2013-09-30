/**
 * 
 */
package org.vidad.tag;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.vidad.autocomplete.Autocomplete;

/**
 * @author moshe
 *
 */
public class VidtagServletContextListener implements ServletContextListener{
	transient Logger log = Logger.getLogger(VidtagServletContextListener.class);

	/**
	 * 
	 */
	public VidtagServletContextListener() {
	}

	/**
	 * @param sce
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		log.info("initialize Autocomplete...");
		Autocomplete.getInstance();
		log.info("initialize Autocomplete - done.");

	}

	/**
	 * @param sce
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
