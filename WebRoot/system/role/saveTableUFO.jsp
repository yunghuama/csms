<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>权限列表</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/xTabPanel.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <div id="tabs" style="width:100%; height:100%;"></div>
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xTabPanel.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script type="text/javascript">
    var tabpanel;
    $(document).ready(function(){
      tabpanel = new TabPanel({
    	id : 'jtable',
        renderTo:'tabs',
        border: false,
        autoResize: true,
        icon: '../../image/module_icon.gif',
        defaultTab: 0,
        items : [{
          id: 'userOperate',
          title:'操作权限',
          html:'<iframe id="operate" name="operate" src="'+projectName+'/system/role/listModuleOperate.v?moduleId=<s:property value="moduleId"/>&roleId=<s:property value="roleId"/>" width="100%" height="100%" frameborder="0"></iframe>',
          closable : false,
          position: {
            a: '-44px 0px',
            b: '-66px 0px'
          }
        }]
      });
      <s:if test="module.hasUsers == \"T\"">
        tabpanel.addTab({
          id:'usersRule',
          title:'用户数据范围',
          html:'<iframe id="users" name="users" src="'+projectName+'/system/role/listRoleDataUsers.v?moduleId=<s:property value="moduleId"/>&roleId=<s:property value="roleId"/>" width="100%" height="100%" frameborder="0"></iframe>',
          closable : false,
          lazyload : true,
          position: {
            a: '0px -176px',
            b: '-22px -176px'
          }
        });
      </s:if>
      <s:if test="module.hasDepartment == \"T\"">
        tabpanel.addTab({
          id:'departmentRule',
          title:'部门数据范围',
          html:'<iframe id="department" name="department" src="'+projectName+'/system/role/listRoleDataDepartments.v?moduleId=<s:property value="moduleId"/>&roleId=<s:property value="roleId"/>" width="100%" height="100%" frameborder="0"></iframe>',
          closable : false,
          lazyload : true,
          position: {
            a: '0px -66px',
            b: '-22px -66px'
          }
        });
      </s:if>
      <s:if test="module.hasField == \"T\"">
        tabpanel.addTab({
          id:'fieldRule',
          title:'字段约束',
          html:'<iframe id="field" name="field" src="'+projectName+'/system/role/listModuleField.v?moduleId=<s:property value="moduleId"/>&roleId=<s:property value="roleId"/>" width="100%" height="100%" frameborder="0"></iframe>',
          closable : false,
          lazyload : true,
          position: {
            a: '0px -198px',
            b: '-22px -198px'
          }
        });
      </s:if>
    });
    </script>
  </body>
</html>