<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
response.setContentType("text/html; charset=UTF-8");
%>
<s:if test="error != null">
				<div class="error ui-corner-all">
					<span style="float: left; margin: 2px;" class="errorIcon"></span>
					<s:property value="error"/>
				</div>
</s:if>
<div align="center" >
		<s:text name="statistic.Statistic"/>
</div>
<table align="center" width="100%">
	<tr>
		<td>
			<fieldset class="ui-corner-all">
				<legend>
					<s:text name="statistic.Books_categories"/>
				</legend>
				<table align="center" width="100%">
					<tr>
						<td>
							<div  align="center" id="realBooksPipe"></div>
						</td>
						<td>
							<div align="center" id="electronicBooksPipe"></div>
						</td>
					</tr>
				</table>
			</fieldset>
		</td>
	</tr>
	<tr>
		<td>
			<fieldset class="ui-corner-all">
				<legend>
					<s:text name="statistic.Books_and_users"/>
				</legend>
				<table align="center" width="100%">
					<tr>
						<td>
							<div  align="center" id="booksBar"></div>
						</td>
						<td>
							<div align="center" id="usersBar"></div>
						</td>
					</tr>
				</table>
			</fieldset>
		</td>
	</tr>
</table>
<script language="JavaScript">
var apiPipe = new jGCharts.Api(); 
var apiBar = new jGCharts.Api();
									 /*real books pipe*/
jQuery('<img>').attr('src', apiPipe.make({data : [
											<s:iterator value="realBooksPipeInformation" status='status'>
                                              [<s:property value='count' />]
                                               <s:if test=' not #status.last'>,</s:if>
                                            </s:iterator>
                                              ],
									  type : 'p3',
									  axis_labels : [<%out.print(request.getAttribute("realBooksPipeLabels"));%>],
									  size        : '330x140',
									  bg        : 'FFFFFF',//default false 
									  bg_offset : '888888', 
									  bg_angle  : '45',//default 90    
									  bg_type   : 'gradient', //default solid 
									  title    : '<s:text name="statistic.Real_books"/>', //default false 
									  title_color : 'black',
									 })).appendTo("#realBooksPipe");	


/*electronic books pipe*/
 jQuery('<img>').attr('src', apiPipe.make({data : [
     											<s:iterator value="electronicBooksPipeInformation" status='status'>
                                                [<s:property value='count' />]
                                                 <s:if test=' not #status.last'>,</s:if>
                                              </s:iterator>
                                                ],
  									  type : 'p3',
  									  axis_labels : [<%out.print(request.getAttribute("electronicBooksPipeLabels"));%>],
  									 title    : '<s:text name="statistic.Electronic_books"/>'
  									 })).appendTo("#electronicBooksPipe");

 jQuery('<img>').attr('src', apiBar.make({data : [[<%out.print(request.getAttribute("bookCounts"));%>]],
	  axis_labels : [' '],
	  legend : [<%out.print(request.getAttribute("bookLabels"));%>],
	  type : 'bvg',
	  size        : '330x140',
	  bg        : 'FFFFFF', 
	  bg_offset : '888888', 
	  bg_angle  : '45',    
	  bg_type   : 'gradient', 
	  title_color : 'black',
	  
	  title       : '<s:text name="statistic.Books"/>', //default false 
	  grid        : true, //default false 
	  grid_x      : 10,    //default 10 
	  grid_y      : 10,    //default 10 
	  grid_line   : 10,   //default 10 
	  grid_blank  : 0    //default 0 
	  
	 })).appendTo("#booksBar");

 jQuery('<img>').attr('src', apiBar.make({data : [[<%out.print(request.getAttribute("userCounts"));%>]],
	  legend : [<%out.print(request.getAttribute("userLabels"));%>],
	  type : 'bvg',
	  size        : '330x140',
	  bg        : 'FFFFFF', 
	  bg_offset : '888888', 
	  bg_angle  : '45',    
	  bg_type   : 'gradient', 
	  title_color : 'black',
	  colors : ['4b9b41','81419b','41599b','111111'], 
	  title       : '<s:text name="statistic.Users"/>' //default false 
	 
	  
	 })).appendTo("#usersBar");		

</script>