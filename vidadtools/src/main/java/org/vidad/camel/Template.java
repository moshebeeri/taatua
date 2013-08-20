package org.vidad.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.context.ApplicationContext;
import org.vidad.spring.SpringApplicationContext;

public class Template {
	
	public static ProducerTemplate byId(String id ){
		ApplicationContext springContext = SpringApplicationContext.getContext();
		CamelContext context = (CamelContext) springContext.getBean("k1response-context");
		return context.createProducerTemplate();
	}
}
