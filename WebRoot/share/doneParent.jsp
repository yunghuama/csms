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
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery-impromptu.js" type="text/javascript"></script>
  </head>
  <body>
    <script type="text/javascript">
    var tabId = '<s:property value="tabId"/>';
    var windowPanelId = '<s:property value="windowPanelId"/>';
    alert('操作成功');
    closeWindowAndRefresh();
    function closeWindowAndRefresh() {
      if(windowPanelId) {
        parent.WPS['jWindowPanel-'+windowPanelId].hide();
      }
      if(tabId) {
        parent.tabpanel.show(tabId);
        parent.tabpanel.flush(tabId);
      }
      if(windowPanelId) {
        parent.WPS['jWindowPanel-'+windowPanelId].close();
      }
    }
    </script>
  </body>
</html>