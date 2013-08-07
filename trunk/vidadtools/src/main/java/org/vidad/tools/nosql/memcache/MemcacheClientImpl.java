package org.vidad.tools.nosql.memcache;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.activity.InvalidActivityException;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import net.rubyeye.xmemcached.Counter;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

//XMemcache
public class MemcacheClientImpl implements Memcache{

	Logger logger = Logger.getLogger(MemcacheClientImpl.class);
	int[]	memcacheServersWeights = null;
	int		poolSize;
	long 	timeout;	
	MemcachedClient mcc = null;
	MemcachedClientBuilder builder = null;
	
	public MemcacheClientImpl() {
	}

	public MemcacheClientImpl(String memcacheServers) {
		this(memcacheServers, false);
	}
	
	public MemcacheClientImpl(String memcacheServers, boolean doSharding) {
		init(memcacheServers, doSharding);
	}
	
	private void init(String servers, boolean doSharding) {
		//according to http://code.google.com/p/xmemcached/wiki/FailureMode_StandbyNode
		builder= new XMemcachedClientBuilder(AddrUtil.getAddressMap(servers));
		builder.setFailureMode(true);
		builder.setConnectionPoolSize(poolSize);

		try {
			mcc = builder.build();
			mcc.setOptimizeMergeBuffer(false);
			mcc.setEnableHeartBeat(false);
			mcc.setConnectTimeout(timeout);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public synchronized long increment(String key) {
		try {
			return mcc.incr(key, 1, 0, timeout);
		} catch (TimeoutException e) {
			logger.error(e.getMessage(), e);
		} catch (MemcachedException e) {
			logger.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		} 
		return 0;
	}

	@Override
	public long getCounter(String IP) {
		try {
			Counter value = mcc.getCounter(IP);
			logger.debug("Got value from memcache [" + IP + "] : " + value.get());
			return value.get();
		} catch (TimeoutException e) {
			logger.error(e.getMessage(), e);
		} catch (MemcachedException e) {
			logger.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		} 
		return 0;
	}

	@Override
	public String getString(String Key) {
		try {
			String value = mcc.get(Key, timeout);
			return value != null ? value : null;
		} catch (TimeoutException e) {
			logger.error(e.getMessage(), e);
		} catch (MemcachedException e) {
			logger.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		} 
		return null;
	}

	@Override
	public long get(String Key) {
		try {
			Object value = mcc.get(Key);
			if (value != null) {
				Long valueLong = Long.valueOf((String)mcc.get(Key, timeout));
				if (valueLong == null) 
					throw new InvalidActivityException("The value returned from memcache for key [" + Key + "] was null");

				logger.debug("Got value from memcache [" + Key + "] : " + valueLong);
				return valueLong;
			}
			return 0;
		} catch (TimeoutException e) {
			logger.error(e.getMessage(), e);
		} catch (MemcachedException e) {
			logger.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}  catch (InvalidActivityException ex) {
			logger.error("The value returned from memcache for key [" + Key + "] was null");
		} 
		
		return 0;
	}

	@Override
	public Boolean getBoolean(String Key) {
		try {
			Object value = mcc.get(Key);
			if (value != null) {
				Boolean valueBoolean = Boolean.valueOf((String) mcc.get(Key, timeout));
				if (valueBoolean == null)
					throw new InvalidActivityException("The value returned from memcache for key [" + Key + "] was null");

				logger.debug("Got value from memcache [" + Key + "] : " + valueBoolean);
				return valueBoolean;
			}
			throw new InvalidActivityException("The key [" + Key + "] doesn't exist");
		} catch (TimeoutException e) {
			logger.error(e.getMessage(), e);
		} catch (MemcachedException e) {
			logger.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		} catch (InvalidActivityException ex) {
			logger.info("The value returned from memcache for key [" + Key + "] was null");
		} 
		return false;
	}

	@Override
	public void setValue(String key, String value, int ttl) {
		try {
			mcc.set(key, ttl, value, timeout);
		} catch (TimeoutException e) {
			logger.error(e.getMessage(), e);
		} catch (MemcachedException e) {
			logger.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		} 
		
	}

	@Override
	public Map<String, Object> getMulti(String[] keys) {
		try {
			Map<String, Object> multipleValues = mcc.get(Arrays.asList(keys), timeout);
			if (multipleValues != null && multipleValues.size() > 0)
				return multipleValues;
		} catch (TimeoutException e) {
			logger.error(e.getMessage(), e);
		} catch (MemcachedException e) {
			logger.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		} 
		return null;
	}

	@Override
	public void setValue(String key, String newData) {
		Gson gson = new Gson();
		String json = gson.toJson(newData);
		try {
			mcc.set(key, 0, json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
