<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title></title>
    <link href="<%=path%>/css/activityHelper.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<%=path%>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=path%>/js/modules/activityHelper.js"></script>
    <script src="<%=path%>/js/share.js" type="text/javascript"></script>
    <script type="text/javascript">
    	$(document).ready(function(){
    	loadReady();
    	var activityHelper = new ActivityHelper({
    		renderTo : 'activity',
    		loadDate : '<s:property value="loadDate"/>',
    		savePath : '<%=path%>/ajax/activity/save.v',
    		loadByWeekPath : '<%=path%>/ajax/activity/listByWeek.v',
    		viewPath : '<%=path%>/ajax/activity/view.v',
    		deletePath :'<%=path%>/ajax/activity/delete.v',
    		loadByMonthPath :'<%=path%>/ajax/activity/loadByMonth.v',
    		updatePath :'<%=path%>/system/activity/toUpdate.v',
    		moreInfoPath:'<%=path%>/system/activity/toSave.v'
    		});
    	});
    </script>
  </head>
  <body onselectstart="return false" style="-moz-user-select: none;">
  <s:include value="/share/btMask.jsp" />
  		<div id="activity">
  		</div>
  </body>
</html>