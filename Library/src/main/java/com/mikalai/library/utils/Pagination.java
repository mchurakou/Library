package com.mikalai.library.utils;
/**
 * Provides work with pagination
 * 
 * @author Mikalai_Churakou
 */
public class Pagination {
	private String sidx;// number of field for sort
	private int rows;// number of rows
	private int count;//count rows in db
	private int totalPages;//count of pages
	private int page;// number of pages
	private String sord;// way for sort
	private int start;// number of start row
	private int end;// number of end row
	
	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Pagination(String sidx, int rows, int count, int page, String sord) {
		if(sidx.equals("")) 
			this.sidx = Constants.FIELD_ID;
		else
			this.sidx = sidx;
		this.rows = rows;
		this.count = count;
		this.sord = sord;
		
		if( count > 0 && rows > 0) 
			totalPages = (count % rows > 0) ? (count / rows + 1) : (count / rows);
		else
			totalPages = 0;
		
		this.page = page;
		if (page > totalPages)
			this.page = totalPages;
		
		start = rows*page - rows + 1; 
		
		if(start <= 0) 
			start = 1;
		end =  start + rows - 1;
	}
	
	public Pagination( int rows, int count, int page) {
		
		this.rows = rows;
		this.count = count;
		
		
		if( count > 0 && rows > 0) 
			totalPages = (count % rows > 0) ? (count / rows + 1) : (count / rows);
		else
			totalPages = 0;
		
		this.page = page;
		if (page > totalPages)
			this.page = totalPages;
		
		start = rows*page - rows + 1; 
		
		if(start <= 0) 
			start = 1;
		end =  start + rows - 1;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

}
