package com.mikalai.library.beans;

import java.sql.Timestamp;

/**
 * Class contain information about report's row
 * 
 * @author Mikalai_Churakou
 */
public class LibrarianReportRecord {
	private String librarian;
	private String operation;
	private Timestamp date;
	private Timestamp start;
	private Timestamp end;
	private int inventoryNumber;
	private String book;
	private String user;
	public LibrarianReportRecord() {
		super();
	}
	public String getLibrarian() {
		return librarian;
	}
	public void setLibrarian(String librarian) {
		this.librarian = librarian;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public Timestamp getStart() {
		return start;
	}
	public void setStart(Timestamp start) {
		this.start = start;
	}
	public Timestamp getEnd() {
		return end;
	}
	public void setEnd(Timestamp end) {
		this.end = end;
	}
	public int getInventoryNumber() {
		return inventoryNumber;
	}
	public void setInventoryNumber(int inventoryNumber) {
		this.inventoryNumber = inventoryNumber;
	}
	public String getBook() {
		return book;
	}
	public void setBook(String book) {
		this.book = book;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}

}
