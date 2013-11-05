package com.platform.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.platform.vo.Page;

/**
 * JSP分页辅助类
 * 
 * @author Marker.King
 * 
 */
public class JSPPageHelper {

    public static final String ALL_RESULT = "allResult";

    /**
     * 初始化集合
     * 
     * @param allResult
     */
    public static void init(List allResult) {
        ServletActionContext.getRequest().setAttribute(ALL_RESULT, allResult);
    }

    /**
     * 生成并计算分页
     * 
     * @param request
     */
    @SuppressWarnings("unchecked")
    public static void generatePage(HttpServletRequest request) {
        List allResult = (List) request.getAttribute(ALL_RESULT);
        int pageNo = 1;
        try {
            pageNo = Integer.parseInt(request.getParameter("page.currPage"));
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        int pageSize = 20;
        try {
            pageSize = Integer.parseInt(request.getParameter("page.pageSize"));
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        Page page = new Page();
        if (Validate.collectionNotNull(allResult)) {
            page.setAllResult(allResult);
            page.setCurrPage(pageNo);
            page.setRowCount(allResult.size());
            page.setPageSize(pageSize);
            page.setMaxPage(PageHelper.getMaxPage(allResult.size(), pageSize));
            page.setList(getList(allResult, pageNo, pageSize));
        }
        ServletActionContext.getRequest().setAttribute("page", page);
    }

    /**
     * 获得当前页集合
     * 
     * @param allResult
     * @param pageNo
     * @param pageSize
     * @return
     */
    @SuppressWarnings("unchecked")
    private static List getList(List allResult, int pageNo, int pageSize) {
        List list = new ArrayList();
        int firstRow = PageHelper.getFirstRow(pageNo, pageSize);
        int length = allResult.size();
        for (int i = 0; firstRow < length && i < pageSize; i++) {
            if (firstRow == length) {
                break;
            } else {
                list.add(allResult.get(firstRow));
                firstRow++;
            }
        }
        return list;
    }
}