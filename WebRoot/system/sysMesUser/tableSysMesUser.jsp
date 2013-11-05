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
    <form id="listForm" action="<%=path%>/system/sysMesUser/listPagination.v" method="post">
      <table id="titleTable" cellpadding="0" cellspacing="0">
        <tr>
          <td>&nbsp;</td>
          <td><input type="checkbox" id="allBox"/></td>
          <td id="typeSort">消息类型</td>
          <td id="timeSort">发送时间</td>
          <td>消息内容</td>
          <td>阅读标示</td>
        </tr>
      </table>
      <table id="dataTable" cellpadding="0" cellspacing="0">
        <s:iterator id="sysMesUser" value="page.list" status="i">
          <tr class="row">
            <td class="num"><s:property value="#i.index+1"/></td>
            <td class="box"><input type="checkbox" name="idList" value="<s:property value="#sysMesUser.id"/>"/></td>
            <td align="center"><span><s:property value="#sysMesUser.sysMessage.type"/></span></td>
            <td align="center"><span><s:date name="#sysMesUser.sysMessage.sendTime" format="yyyy-MM-dd HH:mm"/></span></td>
            <td><span><s:property value="#sysMesUser.sysMessage.contents" escape="false"/></span></td>
            <td align="center"><span><s:property value="#sysMesUser.readFlag"/></span></td>
          </tr>
        </s:iterator>
      </table>
      <s:hidden name="readFlag"/>
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
          useable : '<s:property value="@com.platform.util.Meta@getOperate(\"sysmessage_delete\")"/>',
          position: {
            a: '-40px 0px',
            b: '-40px -120px'
          },
          handler : function(){
            if(hasChecked())
            {
              var txt = '是否删除系统消息？';
              $.prompt(txt,{
                buttons:{删除:true,取消:false},
                callback: function(v,m)
                {
                  if(v)
                  {
                    document.getElementById('listForm').action = '<%=path%>/system/sysMesUser/delete.v';
                    document.getElementById('listForm').submit();
                  }
                }
              });
            }
          }
        },{
          type : 'button',
          text : '清空',
          useable : '<s:property value="@com.platform.util.Meta@getOperate(\"sysmessage_clearall\")"/>',
          position: {
            a: '0px -20px',
            b: '0px -140px'
          },
          handler : function(){
            var txt = '是否清空所有系统消息？';
            $.prompt(txt,{
              buttons:{清空:true,取消:false},
              callback: function(v,m)
              {
                if(v)
                {
                  document.getElementById('listForm').action = '<%=path%>/system/sysMesUser/deleteAll.v';
                  document.getElementById('listForm').submit();
                }
              }
            });
          }
        },'-',{
          type : 'button',
          text : '标记为已读',
          useable : '<s:property value="@com.platform.util.Meta@getOperate(\"sysmessage_markread\")"/>',
          position: {
            a: '-40px -80px',
            b: '-40px -200px'
          },
          handler : function(){
            if(hasChecked())
            {
              var txt = '是否将所选消息标记为已读？';
              $.prompt(txt,{
                buttons:{标记:true,取消:false},
                callback: function(v,m)
                {
                  if(v)
                  {
                    document.getElementById('listForm').action = '<%=path%>/system/sysMesUser/updateReadFlag.v';
                    document.getElementById('listForm').submit();
                  }
                }
              });
            }
          }
        },{
          type : 'button',
          text : '全部标记为已读',
          useable : '<s:property value="@com.platform.util.Meta@getOperate(\"sysmessage_markreadall\")"/>',
          position: {
            a: '-20px -80px',
            b: '-20px -200px'
          },
          handler : function(){
            var txt = '是否将全部消息标记为已读？';
            $.prompt(txt,{
              buttons:{全部标记:true,取消:false},
              callback: function(v,m)
              {
                if(v)
                {
                  document.getElementById('listForm').action = '<%=path%>/system/sysMesUser/updateReadFlagAll.v';
                  document.getElementById('listForm').submit();
                }
              }
            });
          }
        },{
          type: 'filters',
          active: '<s:property value="readFlag"/>',
          items: [{
            id : 'ALL',
            tip : '全部',
            position: {
              a: '-20px -80px'
            },
            handler : function(){
              filterSysMessage('ALL');
            }
          },{
            id : 'F',
            tip : '未读',
            position: {
              a: '0px -80px'
            },
            handler : function(){
              filterSysMessage('F');
            }
          },{
            id : 'T',
            tip : '已读',
            position: {
              a: '-40px -80px'
            },
            handler : function(){
              filterSysMessage('T');
            }
          }]
        }]
      });
      
      new Grid({
        titleTable:'titleTable',
        dataTable:'dataTable',
        widths : [26,24,100,130,200,120],
        height : function(){return getGridHeight({toolbarId:'toolbar',hasPage:true});}
      });
      
      new BoxSelect({
        allId : 'allBox',
        boxName : 'idList'
      });
      
      loadReady();
    });
    
    function filterSysMessage(readFlag) {
      window.location = '<%=path%>/system/sysMesUser/listPagination.v?readFlag='+readFlag;
    }
    </script>
  </body>
</html>