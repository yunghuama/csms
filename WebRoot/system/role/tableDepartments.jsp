<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>角色模块用户</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/pagination.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/list.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/xToolbar.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/xWindowPanel.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <div id="toolbar"></div>
    <form id="listForm" action="<%=path%>/system/role/listRoleDataDepartments.v" method="post">
      <table id="titleTable" cellpadding="0" cellspacing="0">
        <tr>
          <td>&nbsp;</td>
          <td>部门名称</td>
          <td>权限</td>
          <td>操作授权</td>
        </tr>
      </table>
      <table id="dataTable" cellpadding="0" cellspacing="0">
        <s:iterator id="department" value="departmentList" status="i">
          <tr class="row">
            <td class="num"><s:property value="#i.index+1"/></td>
            <!-- 部门名称 -->
            <td>
              <span>
	              <input <s:if test="#department.code == #departmentCode">checked="checked"</s:if> type="checkbox" name="idList" value="<s:property value="#department.id" />" style="width:13px;height:13px;vertical-align:middle" id="<s:property value="#department.code"/>"/>
	              <s:property value="#department.name"/>
              </span>
            </td>
            <td align="center"><span id="sees_<s:property value="#department.id"/>"><img src="<%=path%>/image/nosee.gif"/>禁止</span></td>
            <td align="center"><span id="sdv_<s:property value="#department.id"/>">&nbsp;</span></td>
          </tr>
        </s:iterator>
      </table>
      
      <s:hidden name="moduleId"/>
      <s:hidden name="roleId"/>
    </form>
    
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
    <script src="<%=path%>/js/share.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script src="<%=path%>/js/Grid.js" type="text/javascript"></script>
    <script src="<%=path%>/js/BoxSelect.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xToolbar.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xToolbar.items.js" type="text/javascript"></script>
    <script src="<%=path%>/js/Draggable.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xWindowPanel.js" type="text/javascript"></script>
    <script type="text/javascript">
    
    var see = '<img src="<%=path%>/image/see.gif"/>';
    var nosee = '<img src="<%=path%>/image/nosee.gif"/>禁止';
    var setup = '<img src="<%=path%>/image/setup.gif"/>';
    
    $(document).ready(function(){
    
      var depList = '<s:property value="roleModuleDepartments.departments"/>';
      // 控制部门名称的缩进及checkbox事件 #zhq 2011-01-08
      $(':checkbox').each(function(){
        var checkbox = $(this);
        var code = checkbox.attr('id');
        var value = checkbox.attr('value');
        // 部门缩进效果
        var sp = '';
        for(var i=0,len=(code+'').length-3; i<len; ++i){
          sp += '&nbsp;';
        }
        // 初始加载时的显示效果
        var children = $(':checkbox:[id^='+code+']:not(:[id='+code+'])');
        if(depList.indexOf(value)!=-1){
           checkbox.attr('checked', true);
           loadSee($(this).val(), 'T');
        /*   children.each(function(){
             $(this).attr('checked', true);
             loadSee($(this).val(), 'T', true);
           });*/
        }
        // 鼠标选择事件
        checkbox.before(sp).bind('click', function(){
          // check时选中所有下级部门disabled
          var isChecked = checkbox.attr('checked');
          children.each(function(){
            $(this).attr('checked', isChecked);
          });
            
          /* (暂时删除)  uncheck时取消所有选中的上级部门
          (function(code){
			      var end = code.lastIndexOf('-');
			      // 上级部门是根节点时
			      if(end==-1){
			        return;
			      }
			      // 取上级部门code
			      code = code.substring(0, end);
			      $(':checkbox:[id='+code+']').attr('checked', false);
			      arguments.callee(code);
			    })(code);
			    */
        });
      });
      
      for(var i=0,len=depList.length; i<len; ++i){
        var checkedId = $.trim(depList[i]);
        if(checkedId){
	        $(':checkbox:[value='+checkedId+']').attr('checked', true);
        }
      }
      
      var toolbar = new Toolbar({
        id: '1',
        renderTo : 'toolbar',
        icon: '../../image/op.gif',
        items : [{
          type : 'button',
          text : '保存',
          position: {
            a: '-80px 0px',
            b: '-80px -120px'
          },
          handler : function(){
            $.ajax({
              url : '<%=path%>/system/ajax/saveRoleModuleDepartments.v',
              data : $('#listForm').serialize(),
              success : function(json){
                 json = json || [];
                 $(':checkbox').each(function(){
					        var checkbox = $(this);
					        var value = checkbox.attr('value');
					        var code = checkbox.attr('id');
					        if(!checkbox.attr('disabled')){
					          if(json.join('').indexOf(value)==-1){
					            loadSee($(this).val(), 'F');
					          }else{
					            loadSee($(this).val(), 'T');
					            $(':checkbox:[id^='+code+']:not(:[id='+code+'])').each(function(){
					              loadSee($(this).val(), 'T');
					            });
					          }
					        }
					      });
              }
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
            $('#listForm')[0].submit();
          }
        }]
      });
      
      var grid = new Grid({
        titleTable:'titleTable',
        dataTable:'dataTable',
        widths : [30,300,80,80],
        height : function(){return getGridHeight({toolbarId:'toolbar',hasPage:false});}
      });
      
      $(window).resize(function(){
        setTimeout(function(){
          grid.updateAll();
        }, 200);
      });
      
      <s:iterator value="roleModuleUsersList">
        loadSee('<s:property/>', 'T');
      </s:iterator>
      
      <s:iterator value="roleModuleDepartmentList">
        $('#<s:property/>').attr('checked', true);
      </s:iterator>
      
      //loadReady();
    });
    
    function loadSee(id, state, inheritance) {
      if(state=='T'){
        $('#sees_'+id).html(see+(inheritance?'继承':'可见'));
        $('#sdv_'+id).html(setup+(inheritance?'继承':'<a href="javascript:void(0)">设置</a>'));
        $('#sdv_'+id).css('cursor', 'pointer');
        $('#sdv_'+id).bind('click', function(){
          new WindowPanel({
            id : 'scopeDataVisibleSetup',
            title : '操作授权设置',
            width : 330,
            height : 300,
            html : '<iframe name="saveScopeDeptVisibleFrame" id="saveScopeDeptVisibleFrame" src="<%=path%>/system/role/toUpdateScopeDeptVisible.v?departmentId='+id+'&moduleId='+$('#moduleId').val()+'&roleId='+$('#roleId').val()+'" frameborder="0" scrolling="auto"></iframe>',
            draggable : false,
            tbar : new Toolbar({
              id: '2',
              icon: '../../image/op.gif',
              items : [{
                type : 'button',
                text : '启用',
                position: {
                  a: '-140px -40px',
                  b: '-140px -160px'
                },
                handler : function(){
                  getFrame('saveScopeDeptVisibleFrame').changeVisible('T');
                }
              },'-',{
                type : 'button',
                text : '禁用',
                position: {
                  a: '-140px -20px',
                  b: '-140px -160px'
                },
                handler : function(){
                  getFrame('saveScopeDeptVisibleFrame').changeVisible('F');
                }
              },'-',{
                type : 'button',
                text : '刷新',
                position: {
                  a: '-60px 0px'
                },
                handler : function(){
                  getFrame('saveScopeDeptVisibleFrame').location = getFrame('saveScopeDeptVisibleFrame').location;
                }
              }]
            })
          });
        });
      }
      else
      {
        $('#sees_'+id).html(nosee);
        $('#sdv_'+id).html('&nbsp;');
        $('#sdv_'+id).unbind('click');
      }
    }
    
    //暂时过时
    function updateRoleModuleDepartments(obj) {
      var datas = obj.checked ? '&useable=T' : '&useable=F';
      datas += ('&departmentId='+obj.value);
      $.ajax({
        url : '<%=path%>/system/ajax/updateRoleModuleDepartments.v',
        data : $('#listForm').serialize() + datas
      });
    }
    </script>
  </body>
</html>