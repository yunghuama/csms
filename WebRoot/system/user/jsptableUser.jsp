<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.platform.util.JSPPageHelper"/>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
JSPPageHelper.generatePage(request);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>用户列表</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/pagination.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/list.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/Toolbar.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/WindowPanel.css" rel="stylesheet" type="text/css"/>
    <!-- link href="<%=path%>/css/jquery-impromptu.css" rel="stylesheet" type="text/css"/> -->
  </head>
  <body>
    <div id="toolbar"></div>
    <form id="listForm" action="" method="post">
    	<table id="titleTable" cellpadding="0" cellspacing="0">
        <tr>
          <td>&nbsp;</td>
          <td><input type="checkbox" id="allBox"/></td>
          <td>性别</td>
          <td id="rnSort">真实姓名</td>
          <td id="acSort">用户名</td>
          <td>所在部门</td>
          <td>创建人</td>
          <td>创建时间</td>
          <td>状态</td>
        </tr>
    	</table>
      <table id="dataTable" cellpadding="0" cellspacing="0">
        <s:iterator id="users" value="#request['page'].list" status="i">
          <tr class="row">
            <td class="num"><s:property value="#i.index+1"/></td>
            <td class="box">
              <s:if test="#users.creator.id == #session['LoginBean'].user.id">
                <input type="checkbox" name="idList" value="<s:property value="#users.id"/>"/>
              </s:if>
              <s:else>
                <img class="more-operate" src="<%=path%>/image/more-operate.gif" onclick="openMoreOperateWindow('<s:property value="#users.creator.id"/>', '<s:property value="#users.id"/>');"/>
              </s:else>
            </td>
            <td align="center"><span><img src="<%=path%>/image/sex_<s:property value="#users.sex"/>.gif"/></span></td>
            <td><span><s:property value="#users.realName"/></span></td>
            <td><span><s:property value="#users.accountName"/></span></td>
            <td><span><s:property value="#users.department.name"/>&nbsp;</span></td>
            <td><span><s:property value="#users.creator.realName"/></span></td>
            <td align="center"><span><s:date name="#users.createTime" format="yyyy-MM-dd HH:mm:ss"/></span></td>
            <td id="td<s:property value="#users.id"/>" align="center">
            <span>
              <s:if test="#users.state==@com.platform.constants.StringConstant@TRUE">
                <!-- a href="#" onclick="changeState('<s:property value="@com.platform.constants.StringConstant@FALSE"/>','<s:property value="#users.id"/>');">可用</a-->
                <font color="green">在职</font>
              </s:if>
              <s:else>
                <!-- a href="#" onclick="changeState('<s:property value="@com.platform.constants.StringConstant@TRUE"/>','<s:property value="#users.id"/>');">不可用</a-->
                <font color="red">离职</font>
              </s:else>
            </span>
            </td>
          </tr>
        </s:iterator>
      </table>
      <s:hidden name="deptId"/>
      <s:hidden name="azparam"/>
      <s:include value="/share/pagebar.jsp"/>
    </form>
    
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
    <script src="<%=path%>/js/share.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script src="<%=path%>/js/Grid.js" type="text/javascript"></script>
    <script src="<%=path%>/js/BoxSelect.js" type="text/javascript"></script>
    <script src="<%=path%>/js/Toolbar.js" type="text/javascript"></script>
    <script src="<%=path%>/js/WindowPanel.js" type="text/javascript"></script>
    <script src="<%=path%>/js/Drag.js" type="text/javascript"></script>
    <!-- <script src="<%=path%>/js/jquery-impromptu.js" type="text/javascript"></script> -->
    <script type="text/javascript">
    $(document).ready(function(){
      
      var toolbar = new Toolbar({
        renderTo : 'toolbar',
        items : [{
          type:'button',
          text:'新建',
          bodyStyle : 'new',
          useable : '<s:property value="@com.platform.util.Meta@getOperate(\"users_new\")"/>',
          handler:function(){
            top.openSaveUserWindow($('#deptId').val());
          }
        },'-',{
            type : 'button',
            text : '修改',
            bodyStyle : 'edit',
            useable : '<s:property value="@com.platform.util.Meta@getOperate(\"users_edit\")"/>',
            handler : function(){
              if(getFirstID())
              {
                top.openUpdateUserWindow(getFirstID());
              }
            }
          },/*'-',{
            type : 'button',
            text : '删除',
            bodyStyle : 'delete',
            useable : '<s:property value="@com.platform.util.Meta@getOperate(\"users_delete\")"/>',
            handler : function(){
              if(getFirstID())
              {
                var txt = '是否删除？';
                $.prompt(txt,{
                  buttons:{删除:true,取消:false},
                  callback: function(v,m)
                  {
                    if(v)
                    {
                      document.getElementById('listForm').action = '<%=path%>/system/users/delete.v';
                      document.getElementById('listForm').submit();
                    }
                  }
                });
              }
            }
          },*/'-',{
            type : 'button',
            text : '关联角色',
            bodyStyle : 'link-role',
            useable : '<s:property value="@com.platform.util.Meta@getOperate(\"users_linkrole\")"/>',
            handler : function(){
              if(getFirstID())
              {
                top.openSaveUsersRoleWindow(getFirstID());
              }
            }
          },'-',{
            type:'az',
            text:'A-Z',
            bodyStyle:'filter'
          }],
          azable : true,
          azparam : 'azparam'
      }).render();
      
      //生成AZ搜索
      toolbar.genAZ();
      
      new Grid({
        titleTable:'titleTable',
        dataTable:'dataTable',
        widths : [26,24,40,100,100,150,80,150,80],
        height : function(){return getGridHeight({toolbarId:'toolbar',hasPage:true});}
      });
      
      new BoxSelect({
        allId : 'allBox',
        boxName : 'idList'
      });
      
      loadReady();
    });
    //修改账号状态
    function changeState(param,id)
    {
      $.ajax({
        type : "post",
        dataType : "html",
        url : '<%=path%>/system/ajax/changeUserState.v',
        data : "state="+param+"&userId="+id,
        success : function(){
          document.getElementById('td'+id).innerHTML = returnContent(param,id);
          //$('#td'+id).html
        }
      });
    }
    //返回内容
    function returnContent(param,id)
    {
      var content;
      if(param=='T')
        content = '<span><a href="#" onclick="changeState(\'<s:property value="@com.platform.constants.StringConstant@FALSE"/>\',\''+id+'\');">可用</a></span>';
      else if(param=='F')
        content = '<span><a href="#" onclick="changeState(\'<s:property value="@com.platform.constants.StringConstant@TRUE"/>\',\''+id+'\');">不可用</a></span>';
      return content;
    }
    
    function openMoreOperateWindow(usersId, id) {
      var wp = new WindowPanel({
        id : 'moreOperate',
        title : '可用操作',
        width : 442,
        height : 26,
        dragable : false
      });
      new Toolbar({
        renderTo : wp.windowpanel_content,
        border : 'none',
        items : [{
          type : 'button',
          text : '修改',
          bodyStyle : 'edit',
          useable : findOtherUsersOperate(usersId, 'users_edit'),
          handler : function(){
            top.openUpdateUserWindow(id);
            WindowPanel.killById('moreOperate');
          }
        },/*'-',{
          type : 'button',
          text : '删除',
          bodyStyle : 'delete',
          useable : findOtherUsersOperate(usersId, 'users_delete'),
          handler : function(){
            WindowPanel.killById('moreOperate');
            var txt = '是否删除？';
            $.prompt(txt,{
              buttons:{删除:true,取消:false},
              callback: function(v,m)
              {
                if(v)
                {
                  window.location = '<%=path%>/system/users/delete.v?idList='+id;
                }
              }
            });
          }
        },'-'*/{
          type : 'button',
          text : '关联角色',
          bodyStyle : 'link-role',
          useable : findOtherUsersOperate(usersId, 'users_linkrole'),
          handler : function(){
            top.openSaveUsersRoleWindow(id);
            WindowPanel.killById('moreOperate');
          }
        }]
      }).render();
    }
  
    </script>
  </body>
</html>