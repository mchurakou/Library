package com.mikalai.library.beans;

import com.mikalai.library.beans.base.BasicEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Class contain information about one element in queue
 * 
 * @author Mikalai_Churakou
 */

@Entity
@Table(name="queues")
public class Queue extends BasicEntity{
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;

	private int realBookId;
	
	private Timestamp date;
	
	
	public Queue() {
		super();
	}

	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}


	public int getRealBookId() {
		return realBookId;
	}
	public void setRealBookId(int realBookId) {
		this.realBookId = realBookId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
