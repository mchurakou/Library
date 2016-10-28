package com.mikalai.library.beans;

import com.mikalai.library.beans.base.BasicEntity;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name="debts")
public class Debt extends BasicEntity {

	private Timestamp startPeriod;
	private Timestamp endPeriod;

	@Transient
	private String behind;


	@ManyToOne
	@JoinColumn(name="userId")
	private User user;



	@ManyToOne
	@JoinColumn(name="realBookId")
	private RealBook realBook;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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


	public String getBehind() {
		return behind;
	}

	public void setBehind(String behind) {
		this.behind = behind;
	}

	public RealBook getRealBook() {
		return realBook;
	}

	public void setRealBook(RealBook realBook) {
		this.realBook = realBook;
	}
	

}
