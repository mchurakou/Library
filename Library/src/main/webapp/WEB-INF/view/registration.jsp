<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
	
		<center>
			<b><s:text name="registration.Registration"/></b>
			<s:if test="error != null">
				<div class="error ui-corner-all">
					<span style="float: left; margin: 2px;" class="errorIcon"></span>
					<s:property value="error"/>
				</div>
			</s:if>
		</center>
				
			<form action="registrationConfirm.action" method="Post" onSubmit="return check();">
			<table align="center">
				<tr><td><s:text name="registration.Login" />*:</td><td><input id="login" name="login" value="<s:property value="login"/>"/></td></tr>
				<tr><td><s:text name="registration.Password" />*:</td><td><input id="password" type="password" name="password"/></td></tr>
				<tr><td><s:text name="registration.First_name" />:</td><td><input name="firstName" value="<s:property value="firstName"/>"/></td></tr>
				<tr><td><s:text name="registration.Second_name" />:</td><td><input name="secondName" value="<s:property value="secondName"/>"/></td></tr>				
				<tr><td><s:text name="registration.Email" />*:</td><td><input id="email" name="email" value="<s:property value="email"/>"/></td></tr>
				<tr>
					<td>
						<s:text name="registration.Department" />:
					</td>
					<td>
						<select id="departmentId"  style="width:138px" onchange="selectDepartment();">
							<option ><s:text name="registration.Select_department" /></option>
							<s:iterator value="departments" status="status" var="department">
								<option value="<s:property value="id"/>"><s:property value="name"/></option>
							</s:iterator>
							
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<s:text name="registration.Division" />:
					</td>
					<td>
						<select id="divisionId" name="divisionId" style="width:138px" disabled="true">
							<option ><s:text name="registration.Select_division" /></option>
						</select>
					</td>
				</tr>
				<tr><td><button type="submit"><s:text name="registration.Enter" /></button></td><td></td></tr>
			</table>
			</form>
			<br/>
			<center>* - <s:text name="registration.Required_fields" /></center>
			<br/>
			
	
	
			<center>
				<a href="loginPage.action"><s:text name="registration.Return_To_Login"/></a>
			</center>	

<script type="text/javascript">

/*check fields*/
	function check() {
		if ($("#login").val() == ""){
			jAlert('<s:text name="registration.Please_enter_login" />','<s:text name="alert.alert" />');
			return false;
		}

		if ($("#password").val() == ""){
			jAlert('<s:text name="registration.Please_enter_password" />','<s:text name="alert.alert" />');
			return false;
		}

		if ($("#email").val() == ""){
			jAlert('<s:text name="registration.Please_enter_email" />','<s:text name="alert.alert" />');
			return false;
		}

		if (!isValidEmail($("#email").val(),true)){
			jAlert('<s:text name="registration.Please_enter_correct_email" />','<s:text name="alert.alert" />');
			return false;
		}

		
		

		

		
		return true;
	}

	function selectDepartment(){
		if ($("#departmentId").val() != 0){
			Ajax.post("prepareDivisions.action",{departmentId: $("#departmentId").val()}, successGetDivisions );
		}			
	}

	function successGetDivisions(data){
			
		var divisions = data[0];
		
		var divisionsStr = "";
		for ( var i = 0; i < divisions.length; i++){
			var division = divisions[i];
			divisionsStr += "<option value=" + division.id + ">" + division.name + "</option>"
				
		}

		$("#divisionId option:not(:first)").remove();
	    $("#divisionId").append(divisionsStr);
	    $("#divisionId").attr("disabled",false);
		
	}



</script>