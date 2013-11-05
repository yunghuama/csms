<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title><s:property value="role.name"/> - 权限设置</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
  </head>
  <body style="position: relative;">
    <div class="leftFrame" style="width:25%; position: absolute; left:0; top:0;">
      <iframe src="<%=path%>/system/role/listModuleTree.v?roleId=<s:property value="roleId"/>" frameborder="0" name="leftFrame" id="leftFrame"></iframe>
    </div>
    <div class="mainFrame" style="width:75%; position: absolute; right:0; top:0;">
      <iframe src="<%=path%>/system/role/listRoleUFO.v?roleId=<s:property value="roleId"/>" frameborder="0" name="mainFrame" id="mainFrame"></iframe>
    </div>
  </body>
</html>