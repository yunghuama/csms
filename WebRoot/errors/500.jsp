<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>出错了</title>
    <style>
    .error {
      width: 308px;
      height: 84px;
      background: url('<%=path%>/errors/error.png') no-repeat #FFF;
    }
    </style>
  </head>
  <body>
    <div class="error"></div>
    <pre>
请检查是否由于以下原因造成：
  1)表单内容过长：名称一般不得超过100字，备注一般不得超过2000字；
  2)不可直接将带格式的word文档或excel文档复制到表单文本框中；
  3)所操作的数据已被其它用户更改。
    </pre>
<a href="javascript:history.back(-1)">返回</a>
  </body>
</html>