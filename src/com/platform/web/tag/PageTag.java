package com.platform.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class PageTag extends TagSupport {

    private static final long serialVersionUID = 8513603752224408719L;

    // 当前页
    private int currPage;
    // 最后一页
    private int maxPage;
    // 每页显示数量
    private int pageSize;
    // 请求路径
    private String url;

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut(); // 获取输出对象

        String prev = ""; // 上一页
        String next = ""; // 下一页
        String first = "";
        String last = "";
        StringBuffer pageString = new StringBuffer();
        // 判断最后一页
        if (currPage >= maxPage) {
            next = "-disabled\" disabled=\"true";
            last = "-disabled\" disabled=\"true";
        }
        // 判断第一页
        if (currPage == 1) {
            prev = "-disabled\" disabled=\"true";
            first = "-disabled\" disabled=\"true";
        }

        try {
            // 分页布局的显示
            pageString.append("<div class=\"pagination\">");
            pageString.append("<table><tr>");
            pageString.append("<td>");
            pageString.append("<div class=\"first" + first + "\" title=\"首页\"" + (!first.equals("") ? "onclick=\"return false;\"" : "onclick=\"pagination(1," + pageSize + ",this);return false;\"") + "></div>");
            pageString.append("</td><td>");
            pageString.append("<div class=\"prev" + prev + "\" title=\"上一页\"" + (!prev.equals("") ? "onclick=\"return false;\"" : "onclick=\"pagination(" + (currPage == 1 ? 1 : currPage - 1) + "," + pageSize + ",this);return false;\"") + "></div>");
            // select组件快速翻页
            if (currPage <= maxPage) {
                pageString.append("</td><td>");
                pageString.append("<span class=\"spacer\"></span>");
                pageString.append("</td><td>");
                pageString.append("<select onchange=\"pagination(this.value," + pageSize + ",this);return false;\">");
                for (int i = 1; i <= maxPage; i++) {
                    if (i == currPage)
                        pageString.append("<option value=\"" + i + "\" selected>第" + i + "页</option>");
                    else
                        pageString.append("<option value=\"" + i + "\">第" + i + "页</option>");
                }
                pageString.append("</select>");
            }
            pageString.append("</td><td>");
            pageString.append("<span class=\"spacer\"></span>");
            pageString.append("</td><td>");
            pageString.append("<div class=\"next" + next + "\" title=\"下一页\"" + (!next.equals("") ? "onclick=\"return false;\"" : "onclick=\"pagination(" + (currPage + 1) + "," + pageSize + ",this);return false;\"") + "></div>");
            pageString.append("</td><td>");
            pageString.append("<div class=\"last" + last + "\" title=\"尾页\"" + (!last.equals("") ? "onclick=\"return false;\"" : "onclick=\"pagination(" + maxPage + "," + pageSize + ",this);return false;\"") + "></div>");
            // PageSize组件
            pageString.append("</td><td>");
            pageString.append("<span class=\"spacer\"></span>");
            pageString.append("</td><td>");
            pageString.append("<div class=\"s20" + (pageSize == 20 ? "-active" : "") + "\" title=\"每页显示20条\" onclick=\"pagination(1,20,this);return false;\"></div>");
            pageString.append("</td><td>");
            pageString.append("<div class=\"s50" + (pageSize == 50 ? "-active" : "") + "\" title=\"每页显示50条\" onclick=\"pagination(1,50,this);return false;\"></div>");
            pageString.append("</td><td>");
            pageString.append("<div class=\"s100" + (pageSize == 100 ? "-active" : "") + "\" title=\"每页显示100条\" onclick=\"pagination(1,100,this);return false;\"></div>");
            pageString.append("</td><td>");
            pageString.append("<span class=\"spacer\"></span>");
            pageString.append("</td><td>");
            pageString.append("<div class=\"page-refresh\" title=\"刷新本页面\" onclick=\"$(this).parents('FORM').submit();\"></div>");
            pageString.append("</td><td>");
            pageString.append("</td><td class=\"pagesizeinfo\">");
            pageString.append("第" + currPage + "页&nbsp;/&nbsp;共" + maxPage + "页");
            pageString.append("</td>");
            pageString.append("</tr></table>");
            pageString.append("</div>");
            out.println(pageString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    @Override
    public void setPageContext(PageContext arg0) {
        super.setPageContext(arg0);
    }

    @Override
    public void release() {
        super.release();
    }

    @Override
    public void setParent(Tag arg0) {
        super.setParent(arg0);
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}