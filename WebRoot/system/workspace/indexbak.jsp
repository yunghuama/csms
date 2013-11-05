<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>工作台</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/desktop.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/xTabPanel.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/js/calendar/Calendar.css"/>
  </head>
  <body>
    <s:include value="/share/btMask.jsp" />
    <form id="listForm" action="<%=path%>/showDeskTop.v" method="post"> 
    <div id="desk_left">
      <div class="shortcuts">
        <div id="shortcutsMgr">管理快捷方式</div>
        <div style="margin-bottom: 16px;"></div>
        <div id="test"><s:property value="shorcutHtml"/></div>
        
      </div>
    </div>
    <div id="desk_center">
      <div id="news_div">
        <div class="header">
          <span class="headerword">通&nbsp;&nbsp;知&nbsp;&nbsp;通&nbsp;&nbsp;告</span>
          <span class="more" onclick="javascript:top.notice.viewAll();">更多...</span>
        </div>
        <div class="content">
          <table border="0" width="100%">
            <s:iterator id="notice" value="noticePage.list" status="i">
              <tr class="newsTr1">
                <td style="width:75px"><span title="<s:date name="#notice.createTime" format="yyyy-MM-dd HH:mm:ss"/>">[<s:date name="#notice.createTime" format="yyyy-MM-dd"/>]</span></td>
                <td><span class="newstitle" onclick="javascript:top.notice.openViewNoticeWindow('<s:property value="#notice.id"/>');"><s:property value="#notice.title"/></span></td>
                <td style="width:55px;" align="right"><span><s:property value="#notice.type.name"/>&nbsp;</span></td>
              </tr>
            </s:iterator>
          </table>
        </div>
      </div>
      <div id="tabs_div">
        <div class="title_bg">
          <div id="tabs1" class="label">流程提醒</div>
          <div id="tabs2" class="label">办公文档</div>
          <div id="tabs3" class="label">常用下载</div>
          <img id="tabRefresh" style="float:right;margin:2px;cursor:pointer;" src="<%=path%>/system/workspace/reflush.gif"/>
        </div>
        <div id="tabs_body">
          <div id="loadMsg" style="width:100%;height:100%;background-color:#FFF">数据加载中...</div>
          <iframe id="tabFrame" src="" width="100%" height="100%" frameborder="0"></iframe>
        </div>
      </div>
    </div>
    <div id="desk_right">
      <div id="calendar" style="margin:16px 4px 0px 8px;"></div>
      <div id="todoList_div">
      	<table width="100%" cellpadding="0" cellspacing="0">
      	<tr><td class="todoList_title todoList_title_over" id="sb1">昨日计划</td><td style="border-bottom: 1px solid #DFDFDF">&nbsp;</td><td class="todoList_title" id="sb2">今日活动</td></tr>
      	</table>
      	<div id="todoList_yesterday"></div>
      	<div id="todoList_today"></div>
        <div id="query">
          <div class="header">常用查询</div>
          <table width="100%" cellpadding="0" cellspacing="0">
            <tr>
              <td><div class="item" id="q_air" ><div class="item-icon air"></div>航班查询</div></td>
              <td><div class="item" id="q_train"><div class="item-icon train"></div>列车时刻查询</div></td>
              <td><div class="item" id="q_baiduMap"><div class="item-icon map"></div>百度地图</div></td>
            </tr>
            <tr>
              <td><div class="item" id="q_cellphone"><div class="item-icon cellphone"></div>手机归属地查询</div></td>
              <td><div class="item" id="q_bus"><div class="item-icon bus"></div>公交查询</div></td>
              <td><div class="item" id="q_delivery"><div class="item-icon delivery"></div>快递查询</div></td>
            </tr>
          </table>
        </div>
      </div>
    </div>
    </form>
	<script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
    <script src="<%=path%>/js/share.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xTabPanel.js" type="text/javascript"></script>
    <script src="<%=path%>/js/datePicker/WdatePicker.js" type="text/javascript"></script>
    <script type="text/javascript" src="js/calendar/Calendar.js"></script>
    <script type="text/javascript">
    $(document).ready(function(){
    
      //设置div高度
      $('#desk_left').height($(document.body).height());
      $('#news_div').height(270);
      $('#tabs_div').height($(document.body).height()-270);
      $('#tabs_body').height($('#tabs_div').height()-$('.title_bg').height()-10);
      $('#todoList_div').height($(document.body).height()-250);
      $('#todoList_yesterday').height($('#todoList_div').height()-133);
      $('#todoList_today').height($('#todoList_div').height()-133);
      
      //左侧快捷方式的鼠标移入移出效果
      $('.shortcut_style').hover(
        function(){
          var str=$(this).attr("src");
          var pos = str.lastIndexOf(".");
          var lastname = str.substring(pos,str.length);
          var filename = str.substring(0,pos);
          $(this).attr("src",filename+"_"+lastname);
        },
        function(){
          var str=$(this).attr("src");
          str=str.replace("_","");
          $(this).attr("src",str);
        }
      );
      
      //管理快捷方式
      $('#shortcutsMgr').bind('click', function(){
        top.ShortCuts.management();
      });
      
      //流程提醒、办公文档、其它项目
      $('#tabs1').bind('click', function(){
        $(this).addClass('label_selected');
        $('#tabs2,#tabs3').removeClass('label_selected');
      }).click();
      $('#tabs2').bind('click', function(){
        $(this).addClass('label_selected');
        $('#tabs1,#tabs3').removeClass('label_selected');
      });
      $('#tabs3').bind('click', function(){
        $(this).addClass('label_selected');
        $('#tabs1,#tabs2').removeClass('label_selected');
      });
      $('#tabRefresh').bind('click', function(){
        $('#tabFrame').attr('src', $('#tabFrame').attr('src'));
      });
      
      //昨日计划、今日活动
      $('#sb1').bind('mouseover', function(){
        $(this).addClass('todoList_title_over');
        $('#sb2').removeClass('todoList_title_over');
        $('#todoList_today').hide();
        $('#todoList_yesterday').show();
      }).mouseover();
      $('#sb2').bind('mouseover', function(){
        $(this).addClass('todoList_title_over');
        $('#sb1').removeClass('todoList_title_over');
         $('#todoList_today').show();
        $('#todoList_yesterday').hide();
      });
      
      //常用查询
      var queryFn = function(id,title,url){
        top.tabpanel.addTab({
          id: id,
          title: title,
          html: '<iframe src="'+url+'"></iframe>',
          position: {
            a: '0px -66px',
            b: '-22px -66px'
          },
          closable: true
        });
      }
      $('#q_air').bind('click', function(){queryFn('queryAir', '航班查询', 'http://flight.qunar.com/');});
      $('#q_train').bind('click', function(){queryFn('queryTrain', '列车时刻查询', 'http://huoche.kuxun.cn/index.html');});
      $('#q_baiduMap').bind('click', function(){queryFn('queryMap', '百度地图', 'http://map.baidu.com/');});
      $('#q_cellphone').bind('click', function(){queryFn('queryCellphone', '手机归属地查询', 'http://guishu.showji.com/search.htm');});
      $('#q_bus').bind('click', function(){queryFn('queryBus', '公交查询', 'http://bus.mapbar.com');});
      $('#q_delivery').bind('click', function(){queryFn('queryDelivery', '快递查询', 'http://www.hao123.com/haoserver/kuaidi.htm');});
      loadReady();
    });
    
    //显示日历
    WdatePicker({eCont:'calendar_div', onpicked:function(dp){
      //alert('你选择的日期是:'+dp.cal.getDateStr())
      //显示农历
      var date = dp.cal.getDateStr();
      new LunarCalender({
        loadDate : date,
        renderTo : 'lunar_div'
      });
      //显示当天的活动
      new ActivityLoader({
        loadDate : date,
        renderTo : 'todoList_today'
      });
    }});

    //日历
    new Calendar({
      renderTo:'calendar',
      cellBorder:true,
      height: 250,
      handler : function(calendar){
        var date = calendar.year+"-"+calendar.month+"-"+calendar.day;
        new ActivityLoader({
          loadDate : date,
          renderTo : 'todoList_today',
          loadPath : '<%=path%>/ajax/activity/loadByMonth.v'
        });
      }
    });

    new ActivityLoader({
      loadDate : '',
      renderTo : 'todoList_today',
      loadPath : '<%=path%>/ajax/activity/loadByDay.v'
    });

    //昨日计划
    $.ajax({
      url:'<%=path%>/ajax/workLog/nextPlan.v',
        type:'post',
        dataType:'json',
        success:function(data){
        if(data){
          $("#todoList_yesterday").html(data);
        }
      }
    });
    
    
    function removeLoadMsg(){
      $('#loadMsg').remove();
    }
    </script>
  </body>
</html>