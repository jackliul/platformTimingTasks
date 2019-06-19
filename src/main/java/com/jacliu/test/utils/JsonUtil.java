package com.jacliu.test.utils;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @description
 * @author "jacliu"
 * @version 1.0.0
 */
@SuppressWarnings("rawtypes")
public class JsonUtil {

	/**
	 * json字符串转对象
	 *
	 * @param json
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> T json2Object(String json, Class<T> clazz) {
		return JSON.parseObject(json, clazz);
	}

	public static <T> List<T> json2List(String json, Class<T> clazz) {
		return JSON.parseArray(json, clazz);
	}

	/**
	 * @param source
	 * @return
	 */
	public static Map<String, Object> json2Map(String source) {
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) JSON.parse(source);
		return map;
	}

	/**
	 * 对象转json串
	 *
	 * @param t
	 * @param <T>
	 * @return
	 */
	public static <T> String toJson(T t) {
		return JSON.toJSON(t).toString();
	}

	public static <T> String toJsonNullValue(T t) {
		return JSONObject.toJSONString(t, SerializerFeature.WriteMapNullValue);
	}

	public static <T> T map2Object(Map map, Class<T> clazz) {
		return json2Object(toJson(map), clazz);
	}
}
