<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>添加字典</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/form.css" rel="stylesheet" type="text/css"/>
  </head> 
  <body>
    <form name="departmentForm" id="departmentForm" action="<%=path%>/system/department/update.v" class="form" method="post">
      <s:hidden name="department.id"/>
      <div class="form-group">基本信息</div>
      <table class="form-table" cellspacing="0" cellpadding="0">
        <tr>
          <td class="form-left"><span class="form-required">*</span>部门名称</td>
          <td class="form-right"><s:textfield id="deptName" name="department.name" cssClass="text" theme="simple"/></td>
        </tr>
        <tr>
          <td class="form-left">上级部门</td>
          <td class="form-right"><s:select name="department.superId" list="departmentList" listKey="id" listValue="name" headerKey="%{@com.platform.constants.StringConstant@ROOT_ID}" headerValue="请选择" theme="simple"/></td>
        </tr>
        <tr>
          <td class="form-left">说明</td>
          <td class="form-right"><s:textarea cssStyle="width:200px; height:70px;" cssClass="textarea" id="deptRemark" name="department.remark" theme="simple"/></td>
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
      addValidate('deptName', [{type:'canNull', value:'F', message:'必须填写部门名称'}]);
      
      addValidate('deptName', [{type:'maxlength', value: 50, message:'【部门名称】最大长度为50'}]);
      addValidate('deptRemark', [{type:'maxlength', value: 1000, message:'【说明】最大长度为1000'}]);
    });
    </script>
  </body>
</html>