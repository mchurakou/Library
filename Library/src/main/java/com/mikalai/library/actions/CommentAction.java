package com.mikalai.library.actions;

import com.mikalai.library.ajax_json.AjaxResult;
import com.mikalai.library.beans.Comment;
import com.mikalai.library.beans.ElectronicBook;
import com.mikalai.library.beans.User;
import com.mikalai.library.dao.CommentDAO;
import com.mikalai.library.dao.ElectronicBookDAO;
import com.mikalai.library.utils.Constants;
import com.mikalai.library.utils.Pagination;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * Action for work with comments
 * 
 * @author Mikalai_Churakou
 */
public class CommentAction extends ActionSupport  implements SessionAware{
	@Inject
	private ElectronicBookDAO electronicBookDAO;
	@Inject
	private CommentDAO commentDAO;
	private static final Logger LOG = LogManager.getLogger();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int electronicBookId;
	private ElectronicBook electronicBook;
	private String error;
	private String content;
	private List<Comment> comments;
	private int commentId;
	
	

	

	private Map<String, Object> session;
	private Pagination pagination;
	
	private int count;
	private int rows = 10;
	private int page = 1;
	
	private AjaxResult result;
	
	
	/**
	 * load comments
	 * 
	 */
	public String loadComments(){
		try {
			comments = commentDAO.getComments(electronicBookId);
			electronicBook = electronicBookDAO.getElectronicBook(electronicBookId);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			setError(getText(Constants.MSG_DB_PROBLEM));
			return INPUT;
		}
		
		List<Object> results = new ArrayList<>();
		results.add(electronicBook);
		results.add(comments);
		result = new AjaxResult(results);
		return SUCCESS;
	}
	
	/**
	 * add comment
	 * 
	 */
	public String addComment(){
		User user = (User) session.get(Constants.ATTRIBUTE_USER);
		try {
			commentDAO.addComment(user.getId(), electronicBookId, content);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			return INPUT;
		}
		result = new AjaxResult("success");
		return SUCCESS;
	}
	
	/**
	 * delete comment
	 * 
	 */
	public String deleteComment(){
		try {
			commentDAO.deleteComment(commentId);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			return INPUT;
		}
		result = new AjaxResult("success");
		return SUCCESS;
	}
	
	public int getElectronicBookId() {
		return electronicBookId;
	}
	public void setElectronicBookId(int electronicBookId) {
		this.electronicBookId = electronicBookId;
	}
	
	public ElectronicBook getElectronicBook() {
		return electronicBook;
	}

	public void setElectronicBook(ElectronicBook electronicBook) {
		this.electronicBook = electronicBook;
	}
	

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	public String getLang(){
		return getLocale().getLanguage();
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public void setSession(Map<String, Object> session) {
		this.session = session;
		
	}
	
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}



	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
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
	
	public AjaxResult getResult() {
		return result;
	}

	public void setResult(AjaxResult result) {
		this.result = result;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}


}
