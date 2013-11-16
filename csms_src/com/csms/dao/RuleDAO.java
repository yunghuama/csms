package com.csms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.csms.constants.CSMSSQLConstant;
import com.csms.constants.CSMSStringConstant;
import com.csms.domain.GloRule;
import com.csms.domain.Group;
import com.csms.domain.PreRule;
import com.csms.domain.Rule;
import com.platform.dao.GenericDAO;
import com.platform.domain.Users;
import com.platform.util.Meta;
import com.platform.util.PageHelper;
import com.platform.util.UUIDGenerator;
import com.platform.vo.Page;

/**
 * <p>程序名称：       ContentDAO.java</p>
 * <p>程序说明：       策略管理DAO</p>
 * <p>时间：          2013-1-14</p>	
 * 
 * @author：          cheney.mydream
 * @version：         Ver 0.1
 */
public class RuleDAO extends GenericDAO{

	private static final long serialVersionUID = 376038639038947152L;
	private static RuleDAO instance;
	private JdbcTemplate jdbcTemplate;
	
	public static RuleDAO getInstance(JdbcTemplate jdbcTemplate) {
        if(instance == null) {
        	instance =  new RuleDAO(jdbcTemplate);
        }
        return instance;
    }
	
	public RuleDAO(JdbcTemplate jdbcTemplate){
		super(jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;
	}
	//------------普通策略开始

	public Page<Rule> paginationRule(final Page<Rule> page,String sql,Object[] args){
		Object[] preparedArgs = Meta.addPageToParameters(args, page);
		page.setList(jdbcTemplate.query(sql + CSMSSQLConstant.LIMIT, preparedArgs, new RowMapper<Rule>(){
			@Override
			public Rule mapRow(ResultSet rs, int rowNum) throws SQLException {
				Rule rule = new Rule();
				rule.setContent(rs.getString("contentname"));
				rule.setId(rs.getString("id"));
				rule.setName(rs.getString("name"));
				rule.setRuleDay(formatRuleDay(rs.getString("ruleday")));
				rule.setRuleStartTime(rs.getString("rulestarttime"));
				rule.setRuleEndTime(rs.getString("ruleendtime"));
				rule.setState(rs.getString("state"));
				rule.setCreateTime(rs.getLong("createtime"));
				rule.setTimeType(rs.getString("timetype"));
				rule.setType(rs.getString("type"));
				Group group = new Group();
				group.setId(rs.getString("groupid"));
				group.setName(rs.getString("groupname"));
				rule.setGroup(group);
				return rule;
			}}));
		int rowCount = queryForInt(Meta.getRowCountSQL(CSMSSQLConstant.RULE_ROWCOUNT_SQL , sql),args);
		page.setRowCount(rowCount);
		page.setMaxPage(PageHelper.getMaxPage(rowCount, page.getPageSize()));
		return page;
	}
	
	/**
	 * 格式化时间
	 * @return
	 */
	private String formatRuleDay(String day){
		StringBuilder sb = new StringBuilder();
		if(day==null||day.length()!=7)
			return CSMSStringConstant.RULE_NODAY;
		String[] days = day.split("");
		for(int i=1;i<days.length;i++){
			if("1".equals(days[i])){
				sb.append(CSMSStringConstant.RULE_DAYS[i-1]);
				sb.append(",");
			}
		}
		return sb.toString();
	}
	
	/**
	 * 根据sql 和 args 查询
	 * 
	 */
	public List<Rule> findAllRule(String sql,Object[] args){
		return jdbcTemplate.query(sql, args, new RowMapper<Rule>(){
			@Override
			public Rule mapRow(ResultSet rs, int rowNum) throws SQLException {
				Rule rule = new Rule();
				rule.setId(rs.getString("id"));
				rule.setName(rs.getString("name"));
				rule.setRuleDay(rs.getString("ruleday"));
				rule.setRuleStartTime(rs.getString("rulestarttime"));
				rule.setRuleEndTime(rs.getString("ruleendtime"));
				rule.setState(rs.getString("state"));
				rule.setContent(rs.getString("content"));
				rule.setTimeType(rs.getString("timetype"));
				rule.setType(rs.getString("type"));
				return rule;
			}
		});
	}
	
	/**
	 * 根据sql 查询
	 * 
	 */
	public List<Rule> findAllRule(String sql){
		return findAllRule(sql,null);
	}
	
	/**
	 * 保存
	 * @param department
	 * @return
	 */
	public int saveRule(Rule rule){
     return jdbcTemplate.update(CSMSSQLConstant.RULE_SAVE_SQL, new Object[]{
			UUIDGenerator.generate(),
			rule.getName(),
			rule.getRuleDay(),
			rule.getRuleStartTime(),
			rule.getRuleEndTime(),
			rule.getContent(),
			rule.getDepartment(),
			rule.getState(),
			"",
			new Date(),
			rule.getTimeType(),
			rule.getType(),
			rule.getGroup().getId()
		});
	}
	
	
	/**
	 * 更新
	 * @param department
	 * @return
	 */
	public int updateRule(Rule rule){
     return jdbcTemplate.update(CSMSSQLConstant.RULE_UPDATE_SQL, new Object[]{
			rule.getName(),
			rule.getRuleDay(),
			rule.getRuleStartTime(),
			rule.getRuleEndTime(),
			rule.getContent(),
			rule.getState(),
			rule.getTimeType(),
			rule.getType(),
			rule.getGroup().getId()
		});
	}
	
	/**
	 * 更新状态
	 * @param state
	 * @return
	 */
	public int updateStateRule(String state,String group){
		return jdbcTemplate.update(CSMSSQLConstant.RULE_UPDATE_STATE_SQL, new Object[]{state,group});
	}
	
	/**
	 * 根据ID
	 * @param id
	 */
	public Rule findRule(String sql,Object[] args){
		List<Rule> list = findAllRule(sql,args);
		if(list!=null&&list.size()>0)
			return list.get(0);
		return null;
	}
	//----------普通策略结束
	
	//----------全局策略开始
	public Page<GloRule> paginationGloRule(final Page<GloRule> page,String sql,Object[] args){
		Object[] preparedArgs = Meta.addPageToParameters(args, page);
		page.setList(jdbcTemplate.query(sql + CSMSSQLConstant.LIMIT, preparedArgs, new RowMapper<GloRule>(){
			@Override
			public GloRule mapRow(ResultSet rs, int rowNum) throws SQLException {
				GloRule rule = new GloRule();
				rule.setContent(rs.getString("contentname"));
				rule.setId(rs.getString("id"));
				rule.setState(rs.getString("state"));
				rule.setName(rs.getString("name"));
				rule.setCreateTime(rs.getLong("createtime"));
				Users user = new Users();
				user.setId(rs.getString("creator"));
				user.setRealName(rs.getString("realname"));
				rule.setCreator(user);
				return rule;
			}}));
		page.setRowCount(1);
		page.setMaxPage(PageHelper.getMaxPage(1, page.getPageSize()));
		return page;
	}
	
	/**
	 * 根据sql 和 args 查询
	 * 
	 */
	public List<GloRule> findAllGloRule(String sql,Object[] args){
		return jdbcTemplate.query(sql, args, new RowMapper<GloRule>(){
			@Override
			public GloRule mapRow(ResultSet rs, int rowNum) throws SQLException {
				GloRule rule = new GloRule();
				rule.setId(rs.getString("id"));
				rule.setName(rs.getString("name"));
				rule.setState(rs.getString("state"));
				rule.setContent(rs.getString("content"));
				return rule;
			}
		});
	}
	
	/**
	 * 保存 
	 * @param 
	 * @return
	 */
	public int saveGloRule(GloRule rule){
     return jdbcTemplate.update(CSMSSQLConstant.GLORULE_SAVE_SQL, new Object[]{
			UUIDGenerator.generate(),
			rule.getName(),
			rule.getContent(),
			rule.getDepartment(),
			rule.getState(),
			rule.getCreator().getId(),
			new Date().getTime()
		});
	}
	
	/**
	 * 更新
	 * @param 
	 * @return
	 */
	public int updateGloRule(GloRule rule){
     return jdbcTemplate.update(CSMSSQLConstant.GLORULE_UPDATE_SQL, new Object[]{
			rule.getName(),
			rule.getContent(),
			rule.getState(),
			rule.getId()
		});
	}
	
	/**
	 * 根据ID
	 * @param id
	 */
	public GloRule findGloRule(String sql,Object[] args){
		List<GloRule> list = findAllGloRule(sql,args);
		if(list!=null&&list.size()>0)
			return list.get(0);
		return null;
	}
	
	/**
	 * 根据部门删除策略
	 * @param sql
	 * @param args
	 * @return
	 */
	public int deleteRuleByGroup(String id){
		return jdbcTemplate.update(CSMSSQLConstant.RULE_DELETE_BY_GROUP_SQL, new Object[]{id});
	}
	
	//---------全局策略结束
	
	//---------预先策略开始
	
	public List<PreRule> findAllPreRule(String sql,Object[] args){
		return jdbcTemplate.query(sql, args, new RowMapper<PreRule>(){
			@Override
			public PreRule mapRow(ResultSet rs, int rowNum) throws SQLException {
				PreRule rule = new PreRule();
				rule.setId(rs.getString("id"));
				rule.setName(rs.getString("name"));
				rule.setContent(rs.getString("contentname"));
				rule.setExecuteDate(rs.getLong("executedate"));
				rule.setCreateTime(rs.getLong("createtime"));
				return rule;
			}
		});
	}
	
	/**
	 * 保存
	 * @param 
	 * @return
	 */
	public int savePreRule(PreRule rule){
     return jdbcTemplate.update(CSMSSQLConstant.PRERULE_SAVE_SQL, new Object[]{
			UUIDGenerator.generate(),
			rule.getName(),
			rule.getExecuteDate(),
			rule.getRule(),
			rule.getContent(),
			rule.getCreator().getId(),
			new Date().getTime()
		});
	}
	
	
	//---------预先策略结束
}