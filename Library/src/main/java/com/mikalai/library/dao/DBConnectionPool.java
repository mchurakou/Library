package com.mikalai.library.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mikalai.library.utils.Constants;
/**
 * Pool of connections
 * 
 * @author Mikalai_Churakou
 */
public class DBConnectionPool {
	private String url = Constants.URL_DB;
	private String login = Constants.DB_LOGIN;
	private String password = Constants.DB_PASSWORD;
    private static DBConnectionPool mc;
    private int maxConn = Constants.DB_MAX_CONNECTION;
    private Connection[] ConnArray = new Connection[maxConn];
    private int withdrewed=0;
    
    
    /**
     * Constructor
     */
    private DBConnectionPool() throws SQLException {
        for (int i = 0; i < maxConn; i++){
            ConnArray[i] = openOneConnection();
        }
    }
    
    /**
     * Open one connection
     */
    
    private Connection openOneConnection() throws SQLException{
    	Connection con=null;
		try {
			Class.forName(Constants.DB_DRIVER);
			con = DriverManager.getConnection(url,login,password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	    return con;
    }
    
    /**
     * Method return pool of connections
     * @return pool of connections
     */
    

    static public DBConnectionPool getConnPool() throws SQLException{
        DBConnectionPool mcc;
        mcc = (mc == null ? new DBConnectionPool() : mc);
        mc = mcc;
        return mcc;
    }
    
    /**
     * Method take one connection from pool
     * @return one connection
     */
    
    synchronized public Connection getConnection(){
        Connection conn=null;
        while (withdrewed==10){
            try {
                wait();
            } catch (InterruptedException e) { }
        }

        withdrewed++;
        for (int i = 0; i < maxConn; i++){
            if (ConnArray[i] != null) {
                conn = ConnArray[i];
                ConnArray[i] = null;
                break;
            }
        }

        notify();
        return conn;
    }
    
    /**
     * Method return one connection to pool
     * @param connection
     */
    
    synchronized public void releaseConnection(Connection conn){
        withdrewed--;
        /*try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
        for (int i = 0; i<maxConn; i++){
            if (ConnArray[i] == null){
                ConnArray[i] = conn;
                break;
            }
        }
        notify();
    }
    
    /**
     * Close all connections
     */
    
    synchronized public void closeAll(){
    	if (ConnArray != null)
    		for (int i = 0; i < maxConn; i++){
    			if (ConnArray[i] != null)
    				try{
    					//ConnArray[i].rollback();
    					ConnArray[i].close();
    				} catch (Exception e){}
    		}
        ConnArray = null;
    }
    
}