package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.Constants;
import utils.Pagination;
import beans.SimpleBean;


import ajax_json.Filter;
import exceptions.DBException;
/**
 * Action for work with departments
 * 
 * @author Mikalai_Churakou
 */
public class DepartmentDB {
	
	/**
     * count of departments
	 * @param filters 
     * @return count
     * @throws DBException 
     * 
     */
	public static int getCountOfDepartments(Filter filter) throws DBException{
		String filterStr = SQL.getSqlFilter(filter);
		int count = 0;
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			PreparedStatement s = con.prepareStatement("Select count(*) as count from departments " +  filterStr);
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
     * List of departments for table with searching
     * @return list of departments
     * @throws DBException 
     * 
     */
	public static List<SimpleBean> getDepartmentsForTable(Pagination pagination, Filter filter) throws DBException{
				
		String filterStr = SQL.getSqlFilter(filter);
		List<SimpleBean> departments = new ArrayList<SimpleBean>();
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			String sql = "SELECT * FROM " +
						"(SELECT *,row_number() over(order by " + pagination.getSidx() + " " + pagination.getSord() + ") as row_num " + 
						"FROM departments" + filterStr + ") as a " +
						"WHERE row_num BETWEEN ? AND ?";
			PreparedStatement s = con.prepareStatement(sql);
			s.setInt(1, pagination.getStart());
			s.setInt(2, pagination.getEnd());
			ResultSet rs = s.executeQuery();
			while (rs.next()){
				SimpleBean department = new SimpleBean();
			    department.setId(rs.getInt(Constants.FIELD_ID));
			    department.setName(rs.getString(Constants.FIELD_NAME));
			    department.setName_ru(rs.getString(Constants.FIELD_NAME_RU));
				departments.add(department);
			}
			s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new DBException(e);
					
		}
		return departments;
	}

	/**
     * delete department
     * @param id of department
     * @throws DBException 
     * 
     */
		
	public static boolean deleteDepartment(int id) throws DBException{
		boolean result = true;
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			PreparedStatement s = con.prepareStatement("select " + Constants.DB_DBO + ".can_delete_department(?) as res");
			s.setInt(1, id);
			ResultSet rs = s.executeQuery();
			rs.next();
			String res = rs.getString("res");
			if (res.equals("1")){
				String sql = "exec delete_department ?"; 
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
     * edit department
     * @param  id, name, name_ru
     * @throws DBException 
     * 
     */	
	public static boolean editDepartment(int id, String name,String name_ru) throws DBException  {
		boolean result = true;
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con = pool.getConnection();
			PreparedStatement s = con.prepareStatement("select " + Constants.DB_DBO + ".exist_department(?,?,?) as res");
			s.setInt(1, id);
			s.setString(2, name);
			s.setString(3, name_ru);
			ResultSet rs=s.executeQuery();
			rs.next();
			String res = rs.getString("res");
			if (res.equals("0")){
				s = con.prepareStatement("exec edit_department ?, ?, ?");
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
			throw new DBException(e);
		}
		return result;
		
	}
	
	/**
     * add department
     * @param name, name_ru
     * @throws DBException 
     * 
     */	
	public static boolean addDepartment( String name,String name_ru) throws DBException  {
		boolean result = true;
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con = pool.getConnection();
			PreparedStatement s = con.prepareStatement("select " + Constants.DB_DBO + ".exist_department(?,?,?) as res");
			s.setInt(1, 0);
			s.setString(2, name);
			s.setString(3, name_ru);
			ResultSet rs=s.executeQuery();
			rs.next();
			String res = rs.getString("res");
			if (res.equals("0")){
				s = con.prepareStatement("exec add_department  ?, ?");
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
			throw new DBException(e);
		}
		return result;
		
	}
	
	/**
     * Get list of departments
	 * @param language 
     * @return list of book's categories
     * @throws DBException 
     * 
     */
	public static List<SimpleBean> getDepartments(String language) throws DBException{
		String lang = " ";
		if (language.equals("ru"))
			lang = "_ru  ";
		List<SimpleBean> departments = new ArrayList<SimpleBean>();
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			String sql = "SELECT id, name FROM view_departments" + lang; 
			PreparedStatement s = con.prepareStatement(sql);
			ResultSet rs = s.executeQuery();
			while (rs.next()){
				SimpleBean department = new SimpleBean();
				department.setId(rs.getInt(Constants.FIELD_ID));
				department.setName(rs.getString(Constants.FIELD_NAME));
				departments.add(department);
			}
			s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new DBException(e);
					
		}
		return departments;
	}
}
