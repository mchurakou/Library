<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
	<link href="css/base.css" rel="stylesheet" />
	<link type="text/css" href="css/jquery-ui-1.8.5.custom.css" rel="stylesheet" />
	<link type="text/css" href="css/jquery.alerts.css" rel="stylesheet" />	
	<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.5.custom.min.js"></script>
	<script type="text/javascript" src="js/jquery.alerts.js"></script>
	<script type="text/javascript" src="js/utils.js"></script>
	<script type="text/javascript" src="js/jquery.dateFormat-1.0.js"></script>
	
	<script>
	var DATE_FORMAT = "dd.MM.yyyy";
		$(function() {
			
			$( "button, input[type=submit]").button();
			
			$( ".datepicker" ).datepicker({firstDay : 1,
				monthNames:    ['<s:text name="datepicker.January" />', '<s:text name="datepicker.February" />', '<s:text name="datepicker.March" />', '<s:text name="datepicker.April" />', '<s:text name="datepicker.May" />', '<s:text name="datepicker.June" />', '<s:text name="datepicker.July" />', '<s:text name="datepicker.August" />', '<s:text name="datepicker.September" />', '<s:text name="datepicker.October" />', '<s:text name="datepicker.November" />', '<s:text name="datepicker.December" />'],
				dayNamesMin:     ['<s:text name="datepicker.Su" />', '<s:text name="datepicker.Mo" />', '<s:text name="datepicker.Tu" />', '<s:text name="datepicker.We" />', '<s:text name="datepicker.Th" />', '<s:text name="datepicker.Fr" />', '<s:text name="datepicker.Sa" />'],
				dateFormat: "dd.mm.yy"
				});	
			
		});
	
	var MSG_ALERT = '<s:text name="alert.alert" />';	
	




		
	</script>	
	<tiles:insertAttribute name="head"/>
	<title><tiles:insertAttribute name="title"/></title>
</head>
<body>
<div align="center" class="content" >
	<div id="navigation" ><tiles:insertAttribute name="navigation" /></div>
	<div id="topBlock"><tiles:insertAttribute name="topBlock" /></div>
	<div id="middleBlock" class="wrapBlock middleBlock ui-corner-all ">
		<tiles:insertAttribute name="middleBlock" />
	</div>
	<div id="bottomBlock"><tiles:insertAttribute name="bottomBlock" /></div>
	<hr/>
	<div class="wrapBlock ui-corner-all"><tiles:insertAttribute name="footer" /></div>
</div>





</body>
</html>