<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>字典列表</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <div class="leftFrame">
      <iframe src="<%=path%>/system/users/listTree.v" frameborder="0" name="leftFrame" id="leftFrame"></iframe>
    </div>
    <div class="mainFrame">
      <iframe src="<%=path%>/system/users/listPagination.v" frameborder="0" name="mainFrame" id="mainFrame"></iframe>
    </div>
  </body>
</html>