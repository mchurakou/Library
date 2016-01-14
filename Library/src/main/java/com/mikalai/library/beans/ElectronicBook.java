package com.mikalai.library.beans;
/**
 * Class contain information about electronic book book
 * 
 * @author Mikalai_Churakou
 */
public class ElectronicBook extends BookDescription{
	private int bookDescriptionId;
	private String fileName;
	private int capacity;
	private String extension;
	
	public int getBookDescriptionId() {
		return bookDescriptionId;
	}
	public void setBookDescriptionId(int bookDescriptionId) {
		this.bookDescriptionId = bookDescriptionId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	

}
