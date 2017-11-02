package com.mikalai.library.dao;

import com.mikalai.library.ajax_json.Filter;
import com.mikalai.library.beans.SimpleBean;
import com.mikalai.library.utils.Constants;
import com.mikalai.library.utils.Pagination;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

;
/**
 * Action for work with user categories
 * 
 * @author Mikalai_Churakou
 */

@Repository
public class UserCategoryDAO extends GenericDAO {
	
	/**
     * count of user categories
	 * @param filters 
     * @return count
     * @throws Exception
     * 
     */
	public int getCountOfUserCategories(Filter filter) throws Exception{
		String filterStr = SQL.getSqlFilter(filter);
		int count = 0;
		try {Connection con = getConnection();
			PreparedStatement s = con.prepareStatement("Select count(*) as count from user_categories " +  filterStr);
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
     * List of user categories for table with searching
     * @return list of user categories
     * @throws Exception
     * 
     */
	public  List<SimpleBean> getUserCategoriesForTable(Pagination pagination, Filter filter) throws Exception{
				
		String filterStr = SQL.getSqlFilter(filter);
		List<SimpleBean> userCategories = new ArrayList<SimpleBean>();
		try {Connection con = getConnection();
			String sql = "SELECT * FROM " +
						"(SELECT *,row_number() over(order by " + pagination.getSidx() + " " + pagination.getSord() + ") as row_num " + 
						"FROM user_categories" + filterStr + ") as a " +
						"WHERE row_num BETWEEN ? AND ?";
			PreparedStatement s = con.prepareStatement(sql);
			s.setInt(1, pagination.getStart());
			s.setInt(2, pagination.getEnd());
			ResultSet rs = s.executeQuery();
			while (rs.next()){
				SimpleBean userCategory = new SimpleBean();
				userCategory.setId(rs.getInt(Constants.FIELD_ID));
				userCategory.setName(rs.getString(Constants.FIELD_NAME));
				userCategory.setName_ru(rs.getString(Constants.FIELD_NAME_RU));
				userCategories.add(userCategory);
			}
			s.close();

		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return userCategories;
	}
	
	/**
     * add user category
     * @param name, name_ru
     * @throws Exception
     * 
     */	
	public  boolean addUserCategory( String name,String name_ru) throws Exception  {
		boolean result = true;
		try {Connection con = getConnection();
			PreparedStatement s = con.prepareStatement("select " + Constants.DB_DBO + ".exist_user_category(?,?,?) as res");
			s.setInt(1, 0);
			s.setString(2, name);
			s.setString(3, name_ru);
			ResultSet rs=s.executeQuery();
			rs.next();
			String res = rs.getString("res");
			if (res.equals("0")){
				s = con.prepareStatement("exec add_user_category  ?, ?");
				s.setString(1, name);
				s.setString(2, name_ru);
				s.executeUpdate();
			}
			else
				result = false;
			s.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return result;
		
	}
	
	/**
     * delete user category
     * @param id of user category
     * @throws Exception
     * 
     */
		
	public  boolean deleteUserCategory(int id) throws Exception{
		boolean result = true;
		try {Connection con = getConnection();
			PreparedStatement s = con.prepareStatement("select " + Constants.DB_DBO + ".can_delete_user_category(?) as res");
			s.setInt(1, id);
			ResultSet rs = s.executeQuery();
			rs.next();
			String res = rs.getString("res");
			if (res.equals("1")){
				String sql = "exec delete_user_category ?"; 
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
     * edit user category
     * @param  id, name, name_ru
     * @throws Exception
     * 
     */	
	public  boolean editUserCategory(int id, String name,String name_ru) throws Exception  {
		boolean result = true;
		try {Connection con = getConnection();
			PreparedStatement s = con.prepareStatement("select " + Constants.DB_DBO + ".exist_user_category(?,?,?) as res");
			s.setInt(1, id);
			s.setString(2, name);
			s.setString(3, name_ru);
			ResultSet rs=s.executeQuery();
			rs.next();
			String res = rs.getString("res");
			if (res.equals("0")){
				s = con.prepareStatement("exec edit_user_category ?, ?, ?");
				s.setInt(1, id);
				s.setString(2, name);
				s.setString(3, name_ru);
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
     * List of user categories 
     * @return list of user categories
     * @throws Exception
     * 
     */
	public  List<SimpleBean> getUserCategories() throws Exception{

		
		List<SimpleBean> userCategories = new ArrayList<SimpleBean>();
		try {Connection con = getConnection();
			String sql = "SELECT * FROM view_user_categories";
			PreparedStatement s = con.prepareStatement(sql);
			
			ResultSet rs = s.executeQuery();
			while (rs.next()){
				SimpleBean userCategory = new SimpleBean();
				userCategory.setId(rs.getInt(Constants.FIELD_ID));
				userCategory.setName(rs.getString(Constants.FIELD_NAME));
				userCategories.add(userCategory);
			}
			s.close();

		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return userCategories;
	}

}
