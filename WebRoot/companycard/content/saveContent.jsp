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
    <form name="contentForm" id="contentForm" action="<%=path%>/csms/content/save.v" class="form" method="post">
      <div class="form-group">基本信息</div>
      <table class="form-table" cellspacing="0" cellpadding="0">
        <tr>
          <td class="form-left"><span class="form-required">*</span>名片内容</td>
          <td class="form-right"><textarea style="width:200px; height:70px;" class="textarea" id="content" name="content.content"></textarea></td>
        </tr>
        <tr>
          <td class="form-left">备注说明</td>
          <td class="form-right"><textarea style="width:200px; height:70px;" class="textarea" id="remark" name="content.remark"></textarea></td>
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
      addValidate('content', [{type:'canNull', value:'F', message:'必须填写名片内容'}]);
      
      addValidate('content', [{type:'maxlength', value: 15, message:'【名片内容】最大长度为15'}]);
      addValidate('remark', [{type:'maxlength', value: 1000, message:'【备注说明】最大长度为50'}]);
    });
    </script> 
  </body>
</html>