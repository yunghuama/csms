package com.platform.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.platform.constants.SQLConstant;
import com.platform.domain.Dictionary;
import com.platform.domain.Intercom;
import com.platform.exception.CRUDException;
import com.platform.util.LoginBean;
import com.platform.util.UUIDGenerator;
import com.platform.util.Validate;
import com.platform.vo.Page;

public class IntercomDAO extends GenericDAO{

	private static final long serialVersionUID = -6016416637756110162L;
	private static IntercomDAO instance = null;
	private JdbcTemplate jdbcTemplate;
	
	public static IntercomDAO getInstance(JdbcTemplate jdbcTemplate) {
		if(instance == null) {
            instance =  new IntercomDAO(jdbcTemplate);
        }
        return instance;
	}
	
	private IntercomDAO(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 查找未读数量
	 * 
	 * @return
	 * @throws CRUDException
	 */
	public Integer countNoRead() throws CRUDException {
		return queryForInt(SQLConstant.INTERCOM_SELECT_COUNTNOREAD_BY_OWNER_SQL, new String[]{LoginBean.getLoginBean().getUser().getId()});
	}
	
	public Integer countNoRead(String type) throws CRUDException {
		return queryForInt(SQLConstant.INTERCOM_SELECT_COUNTNOREAD_BY_TYPE_WONER_SQL, new String[]{type,LoginBean.getLoginBean().getUser().getId()});
	}

	
	/**
	 * 保存
	 * @param intercom
	 * @return
	 */
	public int save(Intercom intercom){
		return jdbcTemplate.update(SQLConstant.INTERCOM_SAVE_SQL, new Object[]{
				UUIDGenerator.generate(),
				intercom.getTitle(),
				intercom.getContents(),
				intercom.getSender(),
				intercom.getReplier(),
				intercom.getCopier(),
				intercom.getReadFlag(),
				intercom.getSendTime(),
				intercom.getType(),
				intercom.getOwner().getId(),
				intercom.getInform(),
				intercom.getFlagType()
		});
	}
	
	
	/**
	 * 更新
	 * @param intercom
	 * @return
	 */
	public int update(Intercom intercom){
		return jdbcTemplate.update(SQLConstant.INTERCOM_UPDATE_SQL, new Object[]{
				intercom.getReadFlag(),
				intercom.getId()
		});
	}
	
	
	/**
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public Intercom find(String sql,Object[] args){
		List<Intercom>  list = findAll(sql, args);
		return Validate.collectionNotNull(list) ? list.get(0) : null;
	}
	
	/**
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public List<Intercom> findAll(String sql){
		return findAll(sql,null);
	}
	
	
	/**
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public List<Intercom> findAll(String sql,Object[] args){
		return jdbcTemplate.query(sql, args, new RowMapper<Intercom>(){
			@Override
			public Intercom mapRow(ResultSet rs, int rowNum) throws SQLException {
				Intercom intercom = new Intercom();
				intercom.setId(rs.getString("id"));
				intercom.setContents(rs.getString("contents"));
				intercom.setCopier(rs.getString("copier"));
				intercom.setInform(rs.getString("inform"));
				intercom.setReplier(rs.getString("replier"));
				intercom.setTitle(rs.getString("title"));
				intercom.setType(rs.getString("type"));
				return intercom;
			}
		});
	}
	
	public Page<Intercom> pagination(final Page<Intercom> page,String sql,Object... args){
		page.setAllResult(jdbcTemplate.query(sql, args, new RowMapper<Intercom>(){
			@Override
			public Intercom mapRow(ResultSet rs, int rowNum) throws SQLException {
				Intercom intercom = new Intercom();
				intercom.setId(rs.getString("id"));
				intercom.setContents(rs.getString("contents"));
				intercom.setCopier(rs.getString("copier"));
				intercom.setInform(rs.getString("inform"));
				intercom.setReplier(rs.getString("replier"));
				intercom.setTitle(rs.getString("title"));
				intercom.setType(rs.getString("type"));
				if(rowNum==0)
					page.setRowCount(rs.getInt("rowCount"));
				return intercom;
			}}));
		return page;
	}
}
