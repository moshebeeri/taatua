package org.vidad.tools.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class DataSource {

		protected static final Logger _logger = Logger.getLogger(DataSource.class);
		
		private String host;
		private String username;
		private String password;
	    private Connection connection;
	    
	    //counts opened statements
	    private int statementsCounter = 0;
	    
	    
	    public DataSource(String driver, String host, String  username, String password)
	    {
		  try {
			  
		      Class.forName(driver).newInstance();
		      this.host     = host     ;
		      this.username = username ;
		      this.password = password ;
		      setConnection();
		      
		    } catch (Exception e) {
		      _logger.error("Failed to load mSQL driver: " + e.getMessage() + " : " + e.getStackTrace());
		    }
	    }
	      
	    private void setConnection(){
	    	try {
	    		connection = DriverManager.getConnection(host,username,password);
			} catch (SQLException e) {
				 _logger.error("Failed to create connection to mSQL: " + e.getLocalizedMessage());
			}
	    }
	    
	    /**
	     * counts opened Statements
	     * should be used in pair with closeStatement().
	     * @return Statement
	     */
	    public Statement createStatement(){
	    	try{
	    		if(connection == null || connection.isClosed()){
	    			setConnection();
	    		}
	    		if(connection != null)
		    		return connection.createStatement();
		    	else 
		    		throw new NullPointerException("Trying access null - connection!");
	    	}catch (Exception e) {
				 _logger.error("Failed to create statement: " + e.getLocalizedMessage());
			}
	    	return null;
	    }
	    
	    /**
	     * closes all passed objects
	     * decrements opened statements.
	     * when number of opened statements goes to 0, closes connection.
	     * should be used in pair with createStatement() 
	     * @param st
	     * @param rs
	     */
	    public void closeStatement(Statement st, ResultSet rs){
	    	try{
	    		if(rs != null){
	    			rs.close();
	    		}
	    		if(st != null){
	    			st.close();
	    		}
	    		statementsCounter --;
	    		if(statementsCounter <= 0 && connection != null){
	    			connection.close();
	    		}
	    	} catch (SQLException e) {
				 _logger.error("Failed to close statement: " + e.getLocalizedMessage());
			}
	    }

		/**
		 * @param _host the _host to set
		 */
		public void sethost(String _host) {
			this.host = _host;
		}
		

		/**
		 * @return the _host
		 */
		public String gethost() {
			return host;
		}

		/**
		 * @param _username the _username to set
		 */
		public void setUsername(String _username) {
			this.username = _username;
		}

		/**
		 * @return the _username
		 */
		public String getUsername() {
			return username;
		}

		/**
		 * @param _password the _password to set
		 */
		public void setPassword(String _password) {
			this.password = _password;
		}

		/**
		 * @return the _password
		 */
		public String getPassword() {
			return password;
		}

}
