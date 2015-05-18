package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import beans.Queue;

import utils.Constants;
import utils.Pagination;

import exceptions.DBException;

/**
 * Class for work with user's queues
 * 
 * @author Mikalai_Churakou
 */
public class QueueDB {
	/**
     * Method extract Queue from ResultSet
     * @param ResultSet
     * @return User
     */	
	private static Queue extractQueue(ResultSet rs) throws SQLException{
		Queue queue = new Queue();
		queue.setId(rs.getInt(Constants.FIELD_ID));
		queue.setUserId(rs.getInt(Constants.FIELD_USER_ID));
		queue.setLogin(rs.getString(Constants.FIELD_LOGIN));
		queue.setFirstName(rs.getString(Constants.FIELD_FIRST_NAME));
		queue.setSecondName(rs.getString(Constants.FIELD_SECOND_NAME));
		queue.setEmail(rs.getString(Constants.FIELD_EMAIL));
		queue.setRealBookId(rs.getInt(Constants.FIELD_REAL_BOOK_ID));
		queue.setDate(rs.getTimestamp(Constants.FIELD_DATE));
		return queue;
	}
	
	
	
	/**
     * add user in queue
     * @param user id, real book id
     * @throws DBException 
     * 
     */
	public static boolean addUserInQueue(int userId,int realBookId) throws DBException{
		boolean result = true;
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			PreparedStatement s = con.prepareStatement("select " + Constants.DB_DBO + ".exist_queue(?,?) as res");
			s.setInt(1, userId);
			s.setInt(2, realBookId);
			ResultSet rs=s.executeQuery();
			rs.next();
			String res = rs.getString("res");
			if (res.equals("0")){
				String sql = "exec add_user_in_queue ?,?"; 
				s = con.prepareStatement(sql);
				s.setInt(1, userId);
				s.setInt(2, realBookId);
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
     * List of queue elements for table
     * @return list of users
     * @throws DBException 
     * 
     */
	public static List<Queue> getQueuesForTable(Pagination pagination, int realBookId) throws DBException{
		
		List<Queue> queues = new ArrayList<Queue>();
		
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
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
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new DBException(e);
					
		}
		return queues;
	}
	
	/**
     * count of users
     * @return count
     * @throws DBException 
     * 
     */
	public static int getCountOfQueues(int realBookId) throws DBException{
		int count = 0;
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			PreparedStatement s = con.prepareStatement("Select count(*) as count from view_queues where realBookId = ?");
			s.setInt(1, realBookId);
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
     * delete user from queue
     * @param user id, real book id
     * @throws DBException 
     * 
     */
	public static boolean deleteUserFromQueue(int userId,int realBookId) throws DBException{
		boolean result = true;
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			PreparedStatement s = con.prepareStatement("select " + Constants.DB_DBO + ".exist_queue(?,?) as res");
			s.setInt(1, userId);
			s.setInt(2, realBookId);
			ResultSet rs=s.executeQuery();
			rs.next();
			String res = rs.getString("res");
			if (res.equals("1")){
				String sql = "exec delete_user_from_queue ?,?"; 
				s = con.prepareStatement(sql);
				s.setInt(1, userId);
				s.setInt(2, realBookId);
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
     * delete user from queue
     * @param queue id
     * @throws DBException 
     * 
     */
	public static void deleteUserFromQueueById(int id) throws DBException{
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			PreparedStatement s =con.prepareStatement("exec delete_user_from_queue_by_id ?");
			s.setInt(1, id);
			s.executeUpdate();
			s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new DBException(e);
					
		}
		return;
		
	}
}
