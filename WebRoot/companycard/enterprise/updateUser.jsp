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
    <link href="<%=path%>/system/users/users.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <form name="userForm" id="userForm" action="<%=path%>/csms/enterprise/updateUsers.v" class="form" method="post" enctype="multipart/form-data">
      <div class="form-group">基本信息</div>
      <s:hidden name="users.id"/>
      <s:hidden name="users.department.id"/>
      <s:hidden name="users.enterprise.id"/>
      <s:hidden name="enterprise.id"/>
      <table class="form-table" cellspacing="0" cellpadding="0">
        <tr>
          <td class="form-left"><span class="form-required">*</span>真实姓名</td>
          <td class="form-right"><s:textfield id="realName" name="users.realName" cssClass="text_f" theme="simple"/></td>
        </tr>
        <tr>
          <td class="form-left"><span class="form-required">*</span>用户名</td>
          <td class="form-right"><s:textfield id="realName" name="users.accountName" cssClass="text_f" theme="simple"/></td>
        </tr>
        <tr>
          <td class="form-left"><span class="form-required">*</span>密码</td>
          <td class="form-right"><input type="password" id="password" name="users.password" class="text_f" value="<s:property value="users.password"/>"/></td>
        </tr>
        <tr>
          <td class="form-left">状态</td>
          <td class="form-right"><s:select name="users.state" list="@com.platform.constants.StringConstant@STATE_RADIO" theme="simple"/></td>
        </tr>
      </table>
      <input id="imagePath" name="imagePath" type="hidden"/>
      <s:hidden name="tabId"/>
      <s:hidden name="windowPanelId"/>
      <iframe id="tempUpload" name="tempUpload" style="display: none;"></iframe>
    </form>
    
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script src="<%=path%>/js/Validate.js" type="text/javascript"></script>
    <script src="<%=path%>/js/datePicker/WdatePicker.js" type="text/javascript"></script>
    <script src="<%=path%>/system/user/users.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(document).ready(function(){
      addValidate('realName', [{type:'canNull', value:'F', message:'真实姓名必须填写'}]);
      addValidate('accountName', [{type:'canNull', value:'F', message:'用户名必须填写'}]);
      addValidate('password', [{type:'canNull', value:'F', message:'密码必须填写'}]);
    });
    </script>
  </body>
</html>