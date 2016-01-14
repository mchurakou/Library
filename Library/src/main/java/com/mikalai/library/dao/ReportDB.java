package com.mikalai.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mikalai.library.exceptions.DBException;

import com.mikalai.library.utils.Constants;
import com.mikalai.library.utils.Pagination;
import com.mikalai.library.ajax_json.Filter;
import com.mikalai.library.beans.LibrarianReportRecord;
import com.mikalai.library.beans.User;


/**
 * Class for work with reports
 * 
 * @author Mikalai_Churakou
 */
public class ReportDB {
	/**
     * Method extract User from ResultSet
     * @param ResultSet
     * @return User
     */	
	private static LibrarianReportRecord extractRecord(ResultSet rs) throws SQLException{
		LibrarianReportRecord record = new LibrarianReportRecord();
		record.setLibrarian(rs.getString(Constants.FIELD_LIBRARIAN));
		record.setOperation(rs.getString(Constants.FIELD_OPERATION));
		record.setDate(rs.getTimestamp(Constants.FIELD_DATE));
		record.setStart(rs.getTimestamp(Constants.FIELD_START_PERIOD));
		record.setEnd(rs.getTimestamp(Constants.FIELD_END_PERIOD));
		record.setInventoryNumber(rs.getInt(Constants.FIELD_INVENTORY_NUMBER));
		record.setBook(rs.getString(Constants.FIELD_BOOK));
		record.setUser(rs.getString(Constants.FIELD_PERSON));
		
		
		return record;
	}
	
	/**
     * List of rows for report
     * @return list of rows
     * @throws DBException 
     * 
     */
	public static List<LibrarianReportRecord> getRows(Timestamp start, Timestamp end) throws DBException{
		List<LibrarianReportRecord> records = new ArrayList<LibrarianReportRecord>();
		try {
			DBConnectionPool pool = DBConnectionPool.getConnPool();
			Connection con=pool.getConnection();
			String sql = "select * FROM " + Constants.DB_DBO + ".report(?,?)";
			PreparedStatement s = con.prepareStatement(sql);
			s.setTimestamp(1,start);
			s.setTimestamp(2,end);
			ResultSet rs = s.executeQuery();
			while (rs.next())
				records.add(extractRecord(rs));
			s.close();
			pool.releaseConnection(con);
		} catch (SQLException e) {
			throw new DBException(e);
					
		}
		return records ;
	}

}
