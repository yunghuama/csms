<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>查看通信</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/form.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/Upload.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <s:bean name="com.vwinwork.service.UsersService" id="usersService"></s:bean>
    <form name="intercomForm" id="intercomForm" method="post" action="<%=path%>/system/intercom/view.v" class="form-scroll">
      <input type="hidden" name="intercomId" value="<s:property value="intercom.id"/>"/>
      <div class="form-group">基本信息</div>
      <table class="form-table" cellspacing="0" cellpadding="0">
        <tr>
          <td class="form-left">发送人</td>
          <td class="form-right"><s:property value="intercom.sender.realName"/></td>
          <td class="form-left">发送时间</td>
          <td class="form-right"><s:date name="intercom.sendTime" format="yyyy-MM-dd HH:mm"/></td>
        </tr>
        <tr>
          <td class="form-left">接收人</td>
          <td class="form-right-hidden" title="<s:property value="#usersService.findUserLinks(intercom.replier)" escape="false"/>"><s:property value="#usersService.findUserLinks(intercom.replier)" escape="false"/></td>
          <td class="form-left">抄送人</td>
          <td class="form-right-hidden" title="<s:property value="#usersService.findUserLinks(intercom.copier)" escape="false"/>"><s:property value="#usersService.findUserLinks(intercom.copier)" escape="false"/>&nbsp;</td>
        </tr>
        <tr>
          <td class="form-left">主题</td>
          <td colspan="3" class="form-right"><s:property value="intercom.title" /></td>
        </tr>
      </table>
      <div class="form-group">内容</div>
      <div style="white-space:normal;padding: 8px;">
        <span style="width:100%;height:100%">
          <s:property value="intercom.contents" escape="false"/>
        </span>
      </div>
      <s:include value="/share/uploadView.jsp"></s:include>
    </form>
  </body>
</html>