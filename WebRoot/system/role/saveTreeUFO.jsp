<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>Module tree</title>
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
          
          tree.setIconPath("<%=path%>/js/tree/role/");
          
          tree.nodes['0_1'] = 'text:系统模块';
          
          <s:iterator id="module" value="moduleList">
            <s:if test="#module.superId==@com.platform.constants.StringConstant@ROOT_ID">
              tree.nodes['1_<s:property value="#module.id"/>'] = 'text:<s:property value="#module.name"/>';
            </s:if>
            <s:else>
              tree.nodes['<s:property value="#module.superId"/>_<s:property value="#module.id"/>'] = 'text:<s:property value="#module.name"/>; method:changeUrl(\'<s:property value="#module.id"/>\')';
            </s:else>
          </s:iterator>
          
          document.write(tree.toString());
          
          tree.expandAll();
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
    function changeUrl(moduleId)
    {
      parent.mainFrame.location = '<%=path%>/system/role/listRoleUFO.v?moduleId='+moduleId+'&roleId=<s:property value="roleId"/>';
    }
    
    $(document).ready(function(){
    
      new Toolbar({
        id: '1',
        renderTo : 'toolbarUp',
        icon: '../../image/op.gif',
        items : [{
          type : 'button',
          text : '刷新',
          position: {
            a: '-60px 0px',
            b: '-60px -120px'
          },
          handler : function(){
            window.location.reload();
          }
        },{
          type : 'button',
          text : '帮助',
          position: {
            a: '-180px -80px',
            b: '-180px -200px'
          },
          handler : function(){
            parent.mainFrame.location = '<%=path%>/system/role/help.html';
          }
        }]
      });
      
      new Toolbar({
        id: '2',
        renderTo : 'toolbarDown',
        border : 'top',
        icon: '../../image/op.gif',
        items : [{
          type : 'button',
          text : '展开全部',
          position: {
            a: '-160px -80px',
            b: '-160px -200px'
          },
          handler : function(){
            tree.expandAll();
          }
        }]
      });
      
      setTreeHeight(['toolbarUp', 'toolbarDown']);
      $(window).resize(function(){
        setTimeout(function(){setTreeHeight(['toolbarUp', 'toolbarDown']);},100);
      });
    });
    </script>
  </body>
</html>