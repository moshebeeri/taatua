package org.vidad.tag.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.validation.constraints.Size;

import org.apache.log4j.Logger;

@Named(value = "userBean")
@ManagedBean
@SessionScoped
public class UserBean {
	transient Logger log = Logger.getLogger(UserBean.class);

	    public List<String> autocomplete(String prefix) {
			FacesContext con = FacesContext.getCurrentInstance();
			ExternalContext ec = con.getExternalContext();
			String resource = ec.getRequestParameterMap().get("javax.faces.source");
//			for( Entry<String, String> entry :  ec.getRequestParameterMap().entrySet()){
//		    	System.out.println("ParameterMap:    " + entry.getKey() + "=" + entry.getValue());
//			}
			con = null;ec=null;
	    	System.out.println("prefix="+prefix + " resource=" + resource);
	        ArrayList<String> result = new ArrayList<String>();
	        if(prefix.equals("Is"))
		        result.add("Israel");

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
		public static void main(String[] args) {
			 Path path = Paths.get("one/two/three");
			 System.out.println(path.subpath(0, 2));
			
			
					
		}
	}



