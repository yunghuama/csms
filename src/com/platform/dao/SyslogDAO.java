package com.platform.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.platform.constants.SQLConstant;
import com.platform.domain.Syslog;
import com.platform.domain.Users;
import com.platform.exception.CRUDException;
import com.platform.util.LoginBean;
import com.platform.util.Meta;
import com.platform.util.PageHelper;
import com.platform.util.UUIDGenerator;
import com.platform.vo.Page;

/**
 * 系统日志DAO
 * 
 * @author cheney.mydream
 *
 */
public class SyslogDAO extends GenericDAO {

	private static final long serialVersionUID = -9137703804073963031L;
	private static SyslogDAO instance;
	private JdbcTemplate jdbcTemplate;
	
	public static SyslogDAO getInstance(JdbcTemplate jdbcTemplate) {
        if(instance == null) {
            instance =  new SyslogDAO(jdbcTemplate);
        }
        return instance;
    }
	
	private SyslogDAO(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 保存系统日志
	 * 
	 * @param contents
	 * @param type
	 * @throws CRUDException
	 */
	public void saveSyslog(String contents, String type) throws CRUDException {
		Syslog syslog = new Syslog();
		syslog.setContents(contents);
		syslog.setType(type);
		syslog.setOpTime(new Date());
		syslog.setOptor(LoginBean.getLoginBean().getUser());
		syslog.setIpAdd(ServletActionContext.getRequest().getRemoteAddr());
		save(syslog);
	}
	
	/**
	 * 保存
	 * @param syslog
	 */
	public int save(Syslog syslog){
		return jdbcTemplate.update(SQLConstant.SYSLOG_SAVE_SQL, new Object[]{
				UUIDGenerator.generate(),
				syslog.getContents(),
				syslog.getOpTime(),
				syslog.getOptor(),
				syslog.getIpAdd(),
				syslog.getType()
		});
	}
	
	
	/**
	 * 分页获取部门数据
	 */
	public Page<Syslog> pagination(final Page<Syslog> page,String sql,Object[] args){
		Object[] preparedArgs = Meta.addPageToParameters(args, page);
		page.setList(jdbcTemplate.query(sql+SQLConstant.LIMIT, preparedArgs, new RowMapper<Syslog>(){
			@Override
			public Syslog mapRow(ResultSet rs, int rowNum) throws SQLException {
				Syslog syslog = new Syslog();
				syslog.setId(rs.getString("id"));
				syslog.setContents(rs.getString("contents"));
				syslog.setIpAdd(rs.getString("ipadd"));
				syslog.setType(rs.getString("type"));
				Users u = new Users();
				u.setRealName(rs.getString("realname"));
				u.setAccountName(rs.getString("accountname"));
				syslog.setOptor(u);
				return syslog;
			}}));
		int rowCount = queryForInt(Meta.getRowCountSQL(SQLConstant.SYSLOG_ROWCOUNT_SQL,sql),args);
		page.setRowCount(rowCount);
		page.setMaxPage(PageHelper.getMaxPage(rowCount, page.getPageSize()));
		return page;
	}
	
	/**
	 * 删除所有
	 * @param sql
	 * @return
	 */
	public int deleteAll(String sql){
		return jdbcTemplate.update(sql);
	}

}