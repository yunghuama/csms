<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String swf = request.getParameter("swf");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'OnLineView.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="<%=path%>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=path%>/js/onlineview/flexpaper_flash.js"></script>
  </head>
  <body>
  <a id="viewerPlaceHolder" style="width:830px;height:630px;display:block"></a>
  	<script type="text/javascript">
		var fp = new FlexPaperViewer(	
				 '<%=path%>/js/onlineview/FlexPaperViewer',
				 'viewerPlaceHolder', { config : {
				 SwfFile : '<%=path+swf%>',
				 Scale : 1.0, //显示比例
				 ZoomTransition : 'easeOut',
				 ZoomTime : 0.5,
				 ZoomInterval : 0.2,
				 FitPageOnLoad : false,
				 FitWidthOnLoad : false,
				 PrintEnabled : true,
				 FullScreenAsMaxWindow : false,
				 ProgressiveLoading : false,
				 MinZoomSize : 0.2,
				 MaxZoomSize : 5,
				 SearchMatchAll : false,
				 InitViewMode : 'Portrait',
				 
				 ViewModeToolsVisible : true,
				 ZoomToolsVisible : true,
				 NavToolsVisible : true,
				 CursorToolsVisible : true,
				 SearchToolsVisible : true,
				 localeChain: 'en_US'
				 }});
   </script>
  </body>
</html>
