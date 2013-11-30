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
          <td class="form-left"><span class="form-required">*</span>管理员类型</td>
          <td class="form-right"><s:select list="roleList" listKey="id" listValue="name" name="user.roleId"></s:select> </td>
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
          <td class="form-left"><span class="form-required">*</span>所在区域</td>
          <td class="form-right"><s:select list="districtList" listKey="id" listValue="name" name="user.area"></s:select></td>
        </tr>
        <tr>
          <td class="form-left">备注</td>
          <td class="form-right"><textarea rows="5" cols="20" name="user.remark"></textarea> </td>
        </tr>
        <tr>
          <td class="form-left">状态</td>
          <td class="form-right"><s:radio name="user.state" list="@com.platform.constants.StringConstant@STATE_RADIO" value="'T'" theme="simple"/></td>
        </tr>

      </table>
      <input id="imagePath" name="imagePath" type="hidden"/>
      <s:hidden name="tabId"/>
      <s:hidden name="windowPanelId"/>
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