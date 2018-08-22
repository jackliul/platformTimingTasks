package com.jacliu.test.redis.service;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.jacliu.test.redis.util.JsonUtils;

@Component("developOrTestRedisService")
public class DevelopOrTestRedisServiceImpl implements RedisService {

	@Autowired
	@Qualifier("develop_redisTemplate")
	private StringRedisTemplate intelinkTestRedisTemplate;

	// @Autowired
	// public void setRedisTemplate(StringRedisTemplate develop_redisTemplate) {
	// this.intelinkTestRedisTemplate = develop_redisTemplate;
	// }

	public <T> void put(String key, T obj) {
		intelinkTestRedisTemplate.opsForValue().set(key, JsonUtils.toJson(obj));
	}

	public <T> void put(String key, T obj, int timeout) {
		put(key, obj, timeout, TimeUnit.MINUTES);
	}

	public <T> void put(String key, T obj, int timeout, TimeUnit unit) {
		intelinkTestRedisTemplate.opsForValue().set(key, JsonUtils.toJson(obj), timeout, unit);
	}

	public <T> T get(String key, Class<T> cls) {
		return JsonUtils.fromJson(intelinkTestRedisTemplate.opsForValue().get(key), cls);
	}

	public <E, T extends Collection<E>> T get(String key, Class<E> cls, Class<T> collectionClass) {
		return JsonUtils.fromJson(intelinkTestRedisTemplate.opsForValue().get(key), cls, collectionClass);
	}

	public <T> T putIfAbsent(String key, Class<T> cls, Supplier<T> supplier) {
		T t = get(key, cls);
		if (t == null) {
			t = supplier.get();
			if (t != null) {
				put(key, t);
			}
		}
		return t;
	}

	public <T> T putIfAbsent(String key, Class<T> cls, Supplier<T> supplier, int timeout) {
		T t = get(key, cls);
		if (t == null) {
			t = supplier.get();
			if (t != null) {
				put(key, t, timeout);
			}
		}
		return t;
	}

	public <T> T putIfAbsent(String key, Class<T> cls, Supplier<T> supplier, int timeout, TimeUnit unit) {
		T t = get(key, cls);
		if (t == null) {
			t = supplier.get();
			if (t != null) {
				put(key, t, timeout, unit);
			}
		}
		return t;
	}

	public <T> T putIfAbsent(String key, Class<T> cls, Supplier<T> supplier, int timeout, TimeUnit unit,
			boolean refresh) {
		T t = get(key, cls);
		if (t == null) {
			t = supplier.get();
			if (t != null) {
				put(key, t, timeout, unit);
			}
		} else {
			if (refresh) {
				expire(key, timeout, unit);
			}
		}
		return t;
	}

	public <E, T extends Collection<E>> T putIfAbsent(String key, Class<E> cls, Class<T> collectionCls,
			Supplier<T> supplier) {
		T t = get(key, cls, collectionCls);
		if (t == null || t.isEmpty()) {
			t = supplier.get();
			if (t != null && t.size() > 0) {
				put(key, t);
			}
		}
		return t;
	}

	public <E, T extends Collection<E>> T putIfAbsent(String key, Class<E> cls, Class<T> collectionCls,
			Supplier<T> supplier, int timeout) {
		return putIfAbsent(key, cls, collectionCls, supplier, timeout, TimeUnit.SECONDS);
	}

	public <E, T extends Collection<E>> T putIfAbsent(String key, Class<E> cls, Class<T> collectionCls,
			Supplier<T> supplier, int timeout, TimeUnit unit) {
		return putIfAbsent(key, cls, collectionCls, supplier, timeout, unit, false);
	}

	public <E, T extends Collection<E>> T putIfAbsent(String key, Class<E> cls, Class<T> collectionCls,
			Supplier<T> supplier, int timeout, TimeUnit unit, boolean refresh) {
		T t = get(key, cls, collectionCls);
		if (t == null || t.isEmpty()) {
			t = supplier.get();
			if (t != null && t.size() > 0) {
				put(key, t, timeout, unit);
			}
		} else {
			if (refresh) {
				expire(key, timeout, unit);
			}
		}
		return t;
	}

	public void delKeyByPrefix(String prefix) {
		if (prefix == null || "".equals(prefix))
			throw new RuntimeException("Del key by prefix, but prefix is null.");
		Set<String> keys = intelinkTestRedisTemplate.keys(prefix + "*");
		intelinkTestRedisTemplate.delete(keys);
	}

	public void delKeyBySuffix(String suffix) {
		if (suffix == null || "".equals(suffix))
			throw new RuntimeException("del key by suffix ,but suffix is null.");
		Set<String> keys = intelinkTestRedisTemplate.keys("*" + suffix);
		intelinkTestRedisTemplate.delete(keys);
	}

	public void hput(String key, String hashKey, Object value) {
		HashOperations<String, String, String> stringObjectObjectHashOperations = intelinkTestRedisTemplate
				.opsForHash();
		stringObjectObjectHashOperations.put(key, hashKey, value.toString());
	}

	public String hget(String key, String hashKey) {
		HashOperations<String, String, String> stringObjectObjectHashOperations = intelinkTestRedisTemplate
				.opsForHash();
		String valObj = stringObjectObjectHashOperations.get(key, hashKey);
		if (valObj != null)
			return valObj;
		return null;
	}

	public Long hdel(String key, String hashKey) {
		return intelinkTestRedisTemplate.opsForHash().delete(key, new Object[] { hashKey });
	}

	public boolean exists(String key) {
		return intelinkTestRedisTemplate.hasKey(key);
	}

	public void delete(String key) {
		intelinkTestRedisTemplate.delete(key);
	}

	public boolean expire(String key, long timeout, TimeUnit timeUnit) {
		return intelinkTestRedisTemplate.expire(key, timeout, timeUnit);
	}

	public boolean expire(String key, long timeout) {
		return expire(key, timeout, TimeUnit.MINUTES);
	}

	public void put(String key, String value) {
		intelinkTestRedisTemplate.opsForValue().set(key, value);
	}

	public void put(String key, String value, int timeout) {
		put(key, value, timeout, TimeUnit.MINUTES);
	}

	public void put(String key, String value, int timeout, TimeUnit unit) {
		intelinkTestRedisTemplate.opsForValue().set(key, value, timeout, unit);
	}

	public String get(String key) {
		return intelinkTestRedisTemplate.opsForValue().get(key);
	}

}
