package com.mikalai.library.beans;


import com.mikalai.library.beans.dictionary.Category;
import com.mikalai.library.beans.dictionary.Role;

import javax.persistence.*;

/**
 * Class contain information about user
 * 
 * @author Mikalai_Churakou
 */



@NamedQueries({
		@NamedQuery(
				name =  "User.login",
				query = "select u from User u where u.login = :login and u.password = :password"
		)
})


@Entity
@Table(name="users")
public class User extends BasicEntity {
	private String login;
	private String password;
	private String firstName;
	private String secondName;
	private String email;
	@Enumerated(EnumType.STRING)
	@Column(name="roleId")
	private Role role;
	@Enumerated
	@Column(name="categoryId")
	private Category category;

	@Transient
	private boolean haveDebt;


	@ManyToOne
	@JoinColumn(name="divisionId")
	private Division division;

	public boolean isHaveDebt() {
		return haveDebt;
	}
	public void setHaveDebt(boolean haveDebt) {
		this.haveDebt = haveDebt;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
		
	public User(String login, String password, String firstName,
			String secondName, String email,int divisionId) {
		this.login = login;
		this.password = password;
		this.firstName = firstName;
		this.secondName = secondName;
		this.email = email;
//		this.divisionId = divisionId;
		
	}
	
	public User() {
	}



	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}
	
}
