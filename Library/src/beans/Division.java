package beans;
/**
 * Class contain information about group or cathedra
 * 
 * @author Mikalai_Churakou
 */
public class Division extends SimpleBean{
	private int departmentId;

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public Division() {
		super();
	}

}
