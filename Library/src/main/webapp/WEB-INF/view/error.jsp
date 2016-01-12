<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div  align="left">
<h4><s:text name="error.Error"/>: </h4><s:property value="message" /><br/> 
<h4><s:text name="error.Exception"/>: </h4><s:property value="exception" /><br/> 
<h4><s:text name="error.Trace"/>:</h4> <s:property value="exceptionStack" />
</div> 