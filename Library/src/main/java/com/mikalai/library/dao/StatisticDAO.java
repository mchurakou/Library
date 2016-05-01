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
public class StatisticDAO extends GenericDAO {

	/**
     * Method extract User from ResultSet
     * @param ResultSet
     * @return User
     */	
	private  SimpleBean extractSimpleBean(ResultSet rs) throws SQLException{
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
	public  List<SimpleBean> getInformationForRealBooksPipe() throws Exception{

		List<SimpleBean> result = new ArrayList<SimpleBean>();
		try {Connection con = getConnection();
			String sql = "select name, count from " + Constants.DB_DBO + ".statistic_pipe_real_books()";
			PreparedStatement s = con.prepareStatement(sql);
			ResultSet rs = s.executeQuery();
			while (rs.next())
				result.add(extractSimpleBean(rs));
			s.close();

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
	public  List<SimpleBean> getInformationForElectronicBooksPipe() throws Exception{

		List<SimpleBean> result = new ArrayList<SimpleBean>();
		try {Connection con = getConnection();
			String sql = "select name, count from " + Constants.DB_DBO + ".statistic_pipe_electronic_books()";
			PreparedStatement s = con.prepareStatement(sql);
			ResultSet rs = s.executeQuery();
			while (rs.next())
				result.add(extractSimpleBean(rs));
			s.close();

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
	public  List<SimpleBean> userStatistic() throws Exception{

		List<SimpleBean> result = new ArrayList<SimpleBean>();
		try {Connection con = getConnection();
			String sql = "select name, count from " + Constants.DB_DBO + ".user_statistic()";
			PreparedStatement s = con.prepareStatement(sql);
			ResultSet rs = s.executeQuery();
			while (rs.next())
				result.add(extractSimpleBean(rs));
			s.close();

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
	public  List<SimpleBean> bookrStatistic() throws Exception{

		List<SimpleBean> result = new ArrayList<SimpleBean>();
		try {Connection con = getConnection();
			String sql = "select name, count from " + Constants.DB_DBO + ".book_statistic()";
			PreparedStatement s = con.prepareStatement(sql);
			ResultSet rs = s.executeQuery();
			while (rs.next())
				result.add(extractSimpleBean(rs));
			s.close();

		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return result;
	}
}
