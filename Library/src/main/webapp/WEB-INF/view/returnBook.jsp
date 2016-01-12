<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div align="center" >
		<s:text name="book.Caption_return_book" />
</div>	
<table width="100%" >
	<tr>
	<td valign="top" >
		<fieldset class="ui-corner-all">
			<legend class="notSelected" id="legendUser">
				<s:text name="book.User_not_select" />
			</legend>
			<table align="center">
					<tr>
						<td><s:text name="book.Login" />:</td><td><input id="login" disabled="true" ></input></td>
						<td><s:text name="book.Second_name" />:</td><td><input id="secondName" disabled="true" ></input></td>
						<td><s:text name="book.First_name" />:</td><td><input id="firstName" disabled="true" ></input></td>
						<td colspan="2"><button onclick="showListOfUsers()"><s:text name="book.Select" /></button></td><td></td><td></td>
					</tr>
				</table>
			</fieldset>
			
		</td>
	</tr>
	<tr>
		<td valign="top" >
		<fieldset class="ui-corner-all">
			<legend>
				<s:text name="book.User_debts" />
			</legend>
			<table id="tableDebts" ></table>
			<div id="pagerDebts"></div>
		</fieldset>
		</td>
	</tr>
	
</table>





<div id="listOfUsers">
	<table id="tableUsers" ></table>
	<div id="pagerUsers"></div>
</div>


<script>
var firstShowUser = true;
var userId = 0;


/* show dialog for select user */		
function showListOfUsers(){

if (firstShowUser){
	/* create window for list of users*/
	$( "#listOfUsers" ).dialog({ autoOpen: false,
									draggable: false,
									modal:true,
									resizable:false,
									title: '<s:text name="book.Select_user" />',
									width: 660,
									buttons: {
									<s:text name="button.Ok" />: function(){
											var id = $("#tableUsers").jqGrid('getGridParam','selrow');
											if (id != null){
												if ($("#tableUsers").getRowData(id)["haveDebt"] == "false"){
													jAlert('<s:text name="book.User_without_debt" />','<s:text name="alert.alert" />');
													return;
												}
												var login = $("#tableUsers").getRowData(id)["login"];
												var secondName = $("#tableUsers").getRowData(id)["secondName"];
												var firstName = $("#tableUsers").getRowData(id)["firstName"];
												$("#login").val(login);
												$("#secondName").val(secondName);
												$("#firstName").val(firstName);
												userId = id;
												$("#legendUser").attr("class","selected");
												$("#legendUser").empty();
												$("#legendUser").append('<s:text name="book.User_selected" />');
												$( this ).dialog( "close" );
												var url = "getUserDebtsForLibrarian?userId=" + userId;
												$('#tableDebts').setGridParam({url:url});
												$('#tableDebts').trigger("reloadGrid");
												
											}
											else
												jAlert('<s:text name="book.Select_user" />!','<s:text name="alert.alert" />');
										},
									<s:text name="button.Close" />: function() {
												$( this ).dialog( "close" );
											}
									}
	});	

	$('#tableUsers').jqGrid({
		url : 'getUsersForLibrarian',
			datatype: 'json',
			mtype: 'POST',
			colNames:['Id','<s:text name="registration.Login" />','<s:text name="registration.First_name" />','<s:text name="registration.Second_name" />','<s:text name="registration.Email" />','<s:text name="header.Role" />','<s:text name="header.Category" />','Have debt','<s:text name="registration.Department" />','<s:text name="registration.Division" />'],
			colModel :[
			    {name:'id', index:'id',align:'center', hidden: true},
				{name:'login', index:'login',align:'center',width:80, searchoptions: { sopt: ['bw','eq','cn']}	},
	 		{name:'firstName', index:'firstName',  align:'center',width:80,editable: true,	searchoptions: { sopt: ['bw','eq','cn']}},
	 		{name:'secondName', index:'secondName',  align:'center',width:80,searchoptions: { sopt: ['bw','eq','cn']}},
	 		{name:'email', index:'email',  align:'center',width:80,searchoptions: { sopt: ['bw','eq','cn']}, formatter: 'email'},
	 		{name:'roleId', index:'roleId',  align:'center', width:80,formatter:'select', editoptions:{value:"<%out.print(request.getAttribute("userRoleValue"));%>"},edittype:"select",searchoptions: { sopt: ['bw','eq','cn']} },
	 		{name:'categoryId', index:'categoryId',  align:'center',width:80,formatter:'select', editoptions:{value:"<%out.print(request.getAttribute("userCategoryValue"));%>"} ,edittype:"select",searchoptions: { sopt: ['bw','eq','cn']}},
	 		 {name:'haveDebt', index:'haveDebt',align:'center', hidden: true},
	 		{name:'departmentId', index:'departmentId',  align:'center',width:80,formatter:'select', 
	     			editoptions:{value:"<%out.print(request.getAttribute("departmentValue"));%>"} ,
	     			editable: false,edittype:"select",searchoptions: { sopt: ['bw','eq','cn']}},
	     	{name:'divisionId', index:'divisionId',  align:'center',width:80,formatter:'select', 
	         		editoptions:{value:"<%out.print(request.getAttribute("divisionValue"));%>"} ,
	         		editable: false,edittype:"select",searchoptions: { sopt: ['bw','eq','cn']}}
	 		
	 	],
			pager: "#pagerUsers",
			rowNum:10,
			rowList:[10,20,50],
			viewrecords:true,
			height:220,
			afterInsertRow: function(row_id, row_data){
				if (row_data.haveDebt == false) 
					 $("#tableUsers").jqGrid('setCell',row_id,'login','',{'background':'#FB8A60'});
				else
					$("#tableUsers").jqGrid('setCell',row_id,'login','',{'background':'#97F4A0'});
				
              
			},
			autowidth: true
		}).jqGrid('navGrid',"#pagerUsers",
			{view:false,add: false, del: false, edit: false, search: true}, 
			{}, //edit
			{}, //add
			{}, //del
			{multipleSearch:true, mtype:"post",modal:true,top:250,left:450,closeAfterSearch:true,drag:false}); //search
	firstShowUser = false;
}	
	$('#tableUsers').trigger("reloadGrid")
	$("#listOfUsers" ).dialog('open');
	
}


/* create table for debts */
$('#tableDebts').jqGrid({
	    url : 'getUserDebtsForLibrarian?userId=0',
		datatype: 'json',
		mtype: 'POST',
		colNames:['Id','<s:text name="book.Behind" />','<s:text name="book.Start_date" />','<s:text name="book.End_date" />','<s:text name="book.Inv_number" />','<s:text name="book.Title" />','<s:text name="book.Author" />','<s:text name="book.Cost" />'],
		colModel :[
		    {name:'id', index:'id',align:'center', hidden: true},
			{name:'behind', index:'behind',align:'center',width:80, searchoptions: { sopt: ['bw','eq']}	},
 			{name:'startPeriod', index:'startPeriod',  align:'center',width:80,	searchoptions: { sopt: ['bw','eq']},formatter:'date'},
 			{name:'endPeriod', index:'endPeriod',  align:'center',width:80,	searchoptions: { sopt: ['bw','eq']},formatter:'date'},
 			{name:'inventoryNumber', index:'inventoryNumber',  align:'center', width:80,	searchoptions: { sopt: ['bw','eq','lt','le','gt','ge']}},
 			{name:'name', index:'name',  align:'center',width:80,searchoptions: { sopt: ['bw','eq','cn']}},
 			{name:'author', index:'author',  align:'center',width:80,searchoptions: { sopt: ['bw','eq','cn']}},
 			{name:'cost', index:'cost',  align:'center',width:80,searchoptions: { sopt: ['bw','eq','lt','le','gt','ge']}},
 		],
		pager: "#pagerDebts",
		rowNum:10,
		rowList:[10,20,50],
		viewrecords:true,
		height:210,
		afterInsertRow: function(row_id, row_data){
			if (row_data.behind == 'No' || row_data.behind == 'Нет') 
				 $("#tableDebts").jqGrid('setCell',row_id,'behind','',{'background':'#97F4A0'});
			else
				$("#tableDebts").jqGrid('setCell',row_id,'behind','',{'background':'#FB8A60'});
			
          
		},
		autowidth: true
	}).jqGrid('navGrid',"#pagerDebts",
		{view:false,add: false, del: false, edit: false, search: true}, 
		{}, //edit
		{}, //add
		{}, //del
		{multipleSearch:true, mtype:"post",modal:true,top:250,left:450,closeAfterSearch:true,drag:false}); //search
	$('#tableDebts').jqGrid('navSeparatorAdd','#pagerDebts');
	$('#tableDebts').jqGrid('navButtonAdd','#pagerDebts',{
			                                              caption: '<s:text name="book.Return_book" />',
			                                              title: '<s:text name="book.Return_book" />',
			                                              buttonicon: 'ui-icon-circle-check',
			                                              onClickButton: returnBook,
			                                              position:'last'

			                                            				               });
	function returnBook(){
		var debtId = $("#tableDebts").jqGrid('getGridParam','selrow');
		if (debtId != null){
			Ajax.post("returnRealBook",
					 {"debtId" : debtId},
				 	   successReturnBook);
		}
		else
			jAlert('<s:text name="book.Select_debt" />','<s:text name="alert.alert" />');
		
	}

	function successReturnBook(data){
		$('#tableDebts').trigger("reloadGrid");
		jAlert(data,'<s:text name="alert.alert" />');
	}

	
</script>