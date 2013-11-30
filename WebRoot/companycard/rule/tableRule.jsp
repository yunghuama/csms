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
    <link href="<%=path%>/css/pagination.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/list.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/xToolbar.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/jquery-impromptu.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/xWindowPanel.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/moreexpand.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <s:include value="/share/btMask.jsp" />
  	<div id="toolbar"></div>
  	<form id="listForm" action="<%=path%>/csms/rule/listPagination.v" method="post">
  	<input type="hidden" name="state" id="state"/>
      <table id="titleTable" cellpadding="0" cellspacing="0">
        <tr>
          <td>&nbsp;</td>
          <td><input type="checkbox" id="allBox"/></td>
          <td>部门名称</td>
          <td>名片内容</td>
          <td>当前计划</td>
          <td>备注</td>
          <td>状态</td>
        </tr>
      </table>
      <table id="dataTable" cellpadding="0" cellspacing="0">
        <s:iterator id="rule" value="page.list" status="i">
          <tr class="row"
         
          >
            <td class="num"><s:property value="#i.index+1"/></td>
            <td class="box">
                <input type="checkbox" name="idList" value="<s:property value="#rule.group.id"/>"/>
            </td>
            <td><s:property value="#rule.group.name"/></td>
            <td><span><s:property value="#rule.content"/>&nbsp;</span></td>
            <td align="center"><span><s:property value='#rule.ruleDay'/>&nbsp;
            <s:if test='#rule.timeType=="0"'>工作时间</s:if>
            <s:if test='#rule.timeType=="1"'>非工作时间</s:if>
            <s:if test='#rule.timeType=="2"'>全天</s:if></td>
            <td align="center"><span><s:property value="#rule.creator.realName"/>&nbsp;</span></td>
            <td align="center">
            <s:if test='#rule.state=="0"'><span style="display:inline;color:green">开启</span></s:if>
            <s:if test='#rule.state=="1"'><span style="display:inline;color:red">关闭</span></s:if>
           </td>
          </tr>
        </s:iterator>
      </table>
      <s:hidden name="type"/>
      <s:include value="/share/pagebar.jsp"/>
  	</form>
    
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
    <script src="<%=path%>/js/share.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script src="<%=path%>/js/Grid.js" type="text/javascript"></script>
    <script src="<%=path%>/js/BoxSelect.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xToolbar.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xToolbar.items.js" type="text/javascript"></script>
    <script src="<%=path%>/js/datePicker/WdatePicker.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery-impromptu.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xWindowPanel.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xDateFormat.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(document).ready(function(){
      new Toolbar({
        id: '1',
        renderTo : 'toolbar',
        icon: '../../image/op.gif',
        items : [{
          type:'button',
          text:'计划设置',
          useable : '<s:property value="@com.platform.util.Meta@getOperate(\"cardrule_new\")"/>',
          position: {
            a: '0px 0px',
            b: '0px -120px'
          },
          handler:function(){
        	  if(getFirstID()){
             	 var idArray = [];
             	 //获得所有选中的ID
             	 $("input[name=idList]").each(function(){
             		 if($(this).attr("checked")==true){
 	            		 var id = $(this).val();
 	            		 idArray.push(id);
             		 }
             	 });
             	 top.ruleFunctions.openSaveRuleWindow(idArray.join(','));
              }
           
          }
        },'-',{
            type : 'button',
            text : '启用',
            useable : '<s:property value="@com.platform.util.Meta@getOperate(\"cardrule_edit\")"/>',
            position: {
            	a: '-60px -40px',
                b: '-140px -160px'
            },
            handler : function(){
              if(getFirstID()) {
            	  $("#state").val("0");
            	  $("#listForm").attr("action","<%=path%>/csms/rule/updateState.v");
            	  $("#listForm").submit();
              }
            }
         },'-',{
          type : 'button',
          text : '禁用',
          position: {
        	  a: '-80px -40px',
              b: '-140px -160px'
          },
          useable : '<s:property value="@com.platform.util.Meta@getOperate(\"cardrule_delete\")"/>',
          handler : function(){
        	  if(getFirstID()) {
            	  $("#state").val("1");
            	  $("#listForm").attr("action","<%=path%>/csms/rule/updateState.v");
            	  $("#listForm").submit();
              }
          }
        }]
      });
      
      new Grid({
        titleTable:'titleTable',
        dataTable:'dataTable',
        widths : [30,24,200,250,350,200,130],
        height : function(){return getGridHeight({toolbarId:'toolbar',hasPage:true});}
      });
      
      
      new BoxSelect({
        allId : 'allBox',
        boxName : 'idList'
      });
      
      loadReady();
      
      //预设置策略
      $(".row").each(function(){
    	  var rowObj = $(this);
    	  rowObj.find(".moreexpand").bind("click",function(){
    		  var id = $(this).attr("id");
    		  var moreexpandSpan = $(this);
        	  var extendRow = rowObj.next(".moreExtandRow");
        	  //清空原有数据
        	  extendRow.find("tr").each(function(index){
        		  if(index!=0)
        			  $(this).remove();
        	  });
        	  extendRow.toggle();
        	  //如果是可见的,则载入数据
        	  if(extendRow.is(":visible")){
        		  moreexpandSpan.text("-");
        		 $.ajax({
        			url : '<%=path%>/csms/ruleAjax/findAll.v', 
        			data : {'ruleId' : id },
        			dataType : 'json',
        			type : 'GET',
        			success : function(data){
        				if(data!=null&&data!=""){
        					for(var i=0;i<data.length;i++){
        						var array = [];
                				array.push("<tr id="+data[i].id+"><td align=center>"+data[i].name+"</td><td align=center>"+data[i].content+"</td><td align=center>"+new Date(data[i].executeDate).format("yyyy-MM-dd")+"</td><td align=center><a href='javascript:void(0)' id='del'>删除</a></td></td></tr>");
                				var row = $(array.join('')).appendTo(extendRow.find("#dataMoreExtand"));
                				row.find("#del").bind("click",function(){
                					if(!confirm("确定要删除?"))
                						return;
                					$.ajax({
                						url : '<%=path%>/csms/ruleAjax/delete.v', 
                	        			data : {'idList[0]' : row.attr("id")},
                	        			dataType : 'json',
                	        			type : 'GET',
                						success : function(data){
                							row.remove();
                						}
                					});
                				});
        					}
        				}
        			
        				var addButton = '<tr><td colspan="4"><a href="javascript:void(0);" id="add">添加一条</a></td></tr>';
        				$(addButton).appendTo(extendRow.find("#dataMoreExtand"));
        				extendRow.find("#add").bind("click",function(){
        					addRule(extendRow);
        				});
        			}
        		 });
        		 
        	  }else {
        		  moreexpandSpan.text("+");
        	  }
        	  return false;
          }) ;	
    	  
      });
      
    });
    
      function addRule(obj){
    	  var content = [];
    	  //查询内容
    	  $.ajax({
    			url : '<%=path%>/csms/ruleAjax/toSave.v', 
    			dataType : 'json',
    			type : 'GET',
    			async : false,
    			success : function(data){
    				content.push("<select>")
    				if(data!=null||data!=""){
    					for(var i=0;i<data.length;i++){
    						content.push("<option value="+data[i].id+">"+data[i].content+"</option>")
    					}
    				}
    				content.push("</select>")
    			}
          });
    	  var array = [];
    	  array.push('<tr>');
    	  array.push('<td align=center><input type="text" class="text"></td>');
    	  array.push('<td align=center>'+content.join('')+'</td>');
    	  array.push('<td align=center><input type="text" class="Wdate text f" onclick="new WdatePicker();"></td>');
    	  array.push('<td align="center"><a href="javascript:void(0)" id="save">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" id="del">删除</a></td>');
    	  array.push('</tr>');
    	  var rowObj = $(array.join('')).insertBefore(obj.find("#add").closest("tr"));
    	  rowObj.find("#del").bind("click",function(){
    		  alert();
    		  rowObj.remove();
    	  });
    	  rowObj.find("#save").bind("click",function(){
    		  var name = rowObj.find("input").eq(0).val();
    		  var selectContent = rowObj.find("select").eq(0).val();
    		  var date = rowObj.find("input").eq(1).val();
    		  //提交之前验证
    		  if(selectContent==null||selectContent==""){
    			  alert("请选择内容");
    			  return;
    		  }
    		  if(date==null||date==""){
    			  alert("请选择日期");
    			  return;
    		  }
    		  
    		  $.ajax({
      			url : '<%=path%>/csms/ruleAjax/save.v', 
      			dataType : 'json',
      			data : {"rule.name" : name,"rule.rule" :obj.attr("id") ,"rule.content":selectContent,"rule.executeDate":new Date(date).getTime()},
      			type : 'POST',
      			async : false,
      			success : function(data){
      				alert(data);
      			}
            });
    	  });
      }
    
      function formatData(dataArray){
    	  
      }
    
      function openMoreOperateWindow(e, usersId,departmentId, id) {
      var morebar = new Toolbar({
        id:'2',
        icon: '../../image/op.gif',
        border: 'none',
        items : [{
          type : 'button',
          text : '删除',
          useable : findOtherOperate(usersId,departmentId, 'department_delete'),
          position: {
            a: '-40px 0px',
            b: '-40px -120px'
          },
          handler : function(){
            WPS['jWindowPanel-moreOperate'].close();
            var txt = '是否删除？';
            $.prompt(txt,{
              buttons:{删除:true,取消:false},
              callback: function(v,m) {
                if(v) {
                  window.location = '<%=path%>/csms/content/delete.v?idList='+id;
                }
              }
            });
          }
        }]
      });
      var wp = new WindowPanel({
        id : 'moreOperate',
        title : '可用操作',
        width : 280,
        height : 0,
        draggable : false,
        minimizable: false,
        maximizable: false,
        position: {
          x: e.clientX,
          y: e.clientY
        },
        tbar: morebar
      });
    }
    </script>
  </body>
</html>