<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String intercomId = (String)request.getParameter("intercomId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>内部通信关联信息按钮</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/xToolbar.css" rel="stylesheet" type="text/css"/>
  </head>
  <body class="form-scroll">
    <div id="toolbarUp"></div>
    <div class="split"> 
	    <div id="viewTab" class="closed" onclick="jumpTo('viewTab','<%=path%>/system/intercom/view.v?intercom.id=<%=intercomId %>');">详细信息</div>        
    </div>
    <div id="toolbarDown"></div>
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
    <script src="<%=path%>/js/share.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xToolbar.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xToolbar.items.js" type="text/javascript"></script>
    <script type="text/javascript">
    function refresh()
    {
      window.location.reload();
    }
    
    $(document).ready(function(){
      $('#viewTab').removeClass('closed');
      $('#viewTab').addClass('opened');
      
      new Toolbar({
        id: '1',
        renderTo : 'toolbarUp',
        icon: '<%=path%>/image/op.gif',
        items : [{
          type : 'button',
          text : '刷新',
          position: {
            a: '-60px 0px',
            b: '-60px -120px'
          },
          handler : refresh
        }]
      });
      
      setTreeHeight(['toolbarUp', 'toolbarDown']);
    });
    </script>
  </body>
</html>