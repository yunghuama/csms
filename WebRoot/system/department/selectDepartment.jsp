<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>选择部门</title>
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
          
          tree.nodes['0_1'] = 'text:部门';
          <s:iterator id="department" value="departmentList">
          	<s:if test="#department.superId==@com.platform.constants.StringConstant@ROOT_ID">
          		tree.nodes['1_<s:property value="#department.id"/>'] = 'text:<s:property value="#department.name"/>; method:selectValue(\'<s:property value="#department.id"/>\',\'<s:property value="#department.name"/>\')';
          	</s:if>
          	<s:else>
          		tree.nodes['<s:property value="#department.superId"/>_<s:property value="#department.id"/>'] = 'text:<s:property value="#department.name"/>; method:selectValue(\'<s:property value="#department.id"/>\',\'<s:property value="#department.name"/>\')';
          	</s:else>
          </s:iterator>
          tree.setIconPath("<%=path%>/js/tree/department/");
          document.write(tree.toString());
        </script>
      </div>
    </div>
    <div id="toolbarDown"></div>
    <s:hidden name="inputId"></s:hidden>
    <s:hidden name="contentId"></s:hidden>
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
    <script src="<%=path%>/js/share.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xToolbar.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xToolbar.items.js" type="text/javascript"></script>
    <script type="text/javascript">
    function selectValue(deptId,name)
    {
    var inputId='<s:property value="inputId"/>';
    var contentId = '<s:property value="contentId"/>';
    opener.document.getElementById('<s:property value="inputId"/>').value=deptId;
    opener.document.getElementById('<s:property value="contentId"/>').innerHTML='<div style="height:20px;overflow:hidden"><div style="height:20px;cursor: pointer;float:left;overflow:hidden" ondblclick="document.getElementById(\''+inputId+'\').value=\'\';document.getElementById(\''+contentId+'\').innerHTML=\'\'" title="'+name+'">'+name+'</div>'
          +'<div title="取消选择"  onclick="document.getElementById(\''+inputId+'\').value=\'\';document.getElementById(\''+contentId+'\').innerHTML=\'\'" style="margin-left:3px;float:left; height: 20px;width:7px;cursor: pointer;background: transparent url('+projectName+'/image/l_close.gif) no-repeat center;" /></div>';
    
    window.close();
    }
    
    function refreshTree()
    {
      window.location.reload();
    }
    
    $(document).ready(function(){
    
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