<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>请重新登录</title>
    <script type="text/javascript">
      try{loadReady();}catch(e){}
      top.location = '<%=path%>/doRelogin.jsp';
    </script>
  </head>
  <body>
  </body>
  <script src="<%=path%>/js/share.js" type="text/javascript"></script>
</html>