<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div align="center" >
		<s:text name="electronic.El_books" />
</div>

<fieldset class="ui-corner-all">
	<legend>
		<s:text name="electronic.El_books" />
	</legend>
	<table id="tableElectronicBooks"></table>
	<div id="pagerElectronicBooks"></div>
</fieldset>



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

<script language="JavaScript">
var fistShowComments = true;
var electronicBookId = 0;
var userId = '<s:property value = "#session.user.id"/>';
/* table for electronic books*/
$('#tableElectronicBooks').jqGrid({
	url : 'getElectronicBooksForUser',
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
		altRows:true
		
		
				
}).jqGrid('navGrid',"#pagerElectronicBooks",
		{view:false, del: false, edit: false, search: true,add:false}, 
		{}, //edit
		{}, //add
		{}, //del
		{multipleSearch:true, mtype:"post",modal:true,top:250,left:450,closeAfterSearch:true,width:320,drag:false}); //search;
		$('#tableElectronicBooks').jqGrid('navSeparatorAdd','#pagerElectronicBooks');
		$('#tableElectronicBooks').jqGrid('navButtonAdd','#pagerElectronicBooks',{
						                                              caption: '<s:text name="comment.Show_comments" />',
						                                              title: '<s:text name="comment.Show_comments" />',
						                                              buttonicon: 'ui-icon-comment',
						                                              onClickButton: showComments,
						                                              position:'last' });
		
		function showComments(){
			electronicBookId = $("#tableElectronicBooks").jqGrid('getGridParam','selrow');
			if (electronicBookId != null){
				Ajax.post("loadCommentsForUser",
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
			
			
			$("#commentsTable tr:not(:first)").empty();
			
			for (i=0; i < comments.length;i++){
				var comment =  comments[i];
				var commentTR = "<tr><td valign='top'>" + comment.firstName + " " + comment.secondName + "<br/>" + comment.date + "</td>" + 
				"<td valign='top' style='background:white;'>" + comment.message + "</td><td>";
				if (userId == comment.userId)
					commentTR += "<span class='delete-icon' onclick='deleteComment(" + comment.id + ")'></span>";
				commentTR += "</td></tr>";
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
		jAlert("Comment can't be empty!",'<s:text name="alert.alert" />');
		return false;
	}
	Ajax.post("addCommentForUser",
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
			Ajax.post("deleteCommentForUser",
				 {"commentId" : commentId},
				 showComments);
		 });
	
}
		
		
</script>