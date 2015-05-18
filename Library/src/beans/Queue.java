package beans;

import java.sql.Timestamp;

/**
 * Class contain information about one element in queue
 * 
 * @author Mikalai_Churakou
 */
public class Queue extends User{
	private int id;
	private int userId;
	
	
	private int realBookId;
	
	private Timestamp date;
	
	
	public Queue() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	

	public int getRealBookId() {
		return realBookId;
	}
	public void setRealBookId(int realBookId) {
		this.realBookId = realBookId;
	}
}
