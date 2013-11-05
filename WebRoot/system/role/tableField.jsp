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
    <link href="<%=path%>/css/xWindowPanel.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/jquery-impromptu.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <div id="toolbar"></div>
    <form id="listForm" action="<%=path%>/system/role/listModuleField.v" method="post">
      <table id="titleTable" cellpadding="0" cellspacing="0">
        <tr>
          <td>&nbsp;</td>
          <td><input type="checkbox" id="allBox"/></td>
          <td>字段名称</td>
          <td>字段类型</td>
          <td>最大长度</td>
          <td>可否填空</td>
          <td>约束</td>
        </tr>
      </table>
      <table id="dataTable" cellpadding="0" cellspacing="0">
        <s:iterator id="field" value="fieldList" status="i">
          <tr class="row">
            <td class="num"><s:property value="#i.index+1"/></td>
            <td class="box"><input type="checkbox" name="idList" value="<s:property value="#field.id"/>"/></td>
            <td><span><s:property value="#field.name"/></span></td>
            <td align="center"><span><s:property value="@com.platform.constants.StringConstant@FIELD_TYPE.get(#field.type)"/></span></td>
            <td><span><s:property value="#field.maxSize"/></span></td>
            <td align="center"><span><s:property value="@com.platform.constants.StringConstant@NULLABLE.get(#field.canNull)"/></span></td>
            <td align="center"><span id="state_<s:property value="#field.webId"/>"><img src="<%=path%>/image/f-off.gif"/>未设置</span></td>
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
    <script src="<%=path%>/js/xWindowPanel.js" type="text/javascript"></script>
    <script src="<%=path%>/js/Draggable.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery-impromptu.js" type="text/javascript"></script>
    <script type="text/javascript">
    
    var on = '<img src="<%=path%>/image/f-on.gif"/>已设置';
    var off = '<img src="<%=path%>/image/f-off.gif"/>未设置';
    
    $(document).ready(function(){
    
      var toolbar = new Toolbar({
        id: '1',
        renderTo : 'toolbar',
        icon: '../../image/op.gif',
        items : [{
          type : 'button',
          text : '设置约束',
          position: {
            a: '-100px -20px'
          },
          handler : function(){
            if(getFirstID())
            {
              var roleId = $('#roleId').val();
              new WindowPanel({
                id : 'updateField',
                title : '设置约束',
                width : 442,
                height : 400,
                draggable : false,
                html : '<iframe name="fieldUpdateFrame" id="fieldUpdateFrame" src="'+projectName+'/system/role/toUpdateRoleModuleField.v?field.id='+getFirstID()+'&roleId='+roleId+'" frameborder="0" scrolling="auto"></iframe>',
                tbar : new Toolbar({
                  id: '2',
                  icon: '../../image/op.gif',
                  items : [{
                    type : 'button',
                    text : '设置',
                    position: {
                      a: '-60px -20px'
                    },
                    handler : function(){
                      getFrame('fieldUpdateFrame').submitForm();
                    }
                  }]
                })
              });
            }
          }
        },'-',{
          type : 'button',
          text : '清除约束',
          position: {
            a: '-80px -20px'
          },
          handler : function(){
            if(getFirstID())
            {
              var txt = '确定要清除已有约束吗？';
              $.prompt(txt,{
                buttons:{清除:true,取消:false},
                callback: function(v,m)
                {
                  if(v){
                    $.ajax({
                      url : '<%=path%>/system/ajax/deleteRoleModuleField.v',
                      data : $('#listForm').serialize(),
                      success : function(json){
                        for(var i=0; i<json.length; i++)
                        {
                          loadState(json[i], 'F');
                        }
                      }
                    });
                  }
                }
              });
            }
          }
        },'-',{
          type : 'button',
          text : '刷新',
          position: {
            a: '-60px 0px'
          },
          handler : function(){
            document.getElementById('listForm').submit();
          }
        }]
      });
      
      var grid = new Grid({
        titleTable:'titleTable',
        dataTable:'dataTable',
        widths : [30,23,150,80,80,80,80],
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
      
      <s:iterator id="roleField" value="roleFieldList" status="i">
        loadState('<s:property value="#roleField.webId"/>', 'T');
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