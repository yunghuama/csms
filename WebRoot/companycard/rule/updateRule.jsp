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
    <form name="ruleForm" id="ruleForm" action="<%=path%>/csms/rule/update.v" class="form" method="post">
    <s:hidden name="rule.ruleDay" id="rule_ruleDay" ></s:hidden>
    <s:hidden name="rule.id"/>
    <div id="toolbar"></div>
      <div class="form-group">基本信息</div>
      <table class="form-table" cellspacing="0" cellpadding="0">
        <tr>
          <td class="form-left"><span class="form-required">*</span>内容</td>
          <td class="form-right"><s:select name="rule.content" id="content" list="contentList" listKey="id" listValue="content" headerKey="" headerValue="请选择内容"></s:select> </td>
        </tr>
        <tr>
          <td class="form-left">执行日期</td>
          <td class="form-right">周日<input type="checkbox" name="ruleDay"/>&nbsp;周一<input type="checkbox" name="ruleDay"/>&nbsp;周二<input type="checkbox" name="ruleDay"/>&nbsp;周三<input type="checkbox" name="ruleDay"/>&nbsp;周四<input type="checkbox" name="ruleDay"/>&nbsp;周五<input type="checkbox" name="ruleDay"/>&nbsp;周六<input type="checkbox" name="ruleDay"/>&nbsp; </td>
        </tr>
        <tr>
          <td class="form-left">执行时间</td>
          <td class="form-right"><s:radio list="#{'0':'工作时间','1':'非工作时间','2':'全天'}"  name="rule.timeType"></s:radio></td>
        </tr>
        <tr>
          <td class="form-left">是否开启</td>
          <td class="form-right">
          <s:if test="rule.state==0">
          <s:checkbox  id="state" name="rule.state"  fieldValue="0" value="true"/>
          </s:if>
          <s:else>
          <s:checkbox  id="state" name="rule.state"  fieldValue="0"/>
          </s:else>
          </td>
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
    var days = '<s:property value="rule.ruleDay"/>';
    if(days!=null&&days!=""){
    	var array = days.split("");
    	for(var i=0;i<array.length;i++){
    		if(array[i]==1){
    			$("input[name=ruleDay][type=checkbox]").eq(i).attr("checked","checked");
    		}
    	}
    }
    
    $(document).ready(function(){
      addValidate('name', [{type:'canNull', value:'F', message:'必须填写名称'}]);
      addValidate('name', [{type:'maxlength', value:'15', message:'名称最大长度不能超过15'}]);
      addValidate('content', [{type:'canNull', value:'F', message:'必须选择名片内容'}]);
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
            	var day = [];
            	$("input[name=ruleDay][type=checkbox]").each(function(){
            		var checked = $(this).attr("checked");
            		if(checked)
            			day.push("1");
            		else 
            			day.push("0");
            	});
            	$("#rule_ruleDay").val(day.join(''));
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