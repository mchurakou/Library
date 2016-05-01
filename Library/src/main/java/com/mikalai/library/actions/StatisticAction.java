package com.mikalai.library.actions;

import com.mikalai.library.beans.SimpleBean;
import com.mikalai.library.dao.StatisticDAO;
import com.mikalai.library.utils.Constants;
import com.mikalai.library.utils.StringBuilder;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.RequestAware;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

;
/**
 * Action for work with statistic
 * 
 * @author Mikalai_Churakou
 */
public class StatisticAction extends ActionSupport implements  RequestAware{
	@Inject
	private StatisticDAO statisticDAO;

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String error;
	
	private List<SimpleBean> realBooksPipeInformation;
	private List<SimpleBean> electronicBooksPipeInformation;
	private Map<String, Object> request;
		
	private List<SimpleBean> bookStatistic;
	private List<SimpleBean> userStatistic;
	
	/**
	 * prepare statistic
	 * 
	 */	

	public String statistic(){
		try {
			realBooksPipeInformation = statisticDAO.getInformationForRealBooksPipe();
			String realBooksPipeLabels = StringBuilder.generateLabelsForPipe(realBooksPipeInformation);
			request.put("realBooksPipeLabels", realBooksPipeLabels);
			
			electronicBooksPipeInformation = statisticDAO.getInformationForElectronicBooksPipe();
			String electronicBooksPipeLabels = StringBuilder.generateLabelsForPipe(electronicBooksPipeInformation);
			request.put("electronicBooksPipeLabels", electronicBooksPipeLabels);
			
		
			
			bookStatistic = statisticDAO.bookrStatistic();
			userStatistic = statisticDAO.userStatistic();
			
			String userLabels  = StringBuilder.generateLabelsForPipe(userStatistic);
			request.put("userLabels", userLabels );
			String userCounts  = StringBuilder.generateClearCounts(userStatistic);
			request.put("userCounts", userCounts );
			
			String bookLabels  = StringBuilder.generateLabelsForPipe(bookStatistic);
			request.put("bookLabels", bookLabels );
			String bookCounts  = StringBuilder.generateClearCounts(bookStatistic);
			request.put("bookCounts", bookCounts );
					
			
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			setError(getText(Constants.MSG_DB_PROBLEM));
			return INPUT;
		}
		return SUCCESS;
	}

	
	
	


	public String getError() {
		return error;
	}



	public void setError(String error) {
		this.error = error;
	}
	public List<SimpleBean> getRealBooksPipeInformation() {
		return realBooksPipeInformation;
	}
	public void setRealBooksPipeInformation(
			List<SimpleBean> realBooksPipeInformation) {
		this.realBooksPipeInformation = realBooksPipeInformation;
	}
	
	public List<SimpleBean> getElectronicBooksPipeInformation() {
		return electronicBooksPipeInformation;
	}



	public void setElectronicBooksPipeInformation(
			List<SimpleBean> electronicBooksPipeInformation) {
		this.electronicBooksPipeInformation = electronicBooksPipeInformation;
	}
	
	








	public void setRequest(Map<String, Object> r) {
		request = r;
		
	}



	
}
