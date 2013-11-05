<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>修改字典</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/form.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/jquery-impromptu.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <form name="dictionaryForm" id="dictionaryForm" method="post" action="<%=path%>/system/dictionary/update.v" class="form">
      <s:hidden name="dictionary.id" id="dictId"/>
      <input type="hidden" name="syncUpdate" value="T"/>
      <div class="form-group">基本信息</div>
      <table class="form-table" cellspacing="0" cellpadding="0">
        <tr>
          <td class="form-left"><span class="form-required">*</span>字典名称</td>
          <td class="form-right"><s:textfield id="dictName" name="dictionary.name" cssClass="text" theme="simple"/></td>
        </tr>
        <tr>
          <td class="form-left">字典说明</td>
          <td class="form-right"><s:textarea cssStyle="width:200px; height:70px;" id="dictRemark" name="dictionary.remark" cssClass="textarea" theme="simple"/></td>
        </tr>
      </table>
      <s:hidden name="tabId"/>
      <s:hidden name="windowPanelId"/>
    </form>
    
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script src="<%=path%>/js/Validate.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery-impromptu.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(document).ready(function(){
      addFunctionValidate(validateDictName, '字典名称重复，请重新输入');
      addValidate('dictName', [{type:'canNull', value:'F', message:'必须填写字典名称'}]);
      
      addValidate('dictName', [{type:'maxlength', value: 50, message:'【字典名称】最大长度为50'}]);
      addValidate('dictRemark', [{type:'maxlength', value: 1000, message:'【字典说明】最大长度为1000'}]);
    });
    
    function validateDictName() {
      var dictName = $('#dictName');
      var superId = '<s:property value="dictionary.superId"/>';
      var dictId = $('#dictId').val();
      var result = true;
      $.ajax({
        url : '<%=path%>/system/ajax/validateDictName.v',
        data : 'name='+dictName.val()+'&superId='+superId+'&dictId='+dictId,
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