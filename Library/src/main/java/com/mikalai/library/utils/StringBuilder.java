package com.mikalai.library.utils;

import java.util.List;

import com.mikalai.library.beans.SimpleBean;

public class StringBuilder {
	public static String generateValueForList(List list){
		String result = "";
		
		for (int i = 0; i < list.size(); i++ ){
			SimpleBean bean = (SimpleBean)list.get(i);
			if (i == list.size() - 1)
				result += bean.getId() + ":" + bean.getName();
			else
				result += bean.getId() + ":" + bean.getName() + ";";
		}
		
		return result;
	}
	
	public static String generateLabelsForPipe(List<SimpleBean> list){
		String result = "";
		
		for (int i = 0; i < list.size(); i++ ){
			SimpleBean bean = list.get(i);
			if (i == list.size() - 1)
				result += "'" + bean.getName() + " " + bean.getCount() + "'";
			else
				result += "'" + bean.getName() + " " +bean.getCount() + "'" + ",";
		}
		
		return result;
	}
	
	public static String generateClearLabels(List<SimpleBean> list){
		String result = "";
		
		for (int i = 0; i < list.size(); i++ ){
			SimpleBean bean = list.get(i);
			if (i == list.size() - 1)
				result += "'" + bean.getName( )+ "'";
			else
				result += "'" + bean.getName() + "'" + ",";
		}
		
		return result;
	}
	
	public static String generateClearCounts(List<SimpleBean> list){
		String result = "";
		
		for (int i = 0; i < list.size(); i++ ){
			SimpleBean bean = list.get(i);
			if (i == list.size() - 1)
				result += bean.getCount();
			else
				result += bean.getCount() + ",";
		}
		
		return result;
	}

}
