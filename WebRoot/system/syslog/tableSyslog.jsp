<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>系统日志列表</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/pagination.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/list.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/xToolbar.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/jquery-impromptu.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <s:include value="/share/btMask.jsp" />
    <div id="toolbar"></div>
    <form id="listForm" action="<%=path%>/system/syslog/listPagination.v" method="post">
      <table id="titleTable" cellpadding="0" cellspacing="0">
        <tr>
          <td>&nbsp;</td>
          <td><input type="checkbox" id="allBox"/></td>
          <td>操作人</td>
          <td id="typeSort">操作类型</td>
          <td id="dateSort">操作时间</td>
          <td>操作内容</td>
          <td>执行IP地址</td>
        </tr>
      </table>
      <table id="dataTable" cellpadding="0" cellspacing="0">
        <s:iterator id="syslog" value="page.list" status="i">
          <tr class="row">
            <td class="num"><s:property value="#i.index+1"/></td>
            <td class="box"><input type="checkbox" name="idList" value="<s:property value="#syslog.id"/>"/></td>
            <td><span>[<s:property value="#syslog.optor.accountName"/>]<s:property value="#syslog.optor.realName"/></span></td>
            <td align="center"><span><s:property value="#syslog.type"/></span></td>
            <td align="center"><span><s:date name="#syslog.opTime" format="yyyy-MM-dd HH:mm"/></span></td>
            <td><span><s:property value="#syslog.contents" escape="false"/></span></td>
            <td align="center"><span><s:property value="#syslog.ipAdd"/></span></td>
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
    <script type="text/javascript">
    
    $(document).ready(function(){
    
      var toolbar = new Toolbar({
        id: '1',
        renderTo : 'toolbar',
        icon: '../../image/op.gif',
        items : [{
          type : 'button',
          text : '删除',
          useable : '<s:property value="@com.platform.util.Meta@getOperate(\"syslog_delete\")"/>',
          position: {
            a: '-40px 0px',
            b: '-40px -120px'
          },
          handler : function(){
            del({
              deleteURL : '<%=path%>/system/syslog/delete.v',
              deleteMessage : '是否删除系统日志？<font color="red">[删除后将无法恢复]</font>'
            });
          }
        },{
          type : 'button',
          text : '清空',
          useable : '<s:property value="@com.platform.util.Meta@getOperate(\"syslog_clearall\")"/>',
          position: {
            a: '0px -20px',
            b: '0px -140px'
          },
          handler : function(){
            var txt = '是否清空所有系统日志？<font color="red">[清空后将无法恢复]</font>';
            $.prompt(txt,{
              buttons:{清空:true,取消:false},
              callback: function(v,m)
              {
                if(v)
                {
                  document.getElementById('listForm').action = '<%=path%>/system/syslog/deleteAll.v';
                  document.getElementById('listForm').submit();
                }
              }
            });
          }
        }]
      });
      
      new Grid({
        titleTable:'titleTable',
        dataTable:'dataTable',
        widths : [26,24,130,90,130,200,120],
        height : function(){return getGridHeight({toolbarId:'toolbar',hasPage:true});}
      });
      
      new BoxSelect({
        allId : 'allBox',
        boxName : 'idList'
      });
      
      loadReady();
    });
    </script>
  </body>
</html>