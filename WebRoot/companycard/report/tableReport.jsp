<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title></title>
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
  	<form id="listForm" action="" method="post">
  	<div class="search-div">
        <div class="search-condition">
          <table class="search-table" cellspacing="0" cellpadding="0">
            <tr>
              <td class="c-left">日期：</td>
              <td><input name="searchValue" type="text" class="text" value="<s:property value="searchValue[0]"/>"/></td>
              <td class="c-left">空间：</td>
              <td><input name="searchValue" type="text" class="text" value="<s:property value="searchValue[0]"/>"/></td>
            </tr>
            <tr>
              <td class="c-left">开户集团：</td>
              <td><input name="searchValue" type="text" class="text" value="<s:property value="searchValue[0]"/>"/></td>
              <td class="c-left">个人开户：</td>
              <td><input name="searchValue" type="text" class="text" value="<s:property value="searchValue[0]"/>"/></td>
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
          <td>日期</td>
          <td>空间</td>
          <td>集团开户</td>
          <td>个人开户</td>
          <td>集团销户</td>
          <td>个人销户</td>
        </tr>
      </table>
      <table id="dataTable" cellpadding="0" cellspacing="0">
        <s:iterator id="content" value="page.list" status="i">
          <tr class="row">
            <td class="num"><s:property value="#i.index+1"/></td>
            <td class="box">
              <s:if test="#content.creator.id == #session['LoginBean'].user.id">
                <input type="checkbox" name="idList" value="<s:property value="#content.id"/>"/>
              </s:if>
              <s:else>
                <img class="more-operate" src="<%=path%>/image/more-operate.gif" onclick="openMoreOperateWindow(event, '<s:property value="#content.creator.id"/>','<s:property value="#content.creator.department.id"/>','<s:property value="#content.id"/>');"/>
              </s:else>
            </td>
            <td><span><s:property value="#content.content"/>&nbsp;</span></td>
            <td><span><s:property value="#content.remark"/>&nbsp;</span></td>
            <td align="center"><span> <s:property value='@com.csms.constants.CSMSStringConstant@CONTENT_STATE_TYPE.get(#content.state)'/>&nbsp;</span></td>
            <td align="center"><span><s:property value="#content.reason"/>&nbsp;</span></td>
            <td align="center"><span><s:property value="#content.creator.realName"/>&nbsp;</span></td>
            <td align="center"><span><s:date name="#content.createTime" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;</span></td>
          </tr>
        </s:iterator>
      </table>
      <s:hidden name="type"/>
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
       
      new Toolbar({
        id: '1',
        renderTo : 'toolbar',
        icon: '../../image/op.gif',
        items : [{
          type : 'button',
          text : '删除',
          position: {
            a: '-40px 0px',
            b: '-40px -120px'
          },
          useable : '<s:property value="@com.platform.util.Meta@getOperate(\"cardcontent_delete\")"/>',
          handler : function(){
            validateBeforeDelete({
              validateURL : '<%=path%>/system/ajax/deleteValidate.v',
              validateParams : $('input:checkbox[name="idList"]:checked').serialize(),
              validateTable : 'CSMSRule',
              deleteURL : '<%=path%>/csms/content/delete.v'
            });
          }
        },'-',{
          type: 'search'
        }]
      });
      
      new Grid({
        titleTable:'titleTable',
        dataTable:'dataTable',
        widths : [30,24,200,200,100,200,130,100],
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
          text : '删除',
          useable : findOtherOperate(usersId,departmentId, 'department_delete'),
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
                  window.location = '<%=path%>/csms/content/delete.v?idList='+id;
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