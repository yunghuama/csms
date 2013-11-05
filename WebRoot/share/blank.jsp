<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title></title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <div class="noData">
      <div class="textDiv">未查找到数据</div>
    </div>
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
  </body>
</html>