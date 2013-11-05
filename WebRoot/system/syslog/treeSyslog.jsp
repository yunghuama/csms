<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/xToolbar.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <div id="toolbarUp"></div>
    <div class="treePanel">
      <div class="paddingPanel">
        <script src="<%=path%>/js/tree/MzTreeView10.js" type="text/javascript"></script>
        <script type="text/javascript">
          var tree = new MzTreeView("tree");
          
          tree.nodes['0_1'] = 'text:操作类型';
          
          tree.icons['all'] = 'all.gif';
          tree.nodes['1_TAll'] = 'text:全部; method:changeUrl(\'ALL\'); icon:all';
          
          <s:iterator id="dictionary" value="@com.platform.constants.SyslogConstant@OP_SELECT" status="i">
          
          tree.icons["<s:property value="#i.index"/>"] = "<s:property value="#i.index"/>.gif";
          
          tree.nodes['1_T<s:property value="#i.index"/>'] = 'text:<s:property/>; method:changeUrl(\'<s:property/>\'); icon:<s:property value="#i.index"/>';
          
          </s:iterator>
          
          tree.setIconPath("<%=path%>/js/tree/syslog/");
          document.write(tree.toString());
          
        </script>
      </div>
    </div>
    <div id="toolbarDown"></div>
    
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
    <script src="<%=path%>/js/share.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xToolbar.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xToolbar.items.js" type="text/javascript"></script>
    <script type="text/javascript">
    function changeUrl(type)
    {
      parent.mainFrame.location = "<%=path%>/system/syslog/listPagination.v?type="+type;
    }
    function refreshTree()
    {
      window.location.reload();
    }
    
    $(document).ready(function(){
      new Toolbar({
        renderTo : 'toolbarUp',
        icon: '../../image/op.gif',
        items : [{
          type : 'button',
          text : '刷新',
          position: {
            a: '-60px 0px',
            b: '-60px -120px'
          },
          handler : refreshTree
        }]
      });
      setTreeHeight(['toolbarUp']);
    });
    </script>
  </body>
</html>