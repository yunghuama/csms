<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>Module list</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/list.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/xToolbar.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <div id="toolbar"></div>
    <form id="listForm" action="<%=path%>/system/role/listModuleOperate.v" method="post">
      <table id="titleTable" cellpadding="0" cellspacing="0">
        <tr>
          <td>&nbsp;</td>
          <td><input type="checkbox" id="allBox"/></td>
          <td>操作名称</td>
          <td>说明</td>
          <td>状态</td>
        </tr>
      </table>
      <table id="dataTable" cellpadding="0" cellspacing="0">
        <s:iterator id="operate" value="operateList" status="i">
          <tr class="row">
            <td class="num"><s:property value="#i.index+1"/></td>
            <td class="box"><input type="checkbox" name="idList" value="<s:property value="#operate.webId"/>"/></td>
            <td><span><s:property value="#operate.name"/></span></td>
            <td><span><s:property value="#operate.remark"/>&nbsp;</span></td>
            <td align="center"><span id="state_<s:property value="#operate.webId"/>"><img src="<%=path%>/image/off.gif"/>已禁用</span></td>
          </tr>
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
    <script type="text/javascript">
    
    var on = '<img src="<%=path%>/image/on.gif"/>已启用';
    var off = '<img src="<%=path%>/image/off.gif"/>已禁用';
    
    $(document).ready(function(){
    
      var toolbar = new Toolbar({
        id: '1',
        renderTo : 'toolbar',
        icon: '../../image/op.gif',
        items : [{
          type : 'button',
          text : '启用',
          position: {
            a: '-60px -40px',
            b: '-60px -160px'
          },
          handler : function(){
            if(getFirstID())
            {
              $.ajax({
                url : '<%=path%>/system/ajax/saveRoleOperate.v',
                data : $('#listForm').serialize()+'&useable=T',
                success : function(json){
                  for(var i=0; i<json.length; i++)
                  {
                    loadState(json[i], 'T');
                  }
                }
              });
            }
          }
        },'-',{
          type : 'button',
          text : '禁用',
          position: {
            a: '-80px -40px',
            b: '-80px -160px'
          },
          handler : function(){
            if(getFirstID())
            {
              $.ajax({
                url : '<%=path%>/system/ajax/saveRoleOperate.v',
                data : $('#listForm').serialize()+'&useable=F',
                success : function(json){
                  for(var i=0; i<json.length; i++)
                  {
                    loadState(json[i], 'F');
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
        widths : [30,23,150,200,80],
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
      
      <s:iterator id="roleOperate" value="roleOperateList" status="i">
        loadState('<s:property value="#roleOperate.webId"/>', '<s:property value="#roleOperate.useable"/>');
      </s:iterator>
      
      //loadReady();
    });
    
    function loadState(webId, state) {
      if(state=='T')
        $('#state_'+webId).html(on);
      else
        $('#state_'+webId).html(off);
    }
    </script>
  </body>
</html>