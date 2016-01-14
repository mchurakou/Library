package com.mikalai.library.ajax_json;

import java.util.ArrayList;
import java.util.List;


/**
 * Class provide work with AJAX for table
 * 
 * @author Mikalai_Churakou
 */
public class AjaxTableResult {
	public AjaxTableResult() {
		super();
		rows = new ArrayList<Row>();
	}
	
	private int page;
	private int total;
	private int records;
	private List<Row> rows;
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getRecords() {
		return records;
	}
	public void setRecords(int records) {
		this.records = records;
	}
	public List<Row> getRows() {
		return rows;
	}
	public void setRows(List<Row> rows) {
		this.rows = rows;
	}
	
	public AjaxTableResult(int page, int total, int rows, List<Row> listRows) {
		this.page = page;
		this.total = total;
		this.records = rows;
		this.rows = listRows;
	}
	
	
		

}
