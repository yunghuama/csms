package com.csms.constants;

/**
 * CSMS sql 语句
 * @author cheney
 *
 */
public class CSMSSQLConstant {

	public static final String LIMIT = " limit ?,?";
	
	/**
	 * 内容管理
	 */
	public static final String CONTENT_SAVE_SQL = "insert into sms_content(id,content,department,remark,reason,state,creator,createtime) values(?,?,?,?,?,?,?,?)";
	public static final String CONTENT_SELECT_BY_PAGE_SQL = "select c.*,u.realname from sms_content c,users u where c.department = ? and  c.creator = u.id ";
	public static final String CONTENT_SELECT_BY_PAGE_STATE_SQL = "select c.*,u.realname from sms_content c,users u where c.department = ? and c.state = ? and c.creator = u.id ";
	public static final String CONTENT_SELECT_BY_PAGE_UNCHECKED_SQL = "select c.*,u.realname,d.name as departmentname from sms_content c,users u,department d where c.state = ? and c.creator = u.id and d.id = c.department";
	public static final String CONTENT_ROWCOUNT_SQL = "select count(c.id)";
	public static final String CONTENT_SELECT_ALL_SQL = "select id,content from sms_content c,users u where c.department id = ? and c.creator = u.id ";
	public static final String CONTENT_SELECT_BY_ID = "select c.* from sms_content c where id = ?";
	public static final String CONTENT_DELETE_BY_IDS = "delete from sms_content where id in ";
	public static final String CONTENT_SELECT_BY_STATE = "select id,content from sms_content where department = ? and state = ?";
	public static final String CONTENT_UPDATE_BY_ID_SQL = "update sms_content set reason = ?,state = ? where id = ?";
	
	/**
	 * 策略管理
	 */
	public static final String RULE_SELECT_BY_PAGE_SQL = "select r.*,u.realname,c.content as contentname from sms_rule r,users u,sms_content c where r.department = ? and r.creator = u.id and r.content = c.id";
	public static final String RULE_SELECT_ALL_SQL = "select r.* from sms_rule r,users u where r.department = ? and r.creator = u.id ";
	public static final String RULE_SELECT_BY_ID = "select * from sms_rule where id = ?";
	public static final String RULE_SAVE_SQL = "insert into sms_rule(id,name,ruleday,rulestarttime,ruleendtime,content,department,state,creator,createtime) value(?,?,?,?,?,?,?,?,?,?)";
	public static final String RULE_UPDATE_SQL = "update sms_rule set name =? ,ruleday = ?, rulestarttime = ?,ruleendtime = ?,content = ?,state = ? where id = ?";
	public static final String RULE_DELETE_BY_IDS_SQL = "delete from sms_rule where id in ";
	public static final String RULE_UPDATE_STATE_SQL = "update sms_rule set state = ? where id = ?";
	public static final String RULE_ROWCOUNT_SQL = "select count(r.id)";
	public static final String PRERULE_SELECT_BY_RULE_SQL = "select pr.*,sc.content as contentname from sms_previousrule pr,sms_content sc where pr.rule = ? and pr.content = sc.id ";
	public static final String PRERULE_SAVE_SQL = "insert into sms_previousrule(id,name,executedate,rule,content,creator,createtime) value(?,?,?,?,?,?,?)";
	public static final String PRERULE_DELETE_SQL = "delete from sms_previousrule where id in ";
	public static final String GLORULE_SELECT_SQL = "select gr.*,u.realname,c.content as contentname from sms_globalrule gr,users u,sms_content c  where gr.department = ? and gr.creator = u.id and gr.content = c.id";
	public static final String GLORULE_SELECT_BY_ID = "select * from sms_globalrule where id = ?";
	public static final String GLORULE_COUNT_SQL = "select count(id) from sms_globalrule where department = ?";
	public static final String GLORULE_SAVE_SQL = "insert into sms_globalrule(id,name,content,department,state,creator,createtime) values(?,?,?,?,?,?,?)";
	public static final String GLORULE_UPDATE_SQL = "update sms_globalrule set name = ?,content = ? ,state = ? where id = ?";
	public static final String GLORULE_UPDATE_STATE = "update sms_globalrule set state = ? where id = ?";
	public static final String GLORULE_DELETE_BY_IDS_SQL = "delete from sms_globalrule where id in ";
	
	/**
	 * 组群管理
	 */
	public static final String GROUP_SELECT_BY_PAGE_SQL = "select g.*,u.realname,r.name as rulename from users u, sms_group g left join sms_rule r on g.rule = r.id where g.department = ? and  g.creator = u.id ";
	public static final String GROUP_SAVE_SQL = "insert into sms_group(id,name,rule,department,remark,type,creator,createtime) values(?,?,?,?,?,?,?,?)";
	public static final String GROUP_UPDATE_SQL = "update sms_group set name=?,rule=?,remark=? where id = ?";
	public static final String GROUP_DELETE_BY_IDS_SQL = "delete from sms_group where type='1' and id in ";
	public static final String GROUP_SELECT_BY_ID = "select * from sms_group where id = ? ";
	public static final String GROUP_SELECT_ALL_SQL = "select g.* from sms_group g where g.department = ?";
	public static final String GROUP_ROWCOUNT_SQL = "select count(g.id) ";
	
	/**
	 * 号码管理
	 */
	public static final String NUMBER_SELECT_BY_PAGE_DEP_SQL = "select n.*,u.realname,g.name as groupname from sms_number n,users u,sms_group as g where n.department = ? and n.creator = u.id and n.smsgroup = g.id";
	public static final String NUMBER_SELECT_BY_PAGE_GROUP_SQL = "select n.*,u.realname,g.name as groupname from sms_number n,users u,sms_group as g where n.smsgroup = ? and n.creator = u.id and n.smsgroup = g.id";
	public static final String NUMBER_SAVE_BY_GROUP_SQL = "insert into sms_number(id,number,smsgroup,department,remark,creator,createtime) values(?,?,?,?,?,?,?)";
	public static final String NUMBER_SELECT_BY_ID_SQL = "select n.* from sms_number n where id = ?";
	public static final String NUMBER_UPDATE_ALL_SQL = "update sms_number set number = ?,smsgroup = ?,remark = ? where id = ?";
	public static final String NUMBER_UPDATE_GROUP_SQL = "update sms_number set smsgroup = ?,remark = ? where id = ?";
	public static final String NUMBER_ROWCOUNT_SQL = "select count(n.id)";
	public static final String NUMBER_DELETE_BY_IDS_SQL = "delete from sms_number where id in ";
	public static final String NUMBER_SELECT_ALL_SQL = "select n.* from sms_number n where n.department = ?";
	public static final String NUMBER_SELECT_COUNT_SQL = "select count(id) from sms_number where number = ?";

    /**
     * 报警管理
     */
    public static final String WARN_SAVE_SQL = "insert into sms_warn(id,cpu,memusage,storage,memtotal,createtime) values(?,?,?,?,?,?)";
    public static final String WARN_SELECT_SQL = "select * from sms_warn order by createtime desc limit ?,?";




}
