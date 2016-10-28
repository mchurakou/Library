package com.mikalai.library.beans;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.mikalai.library.beans.base.BasicEntity;
import com.mikalai.library.utils.Constants;

import javax.persistence.*;

/**
 * Class contain information about comment
 * 
 * @author Mikalai_Churakou
 */

@Entity
@Table(name="comments")
public class Comment extends BasicEntity {


	@ManyToOne
	@JoinColumn(name="userId")
	private User user;


	@Transient
	SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);

	private int electronicBookId;
	private Timestamp date;
	private String message;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	

}
