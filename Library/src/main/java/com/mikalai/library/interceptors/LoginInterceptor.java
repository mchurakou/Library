package com.mikalai.library.interceptors;

import java.sql.SQLException;
import java.util.Map;

import com.mikalai.library.utils.Constants;

import com.mikalai.library.beans.User;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import com.mikalai.library.dao.DBConnectionPool;
/**
 * Check authentication
 * 
 * @author Mikalai_Churakou
 */
public class LoginInterceptor implements Interceptor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public void destroy() {
		try {
			DBConnectionPool.getConnPool().closeAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		Map<String, Object> session = actionInvocation.getInvocationContext().getSession();
		User user = (User)session.get(Constants.ATTRIBUTE_USER);
		if (user == null) {
			return Action.LOGIN;
		} else {
			return actionInvocation.invoke();
		}
	}

	
	public void init() {
				
	}

}
