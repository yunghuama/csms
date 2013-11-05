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
    
    //桌面刷新bug
    if(tabId==='workspace'){
      top.WPS['jWindowPanel-'+windowPanelId].close();
    }else{
      closeWindowAndRefresh();
    }
    
    function closeWindowAndRefresh() {
      if(windowPanelId) {
        top.WPS['jWindowPanel-'+windowPanelId].hide();
      }
      if(tabId) {
        top.tabpanel.show(tabId);
        if(tabId !== 'workLog'){//工作日志保存后不刷新tab
          top.tabpanel.flush(tabId);
        }else{
            if('<s:property value="flushFlag"/>' == '1'){
                top.tabpanel.flush(tabId);
            }
        }
      }
      if(windowPanelId) {
        top.WPS['jWindowPanel-'+windowPanelId].close();
      }
    }
    </script>
  </body>
</html>