package com.mikalai.library.ajax_json;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Convert from JSON to JAVA object
 * 
 * @author Mikalai_Churakou
 */
public class ConverterJSON {
	private static final Logger LOG = Logger.getLogger(ConverterJSON.class);
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
