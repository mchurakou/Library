package com.mikalai.library.actions;


import com.mikalai.library.ajax_json.*;
import com.mikalai.library.beans.ElectronicBook;
import com.mikalai.library.beans.SimpleBean;
import com.mikalai.library.beans.User;
import com.mikalai.library.dao.BookDescriptionDAO;
import com.mikalai.library.dao.ElectronicBookDAO;
import com.mikalai.library.dao.UserCategoryDAO;
import com.mikalai.library.utils.Constants;
import com.mikalai.library.utils.Pagination;
import com.mikalai.library.utils.StringBuilder;
import com.opensymphony.xwork2.ActionSupport;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Action for work with electronic books
 * 
 * @author Mikalai_Churakou
 */
public class ElectronicBookAction extends ActionSupport implements SessionAware, RequestAware{
	private static final Logger LOG = Logger.getLogger(ElectronicBookAction.class);
	private static final long serialVersionUID = 1L;
	@Inject
	private UserCategoryDAO userCategoryDAO;
	@Inject
	private ElectronicBookDAO electronicBookDAO;
	@Inject
	private BookDescriptionDAO bookDescriptionDAO;
	private List<SimpleBean> bookCategories;
	private List<SimpleBean> languages;
	private List<SimpleBean> userCategories;
	
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
	
	private List<ElectronicBook> electronicBooks;
	
	private Map<String, Object> session;
	private Map<String, Object> request;

	private int electronicBookId;
	private int[] categoryIds;



	

	/**
	 * Ebooks action 
	 * @throws Exception
	 * 
	 */
	public String eBooks() throws Exception {
		bookCategories = bookDescriptionDAO.getBookCategories();
		String bookCategoryValue = StringBuilder.generateValueForList(bookCategories);
		request.put("bookCategoryValue", bookCategoryValue);
		
		languages = bookDescriptionDAO.getLanguages();
		String languagesValue = StringBuilder.generateValueForList(languages);
		request.put("languagesValue", languagesValue);
		userCategories = userCategoryDAO.getUserCategories();
		
		return SUCCESS;
	}
	
	/**
	 * Prepare list of electronic books for table 
	 * 
	 */
	public String prepareElectronicBooks()  {
		User user = (User) session.get(Constants.ATTRIBUTE_USER);
		int userCategoryId = user.getCategoryId();
		
		Pagination pagination = null;
		try {
			count = electronicBookDAO.getCountOfElectronicBooks(userCategoryId, filters);
			pagination = new Pagination(sidx,rows,count,page,sord);
			if (!_search)	  
				electronicBooks =  electronicBookDAO.getElectronicBooksForTable(pagination,null);
			else
				electronicBooks = electronicBookDAO.getElectronicBooksForTable(pagination,filters);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,getText(Constants.MSG_DB_PROBLEM));
		}
				
		List<Row> listRows = new ArrayList<>();
		for (int i = 0;i < electronicBooks.size();i++){
			ElectronicBook electronicBook = electronicBooks.get(i);
			Row row = new Row();
			row.setId(electronicBook.getId());
			row.setCell(new Object[]{electronicBook.getId(),electronicBook.getFileName(),electronicBook.getCapacity(),electronicBook.getExtension(),electronicBook.getName(),electronicBook.getAuthor(),electronicBook.getBookCategory().getName(),electronicBook.getSize(),electronicBook.getLanguage().getName()});
			listRows.add(row);
		}
		
		tableResult = new AjaxTableResult(page,pagination.getTotalPages(),count,listRows);
		return SUCCESS;
	}
	
	/**
	 * Edit electronic book 
	 * 
	 */
	public String editElectronicBook()  {
		boolean success = true;
		try {
			if (oper.equals(Constants.OPERATION_DELETE)){ //delete
				success = electronicBookDAO.deleteElectronicBook(id);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,getText(Constants.MSG_DB_PROBLEM));
			
		}
		
		if (success)
			result = new AjaxResult("success");
		else
			result = new AjaxResult(false,getText(Constants.MSG_SUBSUDIARY));
		
		
		return SUCCESS;
	}
	
	
	/**
	 * get electronic privileges 
	 * 
	 */
	public String getElectronicPrivileges()  {
		try {
			result = new AjaxResult(electronicBookDAO.getUserCategoriesId(electronicBookId));
			
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,getText(Constants.MSG_DB_PROBLEM));
		}
		return SUCCESS;
		
	}
	
	/**
	 * set electronic privileges 
	 * 
	 */
	public String setElectronicPrivileges()  {
		try {
			electronicBookDAO.setUserCategoriesId(electronicBookId, categoryIds);
			result = new AjaxResult("success");
			
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,getText(Constants.MSG_DB_PROBLEM));
		}
		return SUCCESS;
		
	}
	
	/**
	 * Prepare list of electronic books for table 
	 * 
	 */
	public String prepareElectronicBooksForUser()  {
		User user = (User) session.get(Constants.ATTRIBUTE_USER);
		int userCategoryId = user.getCategoryId();
		
		Pagination pagination = null;
		try {
			count = electronicBookDAO.getCountOfElectronicBooksForUser( filters,userCategoryId);
			pagination = new Pagination(sidx,rows,count,page,sord);
			if (!_search)	  
				electronicBooks =  electronicBookDAO.getElectronicBooksForTableByUserCategory(pagination, null, userCategoryId);
			else
				electronicBooks = electronicBookDAO.getElectronicBooksForTableByUserCategory(pagination, filters, userCategoryId);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,getText(Constants.MSG_DB_PROBLEM));
		}
				
		List<Row> listRows = new ArrayList<>();
		for (int i = 0;i < electronicBooks.size();i++){
			ElectronicBook electronicBook = electronicBooks.get(i);
			Row row = new Row();
			row.setId(electronicBook.getId());
			row.setCell(new Object[]{electronicBook.getId(),electronicBook.getFileName(),electronicBook.getCapacity(),electronicBook.getExtension(),electronicBook.getName(),electronicBook.getAuthor(),electronicBook.getBookCategory().getName(),electronicBook.getSize(),electronicBook.getLanguage().getName()});
			listRows.add(row);
		}
		
		tableResult = new AjaxTableResult(page,pagination.getTotalPages(),count,listRows);
		return SUCCESS;
	}
	
	
	public String getLang(){
		return getLocale().getLanguage();
	}

	public List<SimpleBean> getBookCategories() {
		return bookCategories;
	}



	public void setBookCategories(List<SimpleBean> bookCategories) {
		this.bookCategories = bookCategories;
	}



	public List<SimpleBean> getLanguages() {
		return languages;
	}



	public void setLanguages(List<SimpleBean> languages) {
		this.languages = languages;
	}



	public List<SimpleBean> getUserCategories() {
		return userCategories;
	}



	public void setUserCategories(List<SimpleBean> userCategories) {
		this.userCategories = userCategories;
	}

	public AjaxResult getResult() {
		return result;
	}

	public void setResult(AjaxResult result) {
		this.result = result;
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

	public void setSession(Map<String, Object> session) {
		this.session = session;
		
	}

	
	public void setRequest(Map<String, Object> r) {
		request = r;
		
	}
	
	public int getElectronicBookId() {
		return electronicBookId;
	}

	public void setElectronicBookId(int electronicBookId) {
		this.electronicBookId = electronicBookId;
	}
	
	public void setCategoryIds(String str) {
		String[] strs = str.split(":");
		
		int [] ids = new int[strs.length];
		for (int i = 0; i < strs.length; i++)
			ids[i] = Integer.parseInt(strs[i]);
		this.categoryIds = ids;
	}
	
}
