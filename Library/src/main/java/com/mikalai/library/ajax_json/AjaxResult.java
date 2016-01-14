package com.mikalai.library.ajax_json;
/**
 * Class provide work with AJAX
 * 
 * @author Mikalai_Churakou
 */
public class AjaxResult {
	private boolean success;
	private String error;
	private Object data;
	
	public AjaxResult() {
		super();
		this.success = true;
	}

	public AjaxResult(boolean success, String error) {
		super();
		this.success = success;
		this.error = error;
		this.data = null;
	}
	
	public AjaxResult(Object data){
		this.success = true;
		this.error = null;
		this.data = data;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
}

