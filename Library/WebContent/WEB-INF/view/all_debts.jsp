<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div align="center" >
		<s:text name="book.All_debts" />
</div>
<fieldset class="ui-corner-all">
			<legend>
				<s:text name="book.All_debts" />
			</legend>
			<table id="tableDebts" ></table>
			<div id="pagerDebts"></div>
</fieldset>

<script language="JavaScript">

/* create table for debts */
$('#tableDebts').jqGrid({
	    url : 'getAllDebts',
		datatype: 'json',
		mtype: 'POST',
		colNames:['Id','<s:text name="book.Behind" />','<s:text name="book.Start_date" />','<s:text name="book.End_date" />','<s:text name="book.Inv_number" />','<s:text name="book.Title" />','<s:text name="book.Author" />','<s:text name="book.Cost" />','<s:text name="registration.Login" />','<s:text name="registration.First_name" />','<s:text name="registration.Second_name" />','<s:text name="registration.Email" />','<s:text name="registration.Department" />','<s:text name="registration.Division" />'],
		colModel :[
		    {name:'id', index:'id',align:'center', hidden: true},
			{name:'behind', index:'behind',align:'center',width:80, searchoptions: { sopt: ['bw','eq']}	},
 			{name:'startPeriod', index:'startPeriod',  align:'center',width:80,	searchoptions: { sopt: ['bw','eq']},formatter:'date'},
 			{name:'endPeriod', index:'endPeriod',  align:'center',width:80,	searchoptions: { sopt: ['bw','eq']},formatter:'date'},
 			{name:'inventoryNumber', index:'inventoryNumber',  align:'center', width:80,	searchoptions: { sopt: ['bw','eq','lt','le','gt','ge']}},
 			{name:'name', index:'name',  align:'center',width:80,searchoptions: { sopt: ['bw','eq','cn']}},
 			{name:'author', index:'author',  align:'center',width:80,searchoptions: { sopt: ['bw','eq','cn']}},
 			{name:'cost', index:'cost',  align:'center',width:80,searchoptions: { sopt: ['bw','eq','lt','le','gt','ge']}},
 			{name:'login', index:'login',  align:'center',width:80,searchoptions: { sopt: ['bw','eq','cn']}},
 			{name:'firstName', index:'firstName',  align:'center',width:80,searchoptions: { sopt: ['bw','eq','cn']}},
 			{name:'secondName', index:'secondName',  align:'center',width:80,searchoptions: { sopt: ['bw','eq','cn']}},
 			{name:'email', index:'email',  align:'center',width:80,searchoptions: { sopt: ['bw','eq','cn']}, formatter: 'email'},
 			{name:'departmentId', index:'departmentId',  align:'center',width:80,formatter:'select', 
     			editoptions:{value:"<%out.print(request.getAttribute("departmentValue"));%>"} ,
     			editable: false,edittype:"select",searchoptions: { sopt: ['bw','eq','cn']}},
     		{name:'divisionId', index:'divisionId',  align:'center',width:80,formatter:'select', 
         		editoptions:{value:"<%out.print(request.getAttribute("divisionValue"));%>"} ,
         		editable: false,edittype:"select",searchoptions: { sopt: ['bw','eq','cn']}}
 		
 		],
		pager: "#pagerDebts",
		rowNum:10,
		rowList:[10,20,50],
		viewrecords:true,
		height:290,
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
	
</script>