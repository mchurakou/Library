package com.mikalai.library.actions;

import com.mikalai.library.beans.LibrarianReportRecord;
import com.mikalai.library.dao.ReportDAO;
import com.mikalai.library.utils.Constants;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.jasperreports.engine.JasperCompileManager;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.interceptor.RequestAware;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

;



/**
 * work with reports
 * 
 * @author Mikalai_Churakou
 */
public class ReportAction  extends ActionSupport implements RequestAware{
	private static final Logger LOG = LogManager.getLogger(ReportAction.class);
	SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_REPORT);
	List<LibrarianReportRecord> records;
	@Inject
	private ReportDAO reportDAO;
	private ActionContext ac = ActionContext.getContext();
	private ServletContext sc =  (ServletContext)ac.get(StrutsStatics.SERVLET_CONTEXT);
	private Timestamp start;
	private Timestamp end;
	private Map<String, Object> request;
	private HashMap reportParameters = new HashMap();
	


	public String execute()  {
		try {
			records = reportDAO.getRows(start, end);
			
			String toDay = formatter.format(new Date());
			reportParameters.put("toDay", toDay);
			reportParameters.put("title", getText(Constants.MSG_LIBRARIAN_REPORT));
			reportParameters.put("period",formatter.format(start) + " - " + formatter.format(end));
			
			int givenCount = 0;
			int returnedCount = 0;
			
			for (int i = 0;i < records.size(); i++){
				LibrarianReportRecord record = records.get(i);
				if (record.getOperation().equals("returned")){
					records.get(i).setOperation(getText(Constants.MSG_RETURNED));
					returnedCount++;
				}
				if (record.getOperation().equals("given")){
					records.get(i).setOperation(getText(Constants.MSG_GIVEN));
					givenCount++;
				}
			}
			
			reportParameters.put("givenSummary",getText(Constants.MSG_TOTAL_GIVEN) + " " +  givenCount);
			reportParameters.put("returnedSummary",getText(Constants.MSG_TOTAL_RETURNED) + " " + returnedCount);
			
			reportParameters.put("titleLibrarian", getText(Constants.MSG_LIBRARIAN));
			reportParameters.put("titleOperation", getText(Constants.MSG_OPERATION));
			reportParameters.put("titleDate", getText(Constants.MSG_DATE));
			reportParameters.put("titleStart", getText(Constants.MSG_START));
			reportParameters.put("titleEnd", getText(Constants.MSG_END));
			reportParameters.put("titleInventory", getText(Constants.MSG_INVENTORY));
			reportParameters.put("titleBook", getText(Constants.MSG_BOOK));
			reportParameters.put("titleUser", getText(Constants.MSG_USER));

			JasperCompileManager.compileReportToFile(sc.getRealPath("/reports/report.jrxml"), sc.getRealPath("/reports/report_compile.jasper"));
			
			
		}
		catch (Exception e1) {
			LOG.error(e1.getMessage(),e1);
		}
		
		return SUCCESS;
		
	}
	
	
	public List<LibrarianReportRecord> getRecords() {
		return records;
	}


	public void setRecords(List<LibrarianReportRecord> records) {
		this.records = records;
	}


	public Timestamp getStart() {
		return start;
	}
	public void setStart(String start) throws ParseException {
		this.start =  new Timestamp(formatter.parse(start).getTime());
	}


	
	public Timestamp getEnd() {
		return end;
	}


	public void setEnd(String end) throws ParseException {
		this.end = new Timestamp(formatter.parse(end).getTime());
	}


	public void setRequest(Map<String, Object> r) {
		request = r;
		
	}


	public HashMap getReportParameters() {
		return reportParameters;
	}


	public void setReportParameters(HashMap reportParameters) {
		this.reportParameters = reportParameters;
	}
	
	

}
