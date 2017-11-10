<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
				monthNames:    ['<spring:message code="datepicker.January" />', '<spring:message code="datepicker.February" />', '<spring:message code="datepicker.March" />', '<spring:message code="datepicker.April" />', '<spring:message code="datepicker.May" />', '<spring:message code="datepicker.June" />', '<spring:message code="datepicker.July" />', '<spring:message code="datepicker.August" />', '<spring:message code="datepicker.September" />', '<spring:message code="datepicker.October" />', '<spring:message code="datepicker.November" />', '<spring:message code="datepicker.December" />'],
				dayNamesMin:     ['<spring:message code="datepicker.Su" />', '<spring:message code="datepicker.Mo" />', '<spring:message code="datepicker.Tu" />', '<spring:message code="datepicker.We" />', '<spring:message code="datepicker.Th" />', '<spring:message code="datepicker.Fr" />', '<spring:message code="datepicker.Sa" />'],
				dateFormat: "dd.mm.yy"
				});	
			
		});
	
	var MSG_ALERT = '<spring:message code="alert.alert" />';	
	




		
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