package com.mikalai.library.actions;

import com.mikalai.library.ajax_json.*;
import com.mikalai.library.beans.RealBook;
import com.mikalai.library.beans.SimpleBean;
import com.mikalai.library.beans.User;
import com.mikalai.library.dao.BookDescriptionDAO;
import com.mikalai.library.dao.RealBookDAO;
import com.mikalai.library.dao.UserCategoryDAO;
;
import com.mikalai.library.utils.Constants;
import com.mikalai.library.utils.Pagination;
import com.mikalai.library.utils.StringBuilder;
import com.opensymphony.xwork2.ActionSupport;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * Action for work with real book
 * 
 * @author Mikalai_Churakou
 */
public class RealBookAction extends ActionSupport implements SessionAware,RequestAware {
	private static final Logger LOG = Logger.getLogger(RealBookAction.class);
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private UserCategoryDAO userCategoryDAO;
	@Autowired
	private RealBookDAO realBookDAO;
	@Autowired
	private BookDescriptionDAO bookDescriptionDAO;
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
	
	private int cost;
	private int inventoryNumber;
	private int bookDescriptionId;
	private Map<String, Object> request;
	


	private String error;
	
	private List<RealBook> realBooks;
	
	private List<SimpleBean> bookCategories;
	private List<SimpleBean> languages;
	private List<SimpleBean> userCategories;
	private Map<String, Object> session;
	
	private int realBookId;
	
	private int[] categoryIds;
	




	


	/**
	 * Prepare information for real books 
	 * @throws Exception
	 * 
	 */
	public String realBooks() throws Exception{
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
	 * Prepare list of real books for table 
	 * 
	 */
	public String prepareRealBooks()  {
			
		Pagination pagination = null;
		try {
			count = realBookDAO.getCountOfRealBooks( filters);
			pagination = new Pagination(sidx,rows,count,page,sord);
			if (!_search)	  
				realBooks =  realBookDAO.getRealBooksForTable(pagination,null);
			else
				realBooks = realBookDAO.getRealBooksForTable(pagination,filters);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,getText(Constants.MSG_DB_PROBLEM));
		}
				
		List<Row> listRows = new ArrayList<>();
		for (int i = 0;i < realBooks.size();i++){
			RealBook realBook = realBooks.get(i);
			Row row = new Row();
			row.setId(realBook.getId());
			row.setCell(new Object[]{realBook.getId(),realBook.getInventoryNumber(),realBook.getCost(),realBook.getName(),realBook.getAuthor(),realBook.getBookCategory().getName(),realBook.getPublicationPlace(),realBook.getPublicationYear(),realBook.getSize(),realBook.getLanguage().getName(),realBook.isAvailable()});
			listRows.add(row);
		}
		
		tableResult = new AjaxTableResult(page,pagination.getTotalPages(),count,listRows);
		return SUCCESS;
	}
	
	/**
	 * Prepare list of real books for table 
	 * 
	 */
	public String prepareRealBooksForUser()  {
		
		User user = (User) session.get(Constants.ATTRIBUTE_USER);
		int userCategoryId = user.getCategoryId();
		Pagination pagination = null;
		try {
			count = realBookDAO.getCountOfRealBooksForUser(filters,userCategoryId);
			pagination = new Pagination(sidx,rows,count,page,sord);
			if (!_search)	  
				realBooks =  realBookDAO.getRealBooksForTableByUserCategory(pagination,null,userCategoryId);
			else
				realBooks = realBookDAO.getRealBooksForTableByUserCategory(pagination,filters, userCategoryId);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,getText(Constants.MSG_DB_PROBLEM));
		}
				
		List<Row> listRows = new ArrayList<>();
		for (int i = 0;i < realBooks.size();i++){
			RealBook realBook = realBooks.get(i);
			Row row = new Row();
			row.setId(realBook.getId());
			row.setCell(new Object[]{realBook.getId(),realBook.getInventoryNumber(),realBook.getCost(),realBook.getName(),realBook.getAuthor(),realBook.getBookCategory().getName(),realBook.getPublicationPlace(),realBook.getPublicationYear(),realBook.getSize(),realBook.getLanguage().getName(),realBook.isAvailable()});
			listRows.add(row);
		}
		
		tableResult = new AjaxTableResult(page,pagination.getTotalPages(),count,listRows);
		return SUCCESS;
	}
	
	
	/**
	 * add real book 
	 * 
	 */
	public String addRealBook()  {
		try {
			if (realBookDAO.addRealBook(inventoryNumber,cost,bookDescriptionId))
				result = new AjaxResult(getText(Constants.MSG_BOOK_ADDED));
			else
				result = new AjaxResult(false,getText(Constants.MSG_DUPLICATE_INV));
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,getText(Constants.MSG_DB_PROBLEM));
		}
		return SUCCESS;
		
	}
	
	/**
	 * Edit real book 
	 * 
	 */
	public String editRealBook()  {
		boolean success = true;
		try {
			if (oper.equals(Constants.OPERATION_DELETE)) //delete
				success = realBookDAO.deleteRealBook(id);
			if (oper.equals(Constants.OPERATION_EDIT)) //edit
				realBookDAO.editRealBook(id, inventoryNumber,cost);
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
	 * get real privileges 
	 * 
	 */
	public String getRealPrivileges()  {
		try {
			result = new AjaxResult(realBookDAO.getUserCategoriesId(realBookId));
			
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,getText(Constants.MSG_DB_PROBLEM));
		}
		return SUCCESS;
		
	}
	
	/**
	 * set real privileges 
	 * 
	 */
	public String setRealPrivileges()  {
		try {
			realBookDAO.setUserCategoriesId(realBookId, categoryIds);
			result = new AjaxResult("success");
			
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,getText(Constants.MSG_DB_PROBLEM));
		}
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

	public int getBookDescriptionId() {
		return bookDescriptionId;
	}

	public void setBookDescriptionId(int bookDescriptionId) {
		this.bookDescriptionId = bookDescriptionId;
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


	public void set_search(boolean _search) {
		this._search = _search;
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


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	public String getError() {
		return error;
	}


	public void setError(String error) {
		this.error = error;
	}

	


	public int getCost() {
		return cost;
	}


	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public int getInventoryNumber() {
		return inventoryNumber;
	}


	public void setInventoryNumber(int inventoryNumber) {
		this.inventoryNumber = inventoryNumber;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
		
	}


	public void setRequest(Map<String, Object> r) {
		request = r;
		
	}
	

	public int getRealBookId() {
		return realBookId;
	}


	public void setRealBookId(int realBookId) {
		this.realBookId = realBookId;
	}
	
	public int[] getCategoryIds() {
		return categoryIds;
	}


	public void setCategoryIds(String str) {
		String[] strs = str.split(":");
		
		int [] ids = new int[strs.length];
		for (int i = 0; i < strs.length; i++)
			ids[i] = Integer.parseInt(strs[i]);
		this.categoryIds = ids;
	}
}
