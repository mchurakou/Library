package com.mikalai.library.ajax_json;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

import java.io.IOException;

/**
 * Convert from JSON to JAVA object
 * 
 * @author Mikalai_Churakou
 */
public class ConverterJSON {
	private static final Logger LOG = LogManager.getLogger(ConverterJSON.class);
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
			LOG.error("Error", e);
		}

		return obj;
	}

}
