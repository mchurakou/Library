package com.mikalai.library.actions;

import com.mikalai.library.ajax_json.AjaxResult;
import com.mikalai.library.ajax_json.AjaxTableResult;
import com.mikalai.library.ajax_json.Row;
import com.mikalai.library.beans.Queue;
import com.mikalai.library.beans.User;
import com.mikalai.library.dao.QueueDB;
import com.mikalai.library.exceptions.DBException;
import com.mikalai.library.utils.Constants;
import com.mikalai.library.utils.Pagination;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * Action for work with user's queues
 * 
 * @author Mikalai_Churakou
 */
public class QueueAction extends ActionSupport implements SessionAware{
	private static final Logger LOG = LogManager.getLogger();
	/**
	 * 
	 */
	private static final long serialVersionUID = -8358452265769880826L;
	private Map<String, Object> session;
	private AjaxResult result;
	private int realBookId;
	private int count;
	private int page;
	private int rows;
	private List<Queue> queues;
	private AjaxTableResult tableResult;
	
	private int id;
	
	
	

	/**
	 * add user to queue 
	 * 
	 */
	public String addToQueue()  {
		User user = (User) session.get(Constants.ATTRIBUTE_USER);
		try {
			
			if (QueueDB.addUserInQueue(user.getId(), realBookId))
				result = new AjaxResult(getText(Constants.MSG_YOU_ADDED_TO_QUEUE));
			else
				result = new AjaxResult(false,getText(Constants.MSG_YOU_CANT_ADD_TWICE));
			
		} catch (DBException e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,getText(Constants.MSG_DB_PROBLEM));
		}
		return SUCCESS;
		
	}
	
	/**
	 * Prepare queues 
	 * 
	 */
	public String prepareQueues()  {
		Pagination pagination = null;
		try {
			count = QueueDB.getCountOfQueues(realBookId);
			pagination = new Pagination("date",rows,count,page,"ASC");
			queues = QueueDB.getQueuesForTable(pagination,realBookId);
			
		} catch (DBException e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,Constants.MSG_DB_PROBLEM);
		}
				
		List<Row> listRows = new ArrayList<Row>();
		for (int i = 0;i < queues.size();i++){
			Queue queue = queues.get(i);
			Row row = new Row();
			row.setId(queue.getId());
			row.setCell(new Object[]{queue.getId(),queue.getUserId(),queue.getLogin(),queue.getFirstName(),queue.getSecondName(),queue.getDate(),queue.getEmail()});
			listRows.add(row);
		}
		
		tableResult = new AjaxTableResult(page,pagination.getTotalPages(),count,listRows);
		return SUCCESS;
	}
	
	/**
	 * delete user from queue 
	 * 
	 */
	public String deleteFromQueue()  {
		User user = (User) session.get(Constants.ATTRIBUTE_USER);
		try {
			
			if (QueueDB.deleteUserFromQueue(user.getId(), realBookId))
				result = new AjaxResult(getText(Constants.MSG_YOU_DELETED_FROM_QUEUE));
			else
				result = new AjaxResult(false,getText(Constants.MSG_YOU_DONT_ATTEND_IN_QUEUE));
			
		} catch (DBException e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,getText(Constants.MSG_DB_PROBLEM));
		}
		return SUCCESS;
		
	}
	
	/**
	 * Edit queue 
	 * 
	 */
	public String deleteFromQueueById()  {
		
		try {
			QueueDB.deleteUserFromQueueById(id);
		} catch (DBException e) {
			LOG.error(e.getMessage(),e);
			result = new AjaxResult(false,Constants.MSG_DB_PROBLEM);
			
		}
		
		result = new AjaxResult(getText(Constants.MSG_USER_DELETED_FROM_QUEUE));
		
		
		return SUCCESS;
	}
	
	
	public AjaxResult getResult() {
		return result;
	}

	public void setResult(AjaxResult result) {
		this.result = result;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
		
	}
	
	public int getRealBookId() {
		return realBookId;
	}

	public void setRealBookId(int realBookId) {
		this.realBookId = realBookId;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
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
	
	public AjaxTableResult getTableResult() {
		return tableResult;
	}

	public void setTableResult(AjaxTableResult tableResult) {
		this.tableResult = tableResult;
	}
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

