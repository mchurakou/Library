package com.mikalai.library.dao;

import com.mikalai.library.ajax_json.Filter;
import com.mikalai.library.beans.Department;
import com.mikalai.library.beans.Division;
import com.mikalai.library.utils.Constants;
import com.mikalai.library.utils.Pagination;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Action for work with divisions
 * 
 * @author Mikalai_Churakou
 */
@Repository
@Transactional
public class DivisionDAO extends GenericDAO{

	/**
     * count of divisions
	 * @param filters 
     * @return count
     * @throws Exception
     * 
     */
	public int getCountOfDivisions(Filter filter) throws Exception{
		String filterStr = SQL.getSqlFilter(filter);
		int count = 0;
		try {Connection con = getConnection();
			PreparedStatement s = con.prepareStatement("Select count(*) as count from divisions " +  filterStr);
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
     * List of divisions for table with searching
     * @return list of divisions
     * @throws Exception
     * 
     */
	public List<Division> getDivisionsForTable(Pagination pagination, Filter filter) throws Exception{
				
		String filterStr = SQL.getSqlFilter(filter);
		List<Division> divisions = new ArrayList<Division>();
		try{Connection con = getConnection();
			String sql = "SELECT * FROM " +
						"(SELECT *,row_number() over(order by " + pagination.getSidx() + " " + pagination.getSord() + ") as row_num " + 
						"FROM divisions" + filterStr + ") as a " +
						"WHERE row_num BETWEEN ? AND ?";
			PreparedStatement s = con.prepareStatement(sql);
			s.setInt(1, pagination.getStart());
			s.setInt(2, pagination.getEnd());
			ResultSet rs = s.executeQuery();
			while (rs.next()){
				Division division = new Division();
				division.setId(rs.getInt(Constants.FIELD_ID));
				division.setName(rs.getString(Constants.FIELD_NAME));
				division.setDepartment(new Department(rs.getInt(Constants.FIELD_DEPARTMENT_ID)));
				divisions.add(division);
			}
			s.close();

		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return divisions;
	}
	
	/**
     * add division
     * @param name, name_ru, department id
     * @throws Exception
     * 
     */	
	public boolean addDivision( String name,String name_ru, int departmentId) throws Exception  {
		
		boolean result = true;
		try {Connection con = getConnection();
			PreparedStatement s = con.prepareStatement("select " + Constants.DB_DBO + ".exist_division(?,?,?) as res");
			s.setInt(1, 0);
			s.setString(2, name);
			s.setString(3, name_ru);
			ResultSet rs=s.executeQuery();
			rs.next();
			String res = rs.getString("res");
			if (res.equals("0")){
				s = con.prepareStatement("exec add_division  ?, ?,?");
				s.setString(1, name);
				s.setString(2, name_ru);
				s.setInt(3, departmentId);
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
     * delete division
     * @param id of division
     * @throws Exception
     * 
     */
		
	public boolean deleteDivision(int id) throws Exception{
		boolean result = true;
		try {Connection con = getConnection();
			PreparedStatement s = con.prepareStatement("select " + Constants.DB_DBO + ".can_delete_division(?) as res");
			s.setInt(1, id);
			ResultSet rs = s.executeQuery();
			rs.next();
			String res = rs.getString("res");
			if (res.equals("1")){
				String sql = "exec delete_division ?"; 
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
     * edit division
     * @param  id, name, name_ru, department id
     * @throws Exception
     * 
     */	
	public boolean editDivision(int id, String name,String name_ru, int departmentId) throws Exception  {
		boolean result = true;
		try {Connection con = getConnection();
			PreparedStatement s = con.prepareStatement("select " + Constants.DB_DBO + ".exist_division(?,?,?) as res");
			s.setInt(1, id);
			s.setString(2, name);
			s.setString(3, name_ru);
			ResultSet rs=s.executeQuery();
			rs.next();
			String res = rs.getString("res");
			if (res.equals("0")){
				s = con.prepareStatement("exec edit_division ?, ?, ?,?");
				s.setInt(1, id);
				s.setString(2, name);
				s.setString(3, name_ru);
				s.setInt(4, departmentId);
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
     * List of divisions by department id
     * @return list of divisions
     * @throws Exception
     * 
     */
	public List<Division> getDivisionsByDepartmentId(int departmentId) throws Exception{
		List<Division> divisions = new ArrayList<Division>();
		try {Connection con = getConnection();
			String sql = "SELECT * from view_divisions where departmentId = ?";
			PreparedStatement s = con.prepareStatement(sql);
			s.setInt(1, departmentId);
			ResultSet rs = s.executeQuery();
			while (rs.next()){
				Division division = new Division();
				division.setId(rs.getInt(Constants.FIELD_ID));
				division.setName(rs.getString(Constants.FIELD_NAME));
				division.setDepartment(new Department(rs.getInt(Constants.FIELD_DEPARTMENT_ID)));
				divisions.add(division);
			}
			s.close();

		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return divisions;
	}
	
	/**
     * List of divisions 
     * @return list of divisions
     * @throws Exception
     * 
     */
	public List<Division> getDivisions() throws Exception{

		List<Division> divisions = new ArrayList<Division>();
		try {Connection con = getConnection();
			String sql = "SELECT * from view_divisions";
			PreparedStatement s = con.prepareStatement(sql);
			
			ResultSet rs = s.executeQuery();
			while (rs.next()){
				Division division = new Division();
				division.setId(rs.getInt(Constants.FIELD_ID));
				division.setName(rs.getString(Constants.FIELD_NAME));
				division.setDepartment(new Department(rs.getInt(Constants.FIELD_DEPARTMENT_ID)));
				divisions.add(division);
			}
			s.close();

		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return divisions;
	}
	
}
