package org.vidad.tag.model;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.validation.constraints.Size;

import org.apache.log4j.Logger;

@ManagedBean
@RequestScoped
public class UserBean {
	transient Logger log = Logger.getLogger(UserBean.class);

	    public List<String> autocomplete(String prefix) {
	    	System.out.println("prefix="+prefix);
	        ArrayList<String> result = new ArrayList<String>();
	        result.add("England");
	        result.add("France");
	        result.add("Germany");
	        result.add("Italy");
	        result.add("Spain");

	        return result;
	    }

	    @Size(min=3, max=12,message="Must be between 3 and 12 chars")
	    private String name;

	    private String state;

	    public String getState() {
	        return state;
	    }

	    public String getName() {
	        return name;
	    }

		public void setName(String name) {
			this.name = name;
		}

		public void setState(String state) {
			this.state = state;
		}
	}
