<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>用户角色列表</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/list.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <div id="toolbar"></div>
    <form id="listForm" action="<%=path%>/system/users/listUsersRole.v" method="post">
      <table id="titleTable" cellpadding="0" cellspacing="0">
        <tr>
          <td>&nbsp;</td>
          <td><input type="checkbox" id="allBox"/></td>
          <td id="roleSort">角色</td>
          <td>状态</td>
        </tr>
      </table>
      <table id="dataTable" cellpadding="0" cellspacing="0">
        <s:iterator id="role" value="roleList" status="i">
          <tr class="row">
            <td class="num"><s:property value="#i.index+1"/></td>
            <td class="box"><input type="checkbox" name="idList" value="<s:property value="#role.id"/>"/></td>
            <td><span><s:property value="#role.name"/></span></td>
            <td align="center"><span id="state_<s:property value="#role.id"/>"><img src="<%=path%>/image/unlinked.gif"/>未关联</span></td>
          </tr>
        </s:iterator>
      </table>
      <s:hidden name="userId"/>
    </form>
    
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
    <script src="<%=path%>/js/share.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script src="<%=path%>/js/Grid.js" type="text/javascript"></script>
    <script src="<%=path%>/js/BoxSelect.js" type="text/javascript"></script>
    <script type="text/javascript">
    
    var linked = '<img src="<%=path%>/image/linked.gif"/>已关联';
    var unlinked = '<img src="<%=path%>/image/unlinked.gif"/>未关联';
    
    $(document.body).ready(function(){
    
      new Grid({
        titleTable:'titleTable',
        dataTable:'dataTable',
        widths : [30,24,190,94],
        height : function(){return getGridHeight({hasPage:false});}
      });
      
      new BoxSelect({
        allId : 'allBox',
        boxName : 'idList'
      });
      
      <s:iterator id="ur" value="usersRoleList" status="i">
        loadState('<s:property value="#ur.role.id"/>', 'T');
      </s:iterator>
      
    });
    
    function linkRole(state) {
      if(getFirstID())
      {
        $.ajax({
          url : '<%=path%>/system/ajax/saveUsersRole.v',
          data : $('#listForm').serialize()+'&state='+state,
          success : function(json){
            for(var i=0; i<json.length; i++)
            {
              loadState(json[i], state);
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