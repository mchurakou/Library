package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.Constants;
import utils.Pagination;
import ajax_json.Filter;
import beans.SimpleBean;
import beans.User;
import exceptions.DBException;

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
     * @throws DBException 
     * 
     */
	public static List<SimpleBean> getInformationForRealBooksPipe(String language) throws DBException{
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
			throw new DBException(e);
					
		}
		return result;
	}
	
	/**
     * Information for electronic books pipe
	 * @param language 
     * @return list of SimpleBean
     * @throws DBException 
     * 
     */
	public static List<SimpleBean> getInformationForElectronicBooksPipe(String language) throws DBException{
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
			throw new DBException(e);
					
		}
		return result;
	}
	
	
	/**
     * user statistic
	 * @param language 
     * @return list of SimpleBean
     * @throws DBException 
     * 
     */
	public static List<SimpleBean> userStatistic(String language) throws DBException{
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
			throw new DBException(e);
					
		}
		return result;
	}
	
	
	/**
    * book statistic
	 * @param language 
    * @return list of SimpleBean
    * @throws DBException 
    * 
    */
	public static List<SimpleBean> bookrStatistic(String language) throws DBException{
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
			throw new DBException(e);
					
		}
		return result;
	}
}
