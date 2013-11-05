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
    <link href="<%=path%>/css/pagination.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/list.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/xToolbar.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/xWindowPanel.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/jquery-impromptu.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <s:include value="/share/btMask.jsp" />
    <div id="toolbar"></div>
    <form id="listForm" action="<%=path%>/system/role/listPagination.v" method="post">
      <table id="titleTable" cellpadding="0" cellspacing="0">
        <tr>
          <td>&nbsp;</td>
          <td><input type="checkbox" id="allBox"/></td>
          <td id="nameSort">角色名称</td>
          <td>说明</td>
          <td>创建人</td>
          <td>创建时间</td>
        </tr>
      </table>
      <table id="dataTable" cellpadding="0" cellspacing="0">
        <s:iterator id="role" value="page.list" status="i">
          <tr class="row">
            <td class="num"><s:property value="#i.index+1"/></td>
            <td class="box">
              <s:if test="#role.creator.id == #session['LoginBean'].user.id">
                <input type="checkbox" name="idList" value="<s:property value="#role.id"/>"/>
              </s:if>
              <s:else>
                <img class="more-operate" src="<%=path%>/image/more-operate.gif" onclick="openMoreOperateWindow(event, '<s:property value="#role.creator.id"/>','<s:property value="#role.creator.department.id"/>','<s:property value="#role.id"/>');"/>
              </s:else>
            </td>
            <td><span><s:property value="#role.name"/></span></td>
            <td><span><s:property value="#role.remark" escape="false"/></span></td>
            <td><span><s:property value="#role.creator.realName"/></span></td>
            <td align="center"><span><s:date name="#role.createTime" format="yyyy-MM-dd HH:mm"/></span></td>
          </tr>
        </s:iterator>
      </table>
      <s:include value="/share/pagebar.jsp"/>
    </form>
    
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
    <script src="<%=path%>/js/share.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script src="<%=path%>/js/Grid.js" type="text/javascript"></script>
    <script src="<%=path%>/js/BoxSelect.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xToolbar.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xToolbar.items.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xWindowPanel.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery-impromptu.js" type="text/javascript"></script>
    <script type="text/javascript">
    
    $(document).ready(function(){
    
      var toolbar = new Toolbar({
        id: '1',
        renderTo : 'toolbar',
        icon: '../../image/op.gif',
        items : [{
          type : 'button',
          text : '新建',
          useable : '<s:property value="@com.platform.util.Meta@getOperate(\"role_new\")"/>',
          position: {
            a: '0px 0px',
            b: '0px -120px'
          },
          handler : function(){
            top.roleFunctions.openSaveRoleWindow();
          }
        },'-',{
          type : 'button',
          text : '修改',
          useable : '<s:property value="@com.platform.util.Meta@getOperate(\"role_edit\")"/>',
          position: {
            a: '-20px 0px',
            b: '-20px -120px'
          },
          handler : function(){
            if(getFirstID())
              top.roleFunctions.openUpdateRoleWindow(getFirstID());
          }
        },'-',{
          type : 'button',
          text : '权限设置',
          useable : '<s:property value="@com.platform.util.Meta@getOperate(\"role_setup\")"/>',
          position: {
            a: '-160px -40px',
            b: '-160px -160px'
          },
          handler : function(){
            if(getFirstID()) {
              openCenterWindow({
                url:projectName+'/system/role/toSaveUFO.v?roleId='+getFirstID(),
                width:800,
                height:500
              });
            }
          }
        },'-',{
          type : 'button',
          text : '关联用户',
          useable : '<s:property value="@com.platform.util.Meta@getOperate(\"role_linkusers\")"/>',
          position: {
            a: '0px -60px',
            b: '0px -180px'
          },
          handler : function(){
            if(getFirstID())
            {
              top.roleFunctions.openSaveRoleUsersWindow(getFirstID());
            }
          }
        },'-',{
          type : 'button',
          text : '删除',
          useable : '<s:property value="@com.platform.util.Meta@getOperate(\"role_delete\")"/>',
          position: {
            a: '-40px 0px',
            b: '-40px -120px'
          },
          handler : function(){
            validateBeforeDelete({
              validateURL : '<%=path%>/system/ajax/deleteValidate.v',
              validateParams : $('input:checkbox[name="idList"]:checked').serialize(),
              validateTable : 'Role',
              validateMessage : '标红角色已与用户关联，删除前请清除关联！',
              deleteMessage : '是否删除角色？',
              deleteURL : '<%=path%>/system/role/delete.v'
            });
          }
        }]
      });
      
      new Grid({
        titleTable:'titleTable',
        dataTable:'dataTable',
        widths : [30,23,160,500,100,130],
        height : function(){return getGridHeight({toolbarId:'toolbar',hasPage:true});}
      });
      
      new BoxSelect({
        allId : 'allBox',
        boxName : 'idList'
      });
      
      loadReady();
    });
    
    function openMoreOperateWindow(e, usersId,departmentId, id) {
      var morebar = new Toolbar({
        id:'2',
        icon: '../../image/op.gif',
        border: 'none',
        items : [{
          type : 'button',
          text : '修改',
          useable : findOtherOperate(usersId,departmentId, 'role_edit'),
          position: {
            a: '-20px 0px',
            b: '-20px -120px'
          },
          handler : function(){
            top.roleFunctions.openUpdateRoleWindow(id);
            WPS['jWindowPanel-moreOperate'].close();
          }
        },'-',{
          type : 'button',
          text : '权限设置',
          useable : findOtherOperate(usersId,departmentId, 'role_setup'),
          position: {
            a: '-160px -40px',
            b: '-160px -160px'
          },
          handler : function(){
            openCenterWindow({
              url:projectName+'/system/role/toSaveUFO.v?roleId='+id,
              width:800,
              height:500
            });
            WPS['jWindowPanel-moreOperate'].close();
          }
        },'-',{
          type : 'button',
          text : '关联用户',
          useable : findOtherOperate(usersId,departmentId, 'role_linkusers'),
          position: {
            a: '0px -60px',
            b: '0px -180px'
          },
          handler : function(){
            top.roleFunctions.openSaveRoleUsersWindow(id);
            WPS['jWindowPanel-moreOperate'].close();
          }
        },'-',{
          type : 'button',
          text : '删除',
          useable : findOtherOperate(usersId,departmentId, 'role_delete'),
          position: {
            a: '-40px 0px',
            b: '-40px -120px'
          },
          handler : function(){
            WPS['jWindowPanel-moreOperate'].close();
            var txt = '是否删除？';
            $.prompt(txt,{
              buttons:{删除:true,取消:false},
              callback: function(v,m) {
                if(v) {
                  window.location = '<%=path%>/system/role/delete.v?idList='+id;
                }
              }
            });
          }
        }]
      });
      var wp = new WindowPanel({
        id : 'moreOperate',
        title : '可用操作',
        width : 280,
        height : 0,
        draggable : false,
        minimizable: false,
        maximizable: false,
        position: {
          x: e.clientX,
          y: e.clientY
        },
        tbar: morebar
      });
    }
    </script>
  </body>
</html>