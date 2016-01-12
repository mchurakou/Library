<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<script
      src="http://bluweb.com/chouser/gmapez/gmapez-2.js"
      type="text/javascript"></script>


<div align="center">
	<s:text name="information.Information" />
</div>	



<div id="tabs">
	<ul>
		<li><a href="#tabs-1"><s:text name="information.Information_about" /></a></li>
		<li><a href="#tabs-2"><s:text name="information.Internet" /></a></li>
	</ul>
	<div id="tabs-1">
<table width="100%" align="center">
<tr>
	<td valign="top" width="50%" height="100px">
	<fieldset class="ui-corner-all">
		<legend>
			<s:text name="information.Contact_inf" />
		</legend>
		<table align="center">
			<tr>
				<td><s:text name="information.Address" />:</td><td><s:text name="information.My_address" /></td>
				
			</tr>
			<tr>
				<td><s:text name="information.Phone" />:</td><td>(8-029)238-80-96, (8-0232)789-207</td>
			</tr>
			<tr>
				<td><s:text name="information.Email" />:</td><td><a href="mailto:badbug1@yandex.ru">badbug1@yandex.ru</a></td>
			</tr>
			<tr>
				<td><s:text name="information.Director" />:</td><td><s:text name="information.Churakov" /></td>
			</tr>
		</table>
	</fieldset>
	</td>
	<td valign="top" rowspan="2" width="50%">
		<fieldset class="ui-corner-all">
		<legend>
			<s:text name="information.Location_on_map" />
		</legend>
		<center>
		 <div  class="GMapEZ G_MAP_TYPE" style="width:450px; height: 290px;">
			<a href="http://maps.google.com/maps/ms?ie=UTF8&hl=ru&msa=0&msid=203091207062598995325.00049ce282720a0168dce&ll=52.455709,31.020627&spn=0.009192,0.018733&z=16&iwloc=00049ce29cbac5686dd01"></a>
		 </div>
		 </center>
		</fieldset>
		
	
	</td>
	
	</tr>
	<tr>
		<td valign="top">
			<fieldset class="ui-corner-all">
		<legend>
			<s:text name="information.Work" />
		</legend>
		<table align="center">
			<tr>
				<td><s:text name="information.Monday" /> - <s:text name="information.Friday" /></td><td>9:00 - 18:00</td>
			</tr>
			<tr>
				<td><s:text name="information.Saturday" />, <s:text name="information.Sunday" /></td><td>10:00 - 17:00</td>
			</tr>
		</table>
	</fieldset>
			
		</td>
		<td>
		</td>
	</tr>
</table>
	
	
	</div>
	<div id="tabs-2">
		<fieldset class="ui-corner-all">
		<legend>
			<s:text name="information.Links" />
		</legend>
		<table align="center">
			<tr>
				<td><s:text name="information.Link1" />:</td><td><a href="http://www.nlb.by/portal/page/portal/index">http://www.nlb.by/portal/page/portal/index</a></td>
			</tr>
			<tr>
				<td><s:text name="information.Link2" />:</td><td><a href="http://www.library.bsu.by/?vpath=/about/news/">http://www.library.bsu.by/?vpath=/about/news/</a></td>
			</tr>
			<tr>
				<td><s:text name="information.Link3" />:</td><td><a href="http://www.rsl.ru/">http://www.rsl.ru/</a></td>
			</tr>
			<tr>
				<td><s:text name="information.Link4" />:</td><td><a href="http://www.gpntb.ru/">http://www.gpntb.ru/</a></td>
			</tr>
			<tr>
				<td><s:text name="information.Link5" />:</td><td><a href="http://libfl.ru/">http://libfl.ru/</a></td>
			</tr>
			<tr>
				<td><s:text name="information.Link6" />:</td><td><a href="http://www.scsml.rssi.ru/">http://www.scsml.rssi.ru/</a></td>
			</tr>
			<tr>
				<td><s:text name="information.Link7" />:</td><td><a href="http://library.gsu.by/index.php?q=ru/">http://library.gsu.by/index.php?q=ru</a></td>
			</tr>
		</table>
		</fieldset>
	</div>
</div>

<script>
	$(function() {
		$( "#tabs" ).tabs();
	});
</script>
