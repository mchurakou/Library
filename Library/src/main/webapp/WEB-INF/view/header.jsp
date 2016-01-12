<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">

$(document).ready(function(){
	initMenu();
});
</script>

<div class="container ui-corner-all">
    <div id="header">
    	<table align="right">
    		<tr><td><s:text name="header.User" />:</td><td><s:property value="#session.user.firstName"/> <s:property value="#session.user.secondName"/></td></tr>
    		<tr><td><s:text name="header.Role" />:</td><td><s:property value="#session.user.role.title"/></td></tr>
    		<tr><td><s:text name="header.Category" />:</td><td><s:property value="#session.user.category.name"/></td></tr>
    	</table>
    </div>
    <div class="menu">
        <ul class="topnav">
        	<s:if test="#session.user.role.name eq 'user' or #session.user.role.name eq 'librarian'">
            	<li><a href="profile"><s:text name="header.Profile" /></a></li>
            </s:if>
            
            <!-- librarian begin-->
             <s:if test="#session.user.role.name eq 'librarian'">
             	<li></li>
             	<li><a ><s:text name="header.Books" /></a>
            	   <ul class="subnav">
                    	<li><a href="bookDescriptions"><s:text name="description.Book_descriptions" /></a></li>
                    	<li><a href="realBooks"><s:text name="header.Real_books" /></a></li>
                    	<li><a href="eBooks"><s:text name="header.Ebooks" /></a></li>
                   </ul> 
                </li>
                
                <li><a ><s:text name="header.Readers" /></a>
            	   <ul class="subnav">
                    	<li><a href="giveBook"><s:text name="header.Give_book" /></a></li>
                    	<li><a href="returnBook"><s:text name="header.Return_book" /></a></li>
                   </ul> 
                </li>
                
                <li><a ><s:text name="header.Debts" /></a>
            	   <ul class="subnav">
                    	 <li><a href="debts"><s:text name="header.Personal_debts" /></a></li>
                    	<li><a href="allDebts"><s:text name="header.All_debts" /></a></li>
                   </ul> 
                </li>
                <li><a href="javascript:void" onclick='showReport();' ><s:text name="header.Report" /></a>
                
                  
             </s:if>
            <!-- librarian end -->
            	
            
            <!-- administrator begin-->
            <s:if test="#session.user.role.name eq 'administrator'">
            	<li>
            		
                	<a href="userAdministration"><s:text name="header.Users" /></a>
               	</li>
               	 <li><a><s:text name="header.Handbooks" /></a>
            	   <ul class="subnav">
                    	 <li><a href="departmentAdministration"><s:text name="header.Departments" /></a></li>
                    	 <li><a href="bookCategoryAdministration"><s:text name="header.Book_categories" /></a></li>
                    	 <li><a href="languageAdministration"><s:text name="header.Languages" /></a></li>
                    	 <li><a href="userCategoryAdministration"><s:text name="header.User_categories" /></a></li>
                    	 <li><a href="divisionAdministration"><s:text name="header.Divisions" /></a></li>
                    </ul> 
                </li>
            </s:if>
            <!-- administrator end -->
            
            <!-- user begin-->
            <s:if test="#session.user.role.name eq 'user'" >
            	<li><a ><s:text name="header.Books" /></a>
            	   <ul class="subnav">
            	   		<li><a href="realBooksForUser"><s:text name="header.Real_books" /></a></li>
            	   		<li><a href="eBooksForUser"><s:text name="header.Ebooks" /></a></li>
            	   </ul>
            	 </li>
            	 <li><a href="debts"><s:text name="header.Debts" /></a></li>
             	 
             </s:if>
             <!-- user end -->
            
            
           
            
             
            
            <li><a href="statistic"><s:text name="header.Statistic" /></a></li>
            <li><a href="information"><s:text name="header.Information" /></a></li>
            <li><a href="exit"><s:text name="header.Exit" /></a></li>
        </ul>
    </div>
    
</div>

<div id="report">
	<fieldset class="ui-corner-all">
		<legend>
			<s:text name="book.Start_and_end" />
		</legend>
		<form method="post" action="report" id="report" target="_blank">
		<table>
			<tr><td><s:text name="book.Start_date" />:</td><td><input name="start" id="startReport"   readonly class="datepicker" ></input></td></tr>
			<tr><td><s:text name="book.End_date" />:</td><td><input name="end" id="endReport"  readonly class="datepicker"></input></td></tr>
			
		</table>
		</form>
	</fieldset>
</div>

<script language="javascript">
function showReport(){
	$("#report" ).dialog('open');
}

$( "#report" ).dialog({ autoOpen: false,
	draggable: false,
	modal:true,
	resizable:false,
	title: '<s:text name="header.Report" />',
	width: 220,
	buttons: {
	<s:text name="button.Ok" />: function(){
		checkPeriod();
		},
	<s:text name="button.Close" />: function() {
				$( this ).dialog( "close" );
			}
	}
});

function checkPeriod(){
	var blockS = $("#startReport").val().split('.');
	var s = new Date(blockS[1] + "/" + blockS[0] + "/" + blockS[2]);
	
	var blockE = $("#endReport").val().split('.');
	var e = new Date(blockE[1] + "/" + blockE[0] + "/" + blockE[2]);

	if (s.getTime() < e.getTime()){
		$("form#report").submit();
		$( "#report" ).dialog( "close" );
		
	}
	else{
		jAlert('<s:text name="book.Set_period" />','<s:text name="alert.alert" />');
	}
	
}
	
</script>
