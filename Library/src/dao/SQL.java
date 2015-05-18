package dao;

import ajax_json.Filter;
import ajax_json.Rule;
import utils.Constants;

/**
 * Generation SQLl code
 * 
 * @author Mikalai_Churakou
 */
public class SQL {
	
	/**
	 * SQL for filter in jqGrid
	 * @param filter
	 * @return SQL string
	 */
	public static String getSqlFilter(Filter filter){
		String filterStr = "";
		if (filter != null) {
			String operation = filter.getGroupOp();
			filterStr = " WHERE ";
			for (int i = 0; i < filter.getRules().size();i++){
				if (i != 0)
					filterStr += " " + operation + " ";
				Rule r = filter.getRules().get(i);
				String field = r.getField();
				
				if (field.endsWith("Id")) // search by lookup field
					field = field.substring(0, field.length() - 2);
				
				filterStr += " " + field + " ";
				
				if (r.getOp().equals(Constants.EQUALS)) //equals
					filterStr += " = '" + r.getData() + "' ";
				
				if (r.getOp().equals(Constants.NOT_EQUALS)) //not equals
					filterStr += " <> '" + r.getData() + "' ";
				
				if (r.getOp().equals(Constants.BEGIN_WITH)) //begin with
					filterStr += " LIKE '" + r.getData() + "%' "; 
				
				if (r.getOp().equals(Constants.CONTAIN)) //contain in
					filterStr += " LIKE " + " '%" + r.getData()+ "%' ";
				
				if (r.getOp().equals(Constants.LESS)) //less
					filterStr += " < " + " '" + r.getData()+ "' ";
				
				if (r.getOp().equals(Constants.LESS_OR_EQUAL)) //less or equals
					filterStr += " <= " + " '" + r.getData()+ "' ";
				
				if (r.getOp().equals(Constants.GREATER)) //greater
					filterStr += " > " + " '" + r.getData()+ "' ";
				
				if (r.getOp().equals(Constants.GREATER_OR_EQUAL)) //greater or equals
					filterStr += " >= " + " '" + r.getData()+ "' ";
			}
		}
		return filterStr;
	}

}
