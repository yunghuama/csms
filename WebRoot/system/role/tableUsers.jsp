<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>角色模块用户</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/pagination.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/list.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/xToolbar.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/xWindowPanel.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <div id="toolbar"></div>
    <form id="listForm" action="<%=path%>/system/role/listRoleDataUsers.v" method="post">
      <table id="titleTable" cellpadding="0" cellspacing="0">
        <tr>
          <td>&nbsp;</td>
          <td><input type="checkbox" id="allBox"/></td>
          <td>性别</td>
          <td>真实姓名</td>
          <td>用户名</td>
          <td>状态</td>
          <td>权限</td>
          <td>操作授权</td>
        </tr>
      </table>
      <table id="dataTable" cellpadding="0" cellspacing="0">
        <!-- 声明临时变量 -->
        <s:set name="counter" value="0"/>
        <s:iterator id="department" value="departmentList" status="i">
          <tr class="group">
            <td colspan="8">
              <span>
                <%--input onclick="updateRoleModuleDepartments(this);" id="<s:property value="#department.id"/>" type="checkbox" value="<s:property value="#department.id"/>" name="groupList"/--%>
                <s:property value="#department.name"/>
              </span>
            </td>
          </tr>
          <s:iterator id="users" value="#department.userses" status="j">
            
              <!-- 临时变量增加 -->
              <s:set name="counter" value="#counter + 1"/>
              <tr class="row">
                <td class="num"><s:property value="#counter"/></td>
                <td class="box"><input type="checkbox" name="idList" value="<s:property value="#users.id"/>"/></td>
                <td align="center"><span><img src="<%=path%>/image/sex_<s:property value="#users.sex"/>.gif"/></span></td>
                <td><span><s:property value="#users.realName"/></span></td>
                <td><span><s:property value="#users.accountName"/></span></td>
                <td align="center">
                  <s:if test="#users.state==@com.platform.constants.StringConstant@TRUE">
                    <span style="color:green;">在职</span>
                  </s:if>
                  <s:else>
                    <span style="color:red;">离职</span>
                  </s:else>
                </td>
                <td align="center"><span id="sees_<s:property value="#users.id"/>"><img src="<%=path%>/image/nosee.gif"/>禁止</span></td>
                <td align="center"><span id="sdv_<s:property value="#users.id"/>">&nbsp;</span></td>
              </tr>
           
          </s:iterator>
        </s:iterator>
      </table>
      <s:hidden name="moduleId"/>
      <s:hidden name="roleId"/>
    </form>
    
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
    <script src="<%=path%>/js/share.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script src="<%=path%>/js/Grid.js" type="text/javascript"></script>
    <script src="<%=path%>/js/BoxSelect.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xToolbar.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xToolbar.items.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xWindowPanel.js" type="text/javascript"></script>
    <script src="<%=path%>/js/Draggable.js" type="text/javascript"></script>
    <script type="text/javascript">
    
    var see = '<img src="<%=path%>/image/see.gif"/>可见';
    var nosee = '<img src="<%=path%>/image/nosee.gif"/>禁止';
    var setup = '<img src="<%=path%>/image/setup.gif"/><a href="javascript:void(0)">设置</a>';
    
    $(document).ready(function(){
    
      var toolbar = new Toolbar({
        id: '1',
        renderTo : 'toolbar',
        icon: '../../image/op.gif',
        items : [{
          type : 'button',
          text : '可见',
          position: {
            a: '-140px -40px',
            b: '-140px -160px'
          },
          handler : function(){
            if(getFirstID())
            {
              $.ajax({
                url : '<%=path%>/system/ajax/saveRoleModuleUsers.v',
                data : $('#listForm').serialize()+'&useable=T',
                success : function(json){
                  for(var i=0; i<json.length; i++)
                  {
                    loadSee(json[i], 'T');
                  }
                }
              });
            }
          }
        },'-',{
          type : 'button',
          text : '禁止',
          position: {
            a: '-140px -20px',
            b: '-140px -160px'
          },
          handler : function(){
            if(getFirstID())
            {
              $.ajax({
                url : '<%=path%>/system/ajax/saveRoleModuleUsers.v',
                data : $('#listForm').serialize()+'&useable=F',
                success : function(json){
                  for(var i=0; i<json.length; i++)
                  {
                    loadSee(json[i], 'F');
                  }
                }
              });
            }
          }
        },'-',{
          type : 'button',
          text : '刷新',
          position: {
            a: '-60px 0px',
            b: '-60px -120px'
          },
          handler : function(){
            document.getElementById('listForm').submit();
          }
        }]
      });
      
      var grid = new Grid({
        titleTable:'titleTable',
        dataTable:'dataTable',
        widths : [30,24,40,100,100,60,80,80],
        height : function(){return getGridHeight({toolbarId:'toolbar',hasPage:false});}
      });
      
      $(window).resize(function(){
        setTimeout(function(){
          grid.updateAll();
        }, 200);
      });
      
      new BoxSelect({
        allId : 'allBox',
        boxName : 'idList'
      });
      
      <s:iterator value="roleModuleUsersList">
        loadSee('<s:property/>', 'T');
      </s:iterator>
      
      <s:iterator value="roleModuleDepartmentList">
        $('#<s:property/>').attr('checked', true);
      </s:iterator>
      
    });
    
    function loadSee(id, state) {
      if(state=='T')
      {
        $('#sees_'+id).html(see);
        $('#sdv_'+id).html(setup);
        $('#sdv_'+id).css('cursor', 'pointer');
        $('#sdv_'+id).bind('click', function(){
          new WindowPanel({
            id : 'scopeDataVisibleSetup',
            title : '操作授权设置',
            width : 330,
            height : 300,
            html : '<iframe name="saveScopeDataVisibleFrame" id="saveScopeDataVisibleFrame" src="<%=path%>/system/role/toUpdateScopeDataVisible.v?usersId='+id+'&moduleId='+$('#moduleId').val()+'&roleId='+$('#roleId').val()+'" frameborder="0" scrolling="auto"></iframe>',
            minimizable: false,
            maximizable: false,
            draggable : false,
            tbar : new Toolbar({
              id: '2',
              icon: '../../image/op.gif',
              items : [{
                type : 'button',
                text : '启用',
                position: {
                  a: '-140px -40px',
                  b: '-140px -160px'
                },
                handler : function(){
                  getFrame('saveScopeDataVisibleFrame').changeVisible('T');
                }
              },'-',{
                type : 'button',
                text : '禁用',
                position: {
                  a: '-140px -20px',
                  b: '-140px -160px'
                },
                handler : function(){
                  getFrame('saveScopeDataVisibleFrame').changeVisible('F');
                }
              },'-',{
                type : 'button',
                text : '刷新',
                position: {
                  a: '-60px 0px',
                  b: '-60px -120px'
                },
                handler : function(){
                  getFrame('saveScopeDataVisibleFrame').location = getFrame('saveScopeDataVisibleFrame').location;
                }
              }]
            })
          });
        });
      }
      else
      {
        $('#sees_'+id).html(nosee);
        $('#sdv_'+id).html('&nbsp;');
        $('#sdv_'+id).unbind('click');
      }
    }
    
    //暂时过时
    function updateRoleModuleDepartments(obj) {
      var datas = obj.checked ? '&useable=T' : '&useable=F';
      datas += ('&departmentId='+obj.value);
      $.ajax({
        url : '<%=path%>/system/ajax/updateRoleModuleDepartments.v',
        data : $('#listForm').serialize() + datas
      });
    }
    </script>
  </body>
</html>