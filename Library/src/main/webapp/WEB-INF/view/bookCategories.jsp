<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div align="center">
		<s:text name="handbook.Book_categories_administration" />
</div>	
<table id="table"></table>
<div id="pager"></div>

<script language="javascript">

	$('#table').jqGrid({
		url : 'getBookCategories',
   		datatype: 'json',
   		mtype: 'POST',
   		colNames:['Id','<s:text name="handbook.Name" />','<s:text name="handbook.Name_ru" />'],
   		colModel :[
   		    {name:'id', index:'id',align:'center', hidden: true,editable:false },
   			{name:'name', index:'name',align:'center',width:80, editable: true,	edittype:"text",searchoptions: { sopt: ['bw','eq','cn']}},
   			{name:'name_ru', index:'name_ru',align:'center',width:80,editable: true,	edittype:"text", searchoptions: { sopt: ['bw','eq','cn']}}
     	],
 		pager: "#pager",
  		rowNum:13,
  		rowList:[13,20,50],
  		caption: '<s:text name="handbook.Book_categories" />',
  		viewrecords:true,
  		height:300,
  		altRows:true,
  		autowidth: true,
  		editurl: 'editBookCategory'
	}).jqGrid('navGrid',"#pager",
			{view:false,add: true, del: true, edit: true, search: true}, 
			{closeAfterEdit:true, mtype:"post",modal:true,top:250,left:450,afterSubmit:checkUpdate,drag:false,drag:false}, //edit
			{closeAfterAdd:true, mtype:"post",modal:true,top:250,left:450,afterSubmit:checkUpdate,width:320,drag:false}, //add
			{closeOnEscape:true, mtype:"post",modal:true,top:250,left:450,afterSubmit:checkUpdate,drag:false,drag:false}, //del
			{multipleSearch:true, mtype:"post",modal:true,top:250,left:450,closeAfterSearch:true,drag:false,drag:false}); //search
	
			
		

</script>