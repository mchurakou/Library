package com.mikalai.library.dao;

import com.mikalai.library.ajax_json.Filter;
import com.mikalai.library.beans.Division;
import com.mikalai.library.beans.SimpleBean;
import com.mikalai.library.beans.User;
import com.mikalai.library.beans.dictionary.Role;
import com.mikalai.library.utils.Constants;
import com.mikalai.library.utils.Pagination;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Class for work with users
 * 
 * @author Mikalai_Churakou
 */
public class UserDAO extends GenericDAO {

	/**
     * Method extract User from ResultSet
     * @param ResultSet
     * @return User
     */	
	private static User extractUser(ResultSet rs) throws SQLException{
		User user = new User();
		user.setId(rs.getLong(Constants.FIELD_ID));
		user.setLogin(rs.getString(Constants.FIELD_LOGIN));
		user.setFirstName(rs.getString(Constants.FIELD_FIRST_NAME));
		user.setSecondName(rs.getString(Constants.FIELD_SECOND_NAME));
		user.setEmail(rs.getString(Constants.FIELD_EMAIL));
//		user.setCategory(Category.getById(rs.getInt(rs.getInt(Constants.FIELD_CATEGORY_ID))));
		user.setHaveDebt(rs.getBoolean(Constants.FIELD_HAVE_DEBT));
		user.setDivision(new Division(rs.getInt(Constants.FIELD_DIVISION_ID)));

		return user;
	}
	
	/**
     * Change profile
     * @param user
     * @throws Exception
     * 
     */	
	public void changeProfile(User user) throws Exception  {
		try {Connection con = getConnection();
			PreparedStatement s;
			s = con.prepareStatement("exec edit_user ?, ?, ?, ?,?");
			s.setLong(1, user.getId());
			s.setString(2, user.getFirstName());
			s.setString(3, user.getSecondName());
			s.setString(4, user.getEmail());
			s.setInt(5, (int) user.getDivision().getId());
			s.executeUpdate();
			s.close();

		} catch (SQLException e) {
			throw new Exception(e);
		}
		
	}


	
	/**
     * List of active users for table with searching
     * @return list of users
     * @throws Exception
     * 
     */
	public List<User> getActiveUsersForTable(Pagination pagination, Filter filter) throws Exception{
		String filterStr = SQL.getSqlFilter(filter);
		List<User> users = new ArrayList<User>();
		try {Connection con = getConnection();
			String sql = "SELECT * FROM " +
						"(SELECT *,row_number() over(order by " + pagination.getSidx() + " " + pagination.getSord() + ") as row_num " + 
						"FROM view_active_users " + filterStr + ") as a " +
						"WHERE row_num BETWEEN ? AND ?"; 
			PreparedStatement s = con.prepareStatement(sql);
			s.setInt(1, pagination.getStart());
			s.setInt(2, pagination.getEnd());
			ResultSet rs = s.executeQuery();
			while (rs.next())
				users.add(extractUser(rs));
			s.close();

		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return users;
	}
	

	
	/**
     * count of users
     * @return count
     * @throws Exception
     * 
     */
	public int getCountOfActiveUsers() throws Exception{
		int count = 0;
		try {Connection con = getConnection();
			PreparedStatement s = con.prepareStatement("Select count(*) as count from view_active_users ");
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
     * delete user
     * @param id of user
     * @throws Exception
     * 
     */
		
	public boolean deleteUser(int id) throws Exception{
		boolean result = true;
		try {Connection con = getConnection();
			PreparedStatement s = con.prepareStatement("select " + Constants.DB_DBO + ".can_delete_user(?) as res");
			s.setInt(1, id);
			ResultSet rs = s.executeQuery();
			rs.next();
			String res = rs.getString("res");
			if (res.equals("1")){
				String sql = "exec delete_user ?"; 
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
     * edit user information
	 * @param divisionId 
     * @param user id, first name, second name, email, role id, category id
     * @throws Exception
     * 
     */	
	public void editUser(int id, String firstName,String secondName,String email, String roleId, int categoryId, int divisionId) throws Exception  {
		try {Connection con = getConnection();
			PreparedStatement s;
			s = con.prepareStatement("exec admin_edit_user ?, ?, ?, ?, ?, ?,?");
			s.setInt(1, id);
			s.setString(2, firstName);
			s.setString(3, secondName);
			s.setString(4, email);
			s.setString(5, roleId);
			s.setInt(6, categoryId);
			s.setInt(7, divisionId);
			s.executeUpdate();
			s.close();

		} catch (SQLException e) {
			throw new Exception(e);
		}
		
	}
	
	
	
	
	
	
	
	/**
     * Get list of user's categories
	 * @param  
     * @return list of user's categories
     * @throws Exception
     * 
     */
	public List<SimpleBean> getUserCategories() throws Exception{
		List<SimpleBean> categories = new ArrayList<SimpleBean>();
		try {Connection con = getConnection();
			String sql = "SELECT id, name FROM view_user_categories";
			PreparedStatement s = con.prepareStatement(sql);
			ResultSet rs = s.executeQuery();
			while (rs.next()){
				SimpleBean category = new SimpleBean();
				category.setId(rs.getInt(Constants.FIELD_ID));
				category.setName(rs.getString(Constants.FIELD_NAME));
				categories.add(category);
			}
			s.close();

		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return categories;
	}
	
	
	/**
     * Get list of user's roles
	 * @param  
     * @return list of user's roles
     * @throws Exception
     * 
     */
	public List<SimpleBean> getUserRoles() throws Exception{
		List<SimpleBean> roles = new ArrayList<SimpleBean>();
		for(Role r: Role.values()){
			roles.add(new SimpleBean(r.toString()));
		}
		return roles;
	}
	
	
	
	
	
	
	
	
}

