<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>


	<s:if test="error != null">
				<div class="error ui-corner-all">
					<span style="float: left; margin: 2px;" class="errorIcon"></span>
					<s:property value="error"/>
				</div>
	</s:if>
	<div align="center">
		<s:text name="admin.User_administration" />
	</div>	
		<table id="table"></table>
		<div id="pager"></div>
		
	
<script language="javascript">

	$('#table').jqGrid({
		url : 'getUsers',
   		datatype: 'json',
   		mtype: 'POST',
   		colNames:['Id','<s:text name="registration.Login" />','<s:text name="registration.First_name" />','<s:text name="registration.Second_name" />','<s:text name="registration.Email" />','<s:text name="header.Role" />','<s:text name="header.Category" />','<s:text name="registration.Department" />','<s:text name="registration.Division" />'],
   		colModel :[
   		    {name:'id', index:'id',align:'center', hidden: true},
   			{name:'login', index:'login',align:'center',width:80, searchoptions: { sopt: ['bw','eq','cn']}	},
     		{name:'firstName', index:'firstName',  align:'center',width:80,editable: true,	edittype:"text" ,searchoptions: { sopt: ['bw','eq','cn']}},
     		{name:'secondName', index:'secondName',  align:'center',width:80,editable: true,	edittype:"text",searchoptions: { sopt: ['bw','eq','cn']}},
     		{name:'email', index:'email',  align:'center',width:80,editable: true,	edittype:"text",searchoptions: { sopt: ['bw','eq','cn']}, formatter: 'email', editrules:{required:true, email:true}},
     		{name:'roleId', index:'roleId',  align:'center', width:80,formatter:'select', editoptions:{value:"<%out.print(request.getAttribute("userRoleValue"));%>"},editable: true,edittype:"select",searchoptions: { sopt: ['bw','eq','cn']} },
     		{name:'categoryId', index:'categoryId',  align:'center',width:80,formatter:'select', 
     			editoptions:{value:"<%out.print(request.getAttribute("userCategoryValue"));%>"} ,
     			editable: true,edittype:"select",searchoptions: { sopt: ['bw','eq','cn']}},
     		{name:'departmentId', index:'departmentId',  align:'center',width:80,formatter:'select', 
     			editoptions:{value:"<%out.print(request.getAttribute("departmentValue"));%>"} ,
     			editable: false,edittype:"select",searchoptions: { sopt: ['bw','eq','cn']}},
     		{name:'divisionId', index:'divisionId',  align:'center',width:80,formatter:'select', 
         		editoptions:{value:"<%out.print(request.getAttribute("divisionValue"));%>"} ,
         		editable: true,edittype:"select",searchoptions: { sopt: ['bw','eq','cn']}}
	 	],
 		pager: "#pager",
  		rowNum:13,
  		rowList:[13,20,50],
  		caption: '<s:text name="header.Users" />',
  		viewrecords:true,
  		height:300,
  		altRows:true,
  		autowidth: true,
  		editurl: 'editUser'
	}).jqGrid('navGrid',"#pager",
			{view:false,add: false, del: true, edit: true, search: true}, 
			{closeAfterEdit:true, mtype:"post",modal:true,top:250,left:450,drag:false,drag:false}, //edit
			{}, //add
			{closeOnEscape:true, mtype:"post",modal:true,top:250,left:450,afterSubmit:checkUpdate,drag:false,drag:false}, //del
			{multipleSearch:true, mtype:"post",modal:true,top:250,left:450,closeAfterSearch:true,drag:false,drag:false}); //search
	
			
		

</script>

