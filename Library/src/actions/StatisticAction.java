package actions;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;

import utils.StringBuilder;

import utils.Constants;

import beans.SimpleBean;
import com.opensymphony.xwork2.ActionSupport;

import dao.CommentDB;
import dao.ElectronicBookDB;
import dao.RealBookDB;
import dao.StatisticDB;

import exceptions.DBException;
/**
 * Action for work with statistic
 * 
 * @author Mikalai_Churakou
 */
public class StatisticAction extends ActionSupport implements  RequestAware{
	
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
			realBooksPipeInformation = StatisticDB.getInformationForRealBooksPipe(getLocale().getLanguage());
			String realBooksPipeLabels = StringBuilder.generateLabelsForPipe(realBooksPipeInformation);
			request.put("realBooksPipeLabels", realBooksPipeLabels);
			
			electronicBooksPipeInformation = StatisticDB.getInformationForElectronicBooksPipe(getLocale().getLanguage());
			String electronicBooksPipeLabels = StringBuilder.generateLabelsForPipe(electronicBooksPipeInformation);
			request.put("electronicBooksPipeLabels", electronicBooksPipeLabels);
			
		
			
			bookStatistic = StatisticDB.bookrStatistic(getLocale().getLanguage());
			userStatistic = StatisticDB.userStatistic(getLocale().getLanguage());
			
			String userLabels  = StringBuilder.generateLabelsForPipe(userStatistic);
			request.put("userLabels", userLabels );
			String userCounts  = StringBuilder.generateClearCounts(userStatistic);
			request.put("userCounts", userCounts );
			
			String bookLabels  = StringBuilder.generateLabelsForPipe(bookStatistic);
			request.put("bookLabels", bookLabels );
			String bookCounts  = StringBuilder.generateClearCounts(bookStatistic);
			request.put("bookCounts", bookCounts );
					
			
		} catch (DBException e) {
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
