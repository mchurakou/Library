package com.mikalai.library.ajax_json;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Convert from JSON to JAVA object
 * 
 * @author Mikalai_Churakou
 */
public class ConverterJSON {
	private static final Logger logger = LogManager.getLogger();
	/**
	 * convert to Filter
	 * 
	 * @param str json
	 * @return Filter
	 */
	public static Filter getFilter(String str) {

		ObjectMapper mapper = new ObjectMapper();
		Filter obj = null;
		try {
			obj = mapper.readValue(str, Filter.class);
		} catch (IOException e) {
			logger.error("Error", e);
		}

		return obj;
	}

}
