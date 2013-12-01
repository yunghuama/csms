<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>用户列表</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/pagination.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/list.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/xToolbar.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/jquery-impromptu.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/xWindowPanel.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <s:include value="/share/btMask.jsp" />
    <div id="toolbar"></div>
    <form id="listForm" action="<%=path%>/system/users/listPagination.v" method="post">
      <s:hidden name="searchType"/>
      <div class="search-div">
        <div class="search-condition">
          <table class="search-table" cellspacing="0" cellpadding="0">
            <tr>
              <td class="c-left">管理员类型：</td>
              <td><s:select list="roleList" listKey="id" listValue="name" name="searchValue[0]" headerKey="" headerValue="全部"></s:select></td>
              <td class="c-left">用户名</td>
              <td><input name="searchValue[1]" type="text" class="text" value="<s:property value="searchValue[1]"/>"/></td>
            </tr>
          </table>
        </div>
        <div class="search-commit">
          <a href="javascript:void(0)" class="search-button" onclick="$('#searchType').val('and');$('#listForm').submit();">与查询</a>
          <a href="javascript:void(0)" class="search-button" onclick="$('#searchType').val('or');$('#listForm').submit();">或查询</a>
        </div>
      </div>
    	<table id="titleTable" cellpadding="0" cellspacing="0">
        <tr>
          <td>&nbsp;</td>
          <td><input type="checkbox" id="allBox"/></td>
          <td>用户名</td>
          <td>所在区域</td>
          <td>所在企业</td>
          <td>备注</td>
          <td>状态</td>
          <td>类型</td>
        </tr>
    	</table>
      <table id="dataTable" cellpadding="0" cellspacing="0">
        <s:iterator id="users" value="page.list" status="i">
          <tr class="row">
            <td class="num"><s:property value="#i.index+1"/></td>
            <td class="box">
               <input type="checkbox" name="idList" value="<s:property value="#users.id"/>"/>
            </td>
            <td><span><s:property value="#users.accountName"/></span></td>
            <td align="center"><span><s:property value="#users.district.name"/></span></td>
            <td><span><s:property value="#users.enterprise.name"/></span></td>
            <td><span><s:property value="#users.remark"/>&nbsp;</span></td>
            <td id="td<s:property value="#users.id"/>" align="center">
            <span>
              <s:if test="#users.state==@com.platform.constants.StringConstant@TRUE">
                <font color="green">在职</font>
              </s:if>
              <s:else>
                <font color="red">离职</font>
              </s:else>
            </span>
            </td>
            <td><span><s:property value="#users.role.name"/></span></td>
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
    <script src="<%=path%>/js/xToolbar.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xToolbar.items.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery-impromptu.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xWindowPanel.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(document).ready(function(){
      
      var toolbar = new Toolbar({
        id : '1',
        renderTo : 'toolbar',
        icon: '../../image/op.gif',
        items : [{
          type:'button',
          text:'新建',
          useable : '<s:property value="@com.platform.util.Meta@getOperate(\"users_new\")"/>',
          position: {
            a: '0px 0px',
            b: '0px -120px'
          },
          handler:function(){
            top.userFunctions.openSaveUserWindow($('#deptId').val());
          }
        },'-',{
          type : 'button',
          text : '修改',
          useable : '<s:property value="@com.platform.util.Meta@getOperate(\"users_edit\")"/>',
          position: {
            a: '-20px 0px',
            b: '-20px -120px'
          },
          handler : function(){
            if(getFirstID())
            {
              top.userFunctions.openUpdateUserWindow(getFirstID());
            }
          }
        },
        /**'-',{
          type : 'button',
          text : '删除',
          useable : '<s:property value="@com.platform.util.Meta@getOperate(\"users_delete\")"/>',
          position: {
            a: '-40px 0px',
            b: '-40px -120px'
          },
          handler : function(){
            if(getFirstID())
            {
              var txt = '是否删除员工？';
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
        },
      
        '-',{
          type : 'button',
          text : '关联角色',
          useable : '<s:property value="@com.platform.util.Meta@getOperate(\"users_linkrole\")"/>',
          position: {
            a: '0px -60px',
            b: '0px -180px'
          },
          handler : function(){
            if(getFirstID())
            {
              top.userFunctions.openSaveUsersRoleWindow(getFirstID());
            }
          }
        }, **/
        
        '-',{
          type: 'search'
        }]
      });
      
      new Grid({
        titleTable:'titleTable',
        dataTable:'dataTable',
        widths : [26,50,150,100,150,230,80],
        height : function(){return getGridHeight({toolbarId:'toolbar',hasPage:true});}
      });
      
      new BoxSelect({
        allId : 'allBox',
        boxName : 'idList'
      });
      
      loadReady();
    });
    //修改账号状态
    function changeState(param,id) {
      $.ajax({
        type : "post",
        dataType : "html",
        url : '<%=path%>/system/ajax/changeUserState.v',
        data : "state="+param+"&userId="+id,
        success : function(){
          document.getElementById('td'+id).innerHTML = returnContent(param,id);
        }
      });
    }
    //返回内容
    function returnContent(param,id) {
      var content;
      if(param=='T')
        content = '<span><a href="#" onclick="changeState(\'<s:property value="@com.platform.constants.StringConstant@FALSE"/>\',\''+id+'\');">可用</a></span>';
      else if(param=='F')
        content = '<span><a href="#" onclick="changeState(\'<s:property value="@com.platform.constants.StringConstant@TRUE"/>\',\''+id+'\');">不可用</a></span>';
      return content;
    }
    
    function openMoreOperateWindow(e, usersId,departmentId, id) {
      var morebar = new Toolbar({
        id:'2',
        icon: '../../image/op.gif',
        border: 'none',
        items : [{
          type : 'button',
          text : '修改',
          useable : findOtherOperate(usersId,departmentId, 'users_edit'),
          position: {
            a: '-20px 0px',
            b: '-20px -120px'
          },
          handler : function(){
            top.userFunctions.openUpdateUserWindow(id);
            WPS['jWindowPanel-moreOperate'].close();
          }
        },'-',{
          type : 'button',
          text : '关联角色',
          useable : findOtherOperate(usersId,departmentId, 'users_linkrole'),
          position: {
            a: '0px -60px',
            b: '0px -180px'
          },
          handler : function(){
            top.userFunctions.openSaveUsersRoleWindow(id);
            WPS['jWindowPanel-moreOperate'].close();
          }
        },'-',{
          type : 'button',
          text : '删除',
          useable : findOtherOperate(usersId,departmentId, 'users_delete'),
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
                  window.location = '<%=path%>/system/users/delete.v?idList='+id;
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