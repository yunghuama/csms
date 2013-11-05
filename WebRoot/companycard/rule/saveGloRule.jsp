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
    <link href="<%=path%>/css/xToolbar.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <form name="ruleForm" id="ruleForm" action="<%=path%>/csms/gloRule/save.v" class="form" method="post">
    <div id="toolbar"></div>
      <div class="form-group">基本信息</div>
      <table class="form-table" cellspacing="0" cellpadding="0">
        <tr>
          <td class="form-left"><span class="form-required">*</span>名称</td>
          <td class="form-right"><input type="text" id="name" name="rule.name" class="text"/></td>
        </tr>
        <tr>
          <td class="form-left"><span class="form-required">*</span>内容</td>
          <td class="form-right"><s:select name="rule.content" id="content" list="contentList" listKey="id" listValue="content" headerKey="" headerValue="请选择内容"></s:select> </td>
        </tr>
        <tr>
          <td class="form-left">是否开启</td>
          <td class="form-right"><input type="checkbox" id="state" name="rule.state" checked="checked" value="0"/></td>
        </tr>
      </table>
      <s:hidden name="tabId"/>
      <s:hidden name="windowPanelId"/>
    </form>
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script src="<%=path%>/js/Validate.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xToolbar.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xToolbar.items.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(document).ready(function(){
      addValidate('name', [{type:'canNull', value:'F', message:'必须填写名称'}]);
      addValidate('name', [{type:'maxlength', value:'15', message:'名称最大长度不能超过15'}]);
      addValidate('content', [{type:'canNull', value:'F', message:'必须选择名片内容'}]);
      $("#ruleStartTime").attr("value","08:00");
      $("#ruleEndTime").attr("value","18:00");
      new Toolbar({
    	  renderTo : 'toolbar',
          icon: '../../image/op.gif',
          items : [{
            type : 'button',
            text : '保存',
            position: {
              a: '-80px 0px',
              b: '-80px -120px'
            },
            handler : function(){
            	if(!validate())
                return;
              
              $("#ruleForm").submit();
            }
          }]
        });
    });
    </script> 
  </body>
</html>