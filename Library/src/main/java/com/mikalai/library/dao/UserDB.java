package com.mikalai.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mikalai.library.dao.DBConnectionPool;
import com.mikalai.library.exceptions.DBException;
import com.mikalai.library.ajax_json.Filter;
import com.mikalai.library.beans.SimpleBean;
import com.mikalai.library.beans.User;
import com.mikalai.library.utils.Constants;
import com.mikalai.library.utils.Pagination;
/**
 * Class for work with users
 * 
 * @author Mikalai_Churakou
 */
public class UserDB {
	/**
     * Method extract User from ResultSet
     * @param ResultSet
     * @return User
     */	
	private static User extractUser(ResultSet rs) throws SQLException{
		User user = new User();
		user.setId(rs.getInt(Constants.FIELD_ID));
		user.setLogin(rs.getString(Constants.FIELD_LOGIN));
		user.setFirstName(rs.getString(Constants.FIELD_FIRST_NAME));
		user.setSecondName(rs.getString(Constants.FIELD_SECOND_NAME));
		user.setEmail(rs.getString(Constants.FIELD_EMAIL));
		SimpleBean role =  new SimpleBean();
		role.setId(rs.getInt(Constants.FIELD_ROLE_ID));
		role.setName(rs.getString(Constants.FIELD_ROLE));
		role.setTitle(rs.getString(Constants.FIELD_ROLE_TITLE));
		user.setRole(role);
		SimpleBean category =  new SimpleBean();
		category.setId(rs.getInt(Constants.FIELD_CATEGORY_ID));
		category.setName(rs.getString(Constants.FIELD_CATEGORY));
		user.setCategory(category);
		user.setHaveDebt(rs.getBoolean(Constants.FIELD_HAVE_DEBT));
		user.setDivisionId(rs.getInt(Constants.FIELD_DIVISION_ID));
		user.setDepartmentId(rs.getInt(Constants.FIELD_DEPARTMENT_ID));
		
		return user;
	}
	
	/**
     * Method add new user
     * @param new user
     * @return successful or unsuccessful operation
     */		
	public  static boolean add(User user) throws DBException{
		boolean result = true;
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			PreparedStatement s = con.prepareStatement("select " + Constants.DB_DBO + ".exist_user(?) as res");
			s.setString(1, user.getLogin());
			ResultSet rs = s.executeQuery();
			rs.next();
			String res = rs.getString("res");
			if (res.equals("0")){
				s = con.prepareStatement("exec add_user ?, ?, ?, ?, ?,?");
				s.setString(1, user.getLogin());
				s.setString(2, user.getPassword());
				s.setString(3, user.getFirstName());
				s.setString(4, user.getSecondName());
				s.setString(5, user.getEmail());
				s.setInt(6, user.getDivisionId());
				s.executeUpdate();
			}
			else
				result=false;
			s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			
			throw new DBException(e);
		}
		
		return result;
	}
	
	/**
     * Method return user by login and password
     * @param login 
     * @param password
	 * @param string 
     * @return user with such login and password
     */	
	public static User getUser(String login, String password, String language) throws DBException{
		String lang = " ";
		if (language.equals("ru"))
			lang = "_ru  ";
		
		User user = null;
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			PreparedStatement s = con.prepareStatement("Select * " +
													   "from view_users_all" + lang +
													   "where "+
													   "login=? and "+
													   "password=?");
			s.setString(1, login);
			s.setString(2, password);
			ResultSet rs=s.executeQuery();
			if (rs.next())
				user = extractUser(rs);
		    s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new DBException(e);
					
		}
		return user;
	}
	
	/**
     * Change profile
     * @param user
     * @throws DBException 
     * 
     */	
	public static void changeProfile(User user) throws DBException  {
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con = pool.getConnection();
			PreparedStatement s;
			s = con.prepareStatement("exec edit_user ?, ?, ?, ?,?");
			s.setInt(1, user.getId());
			s.setString(2, user.getFirstName());
			s.setString(3, user.getSecondName());
			s.setString(4, user.getEmail());
			s.setInt(5, user.getDivisionId());
			s.executeUpdate();
			s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new DBException(e);
		}
		
	}
	
	
	
	
	/**
     * List of users for table with searching
     * @return list of users
     * @throws DBException 
     * 
     */
	public static List<User> getUsersForTable(Pagination pagination, Filter filter,String language) throws DBException{
		String lang = " ";
		if (language.equals("ru"))
			lang = "_ru  ";
		
		String filterStr = SQL.getSqlFilter(filter);
		List<User> users = new ArrayList<User>();
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			String sql = "SELECT * FROM " +
						"(SELECT *,row_number() over(order by " + pagination.getSidx() + " " + pagination.getSord() + ") as row_num " + 
						"FROM view_users" + lang + filterStr + ") as a " +
						"WHERE row_num BETWEEN ? AND ?";
			PreparedStatement s = con.prepareStatement(sql);
			s.setInt(1, pagination.getStart());
			s.setInt(2, pagination.getEnd());
			ResultSet rs = s.executeQuery();
			while (rs.next())
				users.add(extractUser(rs));
			s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new DBException(e);
					
		}
		return users;
	}
	
	/**
     * List of active users for table with searching
     * @return list of users
     * @throws DBException 
     * 
     */
	public static List<User> getActiveUsersForTable(Pagination pagination, Filter filter) throws DBException{
		String filterStr = SQL.getSqlFilter(filter);
		List<User> users = new ArrayList<User>();
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
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
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new DBException(e);
					
		}
		return users;
	}
	
	
	/**
     * count of users
	 * @param filters 
     * @return count
     * @throws DBException 
     * 
     */
	public static int getCountOfUsers(Filter filter) throws DBException{
		String filterStr = SQL.getSqlFilter(filter);
		int count = 0;
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			PreparedStatement s = con.prepareStatement("Select count(*) as count from view_users " +  filterStr);
			ResultSet rs = s.executeQuery();
			if (rs.next())
				count = rs.getInt("count");
				
			s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new DBException(e);
					
		}
		return count;
	}
	
	/**
     * count of users
     * @return count
     * @throws DBException 
     * 
     */
	public static int getCountOfActiveUsers() throws DBException{
		int count = 0;
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			PreparedStatement s = con.prepareStatement("Select count(*) as count from view_active_users ");
			ResultSet rs = s.executeQuery();
			if (rs.next())
				count = rs.getInt("count");
				
			s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new DBException(e);
					
		}
		return count;
	}
	
	
	/**
     * delete user
     * @param id of user
     * @throws DBException 
     * 
     */
		
	public static boolean deleteUser(int id) throws DBException{
		boolean result = true;
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
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
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new DBException(e);
					
		}
		return result;
		
	}
	
	
	/**
     * edit user information
	 * @param divisionId 
     * @param user id, first name, second name, email, role id, category id
     * @throws DBException 
     * 
     */	
	public static void editUser(int id, String firstName,String secondName,String email, int roleId, int categoryId, int divisionId) throws DBException  {
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con = pool.getConnection();
			PreparedStatement s;
			s = con.prepareStatement("exec admin_edit_user ?, ?, ?, ?, ?, ?,?");
			s.setInt(1, id);
			s.setString(2, firstName);
			s.setString(3, secondName);
			s.setString(4, email);
			s.setInt(5, roleId);
			s.setInt(6, categoryId);
			s.setInt(7, divisionId);
			s.executeUpdate();
			s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new DBException(e);
		}
		
	}
	
	
	
	
	
	
	
	/**
     * Get list of user's categories
	 * @param  
     * @return list of user's categories
     * @throws DBException 
     * 
     */
	public static List<SimpleBean> getUserCategories(String language) throws DBException{
		String lang = " ";
		if (language.equals("ru"))
			lang = "_ru  ";
		List<SimpleBean> categories = new ArrayList<SimpleBean>();
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			String sql = "SELECT id, name FROM view_user_categories" + lang; 
			PreparedStatement s = con.prepareStatement(sql);
			ResultSet rs = s.executeQuery();
			while (rs.next()){
				SimpleBean category = new SimpleBean();
				category.setId(rs.getInt(Constants.FIELD_ID));
				category.setName(rs.getString(Constants.FIELD_NAME));
				categories.add(category);
			}
			s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new DBException(e);
					
		}
		return categories;
	}
	
	
	/**
     * Get list of user's roles
	 * @param  
     * @return list of user's roles
     * @throws DBException 
     * 
     */
	public static List<SimpleBean> getUserRoles(String language) throws DBException{
		String lang = " ";
		if (language.equals("ru"))
			lang = "_ru  ";
		List<SimpleBean> roles = new ArrayList<SimpleBean>();
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			String sql = "SELECT id, name FROM view_user_roles" + lang; 
			PreparedStatement s = con.prepareStatement(sql);
			ResultSet rs = s.executeQuery();
			while (rs.next()){
				SimpleBean role = new SimpleBean();
				role.setId(rs.getInt(Constants.FIELD_ID));
				role.setName(rs.getString(Constants.FIELD_NAME));
				roles.add(role);
			}
			s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new DBException(e);
					
		}
		return roles;
	}
	
	
	
	
	
	
	
	
}

