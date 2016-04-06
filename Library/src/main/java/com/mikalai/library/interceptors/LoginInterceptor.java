package com.mikalai.library.interceptors;

import com.mikalai.library.beans.User;
import com.mikalai.library.utils.Constants;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import java.util.Map;
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


	public String intercept(ActionInvocation actionInvocation) throws Exception {
		Map<String, Object> session = actionInvocation.getInvocationContext().getSession();
		User user = (User)session.get(Constants.ATTRIBUTE_USER);
		if (user == null) {
			return Action.LOGIN;
		} else {
			return actionInvocation.invoke();
		}
	}


	@Override
	public void destroy() {

	}

	public void init() {
				
	}

}
