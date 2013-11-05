<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>日志列表</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
    .leftFrame { width: 189px;}
    .mainFrame { margin-left: 0;}
    </style>
  </head>
  <body>
    <div class="leftFrame">
      <iframe src="<%=path%>/system/workspace/activity/treeActivity.jsp" frameborder="0" name="leftFrame" id="leftFrame" title="leftFrame"></iframe>
    </div>
    <div class="mainFrame" >
      <iframe src="<%=path%>/system/activity/load.v"  frameborder="0" height="500px;" name="mainFrame" id="mainFrame" title="mainFrame" ></iframe>
    </div>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(function(){
      $('.mainFrame').width($(document.body).width() - 190);
    });
    </script>
  </body>
</html>