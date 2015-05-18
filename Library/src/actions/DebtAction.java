package actions;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import utils.Constants;
import utils.Pagination;
import utils.StringBuilder;
import ajax_json.AjaxResult;
import ajax_json.AjaxTableResult;
import ajax_json.ConverterJSON;
import ajax_json.Filter;
import ajax_json.Row;
import beans.Debt;
import beans.Division;
import beans.SimpleBean;
import beans.User;


import com.opensymphony.xwork2.ActionSupport;

import dao.DebtDB;
import dao.DepartmentDB;
import dao.DivisionDB;
import dao.QueueDB;
import dao.UserDB;



import exceptions.DBException;
/**
 * Action for work with book's debts
 * 
 * @author Mikalai_Churakou
 */
public class DebtAction extends ActionSupport implements RequestAware, SessionAware{
	public static final Logger LOG = Logger.getLogger(UserAction.class); 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private AjaxResult result;
	private int realBookId;
	private int userId;
	private Timestamp start;
	
	private Timestamp end;
	private SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_SHORT);
	
	private AjaxTableResult tableResult;
	
	private int page;
	private int rows;
	private String sidx;
	
	private int id;
	private Map<String, Object> request;
	
	private Map<String, Object> session;
	
	private List<Debt> debts;


	private boolean _search;
	private Filter filters;
		
	private String sord;
	private int count; 
	private int debtId;
	private String error;
	
	


	private List<SimpleBean> userCategories;
	private List<SimpleBean> userRoles;
	
	private List<SimpleBean> departments;
	private List<Division> divisions;


	

	/**
	 * Give book action 
	 * @throws DBException 
	 * 
	 */
	public String giveBook() {
		String userCategoryValue = "";
		String userRoleValue = "";
		String departmentValue = "";
		String divisionValue = "";
		try {
			userCategories = UserDB.getUserCategories(getLocale().getLanguage());
			userCategoryValue = StringBuilder.generateValueForList(userCategories);
			userRoles = UserDB.getUserRoles(getLocale().getLanguage());
			userRoleValue = StringBuilder.generateValueForList(userRoles);
			
			departments = DepartmentDB.getDepartments(getLocale().getLanguage());
			departmentValue = StringBuilder.generateValueForList(departments);
			
			divisions = DivisionDB.getDivisions(getLocale().getLanguage());
			divisionValue = StringBuilder.generateValueForList(divisions);
		} catch (DBException e) {
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
	 * Debts action 
	 * @throws DBException 
	 * 
	 */
	public String debts() {
		String departmentValue = "";
		String divisionValue = "";
		try {
			
			departments = DepartmentDB.getDepartments(getLocale().getLanguage());
			departmentValue = StringBuilder.generateValueForList(departments);
			
			divisions = DivisionDB.getDivisions(getLocale().getLanguage());
			divisionValue = StringBuilder.generateValueForList(divisions);
		} catch (DBException e) {
			LOG.error(e.getMessage(),e);
			setError(getText(Constants.MSG_DB_PROBLEM));
			return INPUT;
		}
		
		request.put("departmentValue", departmentValue);
		request.put("divisionValue", divisionValue);
		
		return SUCCESS;
	}
	
	/**
	 * giving some book 
	 * @throws DBException 
	 * 
	 */
	public String giveRealBook() throws DBException{
		User user = (User) session.get(Constants.ATTRIBUTE_USER);
				
		try {
						
			DebtDB.giveBook(realBookId, userId, start, end,user.getId());
			QueueDB.deleteUserFromQueue(userId, realBookId);
			result = new AjaxResult(getText(Constants.MSG_BOOK_GIVEN));
		} catch (DBException e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,getText(Constants.MSG_DB_PROBLEM));
		}
		
		
		return SUCCESS;
		
	}
	
	/**
	 * Prepare debts for user 
	 * 
	 */
	public String prepareDebtsForUser()  {
		Pagination pagination = null;
		try {
			count = DebtDB.getCountOfDebts(userId,filters);
			pagination = new Pagination(sidx,rows,count,page,sord);
			if (!_search)	  
				debts = DebtDB.getDebtsForTable(pagination, null, userId,getLocale().getLanguage());
			else
				debts = DebtDB.getDebtsForTable(pagination, filters, userId,getLocale().getLanguage());
		} catch (DBException e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,Constants.MSG_DB_PROBLEM);
		}
				
		List<Row> listRows = new ArrayList<Row>();
		for (int i = 0;i < debts.size();i++){
			Debt debt = debts.get(i);
			Row row = new Row();
			row.setId(debt.getId());
			row.setCell(new Object[]{debt.getId(),debt.getBehind(),debt.getStartPeriod(),debt.getEndPeriod(),debt.getInventoryNumber(),debt.getName(),debt.getAuthor(),debt.getCost()});
			listRows.add(row);
		}
		
		tableResult = new AjaxTableResult(page,pagination.getTotalPages(),count,listRows);
		return SUCCESS;
	}
	
	/**
	 * Prepare all debts 
	 * 
	 */
	public String prepareAllDebts()  {
		Pagination pagination = null;
		try {
			count = DebtDB.getCountOfAllDebts();
			pagination = new Pagination(sidx,rows,count,page,sord);
			if (!_search)	  
				debts = DebtDB.getAllDebtsForTable(pagination, null,getLocale().getLanguage());
			else
				debts = DebtDB.getAllDebtsForTable(pagination,  filters,getLocale().getLanguage());
		} catch (DBException e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,Constants.MSG_DB_PROBLEM);
		}
				
		List<Row> listRows = new ArrayList<Row>();
		for (int i = 0;i < debts.size();i++){
			Debt debt = debts.get(i);
			Row row = new Row();
			row.setId(debt.getId());
			row.setCell(new Object[]{debt.getId(),debt.getBehind(),debt.getStartPeriod(),debt.getEndPeriod(),debt.getInventoryNumber(),debt.getName(),debt.getAuthor(),debt.getCost(),debt.getLogin(),debt.getFirstName(),debt.getSecondName(),debt.getEmail(),debt.getDepartmentId(),debt.getDivisionId()});
			listRows.add(row);
		}
		
		tableResult = new AjaxTableResult(page,pagination.getTotalPages(),count,listRows);
		return SUCCESS;
	}
	
	/**
	 * giving some book 
	 * @throws DBException 
	 * 
	 */
	public String returnRealBook() throws DBException{
		User user = (User) session.get(Constants.ATTRIBUTE_USER);
		try {
			DebtDB.returnBook(debtId,user.getId());
			result = new AjaxResult(getText(Constants.MSG_BOOK_RETURNED));
		} catch (DBException e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,getText(Constants.MSG_DB_PROBLEM));
		}
		return SUCCESS;
		
	}
	
	
	
	/**
	 * Return book action 
	 * @throws DBException 
	 * 
	 */
	public String returnBook() {
		String userCategoryValue = "";
		String userRoleValue = "";
		String departmentValue = "";
		String divisionValue = "";
		try {
			userCategories = UserDB.getUserCategories(getLocale().getLanguage());
			userCategoryValue = StringBuilder.generateValueForList(userCategories);
			userRoles = UserDB.getUserRoles(getLocale().getLanguage());
			userRoleValue = StringBuilder.generateValueForList(userRoles);
			
			departments = DepartmentDB.getDepartments(getLocale().getLanguage());
			departmentValue = StringBuilder.generateValueForList(departments);
			
			divisions = DivisionDB.getDivisions(getLocale().getLanguage());
			divisionValue = StringBuilder.generateValueForList(divisions);
		} catch (DBException e) {
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
	
	public String getLang(){
		return getLocale().getLanguage();
	}
	
	
	public AjaxResult getResult() {
		return result;
	}

	public void setResult(AjaxResult result) {
		this.result = result;
	}

	public int getRealBookId() {
		return realBookId;
	}

	public void setRealBookId(int realBookId) {
		this.realBookId = realBookId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Timestamp getStart() {
		return start;
	}

	public void setStart(String start) {
		long time = 0;
		try {
			time = formatter.parse(start).getTime();
		} catch (ParseException e) {
			System.out.println(e.getMessage());

		}
		this.start = new Timestamp(time);
		
		
	}

	public Timestamp getEnd() {
		return end;
	}

	public void setEnd(String end) {
		long time = 0;
		try {
			time = formatter.parse(end).getTime();
		} catch (ParseException e) {
			System.out.println(e.getMessage());

		}
		this.end = new Timestamp(time);
	}
	
	public AjaxTableResult getTableResult() {
		return tableResult;
	}

	public void setTableResult(AjaxTableResult tableResult) {
		this.tableResult = tableResult;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean is_search() {
		return _search;
	}

	public void set_search(boolean search) {
		_search = search;
	}

	public Filter getFilters() {
		return filters;
	}

	public void setFilters(String str) {
		this.filters = ConverterJSON.getFilter(str);
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public List<Debt> getDebts() {
		return debts;
	}

	public void setDebts(List<Debt> debts) {
		this.debts = debts;
	}
	
	public int getDebtId() {
		return debtId;
	}

	public void setDebtId(int debtId) {
		this.debtId = debtId;
	}

	
	public void setRequest(Map<String, Object> r) {
		request = r;
		
	}
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	
	public void setSession(Map<String, Object> s) {
		session = s;
		
	}

	

}
