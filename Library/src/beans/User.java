package beans;


/**
 * Class contain information about user
 * 
 * @author Mikalai_Churakou
 */
public class User {
	private int id;
	private String login;
	private String password;
	private String firstName;
	private String secondName;
	private String email;
	private SimpleBean role;
	private SimpleBean category;
	private boolean haveDebt;
	private int divisionId;
	private int departmentId;
	

	public int getDivisionId() {
		return divisionId;
	}
	public void setDivisionId(int divisionId) {
		this.divisionId = divisionId;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public boolean isHaveDebt() {
		return haveDebt;
	}
	public void setHaveDebt(boolean haveDebt) {
		this.haveDebt = haveDebt;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
		
	public SimpleBean getRole() {
		return role;
	}
	public void setRole(SimpleBean role) {
		this.role = role;
	}
	public SimpleBean getCategory() {
		return category;
	}
	public void setCategory(SimpleBean category) {
		this.category = category;
	}
	public User(String login, String password, String firstName,
			String secondName, String email,int divisionId) {
		super();
		this.login = login;
		this.password = password;
		this.firstName = firstName;
		this.secondName = secondName;
		this.email = email;
		this.divisionId = divisionId;
		
	}
	
	public User() {
		super();
	}
	
}
