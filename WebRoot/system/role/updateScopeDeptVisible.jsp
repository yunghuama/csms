<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>操作授权设置</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/list.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <form id="listForm" action="<%=path%>/system/role/toUpdateScopeDeptVisible.v" method="post">
      <table id="titleTable" cellpadding="0" cellspacing="0">
        <tr>
          <td>&nbsp;</td>
          <td><input type="checkbox" id="allBox"/></td>
          <td>操作名称</td>
          <td>状态</td>
        </tr>
      </table>
      <table id="dataTable" cellpadding="0" cellspacing="0">
        <s:iterator id="operate" value="operateList" status="i">
          <tr class="row">
            <td class="num"><s:property value="#i.index + 1"/></td>
            <td class="box"><input type="checkbox" name="idList" id="<s:property value="#operate.webId"/>" value="<s:property value="#operate.webId"/>"/></td>
            <td><span><s:property value="#operate.name"/></span></td>
            <td align="center"><span id="sees_<s:property value="#operate.webId"/>"><img src="<%=path%>/image/nosee.gif"/>禁止</span></td>
          </tr>
        </s:iterator>
      </table>
      <s:hidden name="moduleId"/>
      <s:hidden name="roleId"/>
      <s:hidden name="departmentId"/>
    </form>
    
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
    <script src="<%=path%>/js/share.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script src="<%=path%>/js/Grid.js" type="text/javascript"></script>
    <script src="<%=path%>/js/BoxSelect.js" type="text/javascript"></script>
    <script src="<%=path%>/js/datePicker/WdatePicker.js" type="text/javascript"></script>
    <script type="text/javascript">
    var see = '<img src="<%=path%>/image/see.gif"/>可见';
    var nosee = '<img src="<%=path%>/image/nosee.gif"/>禁止';
    
    $(document).ready(function(){
      
      new Grid({
        titleTable:'titleTable',
        dataTable:'dataTable',
        widths : [30,24,204,60],
        height : 300
      });
      
      new BoxSelect({
        allId : 'allBox',
        boxName : 'idList'
      });
      
      <s:iterator id="scopeDeptVisible" value="scopeDeptVisibleList">
        <s:if test="#scopeDeptVisible.visible==@com.platform.constants.StringConstant@TRUE">
          loadSee('<s:property value="#scopeDeptVisible.webId"/>', 'T')
        </s:if>
      </s:iterator>
    });
    
    function changeVisible(visible) {
      var datas = $('#listForm').serialize() + '&visible=' + visible;
      $.ajax({
        url : '<%=path%>/system/ajax/updateScopeDeptVisible.v',
        data : datas,
        success : function(json) {
          for(var i = 0; i < json.idList.length; i++) {
            loadSee(json.idList[i], visible);
          }
        }
      });
    }
    
    function loadSee(id, state) {
      if(state=='T') {
        $('#sees_'+id).html(see);
      } else {
        $('#sees_'+id).html(nosee);
      }
    }
    </script>
  </body>
</html>