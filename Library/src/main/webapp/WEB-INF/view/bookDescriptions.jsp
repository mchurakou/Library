<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>




	<s:if test="error != null">
		<div class="error ui-corner-all"><s:property value="error"/></div>
	</s:if>
	<div align="center">
		<s:text name="description.Book_descriptionsn_admin" />
	</div>	
	
		<table id="table"></table>
		<div id="pager"></div>
		
	
<script language="javascript">

	$('#table').jqGrid({
		url : 'getBookDescriptions',
   		datatype: 'json',
   		mtype: 'POST',
   		colNames:['Id','<s:text name="description.Name" />','<s:text name="description.Author" />','<s:text name="description.Book_category" />','<s:text name="description.Publication_place" />','<s:text name="description.Publication_year" />','<s:text name="description.Size" />','<s:text name="description.Language" />'],
   		colModel :[
   		    {name:'id', index:'id',align:'center', hidden: true, editable: false},
   			{name:'name', index:'name',  align:'center', width:220,editable: true,	edittype:"text" ,searchoptions: { sopt: ['bw','eq','cn']}, editrules:{required:true}},
   			{name:'author', index:'author',  align:'center',width:80,editable: true,	edittype:"text" ,searchoptions: { sopt: ['bw','eq','cn']},editrules:{required:true}},
   			
   			{name:'bookCategoryId', index:'bookCategoryId',  align:'center',width:80,formatter:'select',
   			editoptions: {value:"<%out.print(request.getAttribute("bookCategoriesValue"));%>"}
   			,editable: true,edittype:"select",searchoptions: { sopt: ['bw','eq','cn']}},
   			   			
   			{name:'publicationPlace', index:'publicationPlace',  align:'center',width:80,editable: true,	edittype:"text" ,searchoptions: { sopt: ['bw','eq','cn']}},
   			{name:'publicationYear', index:'publicationYear',  align:'center',width:80,editable: true,	edittype:"text" ,searchoptions: { sopt: ['bw','eq','lt','le','gt','ge']},  editrules:{required:true,integer:true, minValue:1900, maxValue:2020}},
   			{name:'size', index:'size',  align:'center',width:60,editable: true,	edittype:"text" ,searchoptions: { sopt: ['bw','eq','lt','le','gt','ge']}, editrules:{required:true,integer:true, minValue:1, maxValue:9999}},
   			   			
   			{name:'languageId', index:'languageId',  align:'center',width:80,formatter:'select',
   	   		editoptions: {value:"<%out.print(request.getAttribute("languagesValue"));%>"}
   	   		,editable: true,edittype:"select",searchoptions: { sopt: ['bw','eq','cn']}}
   		],
 		pager: "#pager",
  		rowNum:13,
  		rowList:[13,20,50],
  		caption: '<s:text name="description.Book_descriptions" />',
  		viewrecords:true,
  		height:300,
  		editurl: 'editBookDescription',
  		altRows:true,
  		autowidth:true
  				
	}).jqGrid('navGrid',"#pager",
			{view:false,add: true, del: true, edit: true, search: true}, 
			{closeAfterEdit:true,mtype:"post",modal:true,top:250,left:450,width:320,drag:false}, //edit
			{closeAfterAdd:true, mtype:"post",modal:true,top:250,left:450,width:320,drag:false}, //add
			{closeOnEscape:true, mtype:"post",modal:true,top:250,left:450,width:320,afterSubmit:checkUpdate,drag:false}, //del
			{multipleSearch:true, mtype:"post",modal:true,top:250,left:450,closeAfterSearch:true,width:320,drag:false}); //search

	
	
</script>

