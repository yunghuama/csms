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
    <form name="numberForm" id="enterpriseForm" action="<%=path%>/csms/enterprise/update.v" class="form" method="post">
      <div class="form-group">基本信息</div>
      <s:hidden name="enterprise.id"></s:hidden>
      <table class="form-table" cellspacing="0" cellpadding="0">
        <tr>
          <td class="form-left"><span class="form-required">*</span>企业名称</td>
          <td class="form-right"><s:textfield type="text" class="text" name="enterprise.name" id="name" theme="simple"/> </td>
        </tr>
        <tr>
          <td class="form-left"><span class="form-required">*</span>手机号码</td>
          <td class="form-right"><s:textfield type="text" class="text" name="enterprise.number" id="number" theme="simple"/></td>
        </tr>
        <tr>
          <td class="form-left"><span class="form-required">*</span>集团编码</td>
          <td class="form-right"><s:textfield type="text" class="text" name="enterprise.code" id="code" theme="simple"/></td>
        </tr>
        <tr>
          <td class="form-left"><span class="form-required">*</span>所属区域</td>
          <td class="form-right"><s:select name="enterprise.district.id" list="districtList"  listKey="id" listValue="name"  headerValue="请选择" theme="simple"/></td>
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
      addValidate('number', [{type:'canNull', value:'F', message:'请填写企业名称'}]);
      addValidate('name',[{type:'canNull', value:'F', message:'请填写正确的手机号'}]);
      addValidate('code',[{type:'canNull', value:'F', message:'请填写集团编号'}]);
    });
    </script> 
  </body>
</html>