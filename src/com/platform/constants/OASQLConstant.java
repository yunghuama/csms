package com.platform.constants;

/**
 * <p>程序名称：       OASQLConstants.java</p>
 * <p>程序说明：       OA的SQL常量</p>
 * <p>版权信息：       Copyright 深圳市维远泰克科技有限公司</p>
 * <p>时间：          Feb 11, 2011 2:59:41 PM</p>  
 * 
 * @author：          Marker.King
 * @version：         Ver 0.1
 */
public class OASQLConstant {
    public static final String BLOG_BY_DATE = "from Blog where forDate = ? ";
    public static final String BLOG_SCOPE_LIST = "from Blog where 1=1 ";
    public static final String BLOG_7_DAY = "from Blog where (forDate between ? and ?) ";
    
    public static final String BLOGTAGS_BY_CREATOR = "from BlogTags where creator = ?";
    
    //客户服务分页
    public static final String CUSSERVICE_PAGE_HQL = "from CusService where deleteFlag = 0 and serviceCategoryFlag = ? ";
    public static final String CUSSERVICE_BY_COMPANY = "from CusService where deleteFlag = 0 and serviceCategoryFlag = ? and company.id=?";
    public static final String CUSSERVICE_BY_LINKMAN = "from CusService where deleteFlag = 0 and serviceCategoryFlag = ? and linkman.id=?";
    
    //工作日志及批注
    public static final String WORKLOG_DEPT_H_HQL = "from WorkLog w where w.deleteFlag=0";
    public static final String WORKLOG_ALL_H_HQL = "from WorkLog w where w.deleteFlag=0";
    public static final String WORKLOGNOTE_BLOG_H_HQL = "from WorkLogNote note where note.blog.id=?";
    
    //产品管理
    public static final String PRODUCTSORT_TREE_HQL = "from ProductSort";
    public static final String PRODUCTSORT_PAGE_HQL = "from ProductSort where 1=1";
    public static final String PRODUCTSORT_CHILD_HQL = "from ProductSort where fatherId = ?";
    public static final String PRODUCTSORT_EXCEPT_SELF = "from ProductSort where id != ? and fatherId != ?";
    public static final String PRODUCT_CHILD_HQL = "from Product where deleteFlag = 0 and sortId = ?";
    public static final String PRODUCT_PAGE_HQL = "from Product where deleteFlag = 0";
    
    //项目信息表
    public static final String PROJECT_PAGE_HQL = "from ProjectItems where deleteFlag = 0 and state = ?";
    public static final String PROJECT_PAGE_HQL_NOVIRABLES = "from ProjectItems where deleteFlag = 0";
    
    //机会管理
    public static final String OPPORTUNITY_PAGE_HQL = "from Opportunity where deleteFlag = 0";
    public static final String OPPORTUNITY_BY_COMPANY = "from Opportunity where deleteFlag = 0 and company.id=?";
    public static final String OPPORTUNITY_BY_LINKMAN = "from Opportunity where deleteFlag = 0 and linkman.id=?";
    public static final String OPPPRODUCT_BY_OPPORTUNITY = "from OppProduct op where op.opportunity.id = ?";
    
    //客户管理
    public static final String COMPANY_PAGE_HQL = "from Company where 1=1";
    public static final String COMPANY_PAGE_HQL2 = "from Company where deleteFlag = 0 and companyTypeId=?";
    
    //联系人管理
    public static final String LINKMAN_PAGE_HQL = "from Linkman where deleteFlag = 0";
    public static final String LINKMAN_BY_COMPANY = "from Linkman where deleteFlag = 0 and company.id=?";
    
    //联系情况管理
    public static final String LINKRECORD_PAGE_HQL = "from LinkRecord where deleteFlag = 0";
    public static final String LINKRECORD_BY_COMPANY = "from LinkRecord where deleteFlag = 0 and company.id=?";
    public static final String LINKRECORD_BY_LINKMAN = "from LinkRecord where deleteFlag = 0 and linkman.id=?";
    //出差报告管理
    public static final String EVECTIONREPORT_PAGE_HQL = "from LinkRecord where deleteFlag = 0 and dictionaryByLinkThingTypeId.id=?";
    
    //工作空间
    //活动管理
    public static final String ACTIVITY_ONEWEEK_HQL = "from Activity where startTime>=? and endTime <=?";
    public static final String ACTIVITY_FROMTODAY_HQL = "from Activity a where  a.startTime>=? and a.startTime <=? and a.owner.id =?";
    
    //合同订单
    public static final String ORDERS_PAGE_HQL = "from Orders where deleteFlag = 0";
    public static final String ORDERS_BY_COMPANY = "from Orders where deleteFlag = 0 and company.id=?";
    public static final String ORDERS_BY_OPPORTUNITY = "from Orders where deleteFlag = 0 and opportunity.id=?";
    
    //发货管理
    public static final String INVOICE_PAGE_HQL = "from Invoice where 1 = 1";
    public static final String INVOICE_BY_COMPANY = "from Invoice where 1 = 1 and company.id=?";
    public static final String INVOICE_BY_ORDERS = "from Invoice where 1 = 1 and order.id=?";
    
    //收款管理
    public static final String BILL_PAGE_HQL = "from Bill where deleteFlag = 0";
    public static final String BILL_BY_COMPANY = "from Bill where deleteFlag = 0 and company.id=?";
    public static final String BILL_BY_ORDERS = "from Bill where deleteFlag = 0 and order.id=?";
    
    //报价管理
    public static final String QUOTE_PAGE_HQL = "from Quote where deleteFlag = 0";
    public static final String QUOTE_BY_COMPANY = "from Quote where deleteFlag = 0 and company.id=?";
    public static final String QUOTE_BY_LINKMAN = "from Quote where deleteFlag = 0 and linkman.id=?";
    public static final String QUOTE_BY_OPPORTUNITY = "from Quote where deleteFlag = 0 and opportunity.id=?";
    
    //客户信息
    public static final String VISITPLAN_PAGE_HQL = "from VisitPlan where deleteFlag = 0";
    public static final String VISITPLAN_BY_COMPANY = "from VisitPlan where deleteFlag = 0 and company.id=?";
    public static final String CUSFOLLOW_PAGE_HQL = "from CusFollow where 1=1";
    public static final String CUSFOLLOW_BY_COMPANY = "from CusFollow where 1=1 and visitPlan.company.id=?";
    public static final String CUSFOLLOW_BY_VISITPLAN = "from CusFollow where 1=1 and visitPlan.id=?";
    
    //通知公告
    public static final String NOTICE_PAGE_HQL = "from Notice where 1=1";
    public static final String NOTICE_PAGE_INDEX = "from Notice where displayFlag='1'";
    
    //任务单
    public static final String TASKORDER_PAGE_HQL = "from TaskOrder where 1=1";
    public static final String TASKORDER_PAGE_HQL_VARIABLE = "from TaskOrder where 1=1 and state = ?";
    
    //办公文档
    public static final String DOCUMENTS_PAGE_HQL = "from Documents where 1=1";
    public static final String DOCUMENTS_CHILD_HQL = "from Documents where sort.id = ?";
    public static final String DOCUMENTS_PAGE_INDEX = "from Documents where displayFlag='1'";
    public static final String DOCUMENTSSORT_TREE_HQL = "from DocumentsSort";
    public static final String DOCUMENTSSORT_PAGE_HQL = "from DocumentsSort where 1=1";
    public static final String DOCUMENTSSORT_CHILD_HQL = "from DocumentsSort where fatherId = ?";
    //public static final String DOCUMENTSSORT_EXCEPT_SELF = "from DocumentsSort where id != ? and fatherId != ?";
    
    //会议纪要
    public static final String MEETINGMINUTES_PAGE_HQL = "from MeetingMinutes mm where mm.deleteFlag='0'";
    
    //会议纪要批注
    public static final String MEETINGCOMMENTS_BY_MEETINGMINUTES_HQL = "from MeetingComments mc where mc.meetingMinutes.id = ?";
    
    //编号规则
    public static final String CODERULE_HQL = "from CodeRule where prefix=? and currDate=?";
    
    //出差计划
    public static final String EVECTIONPROJECT_BY_PLANID_HQL = "from EvectionProject where evectionPlan.id=?";

}
