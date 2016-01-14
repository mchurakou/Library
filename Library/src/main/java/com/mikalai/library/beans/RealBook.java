package com.mikalai.library.beans;
/**
 * Class contain information about real book
 * 
 * @author Mikalai_Churakou
 */
public class RealBook extends BookDescription{
	private int bookDescriptionId;
	private int inventoryNumber;
	private int cost;
	private boolean available;
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public int getBookDescriptionId() {
		return bookDescriptionId;
	}
	public void setBookDescriptionId(int bookDescriptionId) {
		this.bookDescriptionId = bookDescriptionId;
	}
	public int getInventoryNumber() {
		return inventoryNumber;
	}
	public void setInventoryNumber(int inventoryNumber) {
		this.inventoryNumber = inventoryNumber;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}

}
