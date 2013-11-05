<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>集团名片</title>
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
  </head>
  <body>
    <script type="text/javascript" src="<%=path%>/js/core.js" ></script>
    <script type="text/javascript" src="<%=path%>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=path%>/js/share.js"></script>
    <script type="text/javascript">
    if ($.browser.msie) {
      var _left = 0;
      var _top = 0;
      var _location = 'no';
      var aheight=window.screen.availHeight;//屏幕高度
      var awidth=window.screen.availWidth;//屏幕宽度
      var options = new Array();
      options.push('toolbar=no,scrollbars=no,menubar=no,location=no,resizable=yes');
      options.push(',width=');
      options.push(awidth);
      options.push(',height=');
      options.push(aheight);
      options.push(',left=');
      options.push(_left);
      options.push(',top=');
      options.push(_top);
      options.push(',location=');
      options.push(_location);
      window.open(projectName+'/main.jsp', '', options.join(''));
      self.opener = null; 
      self.open('','_self'); 
      self.close();
    }else{
      window.location = projectName+'/main.jsp';
    }
    </script>
  </body>
</html>