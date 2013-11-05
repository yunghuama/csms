<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>角色用户列表</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/list.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <div id="toolbar"></div>
    <form id="listForm" action="<%=path%>/system/role/listRoleUsers.v" method="post">
      <table id="titleTable" cellpadding="0" cellspacing="0">
        <tr>
          <td>&nbsp;</td>
          <td><input type="checkbox" id="allBox"/></td>
          <td>性别</td>
          <td>真实姓名</td>
          <td>用户名</td>
          <td>状态</td>
        </tr>
      </table>
      <table id="dataTable" cellpadding="0" cellspacing="0">
        <!-- 声明临时变量 -->
        <s:set name="counter" value="0"/>
        <s:iterator id="department" value="departmentList" status="i">
          <tr class="group">
            <td colspan="6">
              <span>
                <s:property value="#department.name"/>
              </span>
            </td>
          </tr>
          <s:iterator id="users" value="#department.userses" status="j">
            <s:if test="#users.state==@com.platform.constants.StringConstant@TRUE">
              <!-- 临时变量增加 -->
              <s:set name="counter" value="#counter + 1"/>
              <tr class="row">
                <td class="num"><s:property value="#counter"/></td>
                <td class="box"><input type="checkbox" name="idList" value="<s:property value="#users.id"/>"/></td>
                <td align="center"><span><img src="<%=path%>/image/sex_<s:property value="#users.sex"/>.gif"/></span></td>
                <td><span><s:property value="#users.realName"/></span></td>
                <td><span><s:property value="#users.accountName"/></span></td>
                <td align="center"><span id="state_<s:property value="#users.id"/>"><img src="<%=path%>/image/unlinked.gif"/></span></td>
              </tr>
            </s:if>
          </s:iterator>
        </s:iterator>
      </table>
      <s:hidden name="roleId"/>
    </form>
    
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
    <script src="<%=path%>/js/share.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script src="<%=path%>/js/Grid.js" type="text/javascript"></script>
    <script src="<%=path%>/js/BoxSelect.js" type="text/javascript"></script>
    <script type="text/javascript">
    
    var linked = '<img src="<%=path%>/image/linked.gif"/>';
    var unlinked = '<img src="<%=path%>/image/unlinked.gif"/>';
    
    $(document.body).ready(function(){
    
      new Grid({
        titleTable:'titleTable',
        dataTable:'dataTable',
        widths : [30,24,40,100,100,50],
        height : function(){return getGridHeight({hasPage:false});}
      });
      
      new BoxSelect({
        allId : 'allBox',
        boxName : 'idList'
      });
      
      <s:iterator id="ru" value="roleUsersList" status="i">
        loadState('<s:property value="#ru.users.id"/>', 'T');
      </s:iterator>
      
    });
    
    function linkUsers(useable) {
      if(getFirstID())
      {
        $.ajax({
          url : '<%=path%>/system/ajax/saveRoleUsers.v',
          data : $('#listForm').serialize()+'&useable='+useable,
          success : function(json){
            for(var i=0; i<json.length; i++)
            {
              loadState(json[i], useable);
            }
          }
        });
      }
    }
    
    function loadState(id, state) {
      if(state=='T')
        $('#state_'+id).html(linked);
      else
        $('#state_'+id).html(unlinked);
    }
    </script>
  </body>
</html>