package org.vidad.spring;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author 
 *         http://sujitpal.blogspot.co.il/2007/03/accessing-spring-beans-from-legacy-code.html
 * 
 *         Wrapper to always return a reference to the Spring Application
 *         Context from within non-Spring enabled beans. Unlike Spring MVC's
 *         WebApplicationContextUtils we do not need a reference to the Servlet
 *         context for this. All we need is for this bean to be initialized
 *         during application startup.
 */
public class SpringApplicationContext implements ApplicationContextAware {
	transient Logger log = Logger.getLogger(SpringApplicationContext.class);
	@Autowired
	private static ApplicationContext context;
	/**
	 * This method is called from within the ApplicationContext once it is done
	 * starting up, it will stick a reference to itself into this bean.
	 * 
	 * @param applicationContext
	 *            a reference to the ApplicationContext.
	 * @throws BeansException
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
//		String tenant = Configure.settings().getString("active.tenant","");
//		if(!tenant.equals("production"))
//			System.setProperty("com.amazonaws.sdk.disableCertChecking","true");
		context = applicationContext;
	}

	/**
	 * This is about the same as context.getBean("beanName"), except it has its
	 * own static handle to the Spring context, so calling this method
	 * statically will give access to the beans by name in the Spring
	 * application context. As in the context.getBean("beanName") call, the
	 * caller must cast to the appropriate target class. If the bean does not
	 * exist, then a Runtime error will be thrown.
	 * 
	 * @param beanName
	 *            the name of the bean to get.
	 * @return an Object reference to the named bean.
	 */
	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}

	//TODO: Consider to use the convention so the name will start with lower case char
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		return (T) context.getBean(clazz.getSimpleName());
	}

	/**
	 * @return the context
	 */
	public static ApplicationContext getContext() {
		return context;
	}
}

