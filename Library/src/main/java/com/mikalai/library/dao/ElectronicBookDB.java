package com.mikalai.library.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mikalai.library.ajax_json.Filter;
import com.mikalai.library.beans.ElectronicBook;
import com.mikalai.library.beans.RealBook;
import com.mikalai.library.beans.SimpleBean;

import com.mikalai.library.utils.Constants;
import com.mikalai.library.utils.Pagination;
;
/**
 * Class for work with electronic books
 * 
 * @author Mikalai_Churakou
 */
public class ElectronicBookDB {
	/**
     * Method extract RealBook from ResultSet
     * @param ResultSet
     * @return User
     */	
	private static ElectronicBook extractElectronicBook(ResultSet rs) throws SQLException{
		ElectronicBook electronicBook = new ElectronicBook();
		electronicBook.setId(rs.getInt(Constants.FIELD_ID));
		electronicBook.setBookDescriptionId(rs.getInt(Constants.FIELD_BOOK_DESCRIPTION_ID));
		
		electronicBook.setFileName(rs.getString(Constants.FIELD_FILE_NAME));
		electronicBook.setCapacity(rs.getInt(Constants.FIELD_CAPACITY));
		electronicBook.setExtension(rs.getString(Constants.FIELD_EXTENSION));
		
		
		electronicBook.setName(rs.getString(Constants.FIELD_NAME));
		electronicBook.setAuthor(rs.getString(Constants.FIELD_AUTHOR));
		SimpleBean bookCategory =  new SimpleBean();
		bookCategory.setId(rs.getInt(Constants.FIELD_BOOK_CATEGORIES_ID));
		bookCategory.setName(rs.getString(Constants.FIELD_BOOK_CATEGORY));
		electronicBook.setBookCategory(bookCategory);
		electronicBook.setPublicationPlace(rs.getString(Constants.FIELD_PUBLICATION_PLACE));
		electronicBook.setPublicationYear(rs.getInt(Constants.FIELD_PUBLICATION_YEAR));
		electronicBook.setSize(rs.getInt(Constants.FIELD_SIZE));
		SimpleBean language =  new SimpleBean();
		language.setId(rs.getInt(Constants.FIELD_LANGUAGE_ID));
		language.setName(rs.getString(Constants.FIELD_LANGUAGE));
		electronicBook.setLanguage(language);
				
		return electronicBook;
	}
	
	
	
	
	
	/**
     * get new file id
     * 
     * @throws Exception
     * 
     */
		
	public static int getNewFileId() throws Exception{
		int result;
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			PreparedStatement s = con.prepareStatement("select " + Constants.DB_DBO + ".new_file_id() as res");
			ResultSet rs = s.executeQuery();
			rs.next();
			result = rs.getInt("res");
			s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return result;
		
	}
	
	/**
    * Add new ebook
    * @param bookDescriptionId 
    * @param file name
    * 
    */	
	public static void addElectronicBook(int bookDescriptionId, String fileName, int size, String extention) throws Exception{
		
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			PreparedStatement s = con.prepareStatement("exec add_electronic_book ?, ?,?,?");
			s.setInt(1, bookDescriptionId);
			s.setString(2, fileName);
			s.setInt(3, size);
			s.setString(4, extention);
			s.executeUpdate();
			s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new Exception(e);
		}
		
		return ;
	}
	
	/**
     * count of electronic books
	 * @param filters 
     * @return count
     * @throws Exception
     * 
     */
	public static int getCountOfElectronicBooks(int usetCategoryId, Filter filter) throws Exception{
		String filterStr = SQL.getSqlFilter(filter);
		String catSql = "";
		if (usetCategoryId == 1)
			catSql = " where userCategoryId = " + usetCategoryId;
		int count = 0;
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			PreparedStatement s = con.prepareStatement("select count(*) as count from (Select * from view_electronic_books " + filterStr + ") t " + catSql);
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
     * List of electronic books for table with searching
	 * @param language 
     * @return list of users
     * @throws Exception
     * 
     */
	public static List<ElectronicBook> getElectronicBooksForTable(Pagination pagination, Filter filter, String language) throws Exception{
		String lang = " ";
		if (language.equals("ru"))
			lang = "_ru  ";
				
		String filterStr = SQL.getSqlFilter(filter);
		
		List<ElectronicBook> electronicBooks = new ArrayList<ElectronicBook>();
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			String sql = "SELECT * FROM " +
						"(SELECT *,row_number() over(order by " + pagination.getSidx() + " " + pagination.getSord() + ") as row_num " + 
						"FROM view_electronic_books" + lang + filterStr + ") as a " +
						"WHERE row_num BETWEEN ? AND ?"; 
			PreparedStatement s = con.prepareStatement(sql);
			s.setInt(1, pagination.getStart());
			s.setInt(2, pagination.getEnd());
			ResultSet rs = s.executeQuery();
			while (rs.next())
				electronicBooks.add(extractElectronicBook(rs));
			s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return electronicBooks;
	}

		/**
	     * delete electronic  book
	     * @param id of real book
	     * @throws Exception
	     * 
	     */
		
		public static boolean deleteElectronicBook(int id) throws Exception{
			boolean result = true;
			try {
				DBConnectionPool pool = DBConnectionPool.getConnPool();
				Connection con=pool.getConnection();
				
				PreparedStatement s = con.prepareStatement("select " + Constants.DB_DBO + ".can_delete_electronic_book(?) as res");
				s.setInt(1, id);
				ResultSet rs = s.executeQuery();
				rs.next();
				String res = rs.getString("res");
				if (res.equals("1")){
				
					s = con.prepareStatement("select " + Constants.DB_DBO + ".get_file_name(?) as res");
					s.setInt(1, id);
					rs = s.executeQuery();
					rs.next();
					String fileName = rs.getString("res");
				
					/*delete file*/
					if (fileName != null){
						new File (Constants.PATH_FILES + "\\" + fileName).delete();
					}
				
				
					String sql = "exec delete_electronic_book ?"; 
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
	     * Get electronic book by id
	     * @return electronic book
	     * @throws Exception
	     * 
	     */
		public static ElectronicBook getElectronicBook(int electronicBookId) throws Exception{
			
			ElectronicBook electronicBook = new ElectronicBook();
			try {
				DBConnectionPool pool = DBConnectionPool.getConnPool();
				Connection con=pool.getConnection();
				String sql = "SELECT * FROM view_electronic_books " +
							" WHERE id = ? "; 
				PreparedStatement s = con.prepareStatement(sql);
				s.setInt(1, electronicBookId);
				
				ResultSet rs = s.executeQuery();
				while (rs.next())
					electronicBook = extractElectronicBook(rs);
				s.close();
				pool.releaseConnection(con);
			} catch (SQLException e) {
				throw new Exception(e);
						
			}
			return electronicBook;
		}
		
		/**
	     * get user categories id for electronic book
	     * @param electronic book id 
	      * @return user categories ids
	     */	
		public static List<Integer> getUserCategoriesId(int electronicBookId) throws Exception{
			List<Integer> result = new ArrayList<Integer>();
				
			try {
				DBConnectionPool pool = DBConnectionPool.getConnPool();
				Connection con=pool.getConnection();
				PreparedStatement s = con.prepareStatement("Select userCategoryId FROM privileges_electronic where electronicBookId = ?");
				s.setInt(1, electronicBookId);
				
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
	     * set user categories id for electronic book
	     * @param electronic book id 
	     * @param user categories ids 
	     * 
	     */	
		public static void setUserCategoriesId(int electronicBookId,int[] categoryIds) throws Exception{
			
				
			try {
				DBConnectionPool pool = DBConnectionPool.getConnPool();
				Connection con=pool.getConnection();
				PreparedStatement s = con.prepareStatement("exec clear_electronic_privileges ?");
				s.setInt(1, electronicBookId);
				s.executeUpdate();
				
				for (int i = 0; i < categoryIds.length; i++  ){
					s = con.prepareStatement("exec add_electronic_privileges ?,?");
					s.setInt(1, electronicBookId);
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
	     * count of electronic books for user
		 * @param filter 
	     * @return count
	     * @throws Exception
	     * 
	     */
		public static int getCountOfElectronicBooksForUser(Filter filter,int userCategoryId) throws Exception{
			String filterStr = SQL.getSqlFilter(filter);
			int count = 0;
			try {
				DBConnectionPool pool = DBConnectionPool.getConnPool();
				Connection con=pool.getConnection();
				
				PreparedStatement s = con.prepareStatement("select count(*) as count from ( SELECT * FROM " + Constants.DB_DBO + ".electronic_books_for_user_category(?)"  + filterStr + ") t ");
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
	     * List of electronic books for table with searching
		 * @param language 
	     * @return list of users
	     * @throws Exception
	     * 
	     */
		public static List<ElectronicBook> getElectronicBooksForTableByUserCategory(Pagination pagination, Filter filter, String language,int userCategoryId) throws Exception{
			String lang = " ";
			if (language.equals("ru"))
				lang = "_ru  ";
			
			
			String filterStr = SQL.getSqlFilter(filter);
			
			List<ElectronicBook> electronicBooks = new ArrayList<ElectronicBook>();
			try {
				DBConnectionPool pool = DBConnectionPool.getConnPool();
				Connection con=pool.getConnection();
				String sql = "SELECT * FROM " +
							"(SELECT *,row_number() over(order by " + pagination.getSidx() + " " + pagination.getSord() + ") as row_num " + 
							"FROM " + Constants.DB_DBO + ".electronic_books_for_user_category(?) " + lang + filterStr + ") as a " +
							"WHERE row_num BETWEEN ? AND ?"; 
				PreparedStatement s = con.prepareStatement(sql);
				s.setInt(1,userCategoryId);
				s.setInt(2, pagination.getStart());
				s.setInt(3, pagination.getEnd());
				ResultSet rs = s.executeQuery();
				while (rs.next())
					electronicBooks.add(extractElectronicBook(rs));
				s.close();
				pool.releaseConnection(con);
			} catch (SQLException e) {
				throw new Exception(e);
						
			}
			return electronicBooks;
		}
		
		
	}








