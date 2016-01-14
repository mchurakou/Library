package com.mikalai.library.beans;

import java.sql.Timestamp;

public class Debt extends RealBook {
	
	private int id;
	private String behind;
	private Timestamp startPeriod;
	private Timestamp endPeriod;
	private int userId;
	private String login;
	private String firstName;
	private String secondName;
	private String email;
	private int departmentId;
	private int divisionId;
	
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public int getDivisionId() {
		return divisionId;
	}
	public void setDivisionId(int divisionId) {
		this.divisionId = divisionId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getBehind() {
		return behind;
	}
	public void setBehind(String behind) {
		this.behind = behind;
	}
	public Timestamp getStartPeriod() {
		return startPeriod;
	}
	public void setStartPeriod(Timestamp startPeriod) {
		this.startPeriod = startPeriod;
	}
	public Timestamp getEndPeriod() {
		return endPeriod;
	}
	public void setEndPeriod(Timestamp endPeriod) {
		this.endPeriod = endPeriod;
	}
	

}
