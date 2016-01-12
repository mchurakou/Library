<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div align="center" >
		<s:text name="book.Real_book_admin" />
</div>	

<table width="100%" >
<tr>
<td valign="top" >


<fieldset class="ui-corner-all">
	<legend>
		<s:text name="book.Add_new_real_book" />
	</legend>
		<table align="center" class="realBook">
			<tr>
				<td id="msg" colspan="2">
					
				</td>
			</tr>
			<tr>
				<td><s:text name="book.Inventory_number" />*:</td>
			</tr>
			<tr>
				<td><input id="inventoryNumber"></input></td>
			</tr>
			<tr>
				<td><s:text name="book.Cost" />*:</td>
			</tr>
			<tr>
				<td><input id="cost"></input></td>
			</tr>
			<tr>
				<td><s:text name="book.Book_description" />*:</td>
			</tr>
			<tr>
				<td><input id="bookDescription" disabled="true" ></input></td><td><button onclick="showListOfBookDescriptions()"><span class="ui-icon ui-icon-folder-open"></span></button>
				</td>
			</tr>
			
			<tr>
				<td><button onclick="addRealBook()"><s:text name="book.Add" /></button></td><td><input type="hidden" id="bookDescriptionId"/></td><td></td>
			</tr>
		</table>
	
</fieldset>
</td>

<td valign="top" align="center">
<fieldset class="ui-corner-all">
	<legend>
		<s:text name="book.Real_books" />
	</legend>
	<table id="tableRealBooks"></table>
	<div id="pagerRealBooks"></div>
</fieldset>
</td>
</tr>
</table>

<div id="listOfBookDescriptions">
	<table id="tableBookDescriptions" ></table>
	<div id="pagerBookDescriptions"></div>
</div>

<div id="privileges">
<fieldset class="ui-corner-all">
	<legend>
		<s:text name="book.Set_privileges" />
	</legend>
	<table id="tablePrivileges">
		<s:iterator value="userCategories" status='status'>
		<tr>
			<td><input type="checkbox" id="<s:property value="id"/>"/></td><td><s:property value="name"/></td>
		</tr>
		</s:iterator>
	</table>
</fieldset>


	
</div>



<script language="javascript">
/* global var*/
 var bookDescriptionId = 0;
 var realBookId = 0;



	$('#tableRealBooks').jqGrid({
		url : 'getRealBooks',
   		datatype: 'json',
   		mtype: 'POST',
   		colNames:['Id','<s:text name="book.Inventory_number" />','<s:text name="book.Cost" />','<s:text name="description.Name" />','<s:text name="description.Author" />','<s:text name="description.Book_category" />','<s:text name="description.Publication_place" />','<s:text name="description.Publication_year" />','<s:text name="description.Size" />','<s:text name="description.Language" />','<s:text name="book.Available" />'],
   		colModel :[
   		    {name:'id', index:'id',align:'center', hidden: true},
   		 	{name:'inventoryNumber', index:'inventoryNumber',editable: false,  align:'center', width:80,	searchoptions: { sopt: ['bw','eq','lt','le','gt','ge']},editrules:{required:true,integer:true, minValue:1, maxValue:99999999}},
   		 	{name:'cost', index:'cost',editable: true,  align:'center', width:80,	searchoptions: { sopt: ['bw','eq','lt','le','gt','ge']}, editrules:{required:true,integer:true, minValue:1, maxValue:99999999}},
   			{name:'name', index:'name',  align:'center', width:180,	searchoptions: { sopt: ['bw','eq','cn']}},
   			
   			{name:'author', index:'author',  align:'center',width:80,searchoptions: { sopt: ['bw','eq','cn']}},
   			{name:'bookCategory', index:'bookCategory',  align:'center',width:80,searchoptions: { sopt: ['bw','eq','cn']}},
   			{name:'publicationPlace', index:'publicationPlace',  align:'center', hidden:true,width:80,searchoptions: { sopt: ['bw','eq','cn']}},
   			{name:'publicationYear', index:'publicationYear', hidden:true, align:'center',width:80,searchoptions: { sopt: ['bw','eq','lt','le','gt','ge']} },
   			{name:'size', index:'size',  align:'center',width:60,searchoptions: { sopt: ['bw','eq','lt','le','gt','ge']}},
   			{name:'language', index:'language',  align:'center',width:80,searchoptions: { sopt: ['bw','eq','cn']}},
   			{name:'available', index:'available',align:'center',hidden:true,hidden: true}
			
   		],
 		pager: "#pagerRealBooks",
  		rowNum:13,
  		rowList:[13,20,50],
  		viewrecords:true,
  		height:280,
  		
  		altRows:true,
  		editurl: 'editRealBook',
  		width: 550  	  		
  	  	
  				
	}).jqGrid('navGrid',"#pagerRealBooks",
			{view:false, del: true, edit: true, search: true,add:false}, 
			{closeAfterEdit:true,mtype:"post",modal:true,top:250,left:450,width:320,drag:false}, //edit
			{}, //add
			{closeOnEscape:true, mtype:"post",modal:true,top:250,left:450,width:320,afterSubmit:checkUpdate,drag:false}, //del
			{multipleSearch:true, mtype:"post",modal:true,top:250,left:450,closeAfterSearch:true,width:320,drag:false}); //search;

	$('#tableRealBooks').jqGrid('navSeparatorAdd','#pagerRealBooks');
	$('#tableRealBooks').jqGrid('navButtonAdd','#pagerRealBooks',{
							                                              caption: '<s:text name="book.Privileges" />',
							                                              title: '<s:text name="book.Privileges" />',
							                                              buttonicon: 'ui-icon-key',
							                                              onClickButton: showPrivileges,
							                                              position:'last' });
	
			
	var firstShowDescriptions = true;
	/* show dialog for select book description */		
	function showListOfBookDescriptions(){
		if (firstShowDescriptions){
			
			/* create window for list of book descriptions*/
			$( "#listOfBookDescriptions" ).dialog({ autoOpen: false,
																		draggable: false,
																		modal:true,
																		resizable:false,
																		title: '<s:text name="book.Select_book_description" />',
																		width: 765,
																		buttons: {
																			
																			<s:text name="button.Ok" />: function(){
																					var id = $("#tableBookDescriptions").jqGrid('getGridParam','selrow');
																					if (id != null){
																						var name = $("#tableBookDescriptions").getRowData(id)["name"];
																						$("#bookDescription").val(name);
																						bookDescriptionId = id;
																						$( this ).dialog( "close" );
																					}
																					else
																						jAlert('<s:text name="book.Select_book_description" />!','<s:text name="alert.alert" />');
																				},
																			<s:text name="button.Close" />: function() {
																					$( this ).dialog( "close" );
																				}
																		}
			});	
		
					
			
			
	/* create table for list of book descriptions*/
	
	$('#tableBookDescriptions').jqGrid({
		url : 'getBookDescriptions',
   		datatype: 'json',
   		mtype: 'POST',
   		colNames:['Id','<s:text name="description.Name" />','<s:text name="description.Author" />','<s:text name="description.Book_category" />','<s:text name="description.Publication_place" />','<s:text name="description.Publication_year" />','<s:text name="description.Size" />','<s:text name="description.Language" />'],
   		colModel :[
   		    {name:'id', index:'id',align:'center', hidden: true },
   			{name:'name', index:'name',  align:'center', width:220,	searchoptions: { sopt: ['bw','eq','cn']}},
   			{name:'author', index:'author',  align:'center',width:80,searchoptions: { sopt: ['bw','eq','cn']}},
   			
   			{name:'bookCategoryId', index:'bookCategoryId',  align:'center',width:100,formatter:'select',
   			editoptions: {value:"<%out.print(request.getAttribute("bookCategoryValue"));%>"}
   			,searchoptions: { sopt: ['bw','eq','cn']}},
   			   			
   			{name:'publicationPlace', index:'publicationPlace',  align:'center',width:100,searchoptions: { sopt: ['bw','eq','cn']}},
   			{name:'publicationYear', index:'publicationYear',  align:'center',width:100,searchoptions: { sopt: ['bw','eq','lt','le','gt','ge']}},
   			{name:'size', index:'size',  align:'center',width:60,searchoptions: { sopt: ['bw','eq','lt','le','gt','ge']}},
   			   			
   			{name:'languageId', index:'languageId',  align:'center',width:80,formatter:'select',
   	   		editoptions: {value:"<%out.print(request.getAttribute("languagesValue"));%>"}
   	   		,searchoptions: { sopt: ['bw','eq','cn']}}
   			  	   		
   	   	],
 		pager: "#pagerBookDescriptions",
  		rowNum:10,
  		rowList:[10,20,50],
  		viewrecords:true,
  		height:200,
  		altRows:true,
  		autowidth:true
  				
	}).jqGrid('navGrid',"#pagerBookDescriptions",
			{view:false,add: false, del: false, edit: false, search: true}, 
			{}, //edit
			{}, //add
			{}, //del
			{multipleSearch:true, mtype:"post",modal:true,top:250,left:450,closeAfterSearch:true,width:320,drag:false}); //search
	
		firstShowDescriptions = false;	
	}
			
		$( "#listOfBookDescriptions" ).dialog('open');
	}
			
	/* add real book*/
	function addRealBook(){
		var inventoryNumber = $("#inventoryNumber").val();
		var cost = $("#cost").val();
		
		if (inventoryNumber == "" || !parseInt(inventoryNumber) || parseInt(inventoryNumber) <= 0 || parseInt(inventoryNumber) > 99999999 ){
			jAlert('<s:text name="book.Correct_inventory_number" />','<s:text name="alert.alert" />');
			return;
		}
		
		if (cost == "" || !parseInt(cost) || parseInt(cost) <= 0 || parseInt(cost) > 99999999 ){
			jAlert('<s:text name="book.Correct_cost" />','<s:text name="alert.alert" />');
			return;
		}
				
		if (bookDescriptionId == 0){
			jAlert('<s:text name="book.Select_book_description" />!','<s:text name="alert.alert" />');
			return;
		}
		
		Ajax.post("addRealBook", {
			"cost" : cost,
			"inventoryNumber" : inventoryNumber,
			"bookDescriptionId" : bookDescriptionId
		}, successAddRealBook,errorAddRealBook);
		
	}
	
	/* success add real book*/
	function successAddRealBook(data){
		
		$('#tableRealBooks').trigger("reloadGrid");
		var str =	"<div class='ui-corner-all ui-state-highlight information' >" +
			"<span style='float: left; margin: 2px;' class='ui-icon ui-icon-info'></span>" + 
		data +
		"</div>";
		
		$("#msg").empty();
		$("#msg").append(str);
	}
	
	/* error add real book*/
	function errorAddRealBook(data){
		var str =	"<div class=' error ui-corner-all' >" +
   						"<span style='float: left; margin: 2px;' class='errorIcon'></span>" + 
						data +
  		 			"</div>";
		$("#msg").empty();
		$("#msg").append(str);
	}


	$( "#privileges" ).dialog({ autoOpen: false,
		draggable: false,
		modal:true,
		resizable:false,
		title: '<s:text name="privileges.Privileges" />',
		width: 200,
		buttons: {
			
			<s:text name="button.Ok" />: function(){
				var catIds = [];
				var i = 0;
				
				$("input[type=checkbox]").each(
					function (){
						if ($(this).attr("checked")){
							catIds[i] = $(this).attr("id");
							i++;
						}
					}
				)
				
				var sttringCategoryIds = "";
				for ( i = 0;i < catIds.length; i++)
					if (i != 0)
						sttringCategoryIds += ":" + catIds[i];
					else
						sttringCategoryIds += catIds[i];
				
				Ajax.post("setRealPrivileges",{realBookId: realBookId,categoryIds:sttringCategoryIds} );
				$( this ).dialog( "close" );	
				},

				
			<s:text name="button.Close" />: function() {
					$( this ).dialog( "close" );
				}
		}
});		
	function showPrivileges(){
		realBookId = $("#tableRealBooks").jqGrid('getGridParam','selrow');
		if (realBookId > 0)
			Ajax.post("getRealPrivileges",{realBookId: realBookId }, successGetRealPrivileges );
		else
			jAlert('<s:text name="book.Select_book" />!','<s:text name="alert.alert" />');
		
	}	

	function successGetRealPrivileges (data){
		var categoryIds = data;
		$("input[type=checkbox]").each(
				function (){
					$(this).attr("checked",false);
					for (i = 0; i < categoryIds.length; i++)
						if (categoryIds[i] == $(this).attr("id"))
							$(this).attr("checked",true);
						
				}
		)
		
		$( "#privileges" ).dialog('open');
	}	
			
			
	
</script>