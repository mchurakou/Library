package actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import utils.StringBuilder;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.RequestAware;

import utils.Constants;
import utils.Pagination;
import ajax_json.AjaxResult;
import ajax_json.AjaxTableResult;
import ajax_json.ConverterJSON;
import ajax_json.Filter;
import ajax_json.Row;
import beans.BookDescription;
import beans.SimpleBean;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import dao.BookDescriptionDB;
import dao.UserDB;

import exceptions.DBException;
/**
 * Action for work with book description
 * 
 * @author Mikalai_Churakou
 */
public class BookDescriptionAction extends ActionSupport implements RequestAware{
	public static final Logger LOG = Logger.getLogger(UserAction.class); 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

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
	private String error;
	
	private List<BookDescription> bookDescriptions;
	private List<SimpleBean> bookCategories;
	private List<SimpleBean> languages;
	private List<SimpleBean> userCategories;
	
	private String name;
	private String author;
	private int bookCategoryId;
	private String publicationPlace;
	private int publicationYear;
	private int size;
	private int languageId;
	private int userCategoryId;
	
	private Map request;
	
	
	

	/**
	 * Prepare information for book descriptions  
	 * 
	 */
	public String bookDescriptions(){
		String bookCategoriesValue = "";
		String languagesValue = "";
		String userCategoryValue = "";
		try {
			bookCategories = BookDescriptionDB.getBookCategories(getLocale().getLanguage());
			bookCategoriesValue = StringBuilder.generateValueForList(bookCategories);
			
			languages = BookDescriptionDB.getLanguages(getLocale().getLanguage());
			languagesValue = StringBuilder.generateValueForList(languages);
			
			
			
		} catch (DBException e) {
			LOG.error(e.getMessage(),e);
			setError(getText(Constants.MSG_DB_PROBLEM));
			return Action.INPUT;
		}
		request.put("bookCategoriesValue", bookCategoriesValue);
		request.put("languagesValue", languagesValue);
		
		return SUCCESS;
	}
	
	/**
	 * Prepare list of book descriptions for table 
	 * 
	 */
	public String prepareBookDescriptions()  {
		Pagination pagination = null;
		try {
			count = BookDescriptionDB.getCountOfBookDescriptions(filters);
			pagination = new Pagination(sidx,rows,count,page,sord);
			if (!_search)	  
				bookDescriptions = BookDescriptionDB.getBookDescriptionsForTable(pagination,null,getLocale().getLanguage());
			else
				bookDescriptions = BookDescriptionDB.getBookDescriptionsForTable(pagination,filters,getLocale().getLanguage());
		} catch (DBException e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,Constants.MSG_DB_PROBLEM);
		}
				
		List<Row> listRows = new ArrayList<Row>();
		for (int i = 0;i < bookDescriptions.size();i++){
			BookDescription bookDescription = bookDescriptions.get(i);
			Row row = new Row();
			row.setId(bookDescription.getId());
			row.setCell(new Object[]{bookDescription.getId(),bookDescription.getName(),bookDescription.getAuthor(),bookDescription.getBookCategory().getId(),bookDescription.getPublicationPlace(),bookDescription.getPublicationYear(),bookDescription.getSize(),bookDescription.getLanguage().getId()});
			listRows.add(row);
		}
		
		tableResult = new AjaxTableResult(page,pagination.getTotalPages(),count,listRows);
		return SUCCESS;
	}
	
	
	/**
	 * Edit book's descriptions 
	 * 
	 */
	public String editBookDescription()  {
		boolean success = true;
		try {
			if (oper.equals(Constants.OPERATION_DELETE)) //delete
				success = BookDescriptionDB.deleteBookDescription(id);
			if (oper.equals(Constants.OPERATION_EDIT)) //edit
				BookDescriptionDB.editBookDescription(id, name, author, bookCategoryId, publicationPlace, publicationYear, size, languageId);
			if (oper.equals(Constants.OPERATION_ADD)) //add
				BookDescriptionDB.addBookDescription(name, author, bookCategoryId, publicationPlace, publicationYear, size, languageId);
				
		} catch (DBException e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,Constants.MSG_DB_PROBLEM);
			
		}
		
		if (success)
			result = new AjaxResult("success");
		else
			result = new AjaxResult(false,getText(Constants.MSG_SUBSUDIARY));
		
		
		return SUCCESS;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getBookCategoryId() {
		return bookCategoryId;
	}

	public void setBookCategoryId(int bookCategoryId) {
		this.bookCategoryId = bookCategoryId;
	}

	public String getPublicationPlace() {
		return publicationPlace;
	}

	public void setPublicationPlace(String publicationPlace) {
		this.publicationPlace = publicationPlace;
	}

	public int getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getLanguageId() {
		return languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	public int getUserCategoryId() {
		return userCategoryId;
	}

	public void setUserCategoryId(int userCategoryId) {
		this.userCategoryId = userCategoryId;
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
		if (sidx.endsWith("Id")) // sort by lookup field
			sidx = sidx.substring(0, sidx.length() - 2);
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

	public void setId(String id) {
		if(!id.equals(Constants.VALUE_EMPTY))
			this.id = Integer.parseInt(id);
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
	
	public List<SimpleBean> getLanguages() {
		return languages;
	}

	public void setLanguages(List<SimpleBean> languages) {
		this.languages = languages;
	}

	public List<SimpleBean> getBookCategories() {
		return bookCategories;
	}

	public void setBookCategories(List<SimpleBean> bookCategories) {
		this.bookCategories = bookCategories;
	}

	public List<BookDescription> getBookDescriptions() {
		return bookDescriptions;
	}

	public void setBookDescriptions(List<BookDescription> bookDescriptions) {
		this.bookDescriptions = bookDescriptions;
	}
	
	public List<SimpleBean> getUserCategories() {
		return userCategories;
	}

	public void setUserCategories(List<SimpleBean> userCategories) {
		this.userCategories = userCategories;
	}

	
	public void setRequest(Map<String, Object> r) {
		request = r;
		
	}


}
