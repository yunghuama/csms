package com.platform.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.platform.util.Validate;
import com.platform.vo.Page;

/**
 * 数据库常量
 * 
 * @author MarkerKing
 *
 */
public final class SQLConstant {
	private SQLConstant(){}
	
	public static final List<String> DEPARTMENT_DELETE_LIST = new ArrayList<String>();
	public static final List<String> ROLE_DELETE_LIST = new ArrayList<String>();
	public static final List<String> CSMSGROUP_DELETE_LIST = new ArrayList<String>();
	public static final List<String> CSMSRULE_DELETE_LIST = new ArrayList<String>();
	public static final List<String> CSMSNUMBER_DELETE_LIST = new ArrayList<String>();
	public static final String LIMIT = " limit ?,?";
	public static final String SELECT_ROWCOUNT_PREFIX = "select count(id) ";
	public static final String DEFAULT_USER_WHERE_CREATOR = " creator ";
	
	//字典修改或删除时同步更新的关联语句
	public static final Map<String, List<String>> DICT_UPDATE_MAP = new HashMap<String, List<String>>();

	//用户表
	public static final String USERS_LOGIN_SELECT_SQL = "select * from users where accountName = ? and password = ?";
	public static final String USERS_SELECT_EXITS_SQL = "select count(id) from users where accountName = ?";
	public static final String USERS_SELECT_ALL_SQL  = "select * from users where state = 'T' order by realName";
	public static final String USERS_LIKE_REALNAME_SQL = "select * from users where state = 'T' and (realname like ? or accountname like ?)";
	public static final String USERS_LIKE_REALNAME_AND_DEP_SQL = "select * from users where state = 'T' and (realname like ? or accountname like ?) and departmentid = ? order by realname";
	public static final String USERS_ALL_AND_DEP_SELECT_SQL = "select * from users where state = 'T' and departmentid = ? order by realName";
	public static final String USERS_GET_BY_ID = "select * from users where id = ?";
	public static final String USERS_NOSELF_ALL_SQL = "select * from users where id != ? and state = 'T'";
	public static final String USERS_SAVE_SQL = "insert into users(id,accountname,realname,password,sex,birthday,edu,state,departmentid,creator,createtime,bigimage,normalimage,smallimage,area,cellno,enterprise,role,remark) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	public static final String USERS_UPDATE_SQL = "update users set accountname = ?,realname = ?,password = ?,sex = ?,birthday = ?,edu = ?,state = ?,departmentid = ?,editor = ?,edittime = ?,bigimage = ?,normalimage = ?,smallimage = ?,area = ?,cellno = ?,enterprise = ?,role=?,remark = ? where id = ?";
	public static final String USERS_DEPT_SQL = "select u1.*,u2.departmentid as creatorDepartment,u2.realname as creatorName,d.name as departmentName from users u1 left join users u2 on u1.creator = u2.id left join department d on u1.departmentid = d.id where u1.departmentid = ? ";
	public static final String USERS_ALL_MORE_SELECT = "select u1.*,u2.departmentid as creatorDepartment,u2.realname as creatorName,d.name as departmentName from users u1 left join users u2 on u1.creator = u2.id left join department d on u1.departmentid = d.id where 1=1 ";
	public static final String USERS_ALL_SELECT_PARAMS = "select u.*,district.name as districtName,r.name as roleName,enterprise.name as enterpriseName from users u left join sms_district district on u.area = district.id left join sms_enterprise enterprise on u.enterprise = enterprise.id left join role r on u.role = r.id where 1=1";
	public static final String USERS_ALL_SELECT_PARAMS_DEPT = "select u.*,district.name as districtName,r.name as roleName,enterprise.name as enterpriseName from users u left join sms_district district on u.area = district.id left join sms_enterprise enterprise on u.enterprise = enterprise.id left join role r on u.role = r.id where district.id = ?";
	public static final String USERS_ALL_ROWCOUNT_SQL = "select count(u1.id) ";
	public static final String USERS_BY_DEPARTCODE_SQL = "select * from users u,department d where u.departmentid = d.id and d.code like ?";
	public static final String USERS_ALL_SELECT_SQL = "select * from users where 1 = 1";
	public static final String USERS_UPDATE_TO_DELETE = "update users set state = ? where id = ?";
	public static final String USERS_GET_BY_ENTERPRISE_ID = "select * from users where enterprise = ?";
	
	//部门表
	public static final String DEPARTMENT_SAVE_SQL = "insert into department(id,name,code,superid,isstore,remark,creator,createtime) values(?,?,?,?,?,?,?,?)";
	public static final String DEPARTMENT_UPDATE_SQL = "update department set name = ?,code = ?,superid = ?,isstore = ?,remark = ?, editor = ?,edittime = ? where id = ?";
	public static final String DEPARTMENT_SELECTALL_BY_ID = "select * from department where id = ? limit 0,1";
	public static final String DEPARTMENT_DELETE_BY_ID = "delete from department where id = ?";
	public static final String DEPARTMENT_CHILD_SQL = "select d.*,u.realname,u.departmentid as creatorDepartment from department d,users u where d.superid = ? and  d.creator = u.id ";
	public static final String DEPARTMENT_PAGE_SQL = "select d.*,u.realname,u.departmentid as creatorDepartment from department d,users u where d.creator = u.id ";
	public static final String DEPARTMENT_ROWCOUNT_SQL = "select count(d.id) ";
	public static final String DEPARTMENT_TREE_SQL = "select d.* from department d,users u where d.creator = u.id ";
	public static final String DEPARTMENT_CHILD_LIKE = "select * from Department where code like ?";
	public static final String DEPARTMENT_STORE_LIKE_SQL = "select * from department where code like ? and isStore = 'T' order by code";
	public static final String DEPARTMENT_NOT_LIKE_SQL = "select * from department where code not like ? order by code";
	public static final String DEPARTMENT_DELETE_BY_IDS = "delete from department where id in ";
	public static final String DEPARTMENT_ALL_LIKE_SQL = "select id from department where code like ?";
	
	//字典
	public static final String DICTIONARY_SELECT_NEXT_SORTNUM_SQL = "select max(sortnum) as nextnum from dictionary where superid = ?";
	public static final String DICTIONARY_SAVE_SQL = "insert into dictionary(id,name,superid,sortnum,remark,creator,createtime) values(?,?,?,?,?,?,?)";
	public static final String DICTIONARY_UPDATE_SQL = "update dictionary set name = ?,superid = ?,sortnum = ?, remark = ?, editor = ?,edittime = ? where id = ?";
	public static final String DICTIONARY_SELECT_BY_ID_SQL = "select * from dictionary where id = ?";
	public static final String DICTIONARY_DELETE_BY_ID_SQL = "delete from dictionary where id = ?";
	public static final String DICTIONARY_PAGE_SQL = "select d.*,u.realname,u.departmentid from dictionary d,users u where d.superid = ? and d.creator = u.id";
	public static final String DICTIONARY_SELECT_CHILD_SQL = "select * from dictionary where superid = ? order by sortnum";
	public static final String DICTIONARY_UPDATE_VALIDATE_NAME_SQL = "select count(id) from dictionary where id != ? and name = ? and superid = ?";
	public static final String DICTIONARY_VALIDATE_NAME_SQL = "select count(id) from dictionary where name = ? and superid = ?";
	public static final String DICTIONARY_ROWCOUNT_SQL = "select count(d.id) ";
	
	//内部通信表
	public static final String INTERCOM_SELECT_COUNTNOREAD_BY_OWNER_SQL = "select count(id) from intercom where owner = ? and readFlag = 'F'";
	public static final String INTERCOM_SELECT_COUNTNOREAD_BY_TYPE_WONER_SQL = "select count(id) from Intercom where type=? and owner = ? and readFlag = 'F'";
	public static final String INTERCOM_SELECT_BY_TITLE_SQL = "select * from intercom where title = ? redflag = 'F'";
	public static final String INTERCOM_SELECT_BY_ID = "select * from intercom where id = ?";
	public static final String INTERCOM_SAVE_SQL = "insert into intercom(id,title,contents,sender,replier,copier,readflag,sendtime,type,owner,inform,flagtype) values(?,?,?,?,?,?,?,?,?,?,?,?)";
	public static final String INTERCOM_UPDATE_SQL = "update intercom set readflag = ? where id = ?";
	public static final String INTERCOM_SEND_ALL_SQL = "select * from Intercom where sender = ? and owner = ? order by sendtime desc";
	public static final String INTERCOM_SELECT_OWNER_READFLAG_SQL = "select * from intercom where owner = ? and readflag = ? order by sendtime desc";
	public static final String INTERCOM_INFORM_SQL = "select * from intercom where sender = ?,inform = ?,readflag = 'T' order by sendTime desc";
	public static final String INTERCOM_DELETE_BY_ID_SQL = "delete from intercom where id = ?";
	public static final String INTERCOM_SELECT_ALL_NOREAD_SQL = "select * from intercom where readflag = 'F' order by sendTime desc";
	public static final String INTERCOM_SELECT_BY_READFLAG_OWNER_TYPE_SQL = "select * from intercom where readflag = ? and owner = ? and type = ? order by sendtime desc";
	
	//系统日志
	public static final String SYSLOG_SAVE_SQL = "insert into syslog(id,contents,optime,optor,ipadd,type) values(?,?,?,?,?,?)";
	public static final String SYSLOG_PAGE_SQL = "select s.*,u.realname,u.accountName from syslog s,users u where s.optor = u.id ";
	public static final String SYSLOG_BY_TYPE_PAGE_SQL = "select s.*,u.realname,u.accountName from syslog s,users u where type = ? and s.optor = u.id  order by opTime desc";
	public static final String SYSLOG_DELETE_BY_IDS = "delete from syslog where id in ";
	public static final String SYSLOG_DELETE_ALL = "delete from syslog";
	public static final String SYSLOG_ROWCOUNT_SQL = "select count(s.id) ";
	
	// 系统消息分页
	public static final String SYSMESUSER_BY_READFLAG_SQL = "select * from SysMesUser where readflag = ? and userid = ?";
	public static final String SYSMESUSER_PAGE_SQL = "select * from sysmesuser where userid = ?";
	public static final String SYSMESUSER_SELECT_BY_READFLAG_PAGE_SQL = "select * from sysmesuser where readflag = ? and userid = ?";
	public static final String SYSMESUSER_DELETE_BY_IDS = "delete from sysmesuser where id in ";
	public static final String SYSMESUSER_DELETE_BY_USER_SQL = "delete from sysmesuser where userid = ?";
	public static final String SYSMESUSER_SELECT_BY_ID = "select * from sysmesuser where id = ?";
	public static final String SYSMESUSER_SAVE_SQL = "insert into sysmesuser(id,userid,sysmesid,readflag) values(?,?,?,?)";
	public static final String SYSMESUSER_UPDATE_SQL = "update sysmesuser set userid = ?,sysmesid = ?,readflag = ? where id = ?";
	public static final String SYSMESUSER_SELECT_BY_USER_SQL = "select * from sysmesuser where userid = ?";

	//角色表
	public static final String ROLE_SELECT_BY_PAGE_SQL = "select r.*,u.realname,u.departmentid from role r,users u  where r.superid = ? and r.creator = u.id ";
	public static final String ROLE_ROWCOUNT_SQL = "select count(r.id) ";
	public static final String ROLE_SELECT_ALL_SQL = "select distinct id,name,remark,superid from role where 1=1";
	public static final String ROLE_VALIDATE_NAME = "select * from role where name = ?";
	public static final String ROLE_SELECT_BY_ID = "select * from role where id = ?";
	public static final String ROLE_SAVE_SQL = "insert into role(id,name,superid,remark,createtime,creator) values(?,?,?,?,?,?)";
	public static final String ROLE_UPDATE_SQL = "update role set name = ?,superid = ?,remark = ? ,edittime = ? ,editor = ? where id = ?";
	public static final String ROLE_DELETE_BY_ID = "delete from role where id = ?";
	public static final String ROLE_UPDATE_VALIDATE_NAME_SQL = "select count(id) from role where id != ? and name = ?";
	public static final String ROLE_VALIDATE_NAME_SQL = "select count(id) from role where name = ?";

	//附件
	public static final String ATTACHEDFILE_BY_SUPERID_SQL = "select * from attachedfile where superid = ?";
	public static final String ATTACHEDFILE_BY_SUPERID_AND_EXTENDTYPE_SQL = "select * from attachedfile where superid = ? and extendType = ?";
	public static final String ATTACHEDFILE_DELETE_BY_ID_SQL = "delete from attachedfile where id = ?";
	public static final String ATTACHEDFILE_SELECT_BY_ID_SQL = "select * from attachedfile where id = ?";
	public static final String ATTACHEDFILE_SAVE_SQL = "insert into attachedfile(id,title,filename,filepath,filetype,contenttype,superid,createtime,extendtype) values(?,?,?,?,?,?,?,?,?);";
	public static final String ATTACHEDFILE_UPDATE_SQL = "update attachedfile set title = ?,filename = ?,filepath = ?,filetype = ?,contenttype = ?,superid = ?,extendtype = ? where id = ?";
	public static final String ATTACHEDFILE_SELECTIDS_BY_SUPERID = "select id from attachedfile where superid = ?";
	public static final String ATTACHEDFILE_DELETE_BY_IDS = "delete from attachedfile where id in ";

	//模块表
	public static final String MODULE_SELECT_BY_SUPERID_SQL = "select * from vmeta_module where superid = ?";
	public static final String MODULE_SELECT_CHILD_SQL = "select * from vmeta_module where superId != ?";
	public static final String MODULE_SELECT_ALL_SQL = "select * from vmeta_module order by superid";
	public static final String MODULE_SELECT_BY_ID_SQL = "select * from vmeta_module where id = ?";
	
	//操作表
	public static final String OPERATE_SELECT_BY_MODULE_SQL = "select * from vmeta_operate where moduleid = ?";
	public static final String OPERATE_SELECT_BY_MODULE_VISIBLE_SQL = "select * from vmeta_operate where moduleid = ? and scopedatavisible = 'T'";
	public static final String OPERATE_SELECT_BY_ID_SQL = "select * from vmeta_operate where id = ?";
	public static final String OPERATE_DELETE_BY_IDS_SQL = "delete from vmeta_operate where id in ";		

	//字段表
	public static final String FIELD_SELECT_BY_ID_SQL = "select * from vmeta_field where id = ?";
	public static final String FIELD_DELETE_BY_IDS_SQL = "delete from vmeta_field where id in ";
	public static final String FIELD_SELECT_BY_MODULE_SQL = "select * from vmeta_field where moduleid = ?";
	
	//模块操作表
	public static final String ROLEOPERATE_SELECT_BY_ROLEID_SQL = "select * from vmeta_role_module_operate where roleid = ?";
	public static final String ROLEOPERATE_SELECT_BY_MODULE_ROLE_SQL = "select * from vmeta_role_module_operate where moduleid = ? and roleid = ?";
	public static final String ROLEOPERATE_SELECT_BY_WEBID_ROLE_SQL = "select * from vmeta_role_module_operate where webid = ? and roleid = ?";
	public static final String ROLEOPERATE_SAVE_SQL = "insert into vmeta_role_module_operate(id,webid,useable,roleid,moduleid) values(?,?,?,?,?)";
	public static final String ROLEOPERATE_UPDATE_SQL = "update vmeta_role_module_operate set webid = ? , useable = ?,roleid = ?,moduleid = ? where id = ?";
	public static final String ROLEOPERATE_DELETE_BY_ID_SQL = "delete from vmeta_role_module_operate where id = ?";
	public static final String ROLEOPERATE_DELETE_BY_ROLEID = "delete from vmeta_role_module_operate where roleid = ?";
	
	//角色模块用户表
	public static final String ROLEMODULEUSERS_SELECT_BY_ROLEID_SQL = "select * from vmeta_role_module_users where roleid = ?";
	public static final String ROLEMODULEUSERS_SELECT_BY_ROLE_MODULE_SQL = "select * from vmeta_role_module_users where roleid = ? and moduleid = ?";
	public static final String ROLEMODULEUSERS_SAVE_SQL = "insert into vmeta_role_module_users(id,roleid,moduleid,users) values(?,?,?,?)";
	public static final String ROLEMODULEUSERS_UPDATE_SQL = "update vmeta_role_module_users set roleid = ?,moduleid = ?,users = ? where id = ?";
	public static final String ROLEMODULEUSERS_DELETE_BY_ID = "delete from vmeta_role_module_users where id = ?";
 	public static final String ROLEMODULEUSERS_DELETE_BY_ROLEID = "delete from vmeta_role_module_users where roleid = ?";
	
	//角色模块字段表
	public static final String ROLEMODULEFIELD_SELECT_BY_ROLEID_SQL = "select * from vmeta_role_module_field where roleid = ?";
	public static final String ROLEMODULEFIELD_SELECT_BY_MODULE_ROLE_SQL = "select * from vmeta_role_module_field where moduleid = ? and roleid = ?";
	public static final String ROLEMODULEFIELD_SELECT_BY_ROLE_WEBID_SQL = "select * from vmeta_role_module_field where  roleid = ? and webid = ?";
	public static final String ROLEMODULEFIELD_SAVE_SQL = "insert into vmeta_role_module_field(id,roleid,moduleid,webid,rules) values(?,?,?,?,?)";
	public static final String ROLEMODULEFIELD_UPDATE_SQL = "update vmeta_role_module_field set roleid = ?,moduleid = ?,webid = ?,rules = ? where id = ?";
	public static final String ROLEMODULEFIELD_SELECT_BY_ID = "select * from vmeta_role_module_field where id = ?";
	public static final String ROLEMODULEFIELD_SELECT_BY_ROLE_MODULE_WEBID_SQL = "select * from vmeta_role_module_field where roleid =? and moduleid = ? and webid = ?";
	public static final String ROLEMODULEFIELD_DELETE_BY_ID_SQL = "delete from vmeta_role_module_field where id = ?";
	public static final String ROLEMODULEFIELD_DELETE_BY_ROLEID = "delete from vmeta_role_module_field where roleid = ?";
	
	//角色用户表
	public static final String ROLEUSERS_BY_USERS_SQL = "select * from vmeta_role_users where usersid = ?";
	public static final String ROLEUSERS_SELECT_BY_ROLE_SQL = "select * from vmeta_role_users where roleid = ?";
	public static final String ROLEUSERS_DELETE_BY_ID = "delete from vmeta_role_users where id = ?";
	public static final String ROLEUSERS_DELETE_BY_USERSID = "delete from vmeta_role_users where usersid = ?";
	public static final String ROLEUSERS_SELECT_BY_ROLE_USERS_SQL = "select * from vmeta_role_users where (roleid = ? or roleid = ? or roleid = ? or roleid = ?) and usersid = ?";
	public static final String ROLEUSERS_BY_USERS_ROLE_SQL = "select * from vmeta_role_users where usersid = ? and roleid = ?";
	public static final String ROLEUSERS_SAVE_SQL = "insert into vmeta_role_users(id,roleid,usersid) values(?,?,?)";
	public static final String ROLEUSERS_DELETE_BY_ROLEID = "delete from vmeta_role_users where roleid = ?";
	
	//角色模块部门表
	public static final String ROLEMODULEDEPARTMENT_SELECT_BY_ROLE_SQL = "select * from vmeta_role_module_departments where roleId = ?";
	public static final String ROLEMODULEDEPARTMENT_SELECT_BY_ROLE_MODULE_SQL = "select * from vmeta_role_module_departments where roleid = ? and moduleid = ?";
	public static final String ROLEMODULEDEPARTMENT_SAVE_SQL = "insert into vmeta_role_module_departments(id,roleid,moduleid,departments) values(?,?,?,?)";
	public static final String ROLEMODULEDEPARTMENT_UPDATE_SQL = "update vmeta_role_module_departments set roleid = ?,moduleid = ?,departments = ? where id = ?";
	public static final String ROLEMODULEDEPARTMENT_DELETE_BY_ID_SQL = "delete from vmeta_role_module_departments where id = ?";
	public static final String ROLEMODULEDEPARTMENT_DELETE_BY_ROLEID = "delete from vmeta_role_module_departments where roleid = ?";
	
	//数据可见范围表
	public static final String SCOPEDATAVISIBLE_SELECT_BY_ROLE_SQL = "select * from vmeta_role_module_scopedatavisible where roleid = ?";
	public static final String SCOPEDATAVISIBLE_SELECT_BY_ROLE_MODULE_USERS_SQL = "select * from vmeta_role_module_scopedatavisible where roleid = ? and moduleid = ? and usersid = ?";
	public static final String SCOPEDATAVISIBLE_BY_ROLE_MODULE_USERS_WEBID_SQL = "select * from vmeta_role_module_scopedatavisible where roleid = ? and moduleid = ? and usersid = ? and webid = ?";
	public static final String SCOPEDATAVISIBLE_SAVE_SQL = "insert into vmeta_role_module_scopedatavisible(id,roleid,moduleid,usersid,webid,visible) values(?,?,?,?,?,?)";
	public static final String SCOPEDATAVISIBLE_UPDATE_SQL = "update vmeta_role_module_scopedatavisible set roleid = ?,moduleid = ?,usersid = ?, webid = ?,visible = ? where id = ?";
	public static final String SCOPEDATAVISIBLE_SELECT_ID_BY_ROLE_MODULE_USERS_SQL = "select id from vmeta_role_module_scopedatavisible where roleid = ? and moduleid = ? and usersid = ?";
	public static final String SCOPEDATAVISIBLE_DELETE_BY_IDS_SQL = "delete from vmeta_role_module_scopedatavisible where id in "; 
	public static final String SCOPEDATAVISIBLE_DELETE_BY_ID_SQL = "delete from vmeta_role_module_scopedatavisible where id = ?";
	public static final String SCOPEDATAVISIBLE_DELETE_BY_ROLEID = "delete from vmeta_role_module_scopedatavisible where roleid = ?";
	
	//部门可见范围表
	public static final String SCOPEDEPTVISIBLE_SELECT_BY_ROLE_SQL = "select * from vmeta_role_module_scopedeptvisible where roleid = ?";
	public static final String SCOPEDEPTVISIBLE_SELECT_BY_ROLE_MOUDLE_DEPT_SQL = "select * from vmeta_role_module_scopedeptvisible where roleid = ? and moduleid = ? and departmentid = ?";
	public static final String SCOPEDEPTVISIBLE_SELECT_BY_ROLE_MODULE_DEPT_WEBID_SQL = "select * from vmeta_role_module_scopedeptvisible where roleid = ? and moduleid = ? and departmentid = ? and webid = ?";
	public static final String SCOPEDEPTVISIBLE_SAVE_SQL = "insert into vmeta_role_module_scopedeptvisible(id,roleid,moduleid,departmentid,webid,visible) values(?,?,?,?,?,?)";
	public static final String SCOPEDEPTVISIBLE_UPDATE_SQL = "update vmeta_role_module_scopedeptvisible set roleid = ?,moduleid = ?,departmentid = ?,webid = ?,visible = ? where id = ?";
	public static final String SCOPEDEPTVISIBLE_SELECT_BY_ROLE_MODULE_SQL = "select * from vmeta_role_module_scopedeptvisible where roleid = ? and moduleid = ?";
	public static final String SCOPEDEPTVISIBLE_DELETE_BY_ID_SQL = "delete from vmeta_role_module_scopedeptvisible where id = ?";
	
	
	
	public static final String ROLE_Name = "select * from role where 1=1 and (name = '销售人员（济南）' or name = '销售人员（深圳）' or name = '销售经理（济南）' or name = '销售经理（深圳）')";

	public static final Map<String, List<String>> DELETE_TABLE_VALIDATE = new HashMap<String, List<String>>();

	static {
		DEPARTMENT_DELETE_LIST.add("select count(id) from Department where superId = ?");
		DEPARTMENT_DELETE_LIST.add("select count(id) from Users where departmentid = ?");

		ROLE_DELETE_LIST.add("select count(id) from vmeta_role_users where roleid = ?");
		
		DELETE_TABLE_VALIDATE.put("Department", DEPARTMENT_DELETE_LIST);
		DELETE_TABLE_VALIDATE.put("Role", ROLE_DELETE_LIST);
		
		//---------CSMS 系统
		CSMSRULE_DELETE_LIST.add("select count(id) from sms_rule where content = ? ");
		CSMSGROUP_DELETE_LIST.add("select count(id) from sms_group where rule = ?");
		CSMSNUMBER_DELETE_LIST.add("select count(id) from sms_number where smsgroup = ?");
		DELETE_TABLE_VALIDATE.put("CSMSRule",CSMSRULE_DELETE_LIST);
		DELETE_TABLE_VALIDATE.put("CSMSGroup", CSMSGROUP_DELETE_LIST);
		DELETE_TABLE_VALIDATE.put("CSMSNumber", CSMSNUMBER_DELETE_LIST);
	}

	/**
	 * 获得拼音查询的语句
	 * 
	 * @param table
	 * @param property
	 * @param azparam
	 * @return
	 */
	public static String getAZHQL(String table, String property, String azparam) {
		StringBuilder sb = new StringBuilder();
		sb.append("from ");
		sb.append(table);
		if ("ALL".equals(azparam)) {
			sb.append(" where 1=1");
		} else if ("0~9".equals(azparam)) {
			sb.append(" where (ISNUMERIC(dbo.getPinYin(SUBSTRING(");
			sb.append(property);
			sb.append(", 1, 1))) = 1)");
		} else {
			sb.append(" where dbo.getPinYin(SUBSTRING(");
			sb.append(property);
			sb.append(", 1, 1)) = '");
			sb.append(azparam);
			sb.append("' ");
		}
		return sb.toString();
	}

	/**
	 * 获得数据范围的子句
	 * 
	 * @param usersIds
	 * @return
	 */
	public static String getUsersWhere(String usersIds) {
		return getUsersWhere(null,usersIds);
	}
	
	public static String getUsersWhere(String property,String usersIds){
		if(Validate.notNull(property))
		return getSQLInString(usersIds, property , "and");
		else
		return 	getSQLInString(usersIds, SQLConstant.DEFAULT_USER_WHERE_CREATOR , "and");
	}
	
	public static String getDepartmentsWhere(String depatmentsIds) {
		return getSQLInString(depatmentsIds, "department.id", "and");
	}
	
	/**
	 * 获得数据范围的子句
	 * 
	 * @param usersIds
	 * @return
	 */
	public static String getSQLUsersWhere(String usersIds) {
		return getSQLInString(usersIds, "creator", "and");
	}
	/**
	 * 根据创建人查询
	 * @param usersIds
	 * @param departmentIds
	 * @return
	 */
	public static String getUsersAndDeptWhere(String usersIds,String departmentIds){
		StringBuilder sb = new StringBuilder();
		sb.append(" and (department.creator in (");
		sb.append(usersIds);
		sb.append(") or  creator.department.id in(");
		sb.append(departmentIds);
		sb.append(")) ");
		return sb.toString();
	}
	
	/**
	 * 根据创建人查询
	 * @param usersIds
	 * @param departmentIds
	 * @return
	 */
	public static String getUsersAndDeptWhere(String creatorAlias,String departmentAlias,String usersIds,String departmentIds){
		StringBuilder sb = new StringBuilder();
		sb.append(" and ( "+creatorAlias+" in (");
		sb.append(usersIds);
		sb.append(") or  "+ departmentAlias +" in(");
		sb.append(departmentIds);
		sb.append(")) ");
		return sb.toString();
	}
	
	/**
     * 根据创建人和所属人查询
     * @param usersIds
     * @param departmentIds
     * @return
     */
	public static String getUsersAndDeptAndOwnerWhere(String usersIds,String departmentIds){
		StringBuilder sb = new StringBuilder();
		sb.append(" and (creator.id in (");
		sb.append(usersIds);
		sb.append(") or creator.department.id in(");
		sb.append(departmentIds);
		sb.append(") or owner.id in (");
		sb.append(usersIds);
		sb.append(") or owner.department.id in(");
		sb.append(departmentIds);
		sb.append(")) ");
		return sb.toString();
	}
	
	/**
	 * 根据创建人和superid
	 * 
	 */
	public static String getUsersAndSuperid(String creatorAlias,String superidAlias,String usersIds,String superIds){
		StringBuilder sb = new StringBuilder();
		sb.append(" and ( "+creatorAlias+" in (");
		sb.append(usersIds);
		sb.append(") or  "+ superidAlias +" in(");
		sb.append(superIds);
		sb.append(")) ");
        return sb.toString();
	}
	
	
	/**
     * 根据创建人、共享标志查询（会议纪要）
     * @param usersIds
     * @param departmentIds
     * @return
     */
    public static String getUsersAndDeptWhere(String usersIds,String departmentIds, String isShare){
        StringBuilder sb = new StringBuilder();
        sb.append(" and (creator.id in (");
        sb.append(usersIds);
        sb.append(") or  creator.department.id in(");
        sb.append(departmentIds);
        sb.append(") or  isShare = '");
        sb.append(isShare);
        sb.append("') ");
        return sb.toString();
    }
	
	public static String getSQLInString(String str, String property, String orAnd) {
		StringBuilder sb = new StringBuilder();
		sb.append(" ");
		sb.append(orAnd);
		sb.append(" ");
		sb.append(property);
		sb.append(" in (");
		sb.append(str);
		sb.append(")");
		return sb.toString();
	}
	
	
}