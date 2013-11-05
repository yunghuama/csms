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
    <link href="<%=path%>/css/xToolbar.css" rel="stylesheet" type="text/css"/>
    <style>
    #toSelected{
      margin-left: 30px;
    }
    .sb {
      float: left;
      border: 1px solid #A6B2CA;
      margin: 20px 4px;
      width:130px;
    }
    .sc {
      overflow-x: hidden;
      overflow-y: auto;
      height:250px;
      width:130px;
    }
    .select-item {
      padding: 2px 4px;
      cursor: pointer;
    }
    
    .select-item-select {
      background-color: #CFDAE9;
    }
    #option {
      float: left;
      padding: 100px 4px;
    }
    #allcuts {
      overflow: hidden;
      white-space: nowrap;
      background: #EFEFEF url(../../image/Toolbar/tb_bg.png) repeat-x;
      border-bottom: 1px solid #a6b2ca;
      width: 100%;
      padding: 2px 0;
      height: 22px;
      color: #4477aa;
      font-weight: bolder;
      line-height: 22px;
      text-indent: 2px;
    }
    .leftright {
      margin: 16px 0;
      width: 38px;
      height: 21px;
      cursor: pointer;
      background:  url(../../image/desktop/leftright.gif) repeat-x;
    }
    .right{
      background-position: 0 0;
    }
    .left {
      background-position: 0 -21px;
    }
    </style>
  </head>
  <body>
    <div id="toSelected" class="sb">
      <div id="allcuts">可用快捷方式</div>
      <div class="sc">
      <s:iterator id="shortcut" value="allShortcutList">
        <div class="select-item">
          <s:property value="#shortcut.name"/>
          <input type="hidden" name="idList" value="<s:property value="#shortcut.id"/>"/>
        </div>
      </s:iterator>
      </div>
    </div>
    
    <div id="option">
      <div class="leftright right"></div>
      <div class="leftright left"></div>
    </div>
   
    <form name="aForm" id="aForm" action="<%=path%>/workspace/shotcut/customize.v">
      <div id="hasSelected" class="sb">
        <div id="toolbar"></div>
        <div class="sc">
          <s:iterator id="shortcut" value="shortcutList">
            <div class="select-item">
              <s:property value="#shortcut.name"/>
              <input type="hidden" name="idList" value="<s:property value="#shortcut.id"/>"/>
            </div>
          </s:iterator>
        </div>
      </div>
      <s:hidden name="tabId"/>
      <s:hidden name="windowPanelId"/>
    </form>
  
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xToolbar.js" type="text/javascript"></script>
    <script src="<%=path%>/js/xToolbar.items.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(document).ready(function(){
      //单击选中，双击左右切换
      $('.select-item').bind({
        click : function(){
          $('.select-item-select').removeClass('select-item-select');
          $(this).addClass('select-item-select');
        },
        dblclick: function(){
          var tp = $(this).closest('div.sc').prev().attr('id');
          if(tp==='allcuts'){
            $(this).clone(true).appendTo( $('#hasSelected > div.sc') );
          }else if(tp==='toolbar'){
            $(this).clone(true).appendTo( $('#toSelected > div.sc') );
          }
          $(this).remove();
        }
      });
      
      //左右切换按钮
      $('#option div.right').bind('click', function(){
        var toRight = $('#toSelected .select-item-select');
        if(toRight && toRight.size()){
          toRight.clone(true).appendTo( $('#hasSelected > div.sc') );
          toRight.remove();
        }else{
          alert('请先从左侧选中待增加的快捷方式！')
        }
      });
      $('#option div.left').bind('click', function(){
        var toLeft = $('#hasSelected .select-item-select');
        if(toLeft && toLeft.size()){
          toLeft.clone(true).appendTo( $('#toSelected > div.sc') );
          toLeft.remove();
        }else{
          alert('请先从右侧选中待删除的快捷方式！')
        }
      });
     
      new Toolbar({
        renderTo : 'toolbar',
        icon: '../../image/op.gif',
        items : [{
          type : 'button',
          tip : '置顶',
          position: {
            a: '-140px -80px',
            b: '-80px -200px'
          },
          handler : function(){
            var s = $('#hasSelected .select-item-select');
            if(s && s.size()){
              s.clone(true).prependTo($('#hasSelected > div.sc'));
              s.remove();
            }else{
              alert('请先选中待排序的快捷方式！')
            }
          }
        },{
          type : 'button',
          tip : '向上',
          position: {
            a: '-80px -80px',
            b: '-80px -200px'
          },
          handler : function(){
            var s = $('#hasSelected .select-item-select');
            var p = s.prev();
            if(s && s.size()){
              if(p && p.size()){
                s.clone(true).insertBefore(p);
                s.remove();
              }
            }else{
              alert('请先选中待排序的快捷方式！')
            }
          }
        },{
          type : 'button',
          tip : '向下',
          position: {
            a: '-100px -80px',
            b: '-100px -200px'
          },
          handler : function(){
            var s = $('#hasSelected .select-item-select');
            var n = s.next();
            if(s && s.size()){
              if(n && n.size()){
                s.clone(true).insertAfter(n);
                s.remove();
              }
            }else{
              alert('请先选中待排序的快捷方式！')
            }
          }
        },{
          type : 'button',
          tip : '置底',
          position: {
            a: '-120px -80px',
            b: '-120px -200px'
          },
          handler : function(){
            var s = $('#hasSelected .select-item-select');
            if(s && s.size()){
              s.clone(true).appendTo($('#hasSelected > div.sc'));
              s.remove();
            }else{
              alert('请先选中待排序的快捷方式！')
            }
          }
        }]
      });
      
    });
    </script>
  </body>
</html>