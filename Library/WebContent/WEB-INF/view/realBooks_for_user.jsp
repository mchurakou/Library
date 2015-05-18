<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div align="center" >
		<s:text name="header.Real_books" />
</div>

<fieldset class="ui-corner-all">
	<legend>
		<s:text name="book.Real_books" />
	</legend>
	<table id="tableRealBooks"></table>
	<div id="pagerRealBooks"></div>
</fieldset>

<div id="listOfQueue">
	<table id="tableQueue" ></table>
	<div id="pagerQueue"></div>
</div>

<script language="JavaScript">
var firstShowQueue = true;
var realBookId = 0;


$('#tableRealBooks').jqGrid({
	url : 'getRealBooksForUser',
		datatype: 'json',
		mtype: 'POST',
		colNames:['Id','<s:text name="book.Inventory_number" />','<s:text name="book.Cost" />','<s:text name="description.Name" />','<s:text name="description.Author" />','<s:text name="description.Book_category" />','<s:text name="description.Publication_place" />','<s:text name="description.Publication_year" />','<s:text name="description.Size" />','<s:text name="description.Language" />','<s:text name="book.Available" />'],
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
		rowNum:13,
		rowList:[13,20,50],
		viewrecords:true,
		height:280,
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
       			var url = "getQueueForUser?realBookId=" + realBookId;
    			
           		
           		if (firstShowQueue){
               		
           			$('#tableQueue').jqGrid({
           				url : url,
           				datatype: 'json',
           				mtype: 'POST',
           				colNames:['Id','userId','<s:text name="registration.Login" />','<s:text name="registration.First_name" />','<s:text name="registration.Second_name" />','<s:text name="queue.Date" />'],
           					colModel :[
           					    {name:'id', index:'id',align:'center', hidden: true},
           					 	{name:'userId', index:'userId',align:'center', hidden: true},
           						{name:'login', index:'login',align:'center',width:90,sortable:false	},
           			 			{name:'firstName', index:'firstName',  align:'center',width:90,sortable:false	},
           			 			{name:'secondName', index:'secondName',  align:'center',width:100,sortable:false},
           			 			{name:'date', index:'date',  align:'center',width:110,	formatter:'date',sortable:false}
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
           							                                              title: '<s:text name="queue.Add_to_queue" />',
           							                                              buttonicon: 'ui-icon-plusthick',
           							                                              onClickButton: addToQueue,
           							                                              position:'last' });
           				$('#tableQueue').jqGrid('navButtonAdd','#pagerQueue',{
                               caption: '',
                               title: '<s:text name="queue.Delete_from_queue" />',
                               buttonicon: 'ui-icon-minusthick',
                               onClickButton: deleteFromQueue,
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

        /* add to queue*/
        function addToQueue(){
	   		if (realBookId != 0){
       			Ajax.post("addToQueue",
       					 {"realBookId" : realBookId},
       				 	   success,
       				 	   error);
       		}
       		
        }


        function deleteFromQueue(){
    		Ajax.post("deleteFromQueue",
  					 {"realBookId" : realBookId},
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

    	

    	
</script>