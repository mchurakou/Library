package com.mikalai.library.actions;


import com.mikalai.library.ajax_json.*;
import com.mikalai.library.beans.Division;
import com.mikalai.library.beans.SimpleBean;
import com.mikalai.library.dao.*;
;
import com.mikalai.library.utils.Constants;
import com.mikalai.library.utils.Pagination;
import com.mikalai.library.utils.StringBuilder;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.RequestAware;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * Action for work handbooks
 * 
 * @author Mikalai_Churakou
 */

public class HandBookAction extends ActionSupport implements RequestAware{
	private static final Logger LOG = Logger.getLogger(HandBookAction.class);
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UserCategoryDAO userCategoryDAO;
	@Inject
	private LanguageDAO languageDAO;
	@Inject
	private DivisionDAO divisionDAO;
	@Inject
	private DepartmentDAO departmentDAO;
	@Inject
	private BookCategoryDAO bookCategoryDAO;
	private AjaxTableResult tableResult;
	private int count;
	private Filter filters;
	private AjaxResult result;
	private int page;
	private int rows;
	private String sidx;
	private String sord;
	private boolean _search;
	
	private String name;
	private String name_ru;
	private int departmentId;
	
	

	private List<SimpleBean> departments;
	private List<SimpleBean> bookCategories;
	private List<SimpleBean> languages;
	private List<SimpleBean> userCategories;
	private List<Division> divisions;
	
	
	
	
	private String oper;
	private int id;

	private Map request;
	
	private String error;

	

	public String execute(){
		
		return SUCCESS;
	}
	
	/**
	 * Prepare departments 
	 * 
	 */
	public String prepareDepartments()  {
			
		Pagination pagination = null;
		try {
			count = departmentDAO.getCountOfDepartments(filters);
			pagination = new Pagination(sidx,rows,count,page,sord);
			if (!_search)	  
				departments = departmentDAO.getDepartmentsForTable(pagination, null);
			else
				departments = departmentDAO.getDepartmentsForTable(pagination, filters);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,Constants.MSG_DB_PROBLEM);
		}
				
		List<Row> listRows = new ArrayList<Row>();
		for (int i = 0;i < departments.size();i++){
			SimpleBean department = departments.get(i);
			Row row = new Row();
			row.setId(department.getId());
			row.setCell(new Object[]{department.getId(),department.getName(),department.getName_ru()});
			listRows.add(row);
		}
		
		tableResult = new AjaxTableResult(page,pagination.getTotalPages(),count,listRows);
		return SUCCESS;
	}
	
	/**
	 * Edit department 
	 * 
	 */
	public String editDepartment()  {
		String msg = "";
		boolean success = true;
		try {
			if (oper.equals(Constants.OPERATION_DELETE)) //delete
				if (!departmentDAO.deleteDepartment(id)){
					success = false;
					msg = getText(Constants.MSG_SUBSUDIARY);
				}
			
			if (oper.equals(Constants.OPERATION_EDIT)) //edit
				if (!departmentDAO.editDepartment(id, name, name_ru)){
					success = false;
					msg = getText(Constants.MSG_UNIQUE_FAILD);
				}
				
			
			if (oper.equals(Constants.OPERATION_ADD)) //add
				if (!departmentDAO.addDepartment(name, name_ru)){
					success = false;
					msg =  getText(Constants.MSG_UNIQUE_FAILD);
				}
			
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,Constants.MSG_DB_PROBLEM);
			
		}
		if (success)
			result = new AjaxResult("success");
		else
			result = new AjaxResult(false,msg);
		
		return SUCCESS;
	}
	
	
	
	
	/**
	 * Prepare book categories 
	 * 
	 */
	public String prepareBookCategories()  {
			
		Pagination pagination = null;
		try {
			count = bookCategoryDAO.getCountOfBookCategories(filters);
			pagination = new Pagination(sidx,rows,count,page,sord);
			if (!_search)	  
				bookCategories = bookCategoryDAO.getBookCategoriesForTable(pagination, null);
			else
				bookCategories = bookCategoryDAO.getBookCategoriesForTable(pagination, filters);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,Constants.MSG_DB_PROBLEM);
		}
				
		List<Row> listRows = new ArrayList<Row>();
		for (int i = 0;i < bookCategories.size();i++){
			SimpleBean bookCategory = bookCategories.get(i);
			Row row = new Row();
			row.setId(bookCategory.getId());
			row.setCell(new Object[]{bookCategory.getId(),bookCategory.getName(),bookCategory.getName_ru()});
			listRows.add(row);
		}
		
		tableResult = new AjaxTableResult(page,pagination.getTotalPages(),count,listRows);
		return SUCCESS;
	}
	
	
	
	/**
	 * Edit book category
	 * 
	 */
	public String editBookCategory()  {
		String msg = "";
		boolean success = true;
		try {
			if (oper.equals(Constants.OPERATION_DELETE)) //delete
				if (!bookCategoryDAO.deleteBookCategory(id)){
					success = false;
					msg = getText(Constants.MSG_SUBSUDIARY);
				}
			
			if (oper.equals(Constants.OPERATION_EDIT)) //edit
				if (!bookCategoryDAO.editBookCategory(id, name, name_ru)){
					success = false;
					msg =  getText(Constants.MSG_UNIQUE_FAILD);
				}
				
			
			if (oper.equals(Constants.OPERATION_ADD)) //add
				if (!bookCategoryDAO.addBookCategory(name, name_ru)){
					success = false;
					msg =  getText(Constants.MSG_UNIQUE_FAILD);
				}
			
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,Constants.MSG_DB_PROBLEM);
			
		}
		if (success)
			result = new AjaxResult("success");
		else
			result = new AjaxResult(false,msg);
		
		return SUCCESS;
	}
	
	
	/**
	 * Prepare languages 
	 * 
	 */
	public String prepareLanguages()  {
			
		Pagination pagination = null;
		try {
			count = languageDAO.getCountOfLanguages(filters);
			pagination = new Pagination(sidx,rows,count,page,sord);
			if (!_search)	  
				languages = languageDAO.getLanguagesForTable(pagination, null);
			else
				languages = languageDAO.getLanguagesForTable(pagination, filters);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,Constants.MSG_DB_PROBLEM);
		}
				
		List<Row> listRows = new ArrayList<>();
		for (int i = 0;i < languages.size();i++){
			SimpleBean language = languages.get(i);
			Row row = new Row();
			row.setId(language.getId());
			row.setCell(new Object[]{language.getId(),language.getName(),language.getName_ru()});
			listRows.add(row);
		}
		
		tableResult = new AjaxTableResult(page,pagination.getTotalPages(),count,listRows);
		return SUCCESS;
	}
	
	

	/**
	 * Edit language
	 * 
	 */
	public String editLanguage()  {
		String msg = "";
		boolean success = true;
		try {
			if (oper.equals(Constants.OPERATION_DELETE)) //delete
				if (!languageDAO.deleteLanguage(id)){
					success = false;
					msg = getText(Constants.MSG_SUBSUDIARY);
				}
			
			if (oper.equals(Constants.OPERATION_EDIT)) //edit
				if (!languageDAO.editLanguage(id, name, name_ru)){
					success = false;
					msg =  getText(Constants.MSG_UNIQUE_FAILD);
				}
				
			
			if (oper.equals(Constants.OPERATION_ADD)) //add
				if (!languageDAO.addLanguage(name,name_ru)){
					success = false;
					msg =  getText(Constants.MSG_UNIQUE_FAILD);
				}
			
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,Constants.MSG_DB_PROBLEM);
			
		}
		if (success)
			result = new AjaxResult("success");
		else
			result = new AjaxResult(false,msg);
		
		return SUCCESS;
	}
	
	
	
	/**
	 * Prepare user categories 
	 * 
	 */
	public String prepareUserCategories()  {
			
		Pagination pagination = null;
		try {
			count = userCategoryDAO.getCountOfUserCategories(filters);
			pagination = new Pagination(sidx,rows,count,page,sord);
			if (!_search)	  
				userCategories = userCategoryDAO.getUserCategoriesForTable(pagination, null);
			else
				userCategories = userCategoryDAO.getUserCategoriesForTable(pagination, filters);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,Constants.MSG_DB_PROBLEM);
		}
				
		List<Row> listRows = new ArrayList<Row>();
		for (int i = 0;i < userCategories.size();i++){
			SimpleBean userCategory = userCategories.get(i);
			Row row = new Row();
			row.setId(userCategory.getId());
			row.setCell(new Object[]{userCategory.getId(),userCategory.getName(),userCategory.getName_ru()});
			listRows.add(row);
		}
		
		tableResult = new AjaxTableResult(page,pagination.getTotalPages(),count,listRows);
		return SUCCESS;
	}
	
	
	/**
	 * Edit user category
	 * 
	 */
	public String editUserCategory()  {
		String msg = "";
		boolean success = true;
		try {
			if (oper.equals(Constants.OPERATION_DELETE)) //delete
				if (!userCategoryDAO.deleteUserCategory(id)){
					success = false;
					msg = getText(Constants.MSG_SUBSUDIARY);
				}
			
			if (oper.equals(Constants.OPERATION_EDIT)) //edit
				if (!userCategoryDAO.editUserCategory(id, name, name_ru)){
					success = false;
					msg =  getText(Constants.MSG_UNIQUE_FAILD);
				}
				
			
			if (oper.equals(Constants.OPERATION_ADD)) //add
				if (!userCategoryDAO.addUserCategory(name,name_ru)){
					success = false;
					msg =  getText(Constants.MSG_UNIQUE_FAILD);
				}
			
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,Constants.MSG_DB_PROBLEM);
			
		}
		if (success)
			result = new AjaxResult("success");
		else
			result = new AjaxResult(false,msg);
		
		return SUCCESS;
	}
	
	
	
	

	/**
	 * Prepare information for groups or cathedras
	 * 
	 */
	public String loadDepartments(){
		String departmentValue = "";
		
		try {
			departments = departmentDAO.getDepartments();
			departmentValue= StringBuilder.generateValueForList(departments);
				
			
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			setError(getText(Constants.MSG_DB_PROBLEM));
			return Action.INPUT;
		}
		request.put("departmentValue", departmentValue);
		
		
		return SUCCESS;
	}
	
	
	
	/**
	 * Prepare divisions
	 * 
	 */
	public String getDivisions()  {
			
		Pagination pagination = null;
		try {
			count = divisionDAO.getCountOfDivisions(filters);
			pagination = new Pagination(sidx,rows,count,page,sord);
			if (!_search)	  
				divisions = divisionDAO.getDivisionsForTable(pagination, null);
			else
				divisions = divisionDAO.getDivisionsForTable(pagination, filters);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,Constants.MSG_DB_PROBLEM);
		}
				
		List<Row> listRows = new ArrayList<>();
		for (int i = 0;i < divisions.size();i++){
			Division division = divisions.get(i);
			Row row = new Row();
			row.setId((int) division.getId());
			row.setCell(new Object[]{division.getId(),division.getName(),division.getDepartment().getId() });
			listRows.add(row);
		}
		
		tableResult = new AjaxTableResult(page,pagination.getTotalPages(),count,listRows);
		return SUCCESS;
	}
	
	/**
	 * Edit division
	 * 
	 */
	public String editDivision()  {
		String msg = "";
		boolean success = true;
		try {
			if (oper.equals(Constants.OPERATION_DELETE)) //delete
				if (!divisionDAO.deleteDivision(id)){
					success = false;
					msg = getText(Constants.MSG_SUBSUDIARY);
				}
			
			if (oper.equals(Constants.OPERATION_EDIT)) //edit
				if (!divisionDAO.editDivision(id, name, name_ru,departmentId)){
					success = false;
					msg =  getText(Constants.MSG_UNIQUE_FAILD);
				}
				
			
			if (oper.equals(Constants.OPERATION_ADD)) //add
				if (!divisionDAO.addDivision(name,name_ru,departmentId)){
					success = false;
					msg =  getText(Constants.MSG_UNIQUE_FAILD);
				}
			
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,Constants.MSG_DB_PROBLEM);
			
		}
		if (success)
			result = new AjaxResult("success");
		else
			result = new AjaxResult(false,msg);
		
		return SUCCESS;
	}
	
	
	
	
	
	
	
	
	/**
	 * prepare divisions for department
	 * 
	 */
	public String prepareDivisions()  {
		
		List<List<Division>> res = new ArrayList<>();
		try {
			res.add(divisionDAO.getDivisionsByDepartmentId(departmentId));
			
			
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,Constants.MSG_DB_PROBLEM);
			
		}
		
		
			result = new AjaxResult(res);
		
		
		return SUCCESS;
	}
	
	
	public String getLang(){
		return getLocale().getLanguage();
	}
	
	public AjaxTableResult getTableResult() {
		return tableResult;
	}

	public void setTableResult(AjaxTableResult tableResult) {
		this.tableResult = tableResult;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	

	public Filter getFilters() {
		return filters;
	}

	public void setFilters(String str) {
		this.filters = ConverterJSON.getFilter(str);
	}
	
	public AjaxResult getResult() {
		return result;
	}

	public void setResult(AjaxResult result) {
		this.result = result;
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

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}
	
	public boolean is_search() {
		return _search;
	}

	public void set_search(boolean search) {
		_search = search;
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

	public void setId(String id) {
		if(!id.equals(Constants.VALUE_EMPTY))
			this.id = Integer.parseInt(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName_ru() {
		return name_ru;
	}

	public void setName_ru(String nameRu) {
		name_ru = nameRu;
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
	


	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
}
