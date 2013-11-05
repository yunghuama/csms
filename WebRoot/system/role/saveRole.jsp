<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>添加角色</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/form.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <form name="roleForm" id="roleForm" method="post" action="<%=path%>/system/role/save.v" class="form">
      <div class="form-group">基本信息</div>
      <table class="form-table" cellspacing="0" cellpadding="0">
        <tr>
          <td class="form-left"><span class="form-required">*</span>角色名称</td>
          <td class="form-right"><input type="text" id="roleName" name="role.name" class="text"/></td>
        </tr>
        <tr>
          <td class="form-left"><span class="form-required">*</span>上级角色</td>
          <td class="form-right"><s:select name="role.superId" list="roleList" value="roleId" listKey="id" listValue="name" headerKey="%{@com.platform.constants.StringConstant@ROOT_ID}" headerValue="请选择" theme="simple"/></td>
        </tr>
        <tr>
          <td class="form-left">角色说明</td>
          <td class="form-right"><textarea style="width:200px; height:70px;" id="roleRemark" name="role.remark" class="textarea"></textarea></td>
        </tr>
      </table>
      <s:hidden name="tabId"/>
      <s:hidden name="windowPanelId"/>
    </form>
    
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
    <script src="<%=path%>/js/share.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script src="<%=path%>/js/Validate.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(document).ready(function(){
      addFunctionValidate(validateRoleName, '角色名称重复，请重新输入');
      addValidate('roleName', [{type:'canNull', value:'F', message:'必须填写角色名称'}]);
      
      addValidate('roleName', [{type:'maxlength', value: 100, message:'【角色名称】最大长度为100'}]);
      addValidate('roleRemark', [{type:'maxlength', value: 1000, message:'【角色说明】最大长度为1000'}]);
    });
    function ajaxSaveRole(){
      $.ajax({
        url : '<%=path%>/system/ajax/saveRole.v',
        data : 'roleName='+$('#roleName').val()+'&roleRemark='+$('#roleRemark').val(),
        success : function(json){
          openCenterWindow({
            url:projectName+'/system/role/toSaveUFO.v?roleId='+json.roleId,
            width:800,
            height:500
          });
          top.WPS['jWindowPanel-'+$('#windowPanelId').val()].close();
        }
      });
    }
    function validateRoleName() {    
      var roleName = $('#roleName');     
      var result = true;
      $.ajax({
        url : '<%=path%>/system/ajax/validateRoleName.v',
        data : 'roleName='+roleName.val(),
        async : false,
        success : function(json) {
          if(json=='F') {
            result = false;
          }
        }
      });
      return result;
    }
    </script>
  </body>
</html>