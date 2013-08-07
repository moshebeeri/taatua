package org.vidad.tools.db;

public class Hive extends DataSource {
	protected final static String driver = "org.apache.hadoop.hive.jdbc.HiveDriver";

	public Hive(String host, String username, String password) {
		super(driver, host, username, password);
	}
}