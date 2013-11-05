<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>添加通信</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/form.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/Upload.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/UsersSelect.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/xWindowPanel.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/xToolbar.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <form name="intercomForm" id="intercomForm" method="post" enctype="multipart/form-data" action="<%=path%>/system/intercom/save.v" class="form-scroll">
      <s:hidden name="intercomId"/>
      <s:hidden name="intercom.type" value="0"/>
      <div class="form-group">基本信息</div>
      <table class="form-table" cellspacing="0" cellpadding="0">
        <tr>
          <td class="form-left"><span class="form-required">*</span>收件人</td>
          <td><input type="hidden" name="intercom.replier" id="replierId"/></td>
        </tr>
        <tr>
          <td class="form-left">抄送</td>
          <td><input type="hidden" name="intercom.copier" id="copierId"/></td>
        </tr>
        <tr>
          <td class="form-left"><span class="form-required">*</span>主题</td>
          <td><s:textfield id="icTitle" name="intercom.title" cssStyle="width:450px;" cssClass="text" theme="simple"/></td>
        </tr>
        <tr>
          <td class="form-left">&nbsp;</td>
          <td>
            <div style="width: 458px;" id="fileContent"></div>
            <s:include value="/share/uploadView.jsp"></s:include>
          </td>
        </tr>
        <tr>
          <td class="form-left">内容</td>
          <td><s:textarea id="contents" name="intercom.contents" cssClass="textarea" cssStyle="width:458px; height:240px;" theme="simple"></s:textarea></td>
        </tr>
      </table>
      <s:hidden name="tabId"/>
      <s:hidden name="windowPanelId"/>
    </form>
    
    <script src="<%=path%>/js/tinyMCE/tiny_mce.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
    <script src="<%=path%>/js/Upload.js" type="text/javascript"></script>
    <script src="<%=path%>/js/share.js" type="text/javascript"></script>
    <script src="<%=path%>/js/Draggable.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xWindowPanel.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xToolbar.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xToolbar.items.js" type="text/javascript"></script>
    <script src="<%=path%>/js/UsersSelect.js" type="text/javascript"></script>
    <script src="<%=path%>/js/Validate.js" type="text/javascript"></script>
    <script type="text/javascript">
    tinyMCE.init({
      // General options
      mode : "exact",
      elements : "contents",
      theme : "advanced",
      language : "zh", 
      
      // Theme options
      theme_advanced_buttons1 : "bold,italic,underline,strikethrough,fontselect,fontsizeselect,forecolor,|,backcolor,|,justifyleft,justifycenter,justifyright,justifyfull",
      theme_advanced_buttons2 : "",
      theme_advanced_buttons3 : "",
      theme_advanced_toolbar_location : "top",
      theme_advanced_toolbar_align : "left",
      theme_advanced_resizing : false,
      theme_advanced_path : false,
      theme_advanced_font_sizes : "12px,14px,16px,18px,20px,24px",
  
      // Example content CSS (should be your site CSS)
      content_css : "<%=path%>/css/tinyMCE/content.css"
    });
    var warehouseSelect;
    $(document).ready(function(){
      new Upload({
        renderTo : 'fileContent',
        multiple : true,
        renamable : true
      });
      new UsersSelect({
        url : '<%=path%>/system/ajax/listUsersByCondition.v',
        inputId : 'replierId',
        title : '选择收件人',
        width : 453,
        multiple : true
      });
      new UsersSelect({
        url : '<%=path%>/system/ajax/listUsersByCondition.v',
        inputId : 'copierId',
        title : '选择抄送',
        width : 453,
        multiple : true
      });
      
      addValidate('icTitle',[{type:'canNull', value:'F', message:'必须填写标题'}]);
      addValidate('replierId',[{type:'canNull', value:'F', message:'必须选择接收人'}]);
      addValidate('icTitle', [{type:'maxlength', value: 200, message:'【标题】最大长度为200'}]);
      addValidate('contents', [{type:'maxlength', value: 1000, message:'【内容】最大长度为1000'}]);
    });
  </script>
  </body>
</html>