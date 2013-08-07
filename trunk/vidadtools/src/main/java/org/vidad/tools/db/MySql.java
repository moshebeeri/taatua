package org.vidad.tools.db;

public class MySql extends DataSource {
	protected final static String driver = "com.mysql.jdbc.Driver";

	public MySql(String host, String username, String password) {
		super(driver, host, username, password);
	}
}
