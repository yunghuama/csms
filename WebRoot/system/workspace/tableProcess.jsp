<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>流程提醒列表</title>
    <link href="<%=path%>/css/core.css" rel="stylesheet" type="text/css"/>
    <style>
      #main {
        overflow: auto;
        width: 100%;
        height: 100%;
        text-indent: 8px;
        color: #666;
      }
      #list{
        
      }
      #list tr{
        height: 24px;
      }
    </style>
  </head>
  <body>
    <div id="main">
      <table id="list" width="100%" border="0">
        <s:if test="evectionPlaTodoCount>0">
          <tr><td>★ 待审批出差计划: <a href="#" id="ccsp"><s:property value="evectionPlaTodoCount"/>条</a></td></tr>
        </s:if>
        <s:if test="solutionTodoCount>0">
          <tr><td>★ 待审批技术方案： <a href="#" id="fasp"><s:property value="solutionTodoCount"/>条</a></td></tr>
        </s:if>
        <s:if test="quotationTodoCount>0">
          <tr><td>★ 待审批报价方案： <a href="#" id="bjsp"><s:property value="quotationTodoCount"/>条</a></td></tr>
        </s:if>
        <s:if test="tenderTodoCount>0">
          <tr><td>★ 待审批投标计划： <a href="#" id="tbsp"><s:property value="tenderTodoCount"/>条</a></td></tr>
        </s:if>
        <s:if test="contractTodoCount>0">
          <tr><td>★ 待审批合同： <a href="#" id="htsp"><s:property value="contractTodoCount"/>条</a></td></tr>
        </s:if>
        <s:if test="evectionPlaTodoCount==0&&solutionTodoCount==0&&quotationTodoCount==0&&tenderTodoCount==0&&contractTodoCount==0">
          <tr><td>暂无流程提醒</td></tr>
        </s:if>
      </table>
    </div>
    <script src="<%=path%>/js/core.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(document).ready(function(){
      //$('tr:odd td').css('background-color', '#EEE');
      $('td').css('border-bottom', '1px solid #EEE');
      $('#ccsp').bind('click', function(){
       // var targetFrame = top.document.getElementById('ccsp');
       // alert(targetFrame)
       // $('#ccsp', $(targetFrame)).click();
        top.tabpanel.addTab({
          id: 'evectionExamine',
          title: '出差审批',
          html: '<iframe src="' + projectName + '/sale/evection/listEvectionExamine.v" id="evectionExamineFrame" name="evectionExamineFrame" width="100%" height="100%" frameborder="0"></iframe>',
          position: {
            a: '0px -132px',
            b: '-22px -132px'
          },
          closable: true
        });
      });
      $('#fasp').bind('click', function(){
        top.tabpanel.addTab({
          id: 'solutionExamine',
          title: '方案审批',
          html: '<iframe src="' + projectName + '/sale/proposal/listProposalExamine.v" id="solutionExamineFrame" name="solutionExamineFrame" width="100%" height="100%" frameborder="0"></iframe>',
          position: {
            a: '0px -132px',
            b: '-22px -132px'
          },
          closable: true
        });
      });
      $('#bjsp').bind('click', function(){
        top.tabpanel.addTab({
          id: 'quotationExamine',
          title: '报价审批',
          html: '<iframe src="' + projectName + '/sale/quotation/listQuotationExamine.v" id="quotationExamineFrame" name="quotationExamineFrame" width="100%" height="100%" frameborder="0"></iframe>',
          position: {
            a: '0px -132px',
            b: '-22px -132px'
          },
          closable: true
        });
      });
      $('#faap').bind('click', function(){
        top.tabpanel.addTab({
          id: 'solutionAssign',
          title: '安排人员',
          html: '<iframe src="' + projectName + '/sale/proposal/assignSolution.v" id="solutionAssignFrame" name="solutionAssignFrame" width="100%" height="100%" frameborder="0"></iframe>',
          position: {
            a: '0px -132px',
            b: '-22px -132px'
          },
          closable: true
        });
      });
      $('#fazz').bind('click', function(){
        top.tabpanel.addTab({
          id: 'solutionMake',
          title: '方案制作',
          html: '<iframe src="' + projectName + '/sale/proposal/listProposalMake.v" id="solutionMakeFrame" name="solutionMakeFrame" width="100%" height="100%" frameborder="0"></iframe>',
          position: {
            a: '0px -132px',
            b: '-22px -132px'
          },
          closable: true
        });
      });
      $('#tbsp').bind('click', function(){
        top.tabpanel.addTab({
          id: 'tenderExamine',
          title: '投标审批',
          html: '<iframe src="' + projectName + '/sale/tender/listTenderExamine.v" id="tenderExamineFrame" name="tenderExamineFrame" width="100%" height="100%" frameborder="0"></iframe>',
          position: {
            a: '0px -132px',
            b: '-22px -132px'
          },
          closable: true
        });
      });
      $('#htsp').bind('click', function(){
        top.tabpanel.addTab({
          id: 'contractExamine',
          title: '合同审批',
          html: '<iframe src="' + projectName + '/sale/contract/listContractExamine.v" id="contractExamineFrame" name="contractExamineFrame" width="100%" height="100%" frameborder="0"></iframe>',
          position: {
            a: '0px -132px',
            b: '-22px -132px'
          },
          closable: true
        });
      });
      parent.removeLoadMsg();
    });
    </script>
  </body>
</html>