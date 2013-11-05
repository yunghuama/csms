<%--
  文件名：uploadView.jsp
  说明：上传文件浏览界面
  作者：马金凯
  更改记录：
  --------------------------------------------------------
  改动人   时间      原因
  --------------------------------------------------------
  马金凯   2008-1-29     创建文件  
  --------------------------------------------------------
--%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
  String path = request.getContextPath();
%>
<script type="text/javascript">
<!--
function deleteAF(id){
  if(window.confirm('是否删除附件？'))
  {
    $.ajax({
      url:'<%=path%>/system/ajax/deleteAttachedFile.v',
      data:'attachedFileId='+id,
      success: function(json){
        if(json=='T')
        {
          $('#'+id).fadeOut(400);
          if($('.afView').length==0)
            $('#afTitle').fadeOut(400);
        }
        else
          alert('数据加载失败，请重试');
      }
    });
  }
}
//判断文件是否存在
function fileExists(filePath){
	var fileName = "";
	$.ajax({
	      url:'<%=path%>/ajax/onlineview/swfIsExist.v',
	      data:'filePath='+filePath,
	      async:false,
	      success: function(json){
	        fileName = json;
	      }
	  });
	  return fileName;
}
function viewFile(filePath){
	var fileName = fileExists(filePath);
	if(fileName==""){
		alert("该文件没有预览");
		return;
	}else {
		var height = 650;
		var width = 850;
		var tleft = (window.screen.width-width)/2;
		var ttop = (window.screen.height-height)/2;
		window.open('<%=path%>/share/onLineView.jsp?swf='+fileName,'','height='+height+', width='+width+', top='+ttop+', left='+tleft+', toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
	}
}
//-->
</script>
<s:if test="attachedFileList.size()>0">
  <div class="form-group">相关附件</div>
  <div class="afView">
    <s:iterator id="attachedFile" value="attachedFileList">
      <h3 id="<s:property value="#attachedFile.id"/>">
        <a target="_blank" title="下载" href="<%=path%>/down.v?id=<s:property value="#attachedFile.id"/>">
          <s:property value="#attachedFile.title"/>
        </a>
        <%
          if("true".equals(request.getParameter("deletable"))) {
            %><img src="<%=path%>/image/deletor.gif" onclick="deleteAF('<s:property value="#attachedFile.id"/>')"/><%
          }
        %>
      <!--   <a  style="margin-left:20px" onclick="viewFile('<s:property value="#attachedFile.filePath"/>')" href="javascript:void(0);">预览
        </a> -->
      </h3>
    </s:iterator>
  </div>
</s:if>