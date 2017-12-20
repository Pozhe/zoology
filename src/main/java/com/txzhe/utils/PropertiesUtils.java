package com.txzhe.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * properties�����ļ�������
 * 
 * @author heyangda-bizcent
 */
public class PropertiesUtils {

	private static Logger logger = Logger.getLogger(PropertiesUtils.class);
	/**
	 * freemarker define area
	 */
	private final static String FREEMARKER_FILENAME = "freemarker.config.properties";
	private final static String FREEMARKER_SETTING_PRIFIX = "settings.";
	private final static String FREEMARKER_VARIABLES_PRIFIX = "variables.";

	// freemarker
	// setting Map
	// variables Map
	public static Map<String, Properties> freemarkerMap = new HashMap<String, Properties>();

	static {

		InputStream inputStream = PropertiesUtils.class.getResourceAsStream("/" + FREEMARKER_FILENAME);
		Properties props = new Properties();
		try {
			props.load(inputStream);
			// settings
			Enumeration<Object> keys = props.keys();
			Properties settingProp = new Properties();
			Properties variablesProp = new Properties();
			while (keys.hasMoreElements()) {
				String record = keys.nextElement().toString();
				String value = props.getProperty(record);
				if (record.startsWith(FREEMARKER_SETTING_PRIFIX)) {
					settingProp.put(record.replace(FREEMARKER_SETTING_PRIFIX, ""), value);
				} else if (record.startsWith(FREEMARKER_VARIABLES_PRIFIX)) {
					variablesProp.put(record.replace(FREEMARKER_VARIABLES_PRIFIX, ""), value);
				}
			}
			freemarkerMap.put("settings", settingProp);
			freemarkerMap.put("variables", variablesProp);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("���������ļ�:" + FREEMARKER_FILENAME + "�쳣�����飡", e);
		}
	}
}
