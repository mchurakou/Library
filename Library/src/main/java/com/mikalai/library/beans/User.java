package com.mikalai.library.beans;


import com.mikalai.library.beans.base.BasicEntity;
import com.mikalai.library.beans.dictionary.Role;
import com.mikalai.library.utils.Constants;

import javax.persistence.*;
import java.util.*;

/**
 * Class contain information about user
 * 
 * @author Mikalai_Churakou
 */



@NamedQueries({
		@NamedQuery(
				name =  "User.login",
				query = "select u from User u join fetch u.userCategory c left join fetch u.division d left join fetch d.department  " +
                        "where u.login = :login and u.password = :password"
		),

		@NamedQuery(
				name =  "User.byLogin",
				query = "select u from User u where u.login = :login"
		),
		@NamedQuery(
				name =  "User.getActiveUsers",
				query = "select count(u) from User u where u.role not in ('NEW', 'ADMINISTRATOR')"
		)

})


@Entity
@Table(name="users")
public class User extends BasicEntity {
	@Column(nullable = false,unique = true, length = 50)
	private String login;
	private String password;
	private String firstName;
	private String secondName;
	private String email;
	@Enumerated(EnumType.STRING)
	@Column(name="roleId")
	private Role role;



	@ManyToOne
	@JoinColumn(name="categoryId")
	private UserCategory userCategory;

	@ManyToOne
	@JoinColumn(name="divisionId")
	private Division division;

	@OneToMany(mappedBy="user")
	private List<Debt> debts;

	@OneToMany(mappedBy="user")
	private List<Comment> comments;


	@OneToMany(mappedBy="user")
	private List<Queue> queues;



	public User(String login, String password, String firstName,
				String secondName, String email, int divisionId) {
		this.login = login;
		this.password = password;
		this.firstName = firstName;
		this.secondName = secondName;
		this.email = email;
		this.role = Role.NEW;
		this.userCategory = new UserCategory(Constants.CATEGORY_STUDENT);

		this.division = new Division(divisionId);

	}
	public User() {
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
		
	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public UserCategory getUserCategory() {
		return userCategory;
	}

	public void setUserCategory(UserCategory userCategory) {
		this.userCategory = userCategory;
	}

	public List<Debt> getDebts() {
		return debts;
	}

	public void setDebts(List<Debt> debts) {
		this.debts = debts;
	}


	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}


	public List<Queue> getQueues() {
		return queues;
	}

	public void setQueues(List<Queue> queues) {
		this.queues = queues;
	}
	
}
