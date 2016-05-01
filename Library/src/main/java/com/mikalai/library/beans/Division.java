package com.mikalai.library.beans;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Class contain information about group or cathedra
 * 
 * @author Mikalai_Churakou
 */

@Entity
@Table(name="divisions")
public class Division extends NamedEntity {




	@ManyToOne
	@JoinColumn(name="departmentId")
	private Department department;

	public Division(int id) {
		setId(id);
	}
	public Division() {
	}


	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}




}
