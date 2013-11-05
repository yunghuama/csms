<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head><style>
    #main {
      height: 100%;
      width: 100%;
    }
    #step {
      position: absolute;
      border: 4px solid #C03;
      display: none;
      z-index: 2;
      filter:alpha(opacity=70);
      -moz-opacity:0.7;
      -khtml-opacity: 0.7;
      opacity: 0.7;
    }
    #main img{
      position: absolute;
      top: 32px;
      left: 16px;
      z-index: 1;
    }
  </style></head>
  
  <body>
    <div id="main">
      <img src="<%=path%>/workflow/showDiagram.v?processDefinitionId=<s:property value="processDefinitionId"/>" />
      <div id="step"></div>
    </div>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(function(){
      var xy = $.trim('<s:property value="diagramXY"/>');
      var pos = xy.split('|');
      var start = pos[0].split(',');
      var end = pos[1].split(',');
      $('#step').offset({left: start[0]-0+8, top: start[1]-0+24});
      $('#step').width(end[0]-start[0]+8);
      $('#step').height(end[1]-start[1]+8);
      $('#step').show();
    });
    </script>
  </body>
</html>