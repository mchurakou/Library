<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div align="center" >
		<s:text name="profile.Profile_admin" />
</div>	
<fieldset class="ui-corner-all">
	<legend>
		<s:text name="profile.Personal_inf" /> <s:property value="user.login"/>
	</legend>
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
	<form action="changeProfile" method="Post" onSubmit="return check();">
	<table>
		<tr>
			<td><s:text name="profile.First_name" />:</td>
			<td><input name="firstName" value='<s:property value="user.firstName"/>'/></td>
		</tr>
		<tr>
			<td><s:text name="profile.Second_name" />:</td>
			<td><input name="secondName" value='<s:property value="user.secondName"/>'/></td>
		</tr>
		<tr>
			<td><s:text name="profile.Email" />:</td>
			<td><input id="email" name="email" value='<s:property value="user.email"/>'/></td>
		</tr>
		<tr>
					<td>
						<s:text name="registration.Department" />:
					</td>
					<td>
						<select id="departmentId"  name="departmentId" style="width:148px" onchange="selectDepartment();">
							<option ><s:text name="registration.Select_department" /></option>
							<s:iterator value="departments" status="status" var="department">
								<option <s:if test="user.division.department.id == id">selected</s:if> value="<s:property value="id"/>"><s:property value="name"/></option>
							</s:iterator>
							
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<s:text name="registration.Division" />:
					</td>
					<td>
						<select id="divisionId" name="divisionId" style="width:148px" disabled="true">
							<option ><s:text name="registration.Select_division" /></option>
							<s:iterator value="divisions" status="status" var="division">
								<option <s:if test="user.division.id == id">selected</s:if> value="<s:property value="id"/>"><s:property value="name"/></option>
							</s:iterator>
						</select>
					</td>
				</tr>
		<tr>
			<td><button type="submit"><s:text name="button.Save" /></button></td><td></td>
		</tr>
	</table>
	</form>
</fieldset>


<script type="text/javascript">
if ($("#divisionId option").length > 1)
	$("#divisionId").attr("disabled",false);

/*check fields*/
	function check() {
		
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
			Ajax.post("prepareDivisions",{departmentId: $("#departmentId").val()}, successGetDivisions );
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
