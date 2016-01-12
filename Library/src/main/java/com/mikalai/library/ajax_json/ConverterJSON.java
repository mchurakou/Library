package com.mikalai.library.ajax_json;


import com.google.gson.Gson;
/**
 * Convert from JSON to JAVA object
 * 
 * @author Mikalai_Churakou
 */
public class ConverterJSON {
	/**
	 * convert to Filter
	 * 
	 * @param str json
	 * @return Filter
	 */
	public static Filter getFilter(String str){
		Gson gson = new Gson();
		return gson.fromJson(str, Filter.class);
	}

}
