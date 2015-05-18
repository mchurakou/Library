<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
	<div align="right">
			<a href="changeLanguage?request_locale=ru"><img src=img/ru.jpg border=1 alt="Ru"></a>
			<a href="changeLanguage?request_locale=en"><img src=img/en.jpg border=1 alt="En"></a>
	</div>
	<br/>
	
	<center>
		<b><s:text name="login.Enter_login_and_pass" /></b>
		<s:if test="error != null">
			<div class="error ui-corner-all">
				<span style="float: left; margin: 2px;" class="errorIcon"></span>
				<s:property value="error"/>
			</div>
		</s:if>
		<s:if test="message != null">
			<div class="ui-corner-all ui-state-highlight information" >
				<span style="float: left; margin: 2px;" class="ui-icon ui-icon-info"></span>
				<s:property value="message"/>
			</div>
		</s:if>
	</center>
	<form action="loginConfirm" method="Post" onSubmit="return check();">
	<table align="center">
		<tr><td><s:text name="login.Login" />:</td><td><input id="login" name="login" value="<s:property value="login"/>"/></td></tr>
		<tr><td><s:text name="login.Password" />:</td><td><input type="password" id="password" name="password"/></td></tr>
		<tr><td><button type="submit" ><s:text name="login.Enter" /></button></td><td></td></tr>
	</table>
	</form>
	<center><a href="registrationPage"><s:text name="login.Registration"/></a></center>
	
<script type="text/javascript">
/*check fields*/
function check() {
	if ($("#login").val() == ""){
		jAlert('<s:text name="login.Please_enter_login" />','<s:text name="alert.alert" />');
		return false;
	}

	

	if ($("#password").val() == ""){
		jAlert('<s:text name="login.Please_enter_password" />','<s:text name="alert.alert" />');
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