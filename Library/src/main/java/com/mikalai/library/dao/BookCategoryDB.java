package com.mikalai.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mikalai.library.utils.Constants;
import com.mikalai.library.utils.Pagination;
import com.mikalai.library.beans.SimpleBean;

import com.mikalai.library.ajax_json.Filter;
;

/**
 * Action for work with book categories
 * 
 * @author Mikalai_Churakou
 */
public class BookCategoryDB {
	
	/**
     * count of book categories
	 * @param filters 
     * @return count
     * @throws Exception
     * 
     */
	public static int getCountOfBookCategories(Filter filter) throws Exception{
		String filterStr = SQL.getSqlFilter(filter);
		int count = 0;
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			PreparedStatement s = con.prepareStatement("Select count(*) as count from book_categories " +  filterStr);
			ResultSet rs = s.executeQuery();
			if (rs.next())
				count = rs.getInt("count");
				
			s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return count;
	}
	
	/**
     * List of book categories for table with searching
     * @return list of book categories
     * @throws Exception
     * 
     */
	public static List<SimpleBean> getBookCategoriesForTable(Pagination pagination, Filter filter) throws Exception{
				
		String filterStr = SQL.getSqlFilter(filter);
		List<SimpleBean> bookCategories = new ArrayList<SimpleBean>();
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			String sql = "SELECT * FROM " +
						"(SELECT *,row_number() over(order by " + pagination.getSidx() + " " + pagination.getSord() + ") as row_num " + 
						"FROM book_categories" + filterStr + ") as a " +
						"WHERE row_num BETWEEN ? AND ?";
			PreparedStatement s = con.prepareStatement(sql);
			s.setInt(1, pagination.getStart());
			s.setInt(2, pagination.getEnd());
			ResultSet rs = s.executeQuery();
			while (rs.next()){
				SimpleBean bookCategory = new SimpleBean();
				bookCategory.setId(rs.getInt(Constants.FIELD_ID));
				bookCategory.setName(rs.getString(Constants.FIELD_NAME));
				bookCategory.setName_ru(rs.getString(Constants.FIELD_NAME_RU));
			    bookCategories.add(bookCategory);
			}
			s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return bookCategories;
	}
	
	/**
     * delete book category
     * @param id of book category
     * @throws Exception
     * 
     */
		
	public static boolean deleteBookCategory(int id) throws Exception{
		boolean result = true;
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			PreparedStatement s = con.prepareStatement("select " + Constants.DB_DBO + ".can_delete_book_category(?) as res");
			s.setInt(1, id);
			ResultSet rs = s.executeQuery();
			rs.next();
			String res = rs.getString("res");
			if (res.equals("1")){
				String sql = "exec delete_book_category ?"; 
				s = con.prepareStatement(sql);
				s.setInt(1, id);
				s.executeUpdate();
			}
			else
				result = false;
			s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return result;
		
	}
	
	/**
     * edit book category
     * @param  id, name, name_ru
     * @throws Exception
     * 
     */	
	public static boolean editBookCategory(int id, String name,String name_ru) throws Exception  {
		boolean result = true;
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con = pool.getConnection();
			PreparedStatement s = con.prepareStatement("select " + Constants.DB_DBO + ".exist_book_category(?,?,?) as res");
			s.setInt(1, id);
			s.setString(2, name);
			s.setString(3, name_ru);
			ResultSet rs=s.executeQuery();
			rs.next();
			String res = rs.getString("res");
			if (res.equals("0")){
				s = con.prepareStatement("exec edit_book_category ?, ?, ?");
				s.setInt(1, id);
				s.setString(2, name);
				s.setString(3, name_ru);
				s.executeUpdate();
			}
			else
				result = false;
			s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new Exception(e);
		}
		return result;
		
	}
	
	

	/**
     * add book category
     * @param name, name_ru
     * @throws Exception
     * 
     */	
	public static boolean addBookCategory( String name,String name_ru) throws Exception  {
		boolean result = true;
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con = pool.getConnection();
			PreparedStatement s = con.prepareStatement("select " + Constants.DB_DBO + ".exist_book_category(?,?,?) as res");
			s.setInt(1, 0);
			s.setString(2, name);
			s.setString(3, name_ru);
			ResultSet rs=s.executeQuery();
			rs.next();
			String res = rs.getString("res");
			if (res.equals("0")){
				s = con.prepareStatement("exec add_book_category  ?, ?");
				s.setString(1, name);
				s.setString(2, name_ru);
				s.executeUpdate();
			}
			else
				result = false;
			s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return result;
		
	}

}
