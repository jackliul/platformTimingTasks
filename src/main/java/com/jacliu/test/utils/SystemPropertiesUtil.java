package com.jacliu.test.utils;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class SystemPropertiesUtil extends PropertyPlaceholderConfigurer {
	static Properties properties;

	public SystemPropertiesUtil() {
	}

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
			throws BeansException {
		super.processProperties(beanFactory, props);
		properties = props;
	}

	public static String getContextProperty(String name) {
		if (null == properties) {
			return "propertiesIsNull";
		}
		return properties.getProperty(name);
	}
}
