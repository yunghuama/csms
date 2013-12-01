<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
if(session.getAttribute("LoginBean")==null) {
  response.sendRedirect(path+"/login.jsp");
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>集团名片</title>
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/index.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/message.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/autocomplete.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/xTabPanel.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/xWindowPanel.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/xToolbar.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/form.css" rel="stylesheet" type="text/css"/>
  </head>
  <body>
    <!-- 消息提醒 -->
    <div id="vMsgDiv">
      
      <div id="vMsgFoot">
        <div id="vMsgAll">全 部</div>
        <div id="vMsgClose">&nbsp;</div>
      </div>
      <div id="vMsgArrow"></div>
      <div id="vMsgShadow"></div>
    </div>
    <!-- END 消息提醒 END -->
    
    <s:include value="/share/load.jsp">
      <s:param name="msg" value="'数据加载中...'"/>
    </s:include>
    <div class="header">
      <div class="logo"></div>
      <div class="info">&nbsp;&nbsp;&nbsp;&nbsp;欢迎您：<s:property value="#session['LoginBean'].user.accountName"/> | <a href="javascript:void(0);openChangePassword();">修改密码</a> | <a href="#" style="cursor:help;">帮助</a> | <a href="javascript:void(0);window.location = '<%=path%>/logout.v';">注销</a></div>
    </div>

    <s:set name="tString" value="@com.platform.constants.StringConstant@TRUE"/>
    <!--  
    <ul class="module">

    <s:if test='@com.platform.util.Meta@getOperate(\"card_view1\")=="T"'>
	<li class="od">集团名片
        <ul class="module-second">
            <s:if test='@com.platform.util.Meta@getOperate(\"cardcontentverify_view\")=="T"'>
            <li id="card_content_verify" class="second"><a href="javascript:void(0)">名片审核</a></li>
            </s:if>
            <s:if test='@com.platform.util.Meta@getOperate(\"cardcontent_view\")=="T"'>
            <li id="card_content" class="second"><a href="javascript:void(0)">名片管理</a></li>
            </s:if>
            <s:if test='@com.platform.util.Meta@getOperate(\"cardrule_view\")=="T"'>
            <li id="card_rule" class="second"><a href="javascript:void(0)">策略管理</a></li>
            </s:if>
            <s:if test='@com.platform.util.Meta@getOperate(\"cardgroup_view\")=="T"'>
            <li id="card_group" class="second"><a href="javascript:void(0)">组群管理</a></li>
            </s:if>
            <s:if test='@com.platform.util.Meta@getOperate(\"cardnumber_view\")=="T"'>
            <li id="card_number" class="second"><a href="javascript:void(0)">号码管理</a></li>
            </s:if>
            <s:if test='@com.platform.util.Meta@getOperate(\"cardstatement_view\")=="T"'>
            <li id="card_statement" class="second"><a href="javascript:void(0)">统计报表</a></li>
            </s:if>
         </ul>
      </li>
   </s:if>

      <s:if test='@com.platform.util.Meta@getOperate(\"company_view1\")=="T"'>
      <li class="od">集团账户
        <ul class="module-second">
            <s:if test='@com.platform.util.Meta@getOperate(\"comnumber_view\")=="T"'>
            <li id="com_number" class="second"><a href="javascript:void(0)">号码管理</a></li>
            </s:if>
            <s:if test='@com.platform.util.Meta@getOperate(\"comstatement_view\")=="T"'>
            <li id="com_statement" class="second"><a href="javascript:void(0)">统计报表</a></li>
            </s:if>
         </ul>
      </li>
      </s:if>

       <s:if test='@com.platform.util.Meta@getOperate(\"sys_view1\")=="T"'>
      <li class="od">系统管理
        <ul class="module-second">
        	<s:if test='@com.platform.util.Meta@getOperate(\"dictionary_view\")=="T"'>
            <li id="dic" class="second"><a href="javascript:void(0)">字典管理</a></li>
            </s:if>
            <s:if test='@com.platform.util.Meta@getOperate(\"users_view\")=="T"'>
            <li id="user" class="second"><a href="javascript:void(0)">员工管理</a></li>
            </s:if>
            <s:if test='@com.platform.util.Meta@getOperate(\"department_view\")=="T"'>
            <li id="dept" class="second"><a href="javascript:void(0)">部门管理</a></li>
            </s:if>
            <s:if test='@com.platform.util.Meta@getOperate(\"role_view\")=="T"'>
            <li id="ro" class="second"><a href="javascript:void(0)">角色管理</a></li>
            </s:if>
            <s:if test='@com.platform.util.Meta@getOperate(\"syslog_view\")=="T"'>
            <li id="sys_log" class="second"><a href="javascript:void(0)">系统日志</a></li>
            </s:if>
            <s:if test='@com.platform.util.Meta@getOperate(\"syswarn_view\")=="T"'>
            <li id="sys_warn" class="second"><a href="javascript:void(0)">系统报警</a></li>
            </s:if>
         </ul>
      </li>
       </s:if>
    </ul>
    -->
    <div id="main" class="main"></div>
    <div class="footer">Copyright&nbsp;&copy;&nbsp;Cloudatum</div>
    
  <script src="<%=path%>/js/core.js" type="text/javascript"></script>
  <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
  <script src="<%=path%>/js/index.js" type="text/javascript"></script>
  <script src="<%=path%>/js/share.js" type="text/javascript"></script>
  <script src="<%=path%>/js/xTabPanel.js" type="text/javascript"></script>
  <script src="<%=path%>/js/Draggable.js" type="text/javascript"></script>
  <script src="<%=path%>/js/xWindowPanel.js" type="text/javascript"></script>
  <script src="<%=path%>/js/xToolbar.js" type="text/javascript"></script>
  <script src="<%=path%>/js/xToolbar.items.js" type="text/javascript"></script>
  <script src="<%=path%>/js/modules/system.js" type="text/javascript"></script>
  <script src="<%=path%>/js/modules/content.js" type="text/javascript"></script>
  <script src="<%=path%>/js/modules/rule.js" type="text/javascript"></script>
  <script src="<%=path%>/js/modules/group.js" type="text/javascript"></script>
  <script src="<%=path%>/js/modules/number.js" type="text/javascript"></script>
  <script src="<%=path%>/js/modules/district.js" type="text/javascript"></script>
  <script src="<%=path%>/js/modules/enterprise.js" type="text/javascript"></script>
  <script src="<%=path%>/js/xDateFormat.js" type="text/javascript"></script>
  <script type="text/javascript">
    $(document).ready(function(){
      $('#vMsgClose').bind('click', function(){
        $('#vMsgDiv').slideUp(300);
      });
      $('#noteView').bind('click', function(){
        $('#vMsgDiv').slideToggle(300);
      });

      
      tabpanel = new TabPanel({
    	    id: 'jTabPanel',
    	    renderTo: 'main',
    	    border: false,
    	    autoResize: true,
    	    defaultTab: 0,
    	    icon: 'image/module_icon.gif',
    	    items: [{
    	      id: 'workspace',
    	      title: '',
    	      closable: false,
    	      position: {
    	        a: '0px -110px',
    	        b: '-22px -110px'
    	      }
    	    }]
    	  });
      
      var eventArray = [];
      //--------------------------------------------[系统管理]
      if("<s:property value='@com.platform.util.Meta@getOperate(\"syswarn_view\")'/>"=="T"){
          eventArray.push({
        	    trigger: 'sys_warn',
        	    id: 'syswarn',
        	    title: '系统报警',
        	    html: '<iframe id="syswarnFrame" name="syswarnFrame" src="' + projectName + '/csms/warn/listPagination.v" width="100%" height="100%" frameborder="0"></iframe>',
        	    position: {
        	      a: '0px -66px',
        	      b: '-22px -66px'
        	    }
          });
          }
      if("<s:property value='@com.platform.util.Meta@getOperate(\"dictionary_view\")'/>"=="T"){
      eventArray.push({
        trigger: 'dic',
        id: 'dictionary',
        title: '字典管理',
        html: '<iframe src="' + projectName + '/system/dictionary/list.v" id="dictionaryFrame" name="dictionaryFrame" width="100%" height="100%" frameborder="0"></iframe>',
        position: {
          a: '0px -132px',
          b: '-22px -132px'
        }
      });
      }
      if("<s:property value='@com.platform.util.Meta@getOperate(\"users_view\")'/>"=="T"){
      eventArray.push({
        trigger: 'user',
        id: 'users',
        title: '账号管理',
        html: '<iframe id="usersFrame" name="usersFrame" src="' + projectName + '/system/user/listUser.jsp" width="100%" height="100%" frameborder="0"></iframe>',
        position: {
          a: '0px 0px',
          b: '-22px 0px'
        }
      });
      }
      if("F"=="T"){
      eventArray.push({
        trigger: 'dept',
        id: 'department',
        title: '部门管理',
        html: '<iframe id="departmentFrame" name="departmentFrame" src="' + projectName + '/system/department/listDepartment.jsp" width="100%" height="100%" frameborder="0"></iframe>',
        position: {
          a: '0px -66px',
          b: '-22px -66px'
        }
      });
      }
      if("F"=="T"){
      eventArray.push({
        trigger: 'ro',
        id: 'role',
        title: '角色管理',
        html: '<iframe id="roleFrame" name="roleFrame" src="' + projectName + '/system/role/list.v" width="100%" height="100%" frameborder="0"></iframe>',
        position: {
          a: '0px -154px',
          b: '-22px -154px'
        }
      });
      }
      if("<s:property value='@com.platform.util.Meta@getOperate(\"cardcontentverify_view\")'/>"=="T"){
      eventArray.push({
    	    trigger: 'card_content_verify',
    	    id: 'contentVerify',
    	    title: '名片审核',
    	    html: '<iframe id="contentVerifyFrame" name="contentVerifyFrame" src="' + projectName + '/csms/content/listPaginationA.v" width="100%" height="100%" frameborder="0"></iframe>',
    	    position: {
    	      a: '0px -66px',
    	      b: '-22px -66px'
    	    }
      });
      }
      if("<s:property value='@com.platform.util.Meta@getOperate(\"cardcontent_view\")'/>"=="T"){
      eventArray.push({
        trigger: 'card_content',
        id: 'content',
        title: '内容设置',
        html: '<iframe id="contentFrame" name="contentFrame" src="' + projectName + '/csms/content/list.v" width="100%" height="100%" frameborder="0"></iframe>',
        position: {
          a: '0px -66px',
          b: '-22px -66px'
        }
     });
      }
      if("<s:property value='@com.platform.util.Meta@getOperate(\"cardrule_view\")'/>"=="T"){
      eventArray.push({
        trigger: 'card_rule',
        id: 'rule',
        title: '计划任务',
        html: '<iframe id="ruleFrame" name="ruleFrame" src="' + projectName + '/csms/rule/listPagination.v" width="100%" height="100%" frameborder="0"></iframe>',
        position: {
          a: '0px -66px',
          b: '-22px -66px'
        }
     });
      }
      
      if("<s:property value='@com.platform.util.Meta@getOperate(\"cardgroup_view1\")'/>"=="T"){ 
     eventArray.push({
    	    trigger: 'card_group',
    	    id: 'group',
    	    title: '部门管理',
    	    html: '<iframe id="groupFrame" name="groupFrame" src="' + projectName + '/csms/group/listPagination.v" width="100%" height="100%" frameborder="0"></iframe>',
    	    position: {
    	      a: '0px -66px',
    	      b: '-22px -66px'
    	    }
      });
      }
      if("<s:property value='@com.platform.util.Meta@getOperate(\"cardnumber_view\")'/>"=="T"){
     eventArray.push({
    	    trigger: 'card_number',
    	    id: 'number',
    	    title: '号码管理',
    	    html: '<iframe id="numberFrame" name="numberFrame" src="' + projectName + '/csms/number/listA.v" width="100%" height="100%" frameborder="0"></iframe>',
    	    position: {
    	      a: '0px -66px',
    	      b: '-22px -66px'
    	    }
    });
      }
      
      if("<s:property value='@com.platform.util.Meta@getOperate(\"comnumber_view\")'/>"=="T"){
     eventArray.push({
    	    trigger: 'com_number',
    	    id: 'comnumber',
    	    title: '号码管理',
    	    html: '<iframe id="comnumberFrame" name="comnumberFrame" src="' + projectName + '/csms/number/list.v" width="100%" height="100%" frameborder="0"></iframe>',
    	    position: {
    	      a: '0px -66px',
    	      b: '-22px -66px'
    	    }
    });
      }
      
      
      if("<s:property value='@com.platform.util.Meta@getOperate(\"district_view\")'/>"=="T"){
    	     eventArray.push({
    	    	    trigger: 'com_district',
    	    	    id: 'district',
    	    	    title: '区域管理',
    	    	    html: '<iframe id="districtFrame" name="districtFrame" src="' + projectName + '/csms/district/list.v" width="100%" height="100%" frameborder="0"></iframe>',
    	    	    position: {
    	    	      a: '0px -66px',
    	    	      b: '-22px -66px'
    	    	    }
    	    });
    	      }
      
      if("<s:property value='@com.platform.util.Meta@getOperate(\"enterprise_view\")'/>"=="T"){
    	     eventArray.push({
    	    	    trigger: 'com_enterprise',
    	    	    id: 'enterprise',
    	    	    title: '企业管理',
    	    	    html: '<iframe id="enterpriseFrame" name="enterpriseFrame" src="' + projectName + '/csms/enterprise/list.v" width="100%" height="100%" frameborder="0"></iframe>',
    	    	    position: {
    	    	      a: '0px -66px',
    	    	      b: '-22px -66px'
    	    	    }
    	    });
    	      }
      
     
      if("<s:property value='@com.platform.util.Meta@getOperate(\"comstatement_view\")'/>"=="T"){
     eventArray.push({
    	    trigger: 'com_statement',
    	    id: 'comstatement',
    	    title: '统计报表',
    	    html: '<iframe id="comstatementFrame" name="comstatementFrame" src="' + projectName + '/companycard/report/tableReport.jsp" width="100%" height="100%" frameborder="0"></iframe>',
    	    position: {
    	      a: '0px -66px',
    	      b: '-22px -66px'
    	    }
    });
      }
     /** for (var i = 0; i < eventArray.length; i++) {
        $('#' + eventArray[i].trigger).click(function(index){
          return function(){
            showLoadMask(eventArray[index].id);
            tabpanel.addTab({
              id: eventArray[index].id,
              title: eventArray[index].title,
              icon: eventArray[index].icon,
              html: eventArray[index].html,
              position: eventArray[index].position,
              closable: true
            });
          }
        }(i));
      }
      **/
      for (var i = 0; i < eventArray.length; i++) {
    	        tabpanel.addTab({
    	          id: eventArray[i].id,
    	          title: eventArray[i].title,
    	          icon: null,
    	          html: eventArray[i].html,
    	          position: eventArray[i].position,
    	          closable: false
    	        });
    	  }
    });
    </script>
  </body>
</html>