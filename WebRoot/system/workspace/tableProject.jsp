<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>常用下载列表</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/jquery-impromptu.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/desktop.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <form id="aForm" action="<%=path%>/office/documents/listPaginationIndex.v" method="post" >
      
      <table width="100%" border="0" style="table-layout:fixed;"> 
        <tr class="newsTr1">
        <td width="10%">序号</td><td>组件名称</td><td>描述</td>
        </tr>
        <tr class="newsTr1">
	       	<td>1</td>
	        <td>
	           <a title="右键选择另存为" target="_blank" href="<%=path%>/js/webPrint/install_lodop.exe">
	               [必装]-打印控件
	           </a>
	         </td>
	      	 <td>用于某些查看页面的打印功能</td>
        </tr>
        <tr class="newsTr1">
	        <td>2</td>
	        <td>
	        <a title="右键选择另存为" target="_blank" href="<%=path%>/help/soft/VOA-Client.rar">
	             VOA桌面客户端
	           </a>
	        </td>
	        <td>安装时若遇到杀毒软件提示,请选择允许.</td>
        </tr>
        
         <tr class="newsTr1">
	        <td>3</td>
	        <td>
	        <a title="右键选择另存为" target="_blank" href="<%=path%>/help/video/chuchai.rar">
	             (培训)VOA视频教程-拜访交流流程
	           </a>
	        </td>
	        <td>出差流程操作介绍.</td>
        </tr>
        
        <tr class="newsTr1">
	        <td>4</td>
	        <td>
	        <a title="右键选择另存为" target="_blank" href="<%=path%>/help/video/fangan.rar">
	             (培训)VOA视频教程-方案流程
	           </a>
	        </td>
	        <td>技术方案流程操作介绍.</td>
        </tr>
        <tr class="newsTr1">
	        <td>5</td>
	        <td>
	        <a title="右键选择另存为" target="_blank" href="<%=path%>/help/video/baojia.rar">
	             (培训)VOA视频教程-报价流程
	           </a>
	        </td>
	        <td>项目报价流程操作介绍.</td>
        </tr>
         <tr class="newsTr1">
	        <td>6</td>
	        <td>
	        <a title="右键选择另存为" target="_blank" href="<%=path%>/help/video/toubiao.rar">
	             (培训)VOA视频教程-投标流程
	           </a>
	        </td>
	        <td>投标流程操作介绍.</td>
        </tr>
         <tr class="newsTr1">
	        <td>7</td>
	        <td>
	        <a title="右键选择另存为" target="_blank" href="<%=path%>/help/video/hetong.rar">
	             (培训)VOA视频教程-合同流程
	           </a>
	        </td>
	        <td>合同流程操作介绍.</td>
        </tr>
      </table>
    </form>
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(document).ready(function(){
        
    });
    
    </script>
  </body>
</html>