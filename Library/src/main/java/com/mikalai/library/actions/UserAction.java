package com.mikalai.library.actions;


import com.mikalai.library.ajax_json.*;
import com.mikalai.library.beans.Division;
import com.mikalai.library.beans.SimpleBean;
import com.mikalai.library.beans.User;
import com.mikalai.library.beans.dictionary.Role;
import com.mikalai.library.dao.DepartmentDAO;
import com.mikalai.library.dao.DivisionDAO;
import com.mikalai.library.dao.UserDAO;
import com.mikalai.library.service.UserService;
import com.mikalai.library.utils.Constants;
import com.mikalai.library.utils.Pagination;
import com.mikalai.library.utils.StringBuilder;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import javax.ejb.EJB;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

;
/**
 * Action for work with users
 * 
 * @author Mikalai_Churakou
 */
public class UserAction extends ActionSupport implements SessionAware, RequestAware{
	private static final Logger LOG = LogManager.getLogger();

	@Inject
	private UserDAO userDAO;

	@Inject
	private DivisionDAO divisionDAO;

	@Inject
	private DepartmentDAO departmentDAO;

	@EJB
	private UserService userService;

	private String login;
	private String password;
	private String firstName;
	private String secondName;
	private String email;


	private String roleId;
	private int categoryId;
	
	private String message;
	private String error;
	private AjaxResult result;
	private AjaxTableResult tableResult;
	
	private int page;
	private int rows;
	private String sidx;
	
	private String oper;
	private int id;
	
	private boolean _search;
	private Filter filters;
		
	private String sord;
	private int count;
	
	private User user;
	private List<User> users;
	
	private Map<String, Object> session;
	private List<SimpleBean> userCategories;
	private List<SimpleBean> userRoles;
	private List<SimpleBean> departments;
	private List<Division> divisions;
	
	private int departmentId;
	private int divisionId;
	
	


	

	

	private Map<String, Object> request;
	
	
	public String execute(){
		String userCategoryValue = "";
		String userRoleValue = "";
		String departmentValue = "";
		String divisionValue = "";
		try {
			userCategories = userDAO.getUserCategories();
			userCategoryValue = StringBuilder.generateValueForList(userCategories);
			
			userRoles = userDAO.getUserRoles();
			userRoleValue = StringBuilder.generateRoleValueForList(userRoles);
			
			departments = departmentDAO.getDepartments();
			departmentValue = StringBuilder.generateValueForList(departments);
			
			divisions = divisionDAO.getDivisions();
			divisionValue = StringBuilder.generateNamedValueForList(divisions);
			
			
				
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			setError(getText(Constants.MSG_DB_PROBLEM));
			return INPUT;
		}
		request.put("userCategoryValue", userCategoryValue);
		request.put("userRoleValue", userRoleValue);
		request.put("departmentValue", departmentValue);
		request.put("divisionValue", divisionValue);
		return SUCCESS;
	}
	
	
	/**
	 * Registration new user
	 * 
	 */	
	public String registrationConfirm()  {
		user = new User(login, password, firstName, secondName, email,divisionId);
		
		try {
			departments = departmentDAO.getDepartments();

			if (!userService.add(user)){
				setError(getText(Constants.MSG_REPEAT_LOGIN)); //repeat login
				return INPUT;
			}
			else{
				setMessage(getText(Constants.MSG_ACC_REG)); // registration new account
				return SUCCESS;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			setError(getText(Constants.MSG_DB_PROBLEM));
			return INPUT;
		}
	}
	
	/**
	 * Login and password confirmation
	 * 
	 */	
	public String loginConfirm()  {
		
		
		
		user = null;
		try {
			user = userService.getUser(login, password);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			setError(getText(Constants.MSG_DB_PROBLEM));
			return Action.INPUT;
		}
		
		if (user != null){
			if (user.getRole() != Role.NEW){ //login
				session.put(Constants.ATTRIBUTE_USER, user);
				if (user.getRole() == Role.USER) //user
					return "user";
				if (user.getRole() == Role.LIBRARIAN) //librarian
					return "librarian";
				if (user.getRole() == Role.ADMINISTRATOR) //administrator
					return "administrator";
			}
			else{
				this.setError(getText(Constants.MSG_ACC_NOT_ACTIVE)); //not activation
				return Action.INPUT;
				
			}
		}
		else{
			setError(getText(Constants.MSG_ACC_NOT_REG)); //incorrect login or password
			return Action.INPUT;
		}
		return Action.INPUT;
	}
	
	/**
	 * Display profile information
	 * 
	 */
	public String profile()  {
		user = (User) session.get(Constants.ATTRIBUTE_USER);
		
		try {
			departments = departmentDAO.getDepartments();
			divisions = divisionDAO.getDivisionsByDepartmentId((int) user.getDivision().getDepartment().getId());
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			setError(getText(Constants.MSG_DB_PROBLEM));
			return Action.INPUT;
		}
		return SUCCESS;
		
	}
	
	/**
	 * Change profile 
	 * 
	 */
	public String changeProfile()  {
		user = (User) session.get(Constants.ATTRIBUTE_USER);
		user.setFirstName(firstName);
		user.setSecondName(secondName);
		user.setEmail(email);
		user.setDivision(new Division(divisionId));

		try {
			user = userService.save(user);
			departments = departmentDAO.getDepartments();
			divisions = divisionDAO.getDivisionsByDepartmentId((int) user.getDivision().getDepartment().getId());
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			setError(getText(Constants.MSG_DB_PROBLEM));
			return Action.INPUT;
		}
		session.put(Constants.ATTRIBUTE_USER, user);
		return SUCCESS;
	}
	
	
	public List<Division> getDivisions() {
		return divisions;
	}


	public void setDivisions(List<Division> divisions) {
		this.divisions = divisions;
	}


	/**
	 * Prepare users 
	 * 
	 */
	public String prepareUsers()  {
			
		Pagination pagination = null;
		try {
					
			count = userService.getCount(filters).intValue();
			pagination = new Pagination(sidx,rows,count,page,sord);
			if (!_search)	  
				users = userService.getListForTable(pagination,null);
			else
				users = userService.getListForTable(pagination,filters);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false, Constants.MSG_DB_PROBLEM);
		}
				
		List<Row> listRows = new ArrayList<>();
		for (int i = 0;i < users.size();i++){
			User user = users.get(i);
			Row row = new Row();
			row.setId((int) user.getId());

			long departmentId = 0;
			long divisionId = 0;
			if (user.getDivision() != null){
				divisionId = user.getDivision().getId();
				departmentId = user.getDivision().getDepartment().getId();
			}
			row.setCell(new Object[]{user.getId(),user.getLogin(),user.getFirstName(),user.getSecondName(),user.getEmail(),user.getRole(),user.getCategoryId(),departmentId,divisionId});
			listRows.add(row);
		}
		
		tableResult = new AjaxTableResult(page,pagination.getTotalPages(),count,listRows);
		return SUCCESS;
	}
	
	/**
	 * Prepare users 
	 * 
	 */
	public String prepareActiveUsers()  {
		Pagination pagination = null;
		try {
			count = userService.getCountOfActiveUsers();
			pagination = new Pagination(sidx,rows,count,page,sord);
			if (!_search)	  
				users = userService.getActiveUsersForTable(pagination,null);
			else
				users = userService.getActiveUsersForTable(pagination,filters);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,Constants.MSG_DB_PROBLEM);
		}
				
		List<Row> listRows = new ArrayList<Row>();
		for (int i = 0;i < users.size();i++){
			User user = users.get(i);
			Row row = new Row();
			row.setId((int) user.getId());
			row.setCell(new Object[]{user.getId(),user.getLogin(),user.getFirstName(),user.getSecondName(),user.getEmail(),user.getRole(), user.getCategoryId(),user.isHaveDebt(),(int) user.getDivision().getDepartment().getId(),user.getDivision().getId()});
			listRows.add(row);
		}
		
		tableResult = new AjaxTableResult(page,pagination.getTotalPages(),count,listRows);
		return SUCCESS;
	}
	
	
	
	/**
	 * Edit users 
	 * 
	 */
	public String editUser()  {
		boolean success = true;
		try {
			if (oper.equals(Constants.OPERATION_DELETE)) //delete
				success = userDAO.deleteUser(id);
			if (oper.equals(Constants.OPERATION_EDIT)) //edit
				userDAO.editUser(id, firstName, secondName, email, roleId, categoryId,divisionId);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,Constants.MSG_DB_PROBLEM);
			
		}
		if (success)
			result = new AjaxResult("success");
		else
			result = new AjaxResult(false,getText(Constants.MSG_SUBSUDIARY));
		
		return SUCCESS;
	}
	
	
	public String loadDepartments(){
		
		try {
			departments = departmentDAO.getDepartments();
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			setError(getText(Constants.MSG_DB_PROBLEM));
			return Action.INPUT;
		}
		return SUCCESS;
	}
	
	public String getLang(){
		return getLocale().getLanguage();
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

	

	public void setSession(Map<String, Object> session) {
		this.session = session;
		
	}
	
	public Filter getFilters() {
		return filters;
	}

	public void setFilters(String str) {
		this.filters = ConverterJSON.getFilter(str);
	}

	public boolean is_search() {
		return _search;
	}

	public void set_search(boolean _search) {
		this._search = _search;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		if (sidx.endsWith("Id")) // sort by lookup field
			sidx = sidx.substring(0, sidx.length() - 2);
		this.sidx = sidx;
	}


	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public AjaxTableResult getTableResult() {
		return tableResult;
	}

	public void setTableResult(AjaxTableResult tableResult) {
		this.tableResult = tableResult;
	}

	public AjaxResult getResult() {
		return result;
	}

	public void setResult(AjaxResult result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	
	public void setRequest(Map<String, Object> r) {
		request = r;
		
	}
	
	public List<SimpleBean> getDepartments() {
		return departments;
	}


	public void setDepartments(List<SimpleBean> departments) {
		this.departments = departments;
	}

	public int getDepartmentId() {
		return departmentId;
	}


	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}


	public int getDivisionId() {
		return divisionId;
	}


	public void setDivisionId(int divisionId) {
		this.divisionId = divisionId;
	}


	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}



	
	
}
