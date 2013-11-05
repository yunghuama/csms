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
    <link href="<%=path%>/css/Upload.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/UsersSelect.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/xWindowPanel.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/xToolbar.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <form name="companyForm" id="companyForm" method="post">
      <s:hidden name="company.id"/>
      <div class="form-group">基本信息</div>
      <table class="form-table" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="form-left">编号</td>
          <td class="form-right"><s:property value="company.code"/></td>
          <td class="form-left">名称</td>
          <td class="form-right"><s:property value="company.name"/></td>
        </tr>
        <tr>
          <td class="form-left">来源</td>
          <td class="form-right"><s:property value="company.source.name"/></td>
          <td class="form-left">等级</td>
          <td class="form-right"><s:property value="company.grade.name"/></td>
        </tr>
        <tr>
          <td class="form-left">行业</td>
          <td class="form-right"><s:property value="company.industry.name"/></td>
          <td class="form-left">阶段</td>
          <td class="form-right"><s:property value="company.phase.name"/></td>
        </tr>
         <tr>
        	<td colspan="4">
        <div class="form-group">联系方式</div>
        </td></tr>
         <tr>
          <td class="form-left">联系电话</td>
          <td class="form-right"><s:property value="company.phone"/></td>
          <td class="form-left">邮箱</td>
          <td class="form-right"><s:property value="company.mail"/></td>
        </tr>
         <tr>
          <td class="form-left">传真</td>
          <td class="form-right"><s:property value="company.fax"/></td>
          <td class="form-left">主页</td>
          <td class="form-right"><s:property value="company.homepage"/></td>
        </tr>
         <tr>
          <td class="form-left">所在城市</td>
          <td class="form-right"><s:property value="company.region"/></td>
          <td class="form-left">邮编</td>
          <td class="form-right"><s:property value="company.post"/></td>
        </tr>
        <tr>
          <td class="form-left">详细地址</td>
          <td class="form-right" colspan="3"><s:property value="company.address"/></td>
        </tr>
         <tr>
        	<td colspan="4">
        	<div class="form-group">公司规模</div>
        	</td>
        </tr>
         <tr>
          <td class="form-left">公司性质</td>
          <td class="form-right"><s:property value="company.companyKind.name"/></td>
          <td class="form-left">规模</td>
          <td class="form-right"><s:property value="company.companySize.name"/></td>
        </tr>
        <tr>
          <td class="form-left">营业额</td>
          <td class="form-right"><s:property value="company.turnover.name"/></td>
          <td class="form-left">上级单位</td>
          <td class="form-right"><s:property value=""/></td>
        </tr>
        <tr>
          <td class="form-left">简介</td>
          <td class="form-right"><s:property value="company.summary"/></td>
          <td class="form-left">备注</td>
          <td class="form-right"><s:property value="company.remark"/></td>
        </tr>
         <tr>
        	<td colspan="4">
          <div class="form-group">财务信息</div>
          </td></tr>
        <tr>
          <td class="form-left">开户银行</td>
          <td class="form-right"><s:property value="company.bank"/></td>
          <td class="form-left">账号</td>
          <td class="form-right"><s:property value="company.bankAccount"/></td>
        </tr>
        <tr>
          <td class="form-left">税号</td>
          <td class="form-right"><s:property value="company.taxAccount"/></td>
          <td class="form-left">信用等级</td>
          <td class="form-right"><s:property value="company.creditGrade.name"/></td>
        </tr>
      </table>
      <s:hidden name="tabId"/>
      <s:hidden name="windowPanelId"/>
    </form>
  </body>
</html>