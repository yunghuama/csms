<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>设置约束</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/form.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/list.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <form name="roleModuleFieldForm" id="roleModuleFieldForm" method="post" action="<%=path%>/system/role/updateRoleModuleField.v" class="form">
      <s:hidden name="roleId"/>
      <s:hidden name="field.id"/>
      <s:hidden name="field.webId"/>
      <s:hidden name="field.module.id"/>
      <s:hidden name="roleModuleField.id"/>
      <div class="form-group">字段信息</div>
      <table class="form-table" cellspacing="0" cellpadding="0">
        <tr>
          <td class="form-left">字段名称</td>
          <td class="form-right"><s:property value="field.name"/></td>
        </tr>
        <tr>
          <td class="form-left">字段类型</td>
          <td class="form-right"><s:property value="@com.platform.constants.StringConstant@FIELD_TYPE.get(field.type)"/></td>
        </tr>
        <tr>
          <td class="form-left">最大长度</td>
          <td class="form-right"><s:property value="field.maxSize"/></td>
        </tr>
        <tr>
          <td class="form-left">可否填空</td>
          <td class="form-right">
            <s:if test='field.canNull==@com.platform.constants.StringConstant@FALSE'>
              <input type="radio" name="canNull" value="F" checked="checked"/>否
            </s:if>
            <s:else>
              <input type="radio" name="canNull" value="T" checked="checked"/>是
              <input type="radio" name="canNull" value="F"/>否
            </s:else>
          </td>
        </tr>
      </table>
      <div class="form-group">字段约束</div>
      <table id="titleTable" cellpadding="0" cellspacing="0">
        <tr>
          <td>&nbsp;</td>
          <td>条件</td>
          <td>值</td>
          <td>提示</td>
        </tr>
      </table>
      <table id="columnDatas" cellpadding="0" cellspacing="0">
        
      </table>
      <s:hidden name="tabId"/>
      <s:hidden name="windowPanelId"/>
    </form>
    
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
    <script src="<%=path%>/js/share.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script src="<%=path%>/js/Grid.js" type="text/javascript"></script>
    <script src="<%=path%>/js/BoxSelect.js" type="text/javascript"></script>
    <script src="<%=path%>/js/datePicker/WdatePicker.js" type="text/javascript"></script>
    <script type="text/javascript">
    
    var typeArray = {
      text:[{
        value:'minlength',
        name:'最小长度',
        html:'<input id="minlength" type="text" name="valueList" style="width:91px;" class="text" onblur="validateInteger(this);validateMaxmin(this, \'min\', \'minlength\', \'maxlength\');"/>'
      },{
        value:'maxlength',
        name:'最大长度',
        html:'<input id="maxlength" type="text" name="valueList" style="width:91px;" class="text" onblur="validateInteger(this);validateMaxmin(this, \'max\', \'minlength\', \'maxlength\');"/>'
      },{
        value:'startwith',
        name:'以？开始',
        html:'<input id="startwith" type="text" name="valueList" style="width:91px;" class="text"/>'
      },{
        value:'endwith',
        name:'以？结束',
        html:'<input id="endwith" type="text" name="valueList" style="width:91px;" class="text"/>'
      },{
        value:'include',
        name:'必须包含',
        html:'<input id="include" type="text" name="valueList" style="width:91px;" class="text"/>'
      }],
      integer:[{
        value:'minvalue',
        name:'最小值',
        html:'<input id="minvalue" type="text" name="valueList" style="width:91px;" class="text" onblur="validateInteger(this);validateMaxmin(this, \'min\', \'minvalue\', \'maxvalue\');"/>'
      },{
        value:'maxvalue',
        name:'最大值',
        html:'<input id="maxvalue"  type="text" name="valueList" style="width:91px;" class="text" onblur="validateInteger(this);validateMaxmin(this, \'max\', \'minvalue\', \'maxvalue\');"/>'
      }],
      decimal:[{
        value:'minvalue',
        name:'最小值',
        html:'<input id="minvalue" type="text" name="valueList" style="width:91px;" class="text" onkeyup="" onblur="validateDecimal(this);validateMaxmin(this, \'min\', \'minvalue\', \'maxvalue\');"/>'
      },{
        value:'maxvalue',
        name:'最大值',
        html:'<input id="maxvalue" type="text" name="valueList" style="width:91px;" class="text" onblur="validateDecimal(this);validateMaxmin(this, \'max\', \'minvalue\', \'maxvalue\');"/>'
      },{
        value:'digit',
        name:'小数点保留后几位',
        html:'<input type="text" name="valueList" style="width:91px;" class="text" onblur="validateInteger(this);"/>'
      }],
      datetime:[{
        value:'minDate',
        name:'最早日期',
        html:'<input id="mindate" type="text" name="valueList" style="width:91px;" class="text Wdate" onclick="WdatePicker({maxDate:document.getElementById(\'maxdate\').value});"/>'
      },{
        value:'maxDate',
        name:'最晚日期',
        html:'<input id="maxdate" type="text" name="valueList" style="width:91px;" class="text Wdate" onclick="WdatePicker({minDate:document.getElementById(\'mindate\').value});"/>'
      }],
      linked:[]
    };
    
    var grid;
    $(document).ready(function(){
      
      grid = new Grid({
        titleTable:'titleTable',
        dataTable:'columnDatas',
        widths : [26,80,100,224],
        height : 220
      });
      
      addColumn('<s:property value="field.type"/>');
      
      //将已有的约束值显示出来
      var rules = <s:property value="roleModuleField.rules"/>;
      for(var i=1; i<rules.length; i++)
      {
        var typeValue = $('#'+rules[i].type);
        typeValue.val(rules[i].value);
        typeValue.parent('td').parent('tr').find('input:text[name="messageList"]').val(rules[i].message);
      }
    });
    
    function addColumn(type) {
      
      var array = eval('typeArray.'+type);
      
      for(var i=0; i<array.length; i++)
      {
      
        var columnDatas = $('#columnDatas');
        
        var cTr = $(document.createElement('TR'));
        cTr.addClass('row');
        cTr.appendTo(columnDatas);
        
        var columnsTd = new Array();
        
        columnsTd.push('<td class="num">'+(i+1)+'</td>');
        columnsTd.push('<td><span>'+array[i].name+'<input name="typeList" type="hidden" value="'+array[i].value+'"/></span></td>');
        columnsTd.push('<td>'+array[i].html+'</td>');
        columnsTd.push('<td><input name="messageList" type="text" class="text" style="width:95%;"/></td>');
        
        cTr.html(columnsTd.join(''));
      
      }
      
      grid.updateWidth();
    }
    
    function validateMaxmin(obj, type, min, max) {
    
      var minvalue = parseFloat(document.getElementById(min).value);
      var maxvalue = parseFloat(document.getElementById(max).value);
      
      if(minvalue>maxvalue)
      {
        if('min'==type)
        {
          alert('最小不能大于最大');
          obj.value = document.getElementById(max).value;
        }
        else
        {
          alert('最大不能小于最小');
          obj.value = document.getElementById(min).value;
        }
      }
    }
    
    function validateInteger(obj) {
      if(!/^-?[1-9]|0\d*$/.test(obj.value))
        obj.value = '';
    }
    
    function validateDecimal(obj) {
      if(!/^-?([1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0)$/.test(obj.value))
      {
        obj.value += '.0';
        if(!/^-?([1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0)$/.test(obj.value))
          obj.value = '';
      }
    }
    
    function submitForm() {
      document.getElementById('roleModuleFieldForm').submit();
    }
    </script>
  </body>
</html>