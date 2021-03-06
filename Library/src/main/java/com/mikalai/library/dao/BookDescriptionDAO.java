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
import com.mikalai.library.beans.BookDescription;
import com.mikalai.library.beans.SimpleBean;


;

/**
 * Class for work with book descriptions
 * 
 * @author Mikalai_Churakou
 */
public class BookDescriptionDAO extends GenericDAO {
	/**
     * Method extract BookDescription from ResultSet
     * @param ResultSet
     * @return User
     */	
	private static BookDescription extractBookDescription(ResultSet rs) throws SQLException{
		BookDescription bookDescription = new BookDescription();
		bookDescription.setId(rs.getInt(Constants.FIELD_ID));
		bookDescription.setName(rs.getString(Constants.FIELD_NAME));
		bookDescription.setAuthor(rs.getString(Constants.FIELD_AUTHOR));
		SimpleBean bookCategory =  new SimpleBean();
		bookCategory.setId(rs.getInt(Constants.FIELD_BOOK_CATEGORIES_ID));
		bookCategory.setName(rs.getString(Constants.FIELD_BOOK_CATEGORY));
		bookDescription.setBookCategory(bookCategory);
		bookDescription.setPublicationPlace(rs.getString(Constants.FIELD_PUBLICATION_PLACE));
		bookDescription.setPublicationYear(rs.getInt(Constants.FIELD_PUBLICATION_YEAR));
		bookDescription.setSize(rs.getInt(Constants.FIELD_SIZE));
		SimpleBean language =  new SimpleBean();
		language.setId(rs.getInt(Constants.FIELD_LANGUAGE_ID));
		language.setName(rs.getString(Constants.FIELD_LANGUAGE));
		bookDescription.setLanguage(language);
		return bookDescription;
	}
	
	
	
	/**
     * count of book descriptions
	 * @param filter 
     * @return count
     * @throws Exception
     * 
     */
	public int getCountOfBookDescriptions(Filter filter) throws Exception{
		String filterStr = SQL.getSqlFilter(filter);
		int count = 0;
		try {Connection con = getConnection();
			PreparedStatement s = con.prepareStatement("Select count(*) as count from view_book_descriptions " +  filterStr);
			ResultSet rs = s.executeQuery();
			if (rs.next())
				count = rs.getInt("count");
				
			s.close();

		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return count;
	}
	
	/**
     * List of book descriptions for table with searching
	 * @param language 
     * @return list of users
     * @throws Exception
     * 
     */
	public List<BookDescription> getBookDescriptionsForTable(Pagination pagination, Filter filter) throws Exception{
		String filterStr = SQL.getSqlFilter(filter);
		List<BookDescription> bookDescriptions = new ArrayList<BookDescription>();
		try {Connection con = getConnection();
			String sql = "SELECT * FROM " +
						"(SELECT *,row_number() over(order by " + pagination.getSidx() + " " + pagination.getSord() + ") as row_num " + 
						"FROM view_book_descriptions " + filterStr + ") as a " +
						"WHERE row_num BETWEEN ? AND ?"; 
			PreparedStatement s = con.prepareStatement(sql);
			s.setInt(1, pagination.getStart());
			s.setInt(2, pagination.getEnd());
			ResultSet rs = s.executeQuery();
			while (rs.next())
				bookDescriptions.add(extractBookDescription(rs));
			s.close();

		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return bookDescriptions;
	}
	
	/**
     * Get list of book's categories
	 * @param language 
     * @return list of book's categories
     * @throws Exception
     * 
     */
	public List<SimpleBean> getBookCategories() throws Exception{
		List<SimpleBean> bookCategories = new ArrayList<SimpleBean>();
		try{Connection con = getConnection();
			String sql = "SELECT id, name FROM view_book_categories";
			PreparedStatement s = con.prepareStatement(sql);
			ResultSet rs = s.executeQuery();
			while (rs.next()){
				SimpleBean bookCategory = new SimpleBean();
				bookCategory.setId(rs.getInt(Constants.FIELD_ID));
				bookCategory.setName(rs.getString(Constants.FIELD_NAME));
				bookCategories.add(bookCategory);
			}
			s.close();

		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return bookCategories;
	}
	
	/**
     * Get list of languages
	 * @param language 
     * @return list of languages
     * @throws Exception
     * 
     */
	public List<SimpleBean> getLanguages() throws Exception{
		List<SimpleBean> languages = new ArrayList<SimpleBean>();
		try {Connection con = getConnection();
			String sql = "SELECT id, name FROM view_languages";
			PreparedStatement s = con.prepareStatement(sql);
			ResultSet rs = s.executeQuery();
			while (rs.next()){
				SimpleBean bean = new SimpleBean();
				bean.setId(rs.getInt(Constants.FIELD_ID));
				bean.setName(rs.getString(Constants.FIELD_NAME));
				languages.add(bean);
			}
			s.close();

		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return languages;
	}
	
	
	
	/**
     * delete book description
     * @param id of book description
     * @throws Exception
     * 
     */
	public boolean deleteBookDescription(int id) throws Exception{
		boolean result = true;
		try{Connection con = getConnection();
			PreparedStatement s = con.prepareStatement("select " + Constants.DB_DBO + ".can_delete_book_description(?) as res");
			s.setInt(1, id);
			ResultSet rs = s.executeQuery();
			rs.next();
			String res = rs.getString("res");
			
			if (res.equals("1")){
				String sql = "exec delete_book_description ?"; 
				s = con.prepareStatement(sql);
				s.setInt(1, id);
				s.executeUpdate();
			}
			else
				result = false;
			s.close();

		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return result;
		
	}
	
	
	/**
     * edit book description
     * @param id, name, author, book category id, publication place, publication year, size, language id, user category id
     * @throws Exception
     * 
     */	
	public void editBookDescription(int id, String name,String author,int bookCategoryId, String publicationPlace, int publicationYear, int size, int languageId) throws Exception  {
		try {Connection con = getConnection();
			PreparedStatement s;
			s = con.prepareStatement("exec edit_book_description ?, ?, ?, ?, ?, ?, ?, ?");
			s.setInt(1, id);
			s.setString(2, name);
			s.setString(3, author);
			s.setInt(4, bookCategoryId);
			s.setString(5, publicationPlace);
			s.setInt(6, publicationYear);
			s.setInt(7, size);
			s.setInt(8, languageId);
			s.executeUpdate();
			s.close();

		} catch (SQLException e) {
			throw new Exception(e);
		}
		
	}
	
	
	/**
     * add book description
     * @param id, name, author, book category id, publication place, publication year, size, language id, user category id
     * @throws Exception
     * 
     */	
	public void addBookDescription( String name,String author,int bookCategoryId, String publicationPlace, int publicationYear, int size, int languageId) throws Exception  {
		try {Connection con = getConnection();
			PreparedStatement s;
			s = con.prepareStatement("exec add_book_description  ?, ?, ?, ?, ?, ?, ?");
			s.setString(1, name);
			s.setString(2, author);
			s.setInt(3, bookCategoryId);
			s.setString(4, publicationPlace);
			s.setInt(5, publicationYear);
			s.setInt(6, size);
			s.setInt(7, languageId);
			s.executeUpdate();
			s.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		
	}

}
