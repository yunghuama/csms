package com.csms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.csms.constants.CSMSSQLConstant;
import com.csms.domain.SmsRule;
import com.csms.domain.Warn;
import com.platform.dao.GenericDAO;
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
public class SysDAO extends GenericDAO{

	private static final long serialVersionUID = 376038639038947152L;
	private static SysDAO instance;
	private JdbcTemplate jdbcTemplate;
	
	public static SysDAO getInstance(JdbcTemplate jdbcTemplate) {
        if(instance == null) {
        	instance =  new SysDAO(jdbcTemplate);
        }
        return instance;
    }
	
	public SysDAO(JdbcTemplate jdbcTemplate){
		super(jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;
	}


    /**
     * 保存报警信息
     * @param cpu
     * @param memusage
     * @param storage
     * @return
     */
    public int saveWarn(double cpu,long memusage,int storage,long memtotal){
       return jdbcTemplate.update(CSMSSQLConstant.WARN_SAVE_SQL,new Object[]{
           UUIDGenerator.generate(),
           cpu,
           memusage,
           storage,
           memtotal,
           new Date()
        });
    }


    /**
     * 获得报警列表
     * @param page
     * @return
     */
    public Page<Warn> paginationWarn(Page page){
    	List list = jdbcTemplate.query(CSMSSQLConstant.WARN_SELECT_SQL,new Object[]{(page.getCurrPage()-1)*page.getPageSize(),page.getPageSize()}, new RowMapper<Warn>() {
			@Override
			public Warn mapRow(ResultSet rs, int position) throws SQLException {
			    Warn w = new Warn();
			    w.setId(rs.getString("id"));
			    w.setCpu(rs.getInt("cpu"));
			    w.setMemtotal(rs.getInt("memtotal"));
			    w.setMemusage(rs.getInt("memusage"));
			    w.setCreatetime(rs.getDate("createtime")+" "+rs.getTime("createtime"));
				return w;
			}
		});
    	page.setList(list);
    	int rowCount = 10000;
    	page.setRowCount(rowCount);
		page.setMaxPage(PageHelper.getMaxPage(rowCount, page.getPageSize()));
    	return page;
    }
    
    
    public HashMap<String,SmsRule> listSmsRule(){
    	final HashMap<String,SmsRule> hashMap = new HashMap<String,SmsRule>();
    	jdbcTemplate.query(CSMSSQLConstant.SMS_RULE_QUERY, new RowMapper<SmsRule>(){
			@Override
			public SmsRule mapRow(ResultSet rs, int arg1) throws SQLException {
				SmsRule smsRule = new SmsRule();
				smsRule.setContent(rs.getString("content"));
				smsRule.setNumber(rs.getString("number"));
				smsRule.setRuleDay(rs.getString("ruleDay"));
				smsRule.setTimeType(rs.getString("timeType"));
				hashMap.put(smsRule.getNumber(), smsRule);
				return smsRule;
			}
    		
    	});
    	return hashMap;
    }
}