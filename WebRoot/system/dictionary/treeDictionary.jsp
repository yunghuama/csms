<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>字典列表</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/xToolbar.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <div id="toolbarUp"></div>
    <div class="treePanel">
      <div class="paddingPanel">
        <script src="<%=path%>/js/tree/MzTreeView10.js" type="text/javascript"></script>
        <script type="text/javascript">
          var tree = new MzTreeView("tree");
          
          tree.setIconPath("<%=path%>/js/tree/dictionary/");
          
          tree.nodes['0_1'] = 'text:字典分类; method:changeUrl(\'<s:property value="@com.platform.constants.StringConstant@ROOT_ID"/>\')';
          
          <s:iterator id="dictionary" value="dictionaryList">
          tree.nodes['1_<s:property value="#dictionary.id"/>'] = 'text:<s:property value="#dictionary.name"/>; method:changeUrl(\'<s:property value="#dictionary.id"/>\')';
          </s:iterator>
          
          document.write(tree.toString());
          
        </script>
      </div>
    </div>
    <div id="toolbarDown"></div>
    
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
    <script src="<%=path%>/js/share.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xToolbar.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xToolbar.items.js" type="text/javascript"></script>
    <script type="text/javascript">
    function changeUrl(superId)
    {
      parent.mainFrame.location = "<%=path%>/system/dictionary/listPagination.v?superId="+superId;
    }
    
    $(document).ready(function(){
    
      new Toolbar({
        id: '1',
        renderTo : 'toolbarUp',
        icon: '../../image/op.gif',
        items : [{
          type : 'button',
          text : '刷新',
          position: {
            a: '-60px 0px',
            b: '-60px -120px'
          },
          handler : function(){
            window.location.reload();
          }
        }]
      });
      
      new Toolbar({
        id: '2',
        renderTo : 'toolbarDown',
        border : 'top',
        icon: '../../image/op.gif',
        items : [{
          type : 'button',
          text : '排序',
          useable : '<s:property value="@com.platform.util.Meta@getOperate(\"dictionary_sort\")"/>',
          position: {
            a: '-160px -60px',
            b: '-160px -180px'
          },
          handler : function(){
            var parentId = tree.currentNode.sourceIndex.split('_')[1];
            if(parentId.length === 32)
              top.dictionaryFunctions.openUpdateDictionarySortWindow(parentId);
            else
              alert('请选择需要排序的字典节点');
          }
        }]
      });
      
      setTreeHeight(['toolbarUp', 'toolbarDown']);
    });
    </script>
  </body>
</html>