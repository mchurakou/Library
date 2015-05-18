package actions;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
/**
 * Exit from application
 * 
 * @author Mikalai_Churakou
 */
public class ExitAction extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String execute()  {
		ServletActionContext.getRequest().getSession().invalidate();
		return SUCCESS;
	}
	

}
