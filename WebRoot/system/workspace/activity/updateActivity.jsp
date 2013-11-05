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
    <link href="<%=path%>/css/activity.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <form name="activityForm" id="activityForm" method="post" enctype="multipart/form-data" action="<%=path%>/system/company/save.v">
      <s:hidden name="activityId" id="activityId"/>
      <div class="form-group"><a href="#" class="backToCalender">返回日历</a><div class="toSave">保存</div><div class="toCancel">舍弃</div></div>
      <table class="form-table" cellspacing="0" cellpadding="0">
        <tr>
          <td class="form-left">标题</td>
          <td class="form-right" colspan="3"><input type="text" name="activity.title" id="title" class="text" style="width:380px;"/></td>
        </tr>
        <tr>
          <td class="form-left">开始时间</td>
          <td class="form-right" colspan="3"><input type="text" name="activity.startDate" id="startDate" class="Wdate text f" onclick="new WdatePicker({startDate:'now'});"/></td>
        </tr>
        <tr>
          <td class="form-left">结束时间</td>
          <td class="form-right" colspan="3"><input type="text" name="activity.endDate" id="endDate" class="Wdate text f" onclick="new WdatePicker({startDate:'now'});"/></td>
        </tr>
        <tr>
          <td class="form-left">地点</td>
          <td class="form-right" colspan="3"><input type="text" name="activity.place" id="place" class="text" style="width:380px;"/></td>
        </tr>
         <tr>
          <td class="form-left">说明</td>
          <td class="form-right">
            <s:textarea id="contents" name="activity.contents" cssClass="textarea" cssStyle="width:380px; height:100px;" theme="simple"></s:textarea>
          </td>
        </tr>
        </table>
        <div class="form-group">提醒设置</div>
      <s:hidden name="tabId"/>
      <s:hidden name="windowPanelId"/>
    </form>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
    <script src="<%=path%>/js/share.js" type="text/javascript"></script>
    <script src="<%=path%>/js/datePicker/WdatePicker.js" type="text/javascript"></script>
     <script type="text/javascript">
     	$(document).ready(function(){
     		var startTime = '<select id="startTime" name="activity.startTime" class="time"></select>';
     		$(startTime).insertAfter($("#startDate"));
     		for(var i=0;i<24;i++){
     			var option;
     			if(i==10){
     				option = '<option value="'+i+':00" selected="selected">'+i+':00</option><option value="'+i+':30">'+i+':30</option>';
     			}else if(i<10){
     				i = "0"+i;
     			    option = '<option value="'+i+':00">'+i+':00</option><option value="'+i+':30">'+i+':30</option>';
     			}else {
     			    option = '<option value="'+i+':00">'+i+':00</option><option value="'+i+':30">'+i+':30</option>';
     			}
     			$(option).appendTo($("#startTime"));
     		}
     		var endTime = '<select id="endTime" name="activity.endTime" class="time"></select>';
     		$(endTime).insertAfter($("#endDate"));
     		for(var i=0;i<24;i++){
     			var option;
     			if(i==12){
     				option = '<option value="'+i+':00" selected="selected">'+i+':00</option><option value="'+i+':30">'+i+':30</option>';
     			}else if(i<10){
     				i = "0"+i;
     			    option = '<option value="'+i+':00">'+i+':00</option><option value="'+i+':30">'+i+':30</option>';
     			}else {
     			    option = '<option value="'+i+':00">'+i+':00</option><option value="'+i+':30">'+i+':30</option>';
     			}
     			$(option).appendTo($("#endTime"));
     		}
     		
     		$.ajax({
     				url : '<%=path%>/ajax/activity/view.v',
					data:'activity.id=${activity.id}',
					type:'post',
					dataType:'json',
     				success : function(activity){
     					$("#title").val(activity.title=='null'?'':activity.title);
     					$("#place").val(activity.place=='null'?'':activity.place);
     					$("#contents").val(activity.contents=='null'?'':activity.contents);
     					$("#activityId").val(activity.id=='null'?'':activity.id);
     					var st = activity.startTime;
     					var sa = st.split(" ");
     					var startDate = sa[0];
     					var startTime = sa[1].replace(":00.0","");
     					$("#startDate").val(startDate);
     					$("#startTime").find("option[value='"+startTime+"']").attr("selected","selected");
     					var et = activity.endTime;
     					var ea = et.split(" ");
     					var endDate = ea[0];
     					var endTime = ea[1].replace(":00.0","");
     					$("#endDate").val(endDate);
     					$("#endTime").find("option[value='"+endTime+"']").attr("selected","selected");
     				}
     		});
     		
     		
     		$("div.toSave").click(function(){
     			var startTime = $("#startDate").val()+" "+$("#startTime").val()+":00";
     			var endTime = $("#endDate").val()+" "+$("#endTime").val()+":00";
     			var title = $("#title").val();
     			var place = $("#place").val();
     			var content = $("#contents").val();
     			var id = $("#activityId").val();
     			$.ajax({
     			   	url : '<%=path%>/ajax/activity/update.v',
					data:'activity.id='+id+'&activity.startTime='+startTime+'&activity.endTime='+endTime+'&activity.title='+title+'&activity.contents='+content+'&place='+place,
					type:'post',
					dataType:'json',
     				success : function(result){
     					window.parent.mainFrame.location='<%=path%>/system/activity/load.v?loadDate=${loadDate}';
     				}
     			});
     		});
     		$("div.toCancel").click(function(){
     			window.parent.mainFrame.location='<%=path%>/system/activity/load.v?loadDate=${loadDate}';
     		});
     		$("a.backToCalender").click(function(){
     			window.parent.mainFrame.location='<%=path%>/system/activity/load.v?loadDate=${loadDate}';
     		});
     	});
     </script>
  </body>
</html>