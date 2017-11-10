<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div align="center" >
		<s:text name="book.Caption_give_book" />
</div>	
<table width="100%" >
	<tr>
		<td valign="top" >
			<fieldset class="ui-corner-all">
				<legend class="notSelected" id="legendBook">
					<s:text name="book.Book_not_selected" />
				</legend>
				<table align="center">
					<tr><td><s:text name="book.Inv_number" />:</td><td><input id="inventoryNumber" disabled="true" ></input></td></tr>
					<tr><td><s:text name="book.Title" />:</td><td><input id="name" disabled="true" ></input></td></tr>
					<tr><td><s:text name="book.Author" />:</td><td><input id="author" disabled="true" ></input></td></tr>
					<tr><td><s:text name="book.Cost" />:</td><td><input id="cost" disabled="true" ></input></td></tr>
					<tr><td><button onclick="showListOfRealBooks()"><s:text name="book.Select" /></button></td><td></td><td></td></tr>
					
				</table>
			</fieldset>
		</td>
		<td valign="top">
			<fieldset class="ui-corner-all">
				<legend class="notSelected" id="legendPeriod">
					<s:text name="book.Period_not_define" />
				</legend>
				<table align="center">
					<tr><td><s:text name="book.Start_date" />:</td><td><input id="start"   readonly class="datepicker" onchange="checkPeriod();"></input></td></tr>
					<tr><td><s:text name="book.End_date" />:</td><td><input id="end"  readonly class="datepicker" onchange="checkPeriod();"></input></td></tr>
					<tr><td><button onclick="giveBook()"><s:text name="book.Give_book" /></button></td><td><div id="msg"></div></td></tr>
				</table>
			</fieldset>
			
		</td>
		
	</tr>
	<tr>
		<td valign="top" >
		<fieldset class="ui-corner-all">
			<legend class="notSelected" id="legendUser">
				<s:text name="book.User_not_select" />
			</legend>
			<table align="center">
					<tr><td><s:text name="book.Login" />:</td><td><input id="login" disabled="true" ></input></td></tr>
					<tr><td><s:text name="book.Second_name" />:</td><td><input id="secondName" disabled="true" ></input></td></tr>
					<tr><td><s:text name="book.First_name" />:</td><td><input id="firstName" disabled="true" ></input></td></tr>
					<tr><td><s:text name="book.Email" />:</td><td><input id="email" disabled="true" ></input></td></tr>
					<tr><td><button onclick="showListOfUsers()"><s:text name="book.Select" /></button></td><td></td><td></td></tr>
				</table>
			</fieldset>
			
		</td>
	</tr>
</table>


<div id="listOfRealBooks">
	<table id="tableRealBooks" ></table>
	<div id="pagerRealBooks"></div>
</div>

<div id="listOfUsers">
	<table id="tableUsers" ></table>
	<div id="pagerUsers"></div>
</div>

<div id="listOfQueue">
	<table id="tableQueue" ></table>
	<div id="pagerQueue"></div>
</div>

<script language="JavaScript">

var realBookId = 0;
var userId = 0;
var period = false;
var firstShowBook = true;
var firstShowUser = true;
var firstShowQueue = true;



$("#start").val($.format.date(new Date(), DATE_FORMAT));
$("#end").val($.format.date(new Date(), DATE_FORMAT));


/* show dialog for select real book */		
function showListOfRealBooks(){
	if (firstShowBook){
		/* create window for list of real books*/
		$( "#listOfRealBooks" ).dialog({ autoOpen: false,
										draggable: false,
										modal:true,
										resizable:false,
										title: '<s:text name="book.Select_real_book" />',
										width: 820,
										buttons: {
										<s:text name="button.Ok" />: function(){
												var id = $("#tableRealBooks").jqGrid('getGridParam','selrow');
												if (id != null ){
													if ($("#tableRealBooks").getRowData(id)["available"] == "false"){
														jAlert('<s:text name="book.Book_not_available" />','<s:text name="alert.alert" />');
														return;
													}
													
													var inventoryNumber = $("#tableRealBooks").getRowData(id)["inventoryNumber"];
													var name = $("#tableRealBooks").getRowData(id)["name"];
													var author = $("#tableRealBooks").getRowData(id)["author"];
													var cost = $("#tableRealBooks").getRowData(id)["cost"];
													$("#inventoryNumber").val(inventoryNumber);
													$("#name").val(name);
													$("#author").val(author);
													$("#cost").val(cost);
													realBookId = id;
													$("#legendBook").attr("class","selected");
													$("#legendBook").empty();
													$("#legendBook").append('<s:text name="book.Book_selected" />');
													$( this ).dialog( "close" );
												}
												else
													jAlert('<s:text name="book.Select_real_book" />!','<s:text name="alert.alert" />');
											},
										<s:text name="button.Close" />: function() {
													$( this ).dialog( "close" );
												}
										}
		});	

		/* create real book table*/
		$('#tableRealBooks').jqGrid({
			url : 'getRealBooks',
				datatype: 'json',
				mtype: 'POST',
				colNames:['Id','<s:text name="book.Inventory_number" />','<s:text name="book.Cost" />','<s:text name="description.Name" />','<s:text name="description.Author" />','<s:text name="description.Book_category" />','<s:text name="description.Publication_place" />','<s:text name="description.Publication_year" />','<s:text name="description.Size" />','<s:text name="description.Language" />','available'],
				colModel :[
				    {name:'id', index:'id',align:'center', hidden: true},
				 	{name:'inventoryNumber', index:'inventoryNumber', align:'center', width:80,	searchoptions: { sopt: ['bw','eq','lt','le','gt','ge']}},
				 	{name:'cost', index:'cost',  align:'center', width:80,	searchoptions: { sopt: ['bw','eq','lt','le','gt','ge']}},
					{name:'name', index:'name',  align:'center', width:180,	searchoptions: { sopt: ['bw','eq','cn']}},
					
					{name:'author', index:'author',  align:'center',width:80,searchoptions: { sopt: ['bw','eq','cn']}},
					{name:'bookCategory', index:'bookCategory',  align:'center',width:80,searchoptions: { sopt: ['bw','eq','cn']}},
					{name:'publicationPlace', index:'publicationPlace',  align:'center',width:80,searchoptions: { sopt: ['bw','eq','cn']}},
					{name:'publicationYear', index:'publicationYear',  align:'center',width:80,searchoptions: { sopt: ['bw','eq','lt','le','gt','ge']} },
					{name:'size', index:'size',  align:'center',width:60,searchoptions: { sopt: ['bw','eq','lt','le','gt','ge']}},
					{name:'language', index:'language',  align:'center',width:80,searchoptions: { sopt: ['bw','eq','cn']}},
					{name:'available', index:'available',align:'center',hidden:true}
				],
				pager: "#pagerRealBooks",
				rowNum:10,
				rowList:[10,20,50],
				viewrecords:true,
				height:220,
				autowidth:true,
				afterInsertRow: function(row_id, row_data){
									if (row_data.available == false) 
										 $("#tableRealBooks").jqGrid('setCell',row_id,'name','',{'background':'#FB8A60'});
									else
										$("#tableRealBooks").jqGrid('setCell',row_id,'name','',{'background':'#97F4A0'});
									
					              
								}
		}).jqGrid('navGrid',"#pagerRealBooks",
				{view:false, del: false, edit: false, search: true,add:false}, 
				{}, //edit
				{}, //add
				{}, //del
				{multipleSearch:true, mtype:"post",modal:true,top:250,left:450,closeAfterSearch:true,width:320,drag:false}); //search;
		$('#tableRealBooks').jqGrid('navSeparatorAdd','#pagerRealBooks');
		$('#tableRealBooks').jqGrid('navButtonAdd','#pagerRealBooks',{
		 		    caption: '<s:text name="queue.Show_queue" />',
		            title: '<s:text name="queue.Show_queue" />',
		            buttonicon: 'ui-icon-script',
		            onClickButton: showQueue,
		            position:'last' });
				
		firstShowBook = false;
	}
	$('#tableRealBooks').trigger("reloadGrid");
	$("#listOfRealBooks" ).dialog('open');
}



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
												var login = $("#tableUsers").getRowData(id)["login"];
												var secondName = $("#tableUsers").getRowData(id)["secondName"];
												var firstName = $("#tableUsers").getRowData(id)["firstName"];
												var email = $("#tableUsers").getRowData(id)["email"];
												$("#login").val(login);
												$("#secondName").val(secondName);
												$("#firstName").val(firstName);
												$("#email").val(email);
												userId = id;
												$("#legendUser").attr("class","selected");
												$("#legendUser").empty();
												$("#legendUser").append('<s:text name="book.User_selected" />');
												$( this ).dialog( "close" );
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
		url : 'getUsersForLibrarian.action',
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
			altRows:true,
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

/*check time period*/
function checkPeriod(){
	var blockS = $("#start").val().split('.');
	var s = new Date(blockS[1] + "/" + blockS[0] + "/" + blockS[2]);
	
	var blockE = $("#end").val().split('.');
	var e = new Date(blockE[1] + "/" + blockE[0] + "/" + blockE[2]);

	if (s.getTime() < e.getTime()){
		$("#legendPeriod").attr("class","selected");
		$("#legendPeriod").empty();
		$("#legendPeriod").append('<s:text name="book.Period_defined" />');
		period = true;
	}
	else{
		$("#legendPeriod").attr("class","notSelected");
		$("#legendPeriod").empty();
		$("#legendPeriod").append('<s:text name="book.Period_not_define" />');
		period = false;
	}
	
}

/* give book*/
function giveBook(){
	if (realBookId == 0){
		jAlert('<s:text name="book.Select_book" />!','<s:text name="alert.alert" />');
		return;
	}
	
	if (userId == 0){
		jAlert('<s:text name="book.Select_user" />!','<s:text name="alert.alert" />');
		return;
	}
	
	if (!period){
		jAlert('<s:text name="book.Set_period" />','<s:text name="alert.alert" />');
		return;
	}
	
	Ajax.post("giveRealBook", {
		"realBookId" : realBookId,
		"userId" : userId,
		"start" : $("#start").val(),
		"end" : $("#end").val()
	}, successGiveBook);
}

function successGiveBook(data){
	var str =	"<div class='ui-corner-all ui-state-highlight information' >" +
	"<span style='float: left; margin: 2px;' class='ui-icon ui-icon-info'></span>" + 
	data +
	"</div>";

	realBookId = 0;
	$("#inventoryNumber").val("");
	$("#name").val("");
	$("#author").val("");
	$("#cost").val("");
	$("#legendBook").attr("class","notSelected");
	$("#legendBook").empty();
	$("#legendBook").append('<s:text name="book.Book_not_selected" />');
		
	$("#msg").empty();
	$("#msg").append(str);
	$("#msg").show();
	$("#msg").fadeOut(7000);
	
}


/* create window for queues*/
$( "#listOfQueue" ).dialog({ autoOpen: false,
						 draggable: false,
						 modal:true,
						 resizable:false,
						 title: '<s:text name="queue.Queue" />',
						 width: 515
				});

/* show queue*/
function showQueue(){
	realBookId = $("#tableRealBooks").jqGrid('getGridParam','selrow');
	if (realBookId != null){
		var url = "getQueueForLibrarian?realBookId=" + realBookId;
		if (firstShowQueue){
       		$('#tableQueue').jqGrid({
   				url : url,
   				datatype: 'json',
   				mtype: 'POST',
   				colNames:['Id','userId','<s:text name="registration.Login" />','<s:text name="registration.First_name" />','<s:text name="registration.Second_name" />','<s:text name="queue.Date" />','Email'],
   					colModel :[
   					    {name:'id', index:'id',align:'center', hidden: true},
   					 	{name:'userId', index:'userId',align:'center', hidden: true},
   						{name:'login', index:'login',align:'center',width:90,sortable:false	},
   			 			{name:'firstName', index:'firstName',  align:'center',width:90,sortable:false	},
   			 			{name:'secondName', index:'secondName',  align:'center',width:100,sortable:false},
   			 			{name:'date', index:'date',  align:'center',width:110,	formatter:'date',sortable:false},
   			 			{name:'email', index:'email',align:'center', hidden: true}
   			 	],
   					pager: "#pagerQueue",
   					rowNum:10,
   					viewrecords:true,
   					height:220,
   					altRows:true,
   					width:490
   				}).jqGrid('navGrid',"#pagerQueue",
   					{view:false,add: false, del: false, edit: false, search: false}, 
   					{}, //edit
   					{}, //add
   					{}, //del
   					{}); //search
   				$('#tableQueue').jqGrid('navSeparatorAdd','#pagerQueue');
				$('#tableQueue').jqGrid('navButtonAdd','#pagerQueue',{
                   caption: '',
                   title: '<s:text name="queue.Delete_from_queue" />',
                   buttonicon: 'ui-icon-minusthick',
                   onClickButton: deleteFromQueueById,
                   position:'last' }); 
				$('#tableQueue').jqGrid('navButtonAdd','#pagerQueue',{
	                   caption: '',
	                   title: '<s:text name="queue.Get_user" />',
	                   buttonicon: 'ui-icon-circle-check',
	                   onClickButton: getUserFromQueue,
	                   position:'last' });   
   				
				firstShowQueue = false;
			}
   		else{
   			$('#tableQueue').setGridParam({url:url});
			
   		}
   		$('#tableQueue').trigger("reloadGrid")
   		$("#listOfQueue" ).dialog('open');
		}
		else
			jAlert('<s:text name="book.Select_real_book" />!','<s:text name="alert.alert" />');
}

function deleteFromQueueById(){
	var queueId = $("#tableQueue").jqGrid('getGridParam','selrow');
	
	if (queueId == null){
		jAlert('<s:text name="book.Select_user" />!','<s:text name="alert.alert" />');
		return;
	}
	
	Ajax.post("deleteFromQueueById",
				 {"id" :queueId},
			 	   success,
			 	   error);
}

function success(data){
	$('#tableQueue').trigger("reloadGrid");
	jAlert(data,'<s:text name="alert.alert" />');
}

function error(data){
	jAlert(data,'<s:text name="alert.alert" />');
}


function getUserFromQueue(){
	var id = $("#tableQueue").jqGrid('getGridParam','selrow');
	if (id != null){
		userId = $("#tableQueue").getRowData(id)["userId"];
		var login = $("#tableQueue").getRowData(id)["login"];
		var secondName = $("#tableQueue").getRowData(id)["secondName"];
		var firstName = $("#tableQueue").getRowData(id)["firstName"];
		var email = $("#tableQueue").getRowData(id)["email"];
		$("#login").val(login);
		$("#secondName").val(secondName);
		$("#firstName").val(firstName);
		$("#email").val(email);
		
		$("#legendUser").attr("class","selected");
		$("#legendUser").empty();
		$("#legendUser").append('<s:text name="book.User_selected" />');
		$("#listOfQueue").dialog( "close" );
	}
	else
		jAlert('<s:text name="book.Select_user" />!','<s:text name="alert.alert" />');
}
</script>
	