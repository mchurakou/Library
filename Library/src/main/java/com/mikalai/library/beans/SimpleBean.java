package com.mikalai.library.beans;
/**
 * Class contain simple information: id, name
 * 
 * @author Mikalai_Churakou
 */
public class SimpleBean {
	private int id;
	private String name;
	private String name_ru;
	
	private int count;
	private String title;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public SimpleBean(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}


	public SimpleBean(String name) {
		this.name = name;
	}
	public SimpleBean() {
	}
	
	public String getName_ru() {
		return name_ru;
	}
	public void setName_ru(String nameRu) {
		name_ru = nameRu;
	}
	

}
