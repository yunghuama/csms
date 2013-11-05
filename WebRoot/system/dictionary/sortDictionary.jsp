<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>字典项排序</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/form.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <form name="dictionaryForm" id="dictionaryForm" action="<%=path%>/system/dictionary/updateSort.v" class="form">
      <div class="form-group">顺序调整</div>
      <table class="form-table" cellspacing="0" cellpadding="0">
        <tr>
          <td class="form-right">
            <s:select name="idList" cssClass="mselect" cssStyle="height:250px; width:220px;" list="dictionaryList" listKey="id" listValue="name" multiple="true" theme="simple"/>
          </td>
        </tr>
      </table>
      <s:hidden name="tabId"/>
      <s:hidden name="windowPanelId"/>
    </form>
    
    <script src="<%=path%>/js/Sortter.js" type="text/javascript"></script>
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
  </body>
</html>