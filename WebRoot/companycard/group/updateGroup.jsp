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
    <form name="groupForm" id="goupForm" action="<%=path%>/csms/group/update.v" class="form" method="post">
      <s:hidden name="group.id"/>
      <div class="form-group">基本信息</div>
      <table class="form-table" cellspacing="0" cellpadding="0">
        <tr>
          <td class="form-left"><span class="form-required">*</span>组群名称</td>
          <td class="form-right">
          <s:if test='group.type=="1"'>
          <s:textfield type="text" name="group.name" id="name" cssClass="text"/>
          </s:if>
          <s:elseif test='group.type=="0"'>
          <s:textfield type="text" name="group.name" id="name" cssClass="text" readonly="true"/>
          </s:elseif>
          </td>
        </tr>
        <tr>
          <td class="form-left"><span class="form-required">*</span>组群策略</td>
          <td class="form-right"><s:select name="group.rule" id="rule" list="ruleList" listKey="id" listValue="name" headerKey="" headerValue="无策略"></s:select> </td>
        </tr>
        <tr>
          <td class="form-left">说明</td>
          <td class="form-right"><s:textarea cssStyle="width:200px; height:70px;" cssClass="textarea" id="deptRemark" name="group.remark" theme="simple"/></td>
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