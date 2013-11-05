<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<a href="#" id="load-mask" class="load-mask"></a>
<div id="load-msg" class="load-msg">
  ${param.msg}
</div>