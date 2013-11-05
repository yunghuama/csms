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
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript">
      try{loadReady();}catch(e){}
    </script>
  </head>
  <body>
    <div class="noData">
      <div class="textDiv" style="width:400px; font-size: 18px;">您可能长时间未操作系统，请尝试<a style="font-size: 18px;" href="<%=path%>/login.jsp">重新登录</a></div>
    </div>
  </body>
  <script src="<%=path%>/js/share.js" type="text/javascript"></script>
  <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
  <script type="text/javascript">
  $(function(){
    var textDiv = $('div.textDiv');
    textDiv.css({
      left: ($(document.body).width() - textDiv.width())/2,
      top: ($(document.body).height() - textDiv.height())/2
    });
  });
  </script>
</html>