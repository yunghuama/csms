<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>办公文档列表</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/jquery-impromptu.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/desktop.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <form id="aForm" action="<%=path%>/office/documents/listPaginationIndex.v" method="post">
      
      <table width="100%" border="0" style="table-layout:fixed;"> 
        <s:if test="page.list.size() > 0">
         <s:iterator id="documents" value="page.list" status="i">
          <tr class="newsTr1">
            <td class="newsTdLeft">
                <a id="docTit" title="<s:property value="#documents.title"/>" target="_blank" href="<%=path%>/down.v?id=<s:property value="#documents.aFile.id"/>">
                    <s:property value="#documents.title"/>
                </a>
            </td>
            <td class="newsTdCenter"><span><s:property value="#documents.sort.sortName"/></span></td>
            <td class="newsTdRight"><span><s:date name="#documents.createTime" format="yyyy-MM-dd HH:mm:ss"/></span></td>
          </tr>
        </s:iterator>
        <tr class="newsTr1"><td colspan="3" class="news_more"><a style="color:blue;cursor: pointer;" onclick="javascript:documentsList();">更多...</a></td></tr>
        </s:if>
        <s:else>
            <tr class="newsTr1"><td colspan="3" >暂无办公文档</td></tr>
        </s:else>
        
      </table>
    </form>
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(document).ready(function(){
        
    });
    /**
      *办公文档快速入口
      **/
     function documentsList(){
        if(top.tabpanel.$tabs['documents']){//如果已经打开了浏览标签，则更新html，显示新内容
            top.tabpanel.setHtml('documents','<iframe name="documentsFrame" id="documentsFrame" src="<%=path%>/office/documents/documents/listDocuments.jsp" frameborder="0" scrolling="auto"></iframe>');
            top.tabpanel.show('documents');
        }else{//如果没有找到标签，则打开新标签
            top.tabpanel.addTab({
              id: 'documents',
              title: '办公文档',
              html: '<iframe name="documentsFrame" id="documentsFrame" src="<%=path%>/office/documents/documents/listDocuments.jsp" frameborder="0" scrolling="auto"></iframe>',
              position: {
                a: '0px -110px',
                b: '-22px -110px'
              },
              closable: true
            });
        }
     }
    </script>
  </body>
</html>