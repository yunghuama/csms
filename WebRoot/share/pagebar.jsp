<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/vwin-tags.tld" prefix="p"%>
<%-- 当前页的隐藏变量 --%>
<input type="hidden" id="page_currPage" name="page.currPage" value="${requestScope.page.currPage}"/>
<input type="hidden" id="page_pageSize" name="page.pageSize" value="${requestScope.page.pageSize}"/>
<%-- 每页显示数量的隐藏变量 --%>
<p:pagination url="${requestScope.page.url}" currPage="${requestScope.page.currPage}" pageSize="${requestScope.page.pageSize}" maxPage="${requestScope.page.maxPage}"/>