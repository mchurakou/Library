package ajax_json;
/**
 * Row information for return json object
 * 
 * @author Mikalai_Churakou
 */
public class Row {

	public Object[] getCell() {
		return cell;
	}
	public void setCell(Object[] cell) {
		this.cell = cell;
	}
	private int id;
	private Object[] cell;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

}
