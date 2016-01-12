package com.mikalai.library.ajax_json;

import java.util.List;


/**
 * Filter information for searching in jqGrid
 * 
 * @author Mikalai_Churakou
 */
public class Filter {
	private String groupOp;
	public Filter() {
		super();
	}
	
	public Filter(String groupOp, List<Rule> rules) {
		super();
		this.groupOp = groupOp;
		this.rules = rules;
	}
	
	public List<Rule> getRules() {
		return rules;
	}
	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

	private List<Rule> rules;
	
	
	public String getGroupOp() {
		return groupOp;
	}
	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	}
	

}
