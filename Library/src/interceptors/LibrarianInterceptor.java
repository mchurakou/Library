package interceptors;

import java.util.Map;

import utils.Constants;
import beans.User;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * Check librarian role
 * 
 * @author Mikalai_Churakou
 */
public class LibrarianInterceptor implements Interceptor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String intercept(ActionInvocation actionInvocation) throws Exception {
		Map<String, Object> session = actionInvocation.getInvocationContext().getSession();
		User user = (User)session.get(Constants.ATTRIBUTE_USER);
		if (user.getRole().getId() != Constants.LIBRARIAN_ROLE_ID) {
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