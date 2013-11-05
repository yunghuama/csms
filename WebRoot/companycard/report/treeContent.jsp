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
    <link href="<%=path%>/js/ztree/zTreeStyle.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <div id="toolbarUp"></div>
    <div class="treePanel">
      <div class="paddingPanel">
      <ul id="tree" class="ztree"></ul>
       	
      </div>
    </div>
    <div id="toolbarDown"></div>
    
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
    <script src="<%=path%>/js/share.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xToolbar.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xToolbar.items.js" type="text/javascript"></script>
    <script src="<%=path%>/js/ztree/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
    <script type="text/javascript">
    
    var setting = {
			data: {
				simpleData: {
					enable: true
				}
			},
			callback:{
				onClick : treeOnClick
			}
		};
        var zNodes =[];
        zNodes.push({ id:'-1', pId:'', name:'山东',open : true});
    	zNodes.push({ id:'0', pId:'-1', name:'济南'});
    	zNodes.push({ id:'1', pId:'-1', name:'青岛'});
      function treeOnClick(event,treeId,treeNode)
      {
        parent.mainFrame.location = "<%=path%>/csms/content/listPagination.v?type="+(treeNode.id=="-1" ? "" :  treeNode.id);
      }   
        
    function refreshTree()
    {
      window.location.reload();
    }
    
    $(document).ready(function(){
      var treeObj = $.fn.zTree.init($("#tree"), setting, zNodes);

      
      new Toolbar({
        id: '1',
        renderTo : 'toolbarUp',
        icon: '../../image/op.gif',
        items : [{
          type : 'button',
          text : '全部展开',
          position: {
            a: '-160px -80px',
            b: '-160px -200px'
          },
          handler : function(){
            tree.expandAll();
          }
        },'-',{
          type : 'button',
          text : '刷新',
          position: {
            a: '-60px 0px',
            b: '-60px -120px'
          },
          handler : refreshTree
        }]
      });
      
      setTreeHeight(['toolbarUp', 'toolbarDown']);
    });
    </script>
  </body>
</html>