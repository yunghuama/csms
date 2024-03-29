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
    <link href="<%=path%>/css/form.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <form name="groupForm" id="groupForm" action="<%=path%>/csms/group/save.v" class="form" method="post">
      <div class="form-group">基本信息</div>
      <table class="form-table" cellspacing="0" cellpadding="0">
        <tr>
          <td class="form-left"><span class="form-required">*</span>组群名称</td>
          <td class="form-right"><input type="text" name="group.name" id="name" class="text"/></td>
        </tr>
        <tr>
          <td class="form-left"><span class="form-required">*</span>组群策略</td>
          <td class="form-right"><s:select name="group.rule" id="rule" list="ruleList" listKey="id" listValue="name" headerKey="" headerValue="无策略"></s:select> </td>
        </tr>
        <tr>
          <td class="form-left">备注说明</td>
          <td class="form-right"><textarea style="width:200px; height:70px;" class="textarea" id="remark" name="group.remark"></textarea></td>
        </tr>
      </table>
      <s:hidden name="tabId"/>
      <s:hidden name="windowPanelId"/>
    </form>
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script src="<%=path%>/js/Validate.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(document).ready(function(){
      addValidate('name', [{type:'canNull', value:'F', message:'必须填写组群名称'}]);
      addValidate('name', [{type:'maxlength', value: 15, message:'【组群名称】最大长度为15'}]);
    });
    </script> 
  </body>
</html>