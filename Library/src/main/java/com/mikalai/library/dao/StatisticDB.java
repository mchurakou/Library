package com.mikalai.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mikalai.library.utils.Constants;
import com.mikalai.library.utils.Pagination;
import com.mikalai.library.ajax_json.Filter;
import com.mikalai.library.beans.SimpleBean;
import com.mikalai.library.beans.User;
;

/**
 * Class for work with statistic
 * 
 * @author Mikalai_Churakou
 */
public class StatisticDB {

	/**
     * Method extract User from ResultSet
     * @param ResultSet
     * @return User
     */	
	private static SimpleBean extractSimpleBean(ResultSet rs) throws SQLException{
		SimpleBean bean = new SimpleBean();
		bean.setName(rs.getString(Constants.FIELD_NAME));
		bean.setCount(rs.getInt(Constants.FIELD_COUNT));
		return bean;
	}
	
	/**
     * Information for real books pipe
     * @return list of SimpleBean
     * @throws Exception
     * 
     */
	public static List<SimpleBean> getInformationForRealBooksPipe(String language) throws Exception{
		String lang = " ";
		if (language.equals("ru"))
			lang = "_ru  ";
		List<SimpleBean> result = new ArrayList<SimpleBean>();
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			String sql = "select name, count from " + Constants.DB_DBO + ".statistic_pipe_real_books" + lang + "()"; 
			PreparedStatement s = con.prepareStatement(sql);
			ResultSet rs = s.executeQuery();
			while (rs.next())
				result.add(extractSimpleBean(rs));
			s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return result;
	}
	
	/**
     * Information for electronic books pipe
	 * @param language 
     * @return list of SimpleBean
     * @throws Exception
     * 
     */
	public static List<SimpleBean> getInformationForElectronicBooksPipe(String language) throws Exception{
		String lang = " ";
		if (language.equals("ru"))
			lang = "_ru  ";
		List<SimpleBean> result = new ArrayList<SimpleBean>();
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			String sql = "select name, count from " + Constants.DB_DBO + ".statistic_pipe_electronic_books" + lang + "()"; 
			PreparedStatement s = con.prepareStatement(sql);
			ResultSet rs = s.executeQuery();
			while (rs.next())
				result.add(extractSimpleBean(rs));
			s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return result;
	}
	
	
	/**
     * user statistic
	 * @param language 
     * @return list of SimpleBean
     * @throws Exception
     * 
     */
	public static List<SimpleBean> userStatistic(String language) throws Exception{
		String lang = " ";
		if (language.equals("ru"))
			lang = "_ru  ";
		List<SimpleBean> result = new ArrayList<SimpleBean>();
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			String sql = "select name, count from " + Constants.DB_DBO + ".user_statistic" + lang + "()"; 
			PreparedStatement s = con.prepareStatement(sql);
			ResultSet rs = s.executeQuery();
			while (rs.next())
				result.add(extractSimpleBean(rs));
			s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return result;
	}
	
	
	/**
    * book statistic
	 * @param language 
    * @return list of SimpleBean
    * @throws Exception
    * 
    */
	public static List<SimpleBean> bookrStatistic(String language) throws Exception{
		String lang = " ";
		if (language.equals("ru"))
			lang = "_ru  ";
		List<SimpleBean> result = new ArrayList<SimpleBean>();
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			String sql = "select name, count from " + Constants.DB_DBO + ".book_statistic" + lang + "()"; 
			PreparedStatement s = con.prepareStatement(sql);
			ResultSet rs = s.executeQuery();
			while (rs.next())
				result.add(extractSimpleBean(rs));
			s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return result;
	}
}
