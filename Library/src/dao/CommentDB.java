package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.Constants;


import beans.Comment;


import exceptions.DBException;

/**
 * Class for work with comments
 * 
 * @author Mikalai_Churakou
 */
public class CommentDB {

	
	
	/**
     * Method extract Comment from ResultSet
     * @param ResultSet
     * @return User
     */	
	private static Comment extractComment(ResultSet rs) throws SQLException{
		Comment comment = new Comment();
		comment.setId(rs.getInt(Constants.FIELD_ID));
		comment.setElectronicBookId(rs.getInt(Constants.FIELD_ELECTRONIC_BOOK_ID));
		comment.setUserId(rs.getInt(Constants.FIELD_USER_ID));
		comment.setFirstName(rs.getString(Constants.FIELD_FIRST_NAME));
		comment.setSecondName(rs.getString(Constants.FIELD_SECOND_NAME));
		comment.setDate(rs.getTimestamp(Constants.FIELD_DATE));
		comment.setMessage(rs.getString(Constants.FIELD_MESSAGE));
		
		return comment;
	}
	
	
	/**
	    * Add new comment
	    * @param userId 
	    * @param electronicBookId
	    * @param message
	    * @throws DBException
	    * 
	    */	
		public static void addComment(int userId,int electronicBookId, String message) throws DBException{
			
			try {
				DBConnectionPool pool = DBConnectionPool.getConnPool();
				Connection con=pool.getConnection();
				PreparedStatement s = con.prepareStatement("exec add_comment ?, ?,?");
				s.setInt(1, userId);
				s.setInt(2, electronicBookId);
				s.setString(3, message);
				
				s.executeUpdate();
				s.close();
				pool.releaseConnection(con);
			} catch (SQLException e) {
				throw new DBException(e);
			}
			
			return ;
		}
		
		/**
	     * List of comments
	     * @param electronicBookId
	     * @param pagination
	     * @return list of users
	     * @throws DBException 
	     * 
	     */
		public static List<Comment> getComments(int electronicBookId) throws DBException{
			List<Comment> comments = new ArrayList<Comment>();
			try {
				DBConnectionPool pool = DBConnectionPool.getConnPool();
				Connection con=pool.getConnection();
				String sql = "Select * FROM view_comments where electronicBookId = ? " +
							 " order by date ";
							
				PreparedStatement s = con.prepareStatement(sql);
				s.setInt(1, electronicBookId);
				
				ResultSet rs = s.executeQuery();
				while (rs.next())
					comments.add(extractComment(rs));
				s.close();
				pool.releaseConnection(con);
			} catch (SQLException e) {
				throw new DBException(e);
						
			}
			return comments;
		}
		
		
		/**
		    * Delete comment
		    * @param commentId 
		    * @throws DBException
		    * 
		    */	
			public static void deleteComment(int commentId) throws DBException{
				
				try {
					DBConnectionPool pool = DBConnectionPool.getConnPool();
					Connection con=pool.getConnection();
					PreparedStatement s = con.prepareStatement("exec delete_comment ?");
					s.setInt(1, commentId);
					s.executeUpdate();
					s.close();
					pool.releaseConnection(con);
				} catch (SQLException e) {
					throw new DBException(e);
				}
				
				return ;
			}
			
			
		
		
}
