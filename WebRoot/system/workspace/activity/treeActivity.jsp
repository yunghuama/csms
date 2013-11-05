<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>日志左</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/xToolbar.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/blog.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <div id="toolbar"></div>
    <div class="treePanel">
      <div class="paddingPanel">
        <div id="datePicker"></div>
        <div class="quick-search">
          <ul>
            <li><a href="javascript:void(0);" onclick="changeUrl('<%=path%>/daily/blog/search.v?searchType=and&searchValue[4]=<s:property value="#session['LoginBean'].user.id"/>');" style="background-image:url('<%=path%>/image/Blog/all.gif');">我的日志</a></li>
            <li><a href="javascript:void(0);" onclick="changeUrl('<%=path%>/daily/blog/search.v');" style="background-image:url('<%=path%>/image/Blog/7day.gif');">最近7天</a></li>
          </ul>
        </div>
      </div>
    </div>
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
    <script src="<%=path%>/js/share.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xToolbar.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xToolbar.items.js" type="text/javascript"></script>
    <script src="<%=path%>/js/datePicker/WdatePicker.js" type="text/javascript"></script>
    <script type="text/javascript">
    function changeUrl(url) {
      parent.mainFrame.location = url;
    }
    
    $(function(){
      WdatePicker({eCont:'datePicker',onpicked:function(dp){
        parent.mainFrame.location = '<%=path%>/system/activity/load.v?loadDate='+dp.cal.getDateStr();
      },
      firstDayOfWeek:0
      });
      
      new Toolbar({
        id: '1',
        renderTo : 'toolbar',
        icon: '../../image/op.gif',
        items : [{
          type:'button',
          text:'新建活动',
          useable : '<s:property value="@com.vwinwork.util.Meta@getOperate(\"blogtags_new\")"/>',
          position: {
            a: '0px 0px',
            b: '0px -120px'
          },
          handler:function(){
            top.blogTags.add();
          }
        },'-',{
          type : 'button',
          text : '刷新',
          position: {
            a: '-60px 0px',
            b: '-60px -120px'
          },
          handler : function() {
            window.location.reload();
          }
        }]
      });
      
      setTreeHeight(['toolbar']);
      
    });
    </script>
  </body>
</html>