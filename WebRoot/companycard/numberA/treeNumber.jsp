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
      <ul id="treeDemo" class="ztree"></ul>
       
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
        zNodes.push({ id:'00000000000000000000000000000000', pId:'', name:'部门',open:true});
        <s:iterator id="group" value="groupList" status="st">
    	zNodes.push({ id:'<s:property value="#group.id"/>', pId:'00000000000000000000000000000000', name:'<s:property value="#group.name"/>'});
    	</s:iterator>
      function treeOnClick(event,treeId,treeNode)
      {
        parent.mainFrame.location = "<%=path%>/csms/number/listPaginationA.v?groupId="+treeNode.id;
      }   
        
    function refreshTree()
    {
      window.location.reload();
    }
    
    $(document).ready(function(){
      $.fn.zTree.init($("#treeDemo"), setting, zNodes);
      
      new Toolbar({
        id: '1',
        renderTo : 'toolbarUp',
        icon: '../../image/op.gif',
        items : [{
          type : 'button',
          text : '新建',
          position: {
        	  a: '0px 0px',
              b: '0px -120px'
          },
          handler : function(){
        	  top.groupFunctions.openSaveGroupWindow();
          }
        },'-',{
          type : 'button',
          text : '修改',
          position: {
        	  a: '-20px 0px',
              b: '-20px -120px'
          },
          handler : function(){
        	  top.groupFunctions.openUpdateGroupWindow(getFirstID());
          }
        },'-',{
            type : 'button',
            text : '删除',
            position: {
            	a: '-40px 0px',
                b: '-40px -120px'
            },
            handler : function(){
            	validateBeforeDelete({
                    validateURL : '<%=path%>/system/ajax/deleteValidate.v',
                    validateParams : $('input:checkbox[name="idList"]:checked').serialize(),
                    validateTable : 'CSMSNumber',
                    deleteURL : '<%=path%>/csms/group/delete.v'
                  });
            }
          },'-',{
              type : 'button',
              text : '刷新',
              position: {
            	  a: '-60px 0px',
                  b: '-60px -120px'
              },
              handler : function(){
            	  
              }
            }]
      });
      
      setTreeHeight(['toolbarUp', 'toolbarDown']);
    });
    </script>
  </body>
</html>