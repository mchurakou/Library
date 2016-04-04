<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>



<div align="center" >
	<s:text name="electronic.El_books" />
</div>

<table width="100%" >
<tr>
<td valign="top" >


<fieldset class="ui-corner-all" style="height:200px">
	<legend>
		<s:text name="electronic.New_el_book" />
	</legend>



		<table align="center" class="realBook" width="215px" >
			
			<tr>
				<td colspan="2"><s:text name="book.Book_description" />*:</td>
			</tr>
			<tr>
				<td width="110px"><input id="bookDescription" disabled="true" /></td><td><button onclick="showListOfBookDescriptions()"><span class="ui-icon ui-icon-folder-open"></span></button></td>
				
			</tr>

		<form id="fileUpload" name="fileUpload" action="upload.action" method="post" enctype="multipart/form-data">

			<tr><td colspan="3"><s:text name="electronic.File" />*:<span class="progressBar" id="progressBar"></span></td></tr>
			
			<tr>
				<td>
				
				<div id="wrapper">
					<s:file name="file" />
				</div>
				
				</td>
			</tr>
			
			<tr>
				<td>&nbsp;</td>
			</tr>
			
			<tr>
				<td><button  type="submit"><s:text name="electronic.Add" /></button>
				</td>
			</tr>
			<input type="hidden" id="bookDescriptionId" name="bookDescriptionId"/>

			<tr>
				<td id="msg" colspan="2">
					
				</td>
			</tr>
		</form>
			
		</table>



		
	
</fieldset>
</td>

<td valign="top" align="center">
<fieldset class="ui-corner-all">
	<legend>
		<s:text name="electronic.El_books" />
	</legend>
	<table id="tableElectronicBooks"></table>
	<div id="pagerElectronicBooks"></div>
</fieldset>
</td>
</tr>
</table>

<div id="listOfBookDescriptions">
	<table id="tableBookDescriptions" ></table>
	<div id="pagerBookDescriptions"></div>
</div>



<div id="listOfComments">
<fieldset class="ui-corner-all">
	<legend id="bookName">
		
	</legend>
<table align = "center" id="commentsTable" width = "100%" >
	<tr><th width="25%"><div class="ui-corner-all"><s:text name="comment.Name" /></div></th><th width="75%"><div class="ui-corner-all"><s:text name="comment.Comment" /></div></th><th></th></tr>
	
</table>
	<div align="center">
		<form onsubmit="return saveComment();">
			<textarea name="content" cols="50" rows="4" id="commentText"></textarea>
		</form>
	</div>



</fieldset>
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
var MSG_SELECT_BOOK_DESCRIPTION = '<s:text name="electronic.Select_book_description" />';
var MSG_SELECT_FILE = '<s:text name="electronic.Select_file" />';
var MSG_EL_BOOK_UPLOADED = '<s:text name="electronic.El_book_uploaded" />';

var electronicBookId = 0;


/* global var*/
 var bookDescriptionId = 0;
 var firstShowDescriptions = true;
 var fistShowComments = true;
 var electronicBookId = 0;

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
																						$("#bookDescriptionId").val(bookDescriptionId);
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

	/* table for electronic books*/
	$('#tableElectronicBooks').jqGrid({
		url : 'getElectronicBooksForLibrarian',
   		datatype: 'json',
   		mtype: 'POST',
   		colNames:['Id','<s:text name="electronic.File" />','<s:text name="electronic.Capacity" />','<s:text name="electronic.Extension" />','<s:text name="description.Name" />','<s:text name="description.Author" />','<s:text name="description.Book_category" />','<s:text name="description.Size" />','<s:text name="description.Language" />'],
   		colModel :[
   		    {name:'id', index:'id',align:'center', hidden: true},
   		 	{name:'fileName', index:'fileName', align:'center', width:80,	searchoptions: { sopt: ['bw','eq','cn']}, formatter:'showlink', formatoptions:{baseLinkUrl:'download'}},
   		    
   		 	{name:'capacity', index:'capacity', align:'center', width:80,	searchoptions: { sopt: ['bw','eq','lt','le','gt','ge']}},
   		 	{name:'extension', index:'extension', align:'center', width:80,	searchoptions: { sopt: ['bw','eq','cn']}},
   			
   		 	{name:'name', index:'name',  align:'center', width:180,	searchoptions: { sopt: ['bw','eq','cn']}},
   			
   			{name:'author', index:'author',  align:'center',width:80,searchoptions: { sopt: ['bw','eq','cn']}},
   			{name:'bookCategory', index:'bookCategory',  align:'center',width:80,searchoptions: { sopt: ['bw','eq','cn']}},
   			
   			{name:'size', index:'size',  align:'center',width:60,searchoptions: { sopt: ['bw','eq','lt','le','gt','ge']}},
   			{name:'language', index:'language',  align:'center',width:80,searchoptions: { sopt: ['bw','eq','cn']}}
   			
   		],
   		
   		
 		pager: "#pagerElectronicBooks",
  		rowNum:13,
  		rowList:[13,20,50],
  		viewrecords:true,
  		height:280,
  		autowidth:true,
  		altRows:true,
  		editurl: 'editElectronicBook'
  		
  				
	}).jqGrid('navGrid',"#pagerElectronicBooks",
			{view:false, del: true, edit: false, search: true,add:false}, 
			{}, //edit
			{}, //add
			{closeOnEscape:true, mtype:"post",modal:true,top:250,left:450,width:320,afterSubmit:checkUpdate,drag:false}, //del
			{multipleSearch:true, mtype:"post",modal:true,top:250,left:450,closeAfterSearch:true,width:320,drag:false}); //search;
			
	$('#tableElectronicBooks').jqGrid('navSeparatorAdd','#pagerElectronicBooks');
	$('#tableElectronicBooks').jqGrid('navButtonAdd','#pagerElectronicBooks',{
							                                              caption: '',
							                                              title: '<s:text name="comment.Show_comments" />',
							                                              buttonicon: 'ui-icon-comment',
							                                              onClickButton: showComments,
							                                              position:'last' });


	
	$('#tableElectronicBooks').jqGrid('navButtonAdd','#pagerElectronicBooks',{
							                                              caption: '<s:text name="book.Privileges" />',
							                                              title: '<s:text name="book.Privileges" />',
							                                              buttonicon: 'ui-icon-key',
							                                              onClickButton: showPrivileges,
							                                              position:'last' });
	
	function showComments(){
		electronicBookId = $("#tableElectronicBooks").jqGrid('getGridParam','selrow');
		if (electronicBookId != null){
			Ajax.post("loadCommentsForLibrarian",
					 {"electronicBookId" : electronicBookId},
				 	   successGetComments);
			
			
		}
		else
			jAlert('<s:text name="comment.Select_electronic_book" />','<s:text name="alert.alert" />');
		
	}
	
	function successGetComments(data){
		var electronicBook = data[0];
		var comments = data[1];
		
		$("#bookName").empty();
		$("#bookName").append(electronicBook.name);
		
		
		$("#commentsTable tr:not(:first)").remove();
		
		for (i=0; i < comments.length;i++){
			var comment =  comments[i];
			var commentTR = "<tr><td valign='top'>" + comment.firstName + " " + comment.secondName + "<br/>" + comment.date + "</td>" + 
			"<td valign='top' style='background:white;'>" + comment.message + "</td><td valign='top'><span class='delete-icon' onclick='deleteComment(" + comment.id + ")'></span></td></tr>";
			$("#commentsTable").append(commentTR); 
		}

		if  (fistShowComments){
		 tinyMCE.init({
		        mode:"textareas",
		        theme:"advanced",
				skin : "o2k7",
		        skin_variant : "silver",
				plugins : "save,advhr,advlink,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template",
		   	    theme_advanced_buttons1 : "save,newdocument,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,fontselect,fontsizeselect",
				theme_advanced_buttons2 : "bullist,numlist,|,undo,redo,|,link,unlink,|,forecolor,backcolor,|,hr,charmap,|,fullscreen",
				theme_advanced_buttons3 : "",
				theme_advanced_toolbar_location : "top",
				theme_advanced_toolbar_align : "left",
				width:455,
			    language:'<s:property value="lang"/>',
			    height:100
			    
			    

		    });
		 fistShowComments = false;
		}
		
		$( "#listOfComments").dialog('open');
	}
	
	$( "#listOfComments" ).dialog({ 
    	autoOpen: false,
		draggable: false,
		modal:true,
		resizable:false,
		title: '<s:text name="comment.Comments" />',
		width:500
	});
 
function saveComment(){
	if (tinyMCE.get('commentText').getContent().length == 0){
		jAlert('<s:text name="comment.Comment_cant_empty" />','<s:text name="alert.alert" />');
	return false;
	}
	Ajax.post("addComment",
		 {"content" : tinyMCE.get('commentText').getContent(),
			electronicBookId:electronicBookId},
			successAddComment);
	return false;

}

function successAddComment(){
showComments();
tinyMCE.get('commentText').setContent("");
}
	
	
function deleteComment(commentId){
	jConfirm('<s:text name="comment.Delete_this_comment" />', '<s:text name="comment.Delete_confirmation" />', function(r) {
		if( r )
			Ajax.post("deleteComment",
				 {"commentId" : commentId},
				 showComments);
		 });
	
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
			
			Ajax.post("setElectronicPrivileges",{electronicBookId: electronicBookId,categoryIds:sttringCategoryIds} );
			$( this ).dialog( "close" );	
			},

			
		<s:text name="button.Close" />: function() {
				$( this ).dialog( "close" );
			}
	}
});		
function showPrivileges(){
	electronicBookId = $("#tableElectronicBooks").jqGrid('getGridParam','selrow');
	if (electronicBookId > 0)
		Ajax.post("getElectronicPrivileges",{electronicBookId: electronicBookId }, successGetElectronicPrivileges );
	else
		jAlert('<s:text name="book.Select_book" />!','<s:text name="alert.alert" />');
	
}	

function successGetElectronicPrivileges (data){
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

