<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>内部通讯列表</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/pagination.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/list.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/xToolbar.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/flagManager.css" rel="stylesheet" type="text/css"/>
    <link href="<%=path%>/css/jquery-impromptu.css" rel="stylesheet" type="text/css"/>
    <style>
    #leftFrame {
      width: 200px;
      height: 100%;
      float: left;
      background-color: #dde4ec;
      border-right: 1px solid #9CB8CC;
      overflow: visible;
    }
    #rightFrame {
      float: left;
    }
    #writeMsg {
      height: 30px;
      width: 130px;
      border: 1px solid #3b79b6;
      font-size: 14px;
      color: #069;
      text-align: center;
      line-height: 30px;
      font-weight: bolder;
      background: url('<%=path%>/system/intercom/zwritebtbg.gif');
      cursor: pointer;
    }
    #left-top {
      padding: 6px 30px;
      height: 30px;
      border-right: 1px solid #1f5699;
      border-bottom: 2px solid #19418A;
      margin-bottom: 16px;
      background: url('<%=path%>/system/intercom/zwritebg.gif');
    }
    .left-item {
      padding: 6px 24px;
      color: #069;
      font-family: '宋体';
      cursor: pointer;
    }
    .left-item-selected {
      background-color: #FFF;
      border-bottom: 1px solid #9CB8CC;
      border-top: 1px solid #9CB8CC;
      width: 201px;
    }
    </style>
  </head>
  <body>
    <s:include value="/share/btMask.jsp" />
    
    <div id="leftFrame">
      <div id="left-top">
        <div id="writeMsg"><img src="<%=path%>/system/intercom/zwrite.gif"/>&nbsp;写信</div>
      </div>
      <div id="left-bottom">
        <div id="receiveMsg" class="left-item"><img src="<%=path%>/system/intercom/zreceive.gif"/>&nbsp;收件箱</div>
        <div id="sendMsg" class="left-item"><img src="<%=path%>/system/intercom/zsend.gif"/>&nbsp;已发送</div>
        <div id="deleteMsg" class="left-item"><img src="<%=path%>/system/intercom/zdelete.gif"/>&nbsp;已删除</div>
      </div>
    </div>
    
    <div id="rightFrame">
      <div id="toolbar"></div>
      <form id="listForm" action="<%=path%>/system/intercom/listPagination.v" method="post">
        <s:hidden name="searchType"/>
        <div class="search-div">
          <div class="search-condition">
            <table class="search-table" cellspacing="0" cellpadding="0">
              <tr>
                <td class="c-left">标题：</td>
                <td colspan="3"><input name="searchValue" type="text" class="text" value="<s:property value="searchValue[0]"/>"/></td>
              </tr>
              <tr>
                <td class="c-left">发送时间：</td>
                <td><input name="searchValue" id="minD" type="text" class="text Wdate" value="<s:property value="searchValue[1]"/>" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'maxD\')}'});"/></td>
                <td class="c-left">到：</td>
                <td><input name="searchValue" id="maxD" type="text" class="text Wdate" value="<s:property value="searchValue[2]"/>" onclick="WdatePicker({minDate:'#F{$dp.$D(\'minD\')}'});"/></td>
              </tr>
            </table>
          </div>
          <div class="search-commit">
            <a href="javascript:void(0)" class="search-button" onclick="$('#searchType').val('and');$('#listForm').submit();">与查询</a>
            <a href="javascript:void(0)" class="search-button" onclick="$('#searchType').val('or');$('#listForm').submit();">或查询</a>
          </div>
        </div>
        <table id="titleTable" cellpadding="0" cellspacing="0">
          <tr>
            <td>&nbsp;</td>
            <td><input type="checkbox" id="allBox"/></td>
            <td>&nbsp;</td>
            <td>发送人</td>
            <td id="titleSort">标题</td>
            <td id="timeSort">发送时间</td>
          </tr>
        </table>
        <table id="dataTable" cellpadding="0" cellspacing="0">
          <s:iterator id="intercom" value="page.list" status="i">
            <tr class="row" id="<s:property value="#intercom.id"/>">
              <td class="num"><s:property value="#i.index+1"/></td>
              <td class="box"><input type="checkbox" name="idList" value="<s:property value="#intercom.id"/>"/></td>
              <td align="center">
                <s:if test="#intercom.readFlag.equals(\"F\") && #intercom.type.equals(\"0\")">
                  <img title="未读" src="<%=path%>/system/intercom/ztype0.gif"/>
                </s:if>
                <s:elseif test="#intercom.type.equals(\"1\")">
                  <img title="审批提醒" src="<%=path%>/system/intercom/ztype1.gif"/>
                </s:elseif>
                <s:elseif test="#intercom.type.equals(\"2\")">
                  <img title="日志批注" src="<%=path%>/system/intercom/ztype2.gif"/>
                </s:elseif>
                <s:else>
                  &nbsp;
                </s:else>
              </td>
              <td align="center">
                <span>
                  <s:if test="#intercom.readFlag == \"F\"">
                    <b><s:property value="#intercom.sender.realName"/></b>
                  </s:if>
                  <s:else>
                    <s:property value="#intercom.sender.realName"/>
                  </s:else>
                </span>
              </td>
              <td class="color_<s:property value="#intercom.flagType"/>">
                <span>
                  <s:if test="#intercom.readFlag == \"F\"">
                    <b>&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="#intercom.title"/></b>
                  </s:if>
                  <s:else>
                    &nbsp;&nbsp;&nbsp;&nbsp;<s:property value="#intercom.title"/>
                  </s:else>
                </span>
              </td>
              <td align="center" title="<s:date name="#intercom.sendTime" format="yyyy年MM月dd日 HH:mm(E)"/>">
                <span>
                  <s:if test="#intercom.readFlag == \"F\"">
                    <b><s:date name="#intercom.sendTime" format="yyyy-MM-dd"/></b>
                  </s:if>
                  <s:else>
                    <s:date name="#intercom.sendTime" format="yyyy-MM-dd"/>
                  </s:else>
                </span>
              </td>
            </tr>
          </s:iterator>
        </table>
        <s:hidden name="type"/>
        <s:include value="/share/pagebar.jsp"/>
      </form>
    </div>
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
    <script src="<%=path%>/js/share.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script src="<%=path%>/js/Grid.js" type="text/javascript"></script>
    <script src="<%=path%>/js/BoxSelect.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xToolbar.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xToolbar.items.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery-impromptu.js" type="text/javascript"></script>
    <script src="<%=path%>/js/modules/flagManager.js" type="text/javascript"></script>
    <script src="<%=path%>/js/datePicker/WdatePicker.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(document).ready(function(){

    	var flagManager =  new FlagManager({
             success:function(color,style){
    						$("#dataTable").find(":checkbox:checked").each(function(){
														var id = $(this).val();
														var type = style.replace("color_","");
														$.ajax({
															url : '<%=path %>/system/ajax/updateFlag.v',
															data : {'intercomId':id,'type':type},
															type:'POST',
															success : function(){
																		$("#"+id).find("td").eq(4).attr("class","").addClass(style);		
																}
															});
        				});
             	
             }
         });
      var type = '<s:property value="type"/>';
      $('#'+type+'Msg').addClass('left-item-selected');
      
      $('#rightFrame').width($('body').width()-201);
      $('div.left-item').bind('click', function(){
        $('div.left-item-selected').removeClass('left-item-selected');
        $(this).addClass('left-item-selected');
      });
      
      $('#receiveMsg').bind('click', function(){filterIntercom('receive')});
      $('#sendMsg').bind('click', function(){filterIntercom('send')});
      $('#deleteMsg').bind('click', function(){filterIntercom('delete')});
      $('#writeMsg').bind('click', function(){ top.intercomFunctions.openSaveIntercomWindow('')});
      var toolbarBtn = [];
      toolbarBtn.push({
        type : 'button',
        text : '转发',
        useable : '<s:property value="@com.vwinwork.util.Meta@getOperate(\"intercom_forward\")"/>',
        position: {
          a: '-180px -20px',
          b: '-180px -140px'
        },
        handler : function(){
          if(getFirstID())
            top.intercomFunctions.openSaveIntercomWindow(getFirstID());
        }
      });
      toolbarBtn.push('-');
      if(type==='receive'){
        toolbarBtn.push({
          type : 'button',
          text : '删除',
          useable : '<s:property value="@com.vwinwork.util.Meta@getOperate(\"intercom_delete\")"/>',
          position: {
            a: '-180px 0px',
            b: '-180px -120px'
          },
          handler : function(){del({deleteURL : '<%=path%>/system/intercom/delete.v?flagDelete=flag'});}
        });
        toolbarBtn.push('-');
      }
      if(type==='delete'){
        toolbarBtn.push({
          type : 'button',
          text : '还原',
          useable : '<s:property value="@com.vwinwork.util.Meta@getOperate(\"intercom_delete\")"/>',
          position: {
            a: '-60px -240px',
            b: '-60px -260px'
          },
          handler : function(){del({deleteURL : '<%=path%>/system/intercom/updateReadFlag.v'});}
        });
        toolbarBtn.push('-');
      }
      toolbarBtn.push({
        type : 'button',
        text : '彻底删除',
        useable : '<s:property value="@com.vwinwork.util.Meta@getOperate(\"intercom_delete\")"/>',
        position: {
          a: '-180px 0px',
          b: '-180px -120px'
        },
        handler : function(){ del({deleteURL : '<%=path%>/system/intercom/delete.v'});}
      });
      toolbarBtn.push('-');
      toolbarBtn.push({
        type : 'button',
        text : '查看',
        useable : '<s:property value="@com.vwinwork.util.Meta@getOperate(\"intercom_view\")"/>',
        position: {
          a: '-180px -60px',
          b: '-180px -180px'
        },
        handler : function(){
          if(getFirstID()) {
            readFlagHTML(getFirstID());
          }
        }
      });
      toolbarBtn.push('-');
      toolbarBtn.push({type: 'search'});
      toolbarBtn.push('-');
      toolbarBtn.push({
          type : 'button',
          text : '标记',
          useable : '<s:property value="@com.vwinwork.util.Meta@getOperate(\"intercom_view\")"/>',
          position: {
            a: '-180px -60px',
            b: '-180px -180px'
          },
          handler : function(){
        	  flagManager.toggle();
          }
        });
      var toolbar = new Toolbar({
        renderTo : 'toolbar',
        icon: '../../image/op.gif',
        items : toolbarBtn
      });
      
      new Grid({
        titleTable:'titleTable',
        dataTable:'dataTable',
        widths : [26,24,32,80,500,90,60],
        autoTip: false,
        height : function(){return getGridHeight({toolbarId:'toolbar',hasPage:true});},
        dbView : function(id){readFlagHTML(id);}
      });
      
      new BoxSelect({
        allId : 'allBox',
        boxName : 'idList'
      });
      
      loadReady();
    });
    
    //
    function filterIntercom(type) {
      window.location = '<%=path%>/system/intercom/listPagination.v?type='+type;
    }
    
    function updateReadFlag() {
      if(getFirstID()){
        document.getElementById('listForm').action = '<%=path%>/system/intercom/updateReadFlag.v';
        document.getElementById('listForm').submit();
      }
    }
    
    function updateAllReadFlag() {
      document.getElementById('listForm').action = '<%=path%>/system/intercom/updateAllReadFlag.v';
      document.getElementById('listForm').submit();
    }
    
    function viewIntercom(intercomId, forwardable) {
      top.intercomFunctions.openViewIntercomWindow(intercomId, forwardable);
      top.showNoteCount();
    }
    
    function readFlagHTML(id){
      //将未读标记为已读
      var b = $('b', $('#'+id));
      if(b.size()){
        $.each(b, function(){
          $(this).closest('span').html($(this).html());
        });
        $('img[title=未读]', $('#'+id)).remove();
        $.ajax({
          async : false,
          url : '<%=path%>/system/ajax/updateIntercomToRead.v',
          data : 'intercomId='+id,
          success : function(json){
            $('#read_'+id).html(json);
          }
        });
      }
      viewIntercom(id, '<s:property value="@com.vwinwork.util.Meta@getOperate(\"intercom_forward\")"/>');
    }
    </script>
  </body>
</html>