package com.platform.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * <p>程序名称：       OAStringConstants.java</p>
 * <p>程序说明：       OA常量</p>
 * <p>版权信息：       Copyright 深圳市维远泰克科技有限公司</p>
 * <p>时间：          Feb 11, 2011 2:59:16 PM</p>	
 * 
 * @author：          Marker.King
 * @version：         Ver 0.1
 */
@SuppressWarnings("serial")
public class OAStringConstant {
	
    /*==========================功能菜单id开始==============================*/
	public static final String BLOG_ID = "402881c92e12ba52012e12bbb3af0002";//日志
	public static final String WORKBLOG_ID = "402881cf2fddecd6012fde12dfc30001";//工作日志
	public static final String ORDERS_ID = "402881cf2fddecd6012fde16cfa4000e";//合同订单
	public static final String CUSSERVICE_ID = "402881cf2fddecd6012fde17913b0011";//客户服务
	public static final String CARE_ID = "402881cf2fddecd6012fde17da270013";//客户关怀
	public static final String COMPLAINTS_ID = "402881cf2fddecd6012fde17c3630012";//客户投诉
	public static final String CUSFOLLOW_ID = "402881cf2fddecd6012fde18f3960019";//客户跟进
	public static final String VISITPLAN_ID = "402881cf2fddecd6012fde191874001a";//拜访计划
	public static final String PRODUCT_ID = "402881cf2fddecd6012fde19af62001c";//产品信息
	public static final String PRODUCTSORT_ID = "402881cf2fddecd6012fde199713001b";//产品分类
	public static final String OPPORTUNITY_ID = "402881cf2fddecd6012fde160659000c";//机会管理
	public static final String NOTICE_ID = "40288137306e10f401306e718b960001";//通知公告
	public static final String DOCUMENTSSORT_ID = "40288137324273610132427603690001";//办公文档类别
	public static final String DOCUMENTS_ID = "402881373242736101324276277b0002";//办公文档
	public static final String COMPANY_ID = "402881cf2fddecd6012fde14cdc40006";//客户管理
	public static final String LINKMAN_ID = "402881cf2fddecd6012fde151c150008";//联系人管理
	public static final String LINKRECORD_ID = "402881cf2fddecd6012fde1539760009";//联系情况管理
	public static final String SUPPLIERS_ID = "402881cf2fddecd6012fde14e8fa0007";//供应商管理
	public static final String QUOTE_ID = "402881cf2fddecd6012fde1636b3000d";//报价管理
	public static final String INVOICE_ID = "402881cf2fddecd6012fde170697000f";//发货管理
	public static final String BILL_ID = "402881cf2fddecd6012fde1722080010";//收款管理
	public static final String MEETINGMINUTES_ID = "402881373358f6fa01335911748e0001";//会议纪要
	public static final String EVECTIONPLAN_ID = "f9a5ab6e341b7a4801341b7d0dfb0002";//出差计划
	public static final String EVECTIONEXAMINE_ID = "f9a5ab6e341b7a4801341b7d36440003";//出差审批
	public static final String EVECTIONREPORT_ID = "f9a5ab6e341b7a4801341b7d50730004";//出差报告
	public static final String PROJECTITEMS_ID = "f9a5ab6e3487cd80013487d1bdb90001";//项目信息表(深圳服务器)
	public static final String SOLUTIONEXAMINE_ID = "402881d834c6b6a20134c6b99cda0003";//方案审批
	public static final String SOLUTIONASSIGN_ID = "402881d834c6b6a20134c6ba11ea0005";//安排人员
    public static final String SOLUTIONMAKE_ID = "402881d834c6b6a20134c6b9fa2c0004";//方案制作
    public static final String SOLUTIONAPPLY_ID = "402881d834c6b6a20134c6b985890002";//方案申请
    
    public static final String SEARCHMANAGE_ID = "4028813734e417f20134e43165c60001";//系统管理--搜索管理
    public static final String WORKFLOW_ENGINE_ID = "4028813734e417f20134e43becd70004";//工作流管理--引擎管理
    public static final String WORKFLOW_OPERATION_ID = "4028813734e417f20134e43c86970005";//工作流管理--作业管理
    public static final String WORKFLOW_DEPLOY_ID = "4028813734e417f20134e43cbd280006";//工作流管理--部署管理
    public static final String WORKFLOW_PROCESS_ID = "4028813734e417f20134e43cd5720007";//工作流管理--流程管理
    public static final String WORKFLOW_DATABASE_ID = "4028813734e417f20134e43cf33b0008";//工作流管理--数据库管理
    
    public static final String QUOTATIONAPPLY_ID = "402881373532736e01353286cc8d0002";//报价申请
    public static final String QUOTATIONEXAMINE_ID = "402881373532736e01353286f2c20003";//报价审批
    public static final String QUOTATIONASSIGN_ID = "402881373532736e013532871b1a0004";//安排人员
    public static final String QUOTATIONMAKE_ID = "402881373532736e01353287414f0005";//报价制作
    public static final String QUOTATIONFIXEDPRICE_ID = "402881373532736e013532876e1c0006";//报价定价
    
    public static final String TENDERAPPLY_ID = "402881373583b467013583b9eb8a0002";//投标申请
    public static final String TENDEREXAMINE_ID = "402881373583b467013583ba0c8f0003";//投标审批
    public static final String TENDERASSIGN_ID = "402881373583b467013583ba4b3e0004";//安排人员（技术）
    public static final String TENDERASSIGN_BUSS_ID = "4028813735854ece01358552ae2e0001";//安排人员（商务）
    public static final String TENDERMAKE_ID = "402881373583b467013583ba688a0005";//投标制作
    
    public static final String CONTRACTMAKE_ID = "4028813735efd3140135efdb9e300001";//合同制作
    public static final String CONTRACTEXAMINE_ID = "4028813735efd3140135efdc1f760002";//合同审批
    
    
    public static final String TASKORDERASSIGN_ID = "40288137362e9c1d01362ea2b49d0001";//任务单指派
    
	/*==========================功能菜单id结束==============================*/
	
	/*==========================字典项id开始==============================*/
	public static final String DICTIONARY_ID_LINKRECORD_TYPE = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx29";//联系情况分类
	/*==========================字典项id结束==============================*/
    
	public static final List<String> colorList = new ArrayList<String>();
	
	public static final String BILL_INVOICEFLAG_YES = "是";
	public static final String BILL_INVOICEFLAG_NO = "否";
	
    public static final Integer WORKSPACE_SHOW_NUM = 7;
	
	/**
	 * 办公文档superId
	 */
	public static final String DOCUMENTS_SUPERID = "documentsXXXXXXXXXXXXXXXXXXXXX01";
	static {
		colorList.add("#729C3B");
		colorList.add("#58A8B4");
		colorList.add("#5883BF");
		colorList.add("#6D72BA");
		colorList.add("#E3A325");
		colorList.add("#DA8A22");
		colorList.add("#B34731");
		colorList.add("#BB4C91");
		colorList.add("#995AAE");
		colorList.add("#CC0000");
		colorList.add("#FCD468");
		colorList.add("#FF9966");
		colorList.add("#CC99CC");
		colorList.add("#CC9999");
		colorList.add("#AD855C");
		colorList.add("#CCCC99");
		colorList.add("#FF6633");
		colorList.add("#CC6666");
		colorList.add("#AD33AD");
		colorList.add("#855C85");
		colorList.add("#99CC66");
		colorList.add("#66CCCC");
		colorList.add("#3399FF");
		colorList.add("#2B8787");
		colorList.add("#855C85");
		colorList.add("#6699FF");
		colorList.add("#3385D6");
		colorList.add("#335CAD");
		colorList.add("#5F27B3");
		colorList.add("#262ED7");
		colorList.add("#D5D2C0");
		colorList.add("#B5BFCA");
		colorList.add("#999999");
		colorList.add("#666666");
		colorList.add("#333333");
	}
	/*==========================[START] 出差 ==============================*/
	//出差地点类别1
	public static final TreeMap<String, String> EVECTION_LOCATION_TYPE1 = new TreeMap<String, String>(){{
		put("1", "市内");
		put("2", "省内");
		put("3", "省外");
	}};
	//出差地点类别2
	public static final TreeMap<String, String> EVECTION_LOCATION_TYPE2 = new TreeMap<String, String>(){{
		put("1", "一类地区");
		put("2", "二类地区");
		put("3", "三类地区");
	}};
	//出差类别
	public static final TreeMap<String, String> EVECTION_TYPE = new TreeMap<String, String>(){{
	    put("1", "拜访客户");
		put("2", "展览");
		put("3", "培训");
		put("4", "招聘");
		put("5", "售后服务");
		put("6", "其它");
	}};
	//出差交通工具
	public static final TreeMap<String, String> EVECTION_VEHICLE = new TreeMap<String, String>(){{
		put("1", "飞机");
		put("2", "火车");
		put("3", "汽车");
		put("4", "轮船");
		put("5", "公交");
	}};
	/*==========================[E N D] 出差 ==============================*/
	/*==========================[START] 销售部门id-map ==============================*/
    //出差地点类别1
    public static final TreeMap<String, String> SALE_DEPT_MAP = new TreeMap<String, String>(){{
        put("4028813518f35feb0118f392eee50043", "4028813518f35feb0118f392eee50043");//济南销售部
        put("402881b21950b688011950ce2ea40001", "402881b21950b688011950ce2ea40001");//深圳销售部
    }};

    /*==========================[E N D] 出差 ==============================*/
    //业务对象与审批模块对应map
    public static final HashMap<String, String> MODULE_TABLE_MAP = new HashMap<String, String>(){{
    	put("EvectionPlan", EVECTIONEXAMINE_ID);//出差审批模块
    	put("Solution", SOLUTIONEXAMINE_ID);//方案审批
        put("Quotation", QUOTATIONEXAMINE_ID);//报价审批模块
        put("Tender", TENDEREXAMINE_ID);//投标审批
        put("Contract", CONTRACTEXAMINE_ID);//投标审批
    }};
    //任务单类型
    public static final TreeMap<String,String> TASKORDER_TYPE = new TreeMap<String,String>(){
    	{
    		put("0","周计划任务");
    		put("1","月计划任务");
    		put("2","临时交办任务");
    		put("3","总经理交办任务");
    	}
    };
    //任务单状态
    public static final TreeMap<String,String> TASKORDER_STATE = new TreeMap<String,String>(){
    	{
    		put("0","执行中");
    		put("1","已完成");
    		put("2","已延期");
    	}
    };
}
