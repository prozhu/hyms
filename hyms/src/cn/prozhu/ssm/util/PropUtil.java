package cn.prozhu.ssm.util;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class PropUtil {

	private static Logger logger = Logger.getLogger(PropUtil.class);

	private final static String dataDicFile =  "dataDic.properties";
	private final static String OTHERS =  "其他";
	private final static String[] propFiles = { "appAd.properties", dataDicFile };
	private static Calendar refreshCal = null;

	private static Map<String, Properties> propMap = new HashMap<String, Properties>();

	public static String getProperty(String key) {
		refreshProperties();
		for (String string : propFiles) {
			Properties props = propMap.get(string);
			if (props != null && props.containsKey(key)) {
				return props.getProperty(key);
			}
		}

		return StringUtils.EMPTY;
	}

	private static void refreshProperties() {
		Calendar cal = Calendar.getInstance();
		if (refreshCal == null || cal.after(refreshCal)) {
			Map<String, Properties> newpropMap = new HashMap<String, Properties>();
			try {
				for (String string : propFiles) {
					Resource resource = new ClassPathResource(string);
					Properties props = PropertiesLoaderUtils.loadProperties(resource);
					newpropMap.put(string, props);
				}
				propMap = newpropMap;
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
			cal.add(Calendar.MINUTE, 10);
			refreshCal = cal;
		}
	}
	
	public static String getDataDictionaryProperty(String key) {
		refreshProperties();
		Properties props = propMap.get(dataDicFile);
		if (props != null && props.containsKey(key)) {
			return props.getProperty(key);
		}
		return OTHERS;
	}

}
