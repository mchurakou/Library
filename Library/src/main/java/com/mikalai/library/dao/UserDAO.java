package com.mikalai.library.dao;

import com.mikalai.library.beans.Division;
import com.mikalai.library.beans.SimpleBean;
import com.mikalai.library.beans.User;
import com.mikalai.library.beans.dictionary.Role;
import com.mikalai.library.utils.Constants;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
@Repository
public class UserDAO extends GenericDAO {

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

