package org.vidad.tools.conf;


import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfigurationLayout;
import org.apache.commons.configuration.event.ConfigurationErrorListener;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.apache.commons.configuration.interpol.ConfigurationInterpolator;
import org.apache.commons.configuration.reloading.ReloadingStrategy;
import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;

public class Configure {
	
	private static Map<String,Configure> instances; 
	private static Logger logger = Logger.getLogger(Configure.class); 

	private static Configure settings = Configure.getInstance("settings");
	private PropertiesConfiguration config = null;
	private String drive = ""; 
	public static Configure settings() {
		return settings;
	}

	private Configure() {
		super();
	}

	private void initialize(String name) {
		try {
			config = new PropertiesConfiguration(name + ".properties");
		} catch (ConfigurationException e) {
			logger.error(e);
		}
	}
	
	public static Configure getInstance(String name) {
		Configure instance = null;
		if(instances == null)
			instances = new HashMap<String,Configure>();
		if((instance = instances.get(name)) == null  ){
			instance = new Configure();
			instance.initialize(name);
			instance.drive = instance.getString("drive");
			instances.put(name, instance);
		}
		return instance;
	}

	public void addConfigurationListener(ConfigurationListener l) {
		config.addConfigurationListener(l);
	}

	public void addErrorListener(ConfigurationErrorListener l) {
		config.addErrorListener(l);
	}

	public void addErrorLogListener() {
		config.addErrorLogListener();
	}

	public void addProperty(String key, Object value) {
		config.addProperty(key, value);
	}

	public void append(Configuration arg0) {
		config.append(arg0);
	}

	public void clear() {
		config.clear();
	}

	public void clearConfigurationListeners() {
		config.clearConfigurationListeners();
	}

	public void clearErrorListeners() {
		config.clearErrorListeners();
	}

	public void clearProperty(String key) {
		config.clearProperty(key);
	}

	public Object clone() {
		return config.clone();
	}

	public boolean containsKey(String key) {
		return config.containsKey(key);
	}

	public void copy(Configuration arg0) {
		config.copy(arg0);
	}

	public boolean equals(Object obj) {
		return config.equals(obj);
	}

	public String getBasePath() {
		return config.getBasePath();
	}

	public BigDecimal getBigDecimal(String arg0, BigDecimal arg1) {
		
		return config.getBigDecimal(arg0, arg1);
	}

	public BigDecimal getBigDecimal(String key) {
		return config.getBigDecimal(key);
	}

	public BigInteger getBigInteger(String arg0, BigInteger arg1) {
		return config.getBigInteger(arg0, arg1);
	}

	public BigInteger getBigInteger(String key) {
		return config.getBigInteger(key);
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		return config.getBoolean(key, defaultValue);
	}

	public Boolean getBoolean(String arg0, Boolean arg1) {
		return config.getBoolean(arg0, arg1);
	}

	public boolean getBoolean(String key) {
		return config.getBoolean(key);
	}

	public byte getByte(String key, byte defaultValue) {
		return config.getByte(key, defaultValue);
	}

	public Byte getByte(String arg0, Byte arg1) {
		return config.getByte(arg0, arg1);
	}

	public byte getByte(String key) {
		return config.getByte(key);
	}

	public Collection<?> getConfigurationListeners() {
		return config.getConfigurationListeners();
	}

	public double getDouble(String key, double defaultValue) {
		return config.getDouble(key, defaultValue);
	}

	public Double getDouble(String arg0, Double arg1) {
		return config.getDouble(arg0, arg1);
	}

	public double getDouble(String key) {
		return config.getDouble(key);
	}

	public String getEncoding() {
		return config.getEncoding();
	}

	public Collection<?> getErrorListeners() {
		return config.getErrorListeners();
	}

	public File getFile() {
		return config.getFile();
	}

	public String getFileName() {
		return config.getFileName();
	}

	public float getFloat(String key, float defaultValue) {
		return config.getFloat(key, defaultValue);
	}

	public Float getFloat(String arg0, Float arg1) {
		return config.getFloat(arg0, arg1);
	}

	public float getFloat(String key) {
		return config.getFloat(key);
	}

	public String getHeader() {
		return config.getHeader();
	}

	public boolean getIncludesAllowed() {
		return config.getIncludesAllowed();
	}

	public int getInt(String key, int defaultValue) {
		return config.getInt(key, defaultValue);
	}

	public int getInt(String key) {
		return config.getInt(key);
	}

	public Integer getInteger(String arg0, Integer arg1) {
		return config.getInteger(arg0, arg1);
	}

	public ConfigurationInterpolator getInterpolator() {
		return config.getInterpolator();
	}

	public char getListDelimiter() {
		return config.getListDelimiter();
	}

	public Log getLogger() {
		return config.getLogger();
	}

	public long getLong(String key, long defaultValue) {
		return config.getLong(key, defaultValue);
	}

	public Long getLong(String arg0, Long arg1) {
		return config.getLong(arg0, arg1);
	}

	public long getLong(String key) {
		return config.getLong(key);
	}

	public String getPath() {
		return config.getPath();
	}

	public String getPath(String key) {
		return drive + config.getString(key);
	}

	public Properties getProperties(String arg0, Properties arg1) {
		return config.getProperties(arg0, arg1);
	}

	public Properties getProperties(String key) {
		return config.getProperties(key);
	}

	public Object getProperty(String key) {
		return config.getProperty(key);
	}

	public ReloadingStrategy getReloadingStrategy() {
		return config.getReloadingStrategy();
	}

	public short getShort(String key, short defaultValue) {
		return config.getShort(key, defaultValue);
	}

	public Short getShort(String arg0, Short arg1) {
		return config.getShort(arg0, arg1);
	}

	public short getShort(String key) {
		return config.getShort(key);
	}

	public String getString(String key, String defaultValue) {
		return config.getString(key, defaultValue);
	}

	public String getString(String key) {
		return config.getString(key);
	}

	public String[] getStringArray(String arg0) {
		return config.getStringArray(arg0);
	}

	public StrSubstitutor getSubstitutor() {
		return config.getSubstitutor();
	}

	public URL getURL() {
		return config.getURL();
	}

	public int hashCode() {
		return config.hashCode();
	}

	public Configuration interpolatedConfiguration() {
		return config.interpolatedConfiguration();
	}

	public boolean isAutoSave() {
		return config.isAutoSave();
	}

	public boolean isDelimiterParsingDisabled() {
		return config.isDelimiterParsingDisabled();
	}

	public boolean isDetailEvents() {
		return config.isDetailEvents();
	}

	public boolean isEmpty() {
		return config.isEmpty();
	}

	public boolean isThrowExceptionOnMissing() {
		return config.isThrowExceptionOnMissing();
	}

	public void load() throws ConfigurationException {
		config.load();
	}

	public void load(File arg0) throws ConfigurationException {
		config.load(arg0);
	}

	public void load(InputStream arg0, String arg1)
			throws ConfigurationException {
		config.load(arg0, arg1);
	}

	public void load(InputStream in) throws ConfigurationException {
		config.load(in);
	}

	public void load(Reader in) throws ConfigurationException {
		config.load(in);
	}

	public void load(String arg0) throws ConfigurationException {
		config.load(arg0);
	}

	public void load(URL arg0) throws ConfigurationException {
		config.load(arg0);
	}

	public void reload() {
		config.reload();
	}

	public boolean removeConfigurationListener(ConfigurationListener l) {
		return config.removeConfigurationListener(l);
	}

	public boolean removeErrorListener(ConfigurationErrorListener l) {
		return config.removeErrorListener(l);
	}

	public void save() throws ConfigurationException {
		config.save();
	}

	public void save(File arg0) throws ConfigurationException {
		config.save(arg0);
	}

	public void save(OutputStream arg0, String arg1)
			throws ConfigurationException {
		config.save(arg0, arg1);
	}

	public void save(OutputStream out) throws ConfigurationException {
		config.save(out);
	}

	public void save(String arg0) throws ConfigurationException {
		config.save(arg0);
	}

	public void save(URL arg0) throws ConfigurationException {
		config.save(arg0);
	}

	public void save(Writer writer) throws ConfigurationException {
		config.save(writer);
	}

	public void setAutoSave(boolean autoSave) {
		config.setAutoSave(autoSave);
	}

	public void setBasePath(String basePath) {
		config.setBasePath(basePath);
	}

	public void setDelimiterParsingDisabled(boolean delimiterParsingDisabled) {
		config.setDelimiterParsingDisabled(delimiterParsingDisabled);
	}

	public void setDetailEvents(boolean enable) {
		config.setDetailEvents(enable);
	}

	public void setEncoding(String encoding) {
		config.setEncoding(encoding);
	}

	public void setFile(File file) {
		config.setFile(file);
	}

	public void setHeader(String header) {
		config.setHeader(header);
	}

	public void setLayout(PropertiesConfigurationLayout layout) {
		config.setLayout(layout);
	}

	public void setListDelimiter(char listDelimiter) {
		config.setListDelimiter(listDelimiter);
	}

	public void setLogger(Log log) {
		config.setLogger(log);
	}

	public void setPath(String path) {
		config.setPath(path);
	}

	public void setProperty(String key, Object value) {
		config.setProperty(key, value);
	}

	public void setReloadingStrategy(ReloadingStrategy strategy) {
		config.setReloadingStrategy(strategy);
	}

	public void setThrowExceptionOnMissing(boolean throwExceptionOnMissing) {
		config.setThrowExceptionOnMissing(throwExceptionOnMissing);
	}

	public void setURL(URL url) {
		config.setURL(url);
	}
	
	public Configuration subset(String prefix) {
		return config.subset(prefix);
	}

	public String toString() {
		return config.toString();
	}
}
