package com.mikalai.library.interceptors;

import java.util.Map;

import com.mikalai.library.utils.Constants;
import com.mikalai.library.beans.User;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * Check administrator role
 * 
 * @author Mikalai_Churakou
 */
public class AdministratorInterceptor implements Interceptor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String intercept(ActionInvocation actionInvocation) throws Exception {
		Map<String, Object> session = actionInvocation.getInvocationContext().getSession();
		User user = (User)session.get(Constants.ATTRIBUTE_USER);
		if (user.getRole().getId() != Constants.ADMINISTRATOR_ROLE_ID) {
			return Action.LOGIN;
		} else {
			return actionInvocation.invoke();
		}
	}

	
	public void init() {
	}
	
	public void destroy() {
	}

}