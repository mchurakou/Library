package com.mikalai.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.mikalai.library.beans.Queue;

import com.mikalai.library.utils.Constants;
import com.mikalai.library.utils.Pagination;

;

/**
 * Class for work with user's queues
 * 
 * @author Mikalai_Churakou
 */
public class QueueDAO extends GenericDAO{
	/**
     * Method extract Queue from ResultSet
     * @param ResultSet
     * @return User
     */	
	private static Queue extractQueue(ResultSet rs) throws SQLException{
		Queue queue = new Queue();
		queue.setId(rs.getInt(Constants.FIELD_ID));
		queue.setRealBookId(rs.getInt(Constants.FIELD_REAL_BOOK_ID));
		queue.setDate(rs.getTimestamp(Constants.FIELD_DATE));
		return queue;
	}
	
	
	
	/**
     * add user in queue
     * @param user id, real book id
     * @throws Exception
     */
	public  boolean addUserInQueue(long userId,int realBookId) throws Exception{
		boolean result = true;
		try {Connection con = getConnection();
			PreparedStatement s = con.prepareStatement("select " + Constants.DB_DBO + ".exist_queue(?,?) as res");
			s.setLong(1, userId);
			s.setInt(2, realBookId);
			ResultSet rs=s.executeQuery();
			rs.next();
			String res = rs.getString("res");
			if (res.equals("0")){
				String sql = "exec add_user_in_queue ?,?"; 
				s = con.prepareStatement(sql);
				s.setLong(1, userId);
				s.setInt(2, realBookId);
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
     * List of queue elements for table
     * @return list of users
     * @throws Exception
     * 
     */
	public List<Queue> getQueuesForTable(Pagination pagination, int realBookId) throws Exception{
		
		List<Queue> queues = new ArrayList<Queue>();
		
		try {Connection con = getConnection();
			String sql = "SELECT * FROM " +
						"(SELECT *,row_number() over(order by " + pagination.getSidx() + " " + pagination.getSord() + ") as row_num " + 
						"FROM view_queues where realBookId = ?) as a " +
						"WHERE row_num BETWEEN ? AND ?"; 
			PreparedStatement s = con.prepareStatement(sql);
			s.setInt(1, realBookId);
			s.setInt(2, pagination.getStart());
			s.setInt(3, pagination.getEnd());
			ResultSet rs = s.executeQuery();
			while (rs.next())
				queues.add(extractQueue(rs));
			s.close();

		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return queues;
	}
	
	/**
     * count of users
     * @return count
     * @throws Exception
     * 
     */
	public int getCountOfQueues(int realBookId) throws Exception{
		int count = 0;
		try {Connection con = getConnection();
			PreparedStatement s = con.prepareStatement("Select count(*) as count from view_queues where realBookId = ?");
			s.setInt(1, realBookId);
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
     * delete user from queue
     * @param user id, real book id
     * @throws Exception
     * 
     */
	public boolean deleteUserFromQueue(long userId,int realBookId) throws Exception{
		boolean result = true;
		try {Connection con = getConnection();
			PreparedStatement s = con.prepareStatement("select " + Constants.DB_DBO + ".exist_queue(?,?) as res");
			s.setLong(1, userId);
			s.setInt(2, realBookId);
			ResultSet rs=s.executeQuery();
			rs.next();
			String res = rs.getString("res");
			if (res.equals("1")){
				String sql = "exec delete_user_from_queue ?,?"; 
				s = con.prepareStatement(sql);
				s.setLong(1, userId);
				s.setInt(2, realBookId);
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
     * delete user from queue
     * @param queue id
     * @throws Exception
     * 
     */
	public void deleteUserFromQueueById(int id) throws Exception{
		try {Connection con = getConnection();
			PreparedStatement s =con.prepareStatement("exec delete_user_from_queue_by_id ?");
			s.setInt(1, id);
			s.executeUpdate();
			s.close();

		} catch (SQLException e) {
			throw new Exception(e);
					
		}
		return;
		
	}
}
