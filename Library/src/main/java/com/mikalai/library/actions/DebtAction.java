package com.mikalai.library.actions;

import com.mikalai.library.ajax_json.AjaxResult;
import com.mikalai.library.ajax_json.AjaxTableResult;
import com.mikalai.library.ajax_json.ConverterJSON;
import com.mikalai.library.ajax_json.Filter;
import com.mikalai.library.ajax_json.Row;
import com.mikalai.library.beans.Debt;
import com.mikalai.library.beans.Division;
import com.mikalai.library.beans.SimpleBean;
import com.mikalai.library.beans.User;
import com.mikalai.library.dao.DebtDAO;
import com.mikalai.library.dao.DepartmentDAO;
import com.mikalai.library.dao.DivisionDAO;
import com.mikalai.library.dao.QueueDAO;
import com.mikalai.library.dao.UserDAO;
import com.mikalai.library.utils.Constants;
import com.mikalai.library.utils.Pagination;
import com.mikalai.library.utils.StringBuilder;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

;
/**
 * Action for work with book's debts
 * 
 * @author Mikalai_Churakou
 */
public class DebtAction extends ActionSupport implements RequestAware, SessionAware{
	private static final Logger LOG = LogManager.getLogger();

	@Inject
	private UserDAO userDAO;

	@Inject
	private QueueDAO queueDAO;

	@Inject
	private DivisionDAO divisionDAO;

	@Inject
	private DepartmentDAO departmentDAO;

	@Inject
	private DebtDAO debtDAO;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private AjaxResult result;
	private int realBookId;
	private long userId;
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
	 * @throws Exception
	 * 
	 */
	public String giveBook() {
		String userCategoryValue = "";
		String userRoleValue = "";
		String departmentValue = "";
		String divisionValue = "";
		try {
			userCategories = userDAO.getUserCategories();
			userCategoryValue = StringBuilder.generateValueForList(userCategories);
			userRoles = userDAO.getUserRoles();
			userRoleValue = StringBuilder.generateValueForList(userRoles);
			
			departments = departmentDAO.getDepartments();
			departmentValue = StringBuilder.generateValueForList(departments);
			
			divisions = divisionDAO.getDivisions();
			divisionValue = StringBuilder.generateValueForList(divisions);
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
	 * Debts action 
	 * @throws Exception
	 * 
	 */
	public String debts() {
		String departmentValue = "";
		String divisionValue = "";
		try {
			
			departments = departmentDAO.getDepartments();
			departmentValue = StringBuilder.generateValueForList(departments);
			
			divisions = divisionDAO.getDivisions();
			divisionValue = StringBuilder.generateValueForList(divisions);
		} catch (Exception e) {
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
	 * @throws Exception
	 * 
	 */
	public String giveRealBook() throws Exception{
		User user = (User) session.get(Constants.ATTRIBUTE_USER);
				
		try {
						
			debtDAO.giveBook(realBookId, userId, start, end,user.getId());
			queueDAO.deleteUserFromQueue(userId, realBookId);
			result = new AjaxResult(getText(Constants.MSG_BOOK_GIVEN));
		} catch (Exception e) {
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
			count = debtDAO.getCountOfDebts(userId,filters);
			pagination = new Pagination(sidx,rows,count,page,sord);
			if (!_search)	  
				debts = debtDAO.getDebtsForTable(pagination, null, userId);
			else
				debts = debtDAO.getDebtsForTable(pagination, filters, userId);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,Constants.MSG_DB_PROBLEM);
		}
				
		List<Row> listRows = new ArrayList<>();
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
			count = debtDAO.getCountOfAllDebts();
			pagination = new Pagination(sidx,rows,count,page,sord);
			if (!_search)	  
				debts = debtDAO.getAllDebtsForTable(pagination, null);
			else
				debts = debtDAO.getAllDebtsForTable(pagination,  filters);
		} catch (Exception e) {
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
	 * @throws Exception
	 * 
	 */
	public String returnRealBook() throws Exception{
		User user = (User) session.get(Constants.ATTRIBUTE_USER);
		try {
			debtDAO.returnBook(debtId,user.getId());
			result = new AjaxResult(getText(Constants.MSG_BOOK_RETURNED));
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,getText(Constants.MSG_DB_PROBLEM));
		}
		return SUCCESS;
		
	}
	
	
	
	/**
	 * Return book action 
	 * @throws Exception
	 * 
	 */
	public String returnBook() {
		String userCategoryValue = "";
		String userRoleValue = "";
		String departmentValue = "";
		String divisionValue = "";
		try {
			userCategories = userDAO.getUserCategories();
			userCategoryValue = StringBuilder.generateValueForList(userCategories);
			userRoles = userDAO.getUserRoles();
			userRoleValue = StringBuilder.generateValueForList(userRoles);
			
			departments = departmentDAO.getDepartments();
			departmentValue = StringBuilder.generateValueForList(departments);
			
			divisions = divisionDAO.getDivisions();
			divisionValue = StringBuilder.generateValueForList(divisions);
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
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
