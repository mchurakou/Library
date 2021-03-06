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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
;

/**
 * Action for work with languages
 * 
 * @author Mikalai_Churakou
 */
@Repository
public class LanguageDAO extends GenericDAO{
	
	/**
     * count of languages
	 * @param filters 
     * @return count
     * @throws Exception
     * 
     */
	public int getCountOfLanguages(Filter filter) throws Exception{
		String filterStr = SQL.getSqlFilter(filter);
		int count = 0;
		try {Connection con = getConnection();
			PreparedStatement s = con.prepareStatement("Select count(*) as count from languages " +  filterStr);
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
     * List of languages for table with searching
     * @return list of languages
     * @throws Exception
     * 
     */
	public List<SimpleBean> getLanguagesForTable(Pagination pagination, Filter filter) throws Exception{
				
		String filterStr = SQL.getSqlFilter(filter);
		List<SimpleBean> languages = new ArrayList<SimpleBean>();
		try {Connection con = getConnection();
			String sql = "SELECT * FROM " +
						"(SELECT *,row_number() over(order by " + pagination.getSidx() + " " + pagination.getSord() + ") as row_num " + 
						"FROM languages" + filterStr + ") as a " +
						"WHERE row_num BETWEEN ? AND ?";
			PreparedStatement s = con.prepareStatement(sql);
			s.setInt(1, pagination.getStart());
			s.setInt(2, pagination.getEnd());
			ResultSet rs = s.executeQuery();
			while (rs.next()){
				SimpleBean language = new SimpleBean();
				language.setId(rs.getInt(Constants.FIELD_ID));
				language.setName(rs.getString(Constants.FIELD_NAME));
				language.setName_ru(rs.getString(Constants.FIELD_NAME_RU));
			    languages.add(language);
			}
			s.close();

		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return languages;
	}
	
	/**
     * add language
     * @param name, name_ru
     * @throws Exception
     * 
     */	
	public boolean addLanguage( String name,String name_ru) throws Exception  {
		boolean result = true;
		try {Connection con = getConnection();
			PreparedStatement s = con.prepareStatement("select " + Constants.DB_DBO + ".exist_language(?,?,?) as res");
			s.setInt(1, 0);
			s.setString(2, name);
			s.setString(3, name_ru);
			ResultSet rs=s.executeQuery();
			rs.next();
			String res = rs.getString("res");
			if (res.equals("0")){
				s = con.prepareStatement("exec add_language  ?, ?");
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
     * delete language
     * @param id of language
     * @throws Exception
     * 
     */
		
	public boolean deleteLanguage(int id) throws Exception{
		boolean result = true;
		try {Connection con = getConnection();
			PreparedStatement s = con.prepareStatement("select " + Constants.DB_DBO + ".can_delete_language(?) as res");
			s.setInt(1, id);
			ResultSet rs = s.executeQuery();
			rs.next();
			String res = rs.getString("res");
			if (res.equals("1")){
				String sql = "exec delete_language ?"; 
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
     * edit language
     * @param  id, name, name_ru
     * @throws Exception
     * 
     */	
	public boolean editLanguage(int id, String name,String name_ru) throws Exception  {
		boolean result = true;
		try {Connection con = getConnection();
			PreparedStatement s = con.prepareStatement("select " + Constants.DB_DBO + ".exist_language(?,?,?) as res");
			s.setInt(1, id);
			s.setString(2, name);
			s.setString(3, name_ru);
			ResultSet rs=s.executeQuery();
			rs.next();
			String res = rs.getString("res");
			if (res.equals("0")){
				s = con.prepareStatement("exec edit_language ?, ?, ?");
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

}
