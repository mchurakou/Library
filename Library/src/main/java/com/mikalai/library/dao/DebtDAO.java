package com.mikalai.library.dao;

import com.mikalai.library.ajax_json.Filter;
import com.mikalai.library.beans.Debt;
import com.mikalai.library.utils.Constants;
import com.mikalai.library.utils.Pagination;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

;

/**
 * Class for work with user's debts
 * 
 * @author Mikalai_Churakou
 */
public class DebtDAO extends GenericDAO {
	
	/**
     * Method extract RealBook from ResultSet
     * @param ResultSet
     * @return User
     */	
	private static Debt extractDebt(ResultSet rs) throws SQLException{
		Debt debt = new Debt();
		debt.setId(rs.getInt(Constants.FIELD_ID));

		debt.setStartPeriod(rs.getTimestamp(Constants.FIELD_START_PERIOD));
		debt.setEndPeriod(rs.getTimestamp(Constants.FIELD_END_PERIOD));

		return debt;
	}
	
	
	/**
     * give real book
     * @param real book id, user id, start, end
     * @throws Exception
     * 
     */
	public void giveBook(int realBookId,long userId,Timestamp start, Timestamp end,long librarianId) throws Exception{
		try {Connection con = getConnection();
			String sql = "exec give_book ?,?,?,?"; 
			PreparedStatement s = con.prepareStatement(sql);
			s.setInt(1, realBookId);
			s.setLong(2, userId);
			s.setTimestamp(3, start);
			s.setTimestamp(4, end);
			s.executeUpdate();
			
			sql = "exec add_report ?,?,?,?,?,?";
			s = con.prepareStatement(sql);
			s.setLong(1,librarianId);
			s.setString(2, "given");
			s.setTimestamp(3, start);
			s.setTimestamp(4, end);
			s.setInt(5, realBookId);
			s.setLong(6, userId);
			s.executeUpdate();
			
			
			s.close();

		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		
	}
	
		
	
	/**
     * List of user's debts for table with searching
	 * @param language 
     * @param user id
     * @return list of debts
     * @throws Exception
     * 
     */
	public List<Debt> getDebtsForTable(Pagination pagination, Filter filter, long userId) throws Exception{

		String filterStr = SQL.getSqlFilter(filter);
		List<Debt> debts = new ArrayList<Debt>();
		try {Connection con = getConnection();
			String sql = "SELECT * FROM " +
						"(SELECT *,row_number() over(order by " + pagination.getSidx() + " " + pagination.getSord() + ") as row_num " + 
						"FROM user_debts (?) " +
						filterStr + ") as a " +
						"WHERE row_num BETWEEN ? AND ?"; 
			PreparedStatement s = con.prepareStatement(sql);
			s.setLong(1, userId);
			s.setInt(2, pagination.getStart());
			s.setInt(3, pagination.getEnd());
			ResultSet rs = s.executeQuery();
			while (rs.next())
				debts.add(extractDebt(rs));
			s.close();

		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return debts;
	}
	
	
	/**
     * List of all debts for table with searching
	 * @param language 
     * @param user id
     * @return list of debts
     * @throws Exception
     * 
     */
	public List<Debt> getAllDebtsForTable(Pagination pagination, Filter filter) throws Exception{

		String filterStr = SQL.getSqlFilter(filter);
		List<Debt> debts = new ArrayList<Debt>();
		try {Connection con = getConnection();
			String sql = "SELECT * FROM " +
						"(SELECT *,row_number() over(order by " + pagination.getSidx() + " " + pagination.getSord() + ") as row_num " + 
						"FROM view_all_debts " + filterStr + ") as a " +
						"WHERE row_num BETWEEN ? AND ?"; 
			PreparedStatement s = con.prepareStatement(sql);
			s.setInt(1, pagination.getStart());
			s.setInt(2, pagination.getEnd());
			ResultSet rs = s.executeQuery();
			while (rs.next())
				debts.add(extractDebt(rs));
			s.close();

		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return debts;
	}
	
	/**
     * count of debts for user
	 * @param filter 
     * @param user id
     * @return count
     * @throws Exception
     * 
     */
	public int getCountOfDebts(long userId, Filter filter) throws Exception{
		String filterStr = SQL.getSqlFilter(filter);
		int count = 0;
		try {Connection con = getConnection();
			PreparedStatement s = con.prepareStatement("Select count(*) as count from user_debts(?) " + filterStr);
			s.setLong(1, userId);
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
     * count of all debts
     * @param user id
     * @return count
     * @throws Exception
     * 
     */
	public int getCountOfAllDebts() throws Exception{
		int count = 0;
		try {Connection con = getConnection();
			PreparedStatement s = con.prepareStatement("Select count(*) as count from debts");
			ResultSet rs = s.executeQuery();
			
			if (rs.next())
				count = rs.getInt("count");
				
			s.close();

		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return count;
	}
	
	


	public void returnBook(int debtId,long librarianId) throws Exception {
		try {Connection con = getConnection();
			
			String sql = "select * from debts where id = ?";
			PreparedStatement s = con.prepareStatement(sql);
			s.setInt(1, debtId);
		
			ResultSet rs=s.executeQuery();
			rs.next();
			Timestamp start  = rs.getTimestamp("startPeriod");
			Timestamp end  = rs.getTimestamp("endPeriod");
			int realBookId = rs.getInt("realBookId");
			long userId = rs.getInt("userId");
			
			sql = "exec add_report ?,?,?,?,?,?";
			s = con.prepareStatement(sql);
			s.setLong(1,librarianId);
			s.setString(2, "returned");
			s.setTimestamp(3, start);
			s.setTimestamp(4, end);
			s.setInt(5, realBookId);
			s.setLong(6, userId);
			s.executeUpdate();
			
			
			
			
			
			sql = "exec return_book ?"; 
			s = con.prepareStatement(sql);
			s.setInt(1, debtId);
			s.executeUpdate();
			s.close();

		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		
	}

}
