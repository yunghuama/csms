<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>新建用户</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/form.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/jquery-impromptu.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/system/user/users.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <form name="userForm" id="userForm" action="<%=path%>/system/users/save.v" class="form" method="post" enctype="multipart/form-data">
      <div class="form-group">基本信息</div>
      <table class="form-table" cellspacing="0" cellpadding="0">
        <tr>
          <td class="form-left"><span class="form-required">*</span>性别</td>
          <td class="form-right"><s:radio name="user.sex" value="'0'" list="@com.platform.constants.StringConstant@SEX_RADIO" theme="simple"/></td>
        </tr>
        <tr>
          <td class="form-left"><span class="form-required">*</span>真实姓名</td>
          <td class="form-right"><input type="text" id="realName" name="user.realName" class="text_f"/></td>
        </tr>
        <tr>
          <td class="form-left">出生日期</td>
          <td class="form-right"><input type="text" name="user.birthday" class="Wdate text f" onclick="new WdatePicker({startDate:'1970-01-01'});"/></td>
        </tr>
        <tr>
          <td class="form-left"><span class="form-required">*</span>用户名</td>
          <td class="form-right"><input type="text" id="accountName" name="user.accountName" class="text_f"/></td>
        </tr>
        <tr>
          <td class="form-left"><span class="form-required">*</span>密码</td>
          <td class="form-right"><input type="password" id="password" name="user.password" class="text_f"/></td>
        </tr>
        <tr>
          <td class="form-left"><span class="form-required">*</span>所在部门</td>
          <td class="form-right"><s:select cssStyle="width:143px;" name="user.department.id" list="departmentList" value="deptId" listKey="id" listValue="name" theme="simple"/></td>
        </tr>
        <tr>
          <td class="form-left">状态</td>
          <td class="form-right"><s:select name="user.state" list="@com.platform.constants.StringConstant@STATE_RADIO" value="'T'" theme="simple"/></td>
        </tr>
        <tr>
          <td class="form-left">手机号</td>
          <td class="form-right"><input type="text" id="cellNo" name="user.cellNo" class="text_f"/></td>
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
    <script src="<%=path%>/js/jquery-impromptu.js" type="text/javascript"></script>
    <script src="<%=path%>/js/datePicker/WdatePicker.js" type="text/javascript"></script>
    <script src="<%=path%>/system/user/users.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(document).ready(function(){
      addFunctionValidate(validateAccountName, '用户名重复，请重新输入');
      addValidate('realName', [{type:'canNull', value:'F', message:'真实姓名必须填写'}]);
      addValidate('accountName', [{type:'canNull', value:'F', message:'用户名必须填写'}]);
      addValidate('password', [{type:'canNull', value:'F', message:'密码必须填写'}]);
      
      addValidate('realName', [{type:'maxlength', value: 50, message:'【姓名】最大长度为50'}]);
      addValidate('accountName', [{type:'maxlength', value: 50, message:'【用户名】最大长度为50'}]);
      addValidate('password', [{type:'maxlength', value: 20, message:'【密码】最大长度为20'}]);
      addValidate('cellNo', [{type:'maxlength', value: 11, message:'【手机号】最大长度为11'}]);
      addValidate('cellNo', [{type:'isMobile', value: 'T', message:'输入的手机号不是正确的手机号'}]);
    });
    
    function validateAccountName() {
      var obj = $('#accountName');
      var result = true;
      $.ajax({
        url : '<%=path%>/system/ajax/validateAccountName.v',
        data : 'accountName='+obj.val(),
        async : false,
        success : function(json) {
          if(json=='F') {
            result = false;
          }
        }
      });
      return result;
    }
    var defaultPath = '<%=path%>/image/default.png';
    </script>
  </body>
</html>