<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<div align="right">
			<a href="loginPage?lang=ru"><img src=img/ru.jpg border=1 alt="Ru"></a>
			<a href="loginPage?lang=en"><img src=img/en.jpg border=1 alt="En"></a>
	</div>
	<br/>
	
	<center>
		<b><spring:message code="login.Enter_login_and_pass" /></b>
		<c:if test="${error != null}">
			<div class="error ui-corner-all">
				<span style="float: left; margin: 2px;" class="errorIcon"></span>
				<c:out value="${error}"/>
			</div>
		</c:if>
		<c:if test="${message != null}">
			<div class="ui-corner-all ui-state-highlight information" >
				<span style="float: left; margin: 2px;" class="ui-icon ui-icon-info"></span>
				<c:out value="${message}"/>
			</div>
		</c:if>
	</center>
	<form action="loginConfirm.action" method="Post" onSubmit="return check();">
	<table align="center">
		<tr><td><spring:message code="login.Login" />:</td><td><input id="login" name="login" value="<c:out value="${login}"/>"/></td></tr>
		<tr><td><spring:message code="login.Password" />:</td><td><input type="password" id="password" name="password"/></td></tr>
		<tr><td><button type="submit" ><spring:message code="login.Enter" /></button></td><td></td></tr>
	</table>
	</form>
	<center><a href="registrationPage.action"><spring:message code="login.Registration"/></a></center>
	
<script type="text/javascript">
/*check fields*/
function check() {
	if ($("#login").val() == ""){
		jAlert('<spring:message code="login.Please_enter_login" />','<spring:message code="alert.alert" />');
		return false;
	}

	

	if ($("#password").val() == ""){
		jAlert('<spring:message code="login.Please_enter_password" />','<spring:message code="alert.alert" />');
		return false;
	}
	return true;
	
}
/*check hotkey*/
$(function(){
	$("#password").keypress(function(e) {
	    if(e.keyCode == 13) {
	       $("form").submit();
	    }
	});

});

</script>