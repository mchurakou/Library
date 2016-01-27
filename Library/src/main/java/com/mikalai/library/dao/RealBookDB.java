package com.mikalai.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

;

import com.mikalai.library.utils.Constants;
import com.mikalai.library.utils.Pagination;
import com.mikalai.library.ajax_json.Filter;
import com.mikalai.library.beans.RealBook;
import com.mikalai.library.beans.SimpleBean;
import com.mikalai.library.beans.User;

/**
 * Class for work with real books
 * 
 * @author Mikalai_Churakou
 */
public class RealBookDB {
	
	/**
     * Method extract RealBook from ResultSet
     * @param ResultSet
     * @return User
     */	
	private static RealBook extractRealBook(ResultSet rs) throws SQLException{
		RealBook realBook = new RealBook();
		realBook.setId(rs.getInt(Constants.FIELD_ID));
		realBook.setBookDescriptionId(rs.getInt(Constants.FIELD_BOOK_DESCRIPTION_ID));
		realBook.setInventoryNumber(rs.getInt(Constants.FIELD_INVENTORY_NUMBER));
		realBook.setCost(rs.getInt(Constants.FIELD_COST));
		realBook.setName(rs.getString(Constants.FIELD_NAME));
		realBook.setAuthor(rs.getString(Constants.FIELD_AUTHOR));
		SimpleBean bookCategory =  new SimpleBean();
		bookCategory.setId(rs.getInt(Constants.FIELD_BOOK_CATEGORIES_ID));
		bookCategory.setName(rs.getString(Constants.FIELD_BOOK_CATEGORY));
		realBook.setBookCategory(bookCategory);
		realBook.setPublicationPlace(rs.getString(Constants.FIELD_PUBLICATION_PLACE));
		realBook.setPublicationYear(rs.getInt(Constants.FIELD_PUBLICATION_YEAR));
		realBook.setSize(rs.getInt(Constants.FIELD_SIZE));
		SimpleBean language =  new SimpleBean();
		language.setId(rs.getInt(Constants.FIELD_LANGUAGE_ID));
		language.setName(rs.getString(Constants.FIELD_LANGUAGE));
		realBook.setLanguage(language);
		
		realBook.setAvailable(rs.getBoolean(Constants.FIELD_AVAILABLE));
		return realBook;
	}
	
	/**
     * count of real books
	 * @param filter 
     * @return count
     * @throws Exception
     * 
     */
	public static int getCountOfRealBooks(Filter filter) throws Exception{
		String filterStr = SQL.getSqlFilter(filter);
		int count = 0;
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			PreparedStatement s = con.prepareStatement("select count(*) as count from (Select * from view_real_books "  + filterStr + ") t ");
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
     * List of real books for table with searching
	 * @param language 
     * @return list of users
     * @throws Exception
     * 
     */
	public static List<RealBook> getRealBooksForTable(Pagination pagination, Filter filter, String language) throws Exception{
		String lang = " ";
		if (language.equals("ru"))
			lang = "_ru  ";
		
		
		String filterStr = SQL.getSqlFilter(filter);
		
		List<RealBook> realBooks = new ArrayList<RealBook>();
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			String sql = "SELECT * FROM " +
						"(SELECT *,row_number() over(order by " + pagination.getSidx() + " " + pagination.getSord() + ") as row_num " + 
						"FROM view_real_books" + lang + filterStr + ") as a " +
						"WHERE row_num BETWEEN ? AND ?"; 
			PreparedStatement s = con.prepareStatement(sql);
			s.setInt(1, pagination.getStart());
			s.setInt(2, pagination.getEnd());
			ResultSet rs = s.executeQuery();
			while (rs.next())
				realBooks.add(extractRealBook(rs));
			s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return realBooks;
	}
	
	/**
     * delete real book
     * @param id of real book
     * @throws Exception
     * 
     */
	
	public static boolean deleteRealBook(int id) throws Exception{
		boolean result = true;
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			PreparedStatement s = con.prepareStatement("select " + Constants.DB_DBO + ".can_delete_real_book(?) as res");
			s.setInt(1, id);
			ResultSet rs = s.executeQuery();
			rs.next();
			String res = rs.getString("res");
			
			if (res.equals("1")){
				String sql = "exec delete_real_book ?"; 
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
     * edit real book
     * @param id, inventory number, cost
     * @throws Exception
     * 
     */	
	public static void editRealBook(int id, int inventoryNumber,int cost) throws Exception  {
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con = pool.getConnection();
			PreparedStatement s;
			s = con.prepareStatement("exec edit_real_book ?, ?,?");
			s.setInt(1, id);
			s.setInt(2, inventoryNumber);
			s.setInt(3, cost);
			s.executeUpdate();
			s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new Exception(e);
		}
		
	}

	
	/**
     * add real book
     * @param nventoryNumber, cost,bookDescriptionId
     * @throws Exception
     * 
     */
	public static boolean addRealBook(int inventoryNumber, int cost,int bookDescriptionId) throws Exception{
		boolean result = true;
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con = pool.getConnection();
			PreparedStatement s = con.prepareStatement("select " + Constants.DB_DBO + ".exist_real_book(?) as res");
			s.setInt(1, inventoryNumber);
			ResultSet rs=s.executeQuery();
			rs.next();
			String res = rs.getString("res");
			if (res.equals("0")){
				s = con.prepareStatement("exec add_real_book ?, ?,?");
				s.setInt(1, inventoryNumber);
				s.setInt(2, cost);
				s.setInt(3, bookDescriptionId);
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
     * get user categories id for real book
     * @param real book id 
      * @return user categories ids
     */	
	public static List<Integer> getUserCategoriesId(int realBookId) throws Exception{
		List<Integer> result = new ArrayList<Integer>();
			
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			PreparedStatement s = con.prepareStatement("Select userCategoryId FROM privileges_real where realBookId = ?");
			s.setInt(1, realBookId);
			
			ResultSet rs=s.executeQuery();
			int i = 0;
			while (rs.next()){
				result.add(rs.getInt(Constants.FIELD_USER_CATEGORIES_ID));
			}
		    s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		
		return  result;
	}
	
	/**
     * set user categories id for real book
     * @param real book id 
     * @param user categories ids 
     * 
     */	
	public static void setUserCategoriesId(int realBookId,int[] categoryIds) throws Exception{
		
			
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			PreparedStatement s = con.prepareStatement("exec clear_real_privileges ?");
			s.setInt(1, realBookId);
			s.executeUpdate();
			
			for (int i = 0; i < categoryIds.length; i++  ){
				s = con.prepareStatement("exec add_real_privileges ?,?");
				s.setInt(1, realBookId);
				s.setInt(2, categoryIds[i]);
				s.executeUpdate();
				
			}
			
		    s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return;
	}
	
	
	
	
	/**
     * count of real books for user
	 * @param filter 
     * @return count
     * @throws Exception
     * 
     */
	public static int getCountOfRealBooksForUser(Filter filter,int userCategoryId) throws Exception{
		String filterStr = SQL.getSqlFilter(filter);
		int count = 0;
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			
			PreparedStatement s = con.prepareStatement("select count(*) as count from ( SELECT * FROM " + Constants.DB_DBO + ".real_books_for_user_category(?)"  + filterStr + ") t ");
			s.setInt(1, userCategoryId);
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
     * List of real books for table with searching
	 * @param language 
     * @return list of users
     * @throws Exception
     * 
     */
	public static List<RealBook> getRealBooksForTableByUserCategory(Pagination pagination, Filter filter, String language,int userCategoryId) throws Exception{
		String lang = " ";
		if (language.equals("ru"))
			lang = "_ru  ";
		
		
		String filterStr = SQL.getSqlFilter(filter);
		
		List<RealBook> realBooks = new ArrayList<RealBook>();
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			String sql = "SELECT * FROM " +
						"(SELECT *,row_number() over(order by " + pagination.getSidx() + " " + pagination.getSord() + ") as row_num " + 
						"FROM " + Constants.DB_DBO + ".real_books_for_user_category(?) " + lang + filterStr + ") as a " +
						"WHERE row_num BETWEEN ? AND ?"; 
			PreparedStatement s = con.prepareStatement(sql);
			s.setInt(1,userCategoryId);
			s.setInt(2, pagination.getStart());
			s.setInt(3, pagination.getEnd());
			ResultSet rs = s.executeQuery();
			while (rs.next())
				realBooks.add(extractRealBook(rs));
			s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return realBooks;
	}
	
	
	

}
