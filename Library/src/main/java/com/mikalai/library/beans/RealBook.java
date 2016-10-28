package com.mikalai.library.beans;

import com.mikalai.library.beans.base.BasicEntity;
import com.mikalai.library.beans.base.NamedEntity;

import javax.persistence.*;

/**
 * Class contain information about real book
 * 
 * @author Mikalai_Churakou
 */

@Entity
@Table(name="real_books")
public class RealBook extends BasicEntity {
	@ManyToOne
	@JoinColumn(name="bookDescriptionId")
	private BookDescription bookDescription;
	private int inventoryNumber;
	private int cost;

	@Transient
	private boolean available;

	public BookDescription getBookDescription() {
		return bookDescription;
	}

	public void setBookDescription(BookDescription bookDescription) {
		this.bookDescription = bookDescription;
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

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}




}
