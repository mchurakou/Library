package com.mikalai.library.beans;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.mikalai.library.utils.Constants;

/**
 * Class contain information about comment
 * 
 * @author Mikalai_Churakou
 */
public class Comment {
	private int id;
	private int userId;
	private String firstName;
	private String secondName;
	
	SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	private int electronicBookId;
	private Timestamp date;
	private String message;
	public Comment() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getElectronicBookId() {
		return electronicBookId;
	}
	public void setElectronicBookId(int electronicBookId) {
		this.electronicBookId = electronicBookId;
	}
	public String getDate() {
		return formatter.format(date);
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	

}
