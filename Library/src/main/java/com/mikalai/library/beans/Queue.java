package com.mikalai.library.beans;

import java.sql.Timestamp;

/**
 * Class contain information about one element in queue
 * 
 * @author Mikalai_Churakou
 */
public class Queue extends User{
	private int id;
	private long userId;
	
	
	private int realBookId;
	
	private Timestamp date;
	
	
	public Queue() {
		super();
	}
	public long getId() {
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
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	

	public int getRealBookId() {
		return realBookId;
	}
	public void setRealBookId(int realBookId) {
		this.realBookId = realBookId;
	}
}
