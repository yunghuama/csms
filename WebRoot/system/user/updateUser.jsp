<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>修改用户</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/form.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/system/user/users.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <form name="userForm" id="userForm" action="<%=path%>/system/users/update.v" class="form" method="post" enctype="multipart/form-data">
      <div class="form-group">基本信息</div>
      <s:hidden name="user.id"/>
      <s:hidden name="user.roleId"></s:hidden>
      <table class="form-table" cellspacing="0" cellpadding="0">
        <tr>
          <td class="form-left"><span class="form-required">*</span>用户名</td>
          <td class="form-right"><s:textfield type="text" id="accountName" name="user.accountName" class="text_f"/></td>
        </tr>
        <tr>
          <td class="form-left"><span class="form-required">*</span>密码</td>
          <td class="form-right"><s:textfield type="password" id="password" name="user.password" class="text_f"/></td>
        </tr>
        <tr>
          <td class="form-left"><span class="form-required">*</span>所在区域</td>
          <td class="form-right"><s:select list="districtList" listKey="id" listValue="name" name="user.area"></s:select></td>
        </tr>
        <tr>
          <td class="form-left">备注</td>
          <td class="form-right"><s:textarea rows="5" cols="20" name="user.remark"></s:textarea> </td>
        </tr>
        <tr>
          <td class="form-left">状态</td>
          <td class="form-right"><s:radio name="user.state" list="@com.platform.constants.StringConstant@STATE_RADIO" theme="simple"/></td>
        </tr>
      </table>
      <s:hidden name="tabId"/>
      <s:hidden name="windowPanelId"/>
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
      
      addValidate('realName', [{type:'maxlength', value: 50, message:'【姓名】最大长度为50'}]);
      addValidate('accountName', [{type:'maxlength', value: 50, message:'【用户名】最大长度为50'}]);
      addValidate('password', [{type:'maxlength', value: 20, message:'【密码】最大长度为20'}]);
      addValidate('cellNo', [{type:'maxlength', value: 20, message:'【手机号】最大长度为20'}]);
      addValidate('cellNo', [{type:'isMobile', value: 'T', message:'输入的手机号不是正确的手机号'}]);
      
      rebuildSize($('#preview'));
    });
    <s:if test="user.bigImage != null">
      var defaultPath = '<%=path%><s:property value="user.bigImage"/>';
    </s:if>
    <s:else>
      var defaultPath = '<%=path%>/image/default.png';
    </s:else>
    </script>
  </body>
</html>