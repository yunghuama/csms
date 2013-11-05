<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>Refresh</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/jquery-impromptu.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery-impromptu.js" type="text/javascript"></script>
    <script type="text/javascript">
      var txt = '操作成功';
      $.prompt(txt,{buttons:{确定:true},callback:closeWindowAndRefresh});
      //更新为已设置的样式
      function closeWindowAndRefresh() {
        parent.$('#state_<s:property value="field.webId"/>').html(parent.on);
        parent.WindowPanel.hideById('updateField');
        parent.WindowPanel.killById('updateField');
      }
    </script>
  </body>
</html>