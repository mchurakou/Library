<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
	<s:if test="error != null">
		<div class="error ui-corner-all"><s:property value="error"/></div>
	</s:if>
	<div align="center">
		<s:text name="handbook.Divisions_administration" />
	</div>	
	
		<table id="table"></table>
		<div id="pager"></div>
		
	
<script language="javascript">

	$('#table').jqGrid({
		url : 'getDivisions',
   		datatype: 'json',
   		mtype: 'POST',
   		colNames:['Id','<s:text name="handbook.Name" />','<s:text name="handbook.Name_ru" />','<s:text name="handbook.Department" />'],
   		colModel :[
			{name:'id', index:'id',align:'center', hidden: true,editable:false },
			{name:'name', index:'name',align:'center',width:80, editable: true,	edittype:"text",searchoptions: { sopt: ['bw','eq','cn']}},
			{name:'name_ru', index:'name_ru',align:'center',width:80,editable: true,	edittype:"text", searchoptions: { sopt: ['bw','eq','cn']}},
			{name:'departmentId', index:'departmentId',  align:'center',width:80,formatter:'select',
   	   		editoptions: {value:"<%out.print(request.getAttribute("departmentValue"));%>"}
   	   		,editable: true,edittype:"select",searchoptions: { sopt: ['bw','eq','cn']}}
   		],
 		pager: "#pager",
  		rowNum:13,
  		rowList:[13,20,50],
  		caption: '<s:text name="handbook.Divisions" />',
  		viewrecords:true,
  		height:300,
  		editurl: 'editDivision',
  		altRows:true,
  		autowidth:true
  				
	}).jqGrid('navGrid',"#pager",
			{view:false,add: true, del: true, edit: true, search: true}, 
			{closeAfterEdit:true,mtype:"post",modal:true,top:250,afterSubmit:checkUpdate,left:450,width:320,drag:false}, //edit
			{closeAfterAdd:true, mtype:"post",modal:true,top:250,afterSubmit:checkUpdate,left:450,width:320,drag:false}, //add
			{closeOnEscape:true, mtype:"post",modal:true,top:250,afterSubmit:checkUpdate,left:450,width:320,afterSubmit:checkUpdate,drag:false}, //del
			{multipleSearch:true, mtype:"post",modal:true,top:250,left:450,closeAfterSearch:true,width:320,drag:false}); //search

	
	
</script>

