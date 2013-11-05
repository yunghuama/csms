package com.platform.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.platform.constants.SQLConstant;
import com.platform.domain.Department;
import com.platform.domain.SysMesUser;
import com.platform.domain.VmetaOperate;
import com.platform.util.UUIDGenerator;
import com.platform.util.Validate;
import com.platform.vo.Page;

/**
 * <p>程序名称：       SysMesUserDAO.java</p>
 * <p>程序说明：       系统消息用户DAO</p>
 * <p>时间：          2012-12-17 11:29</p>	
 * 
 * @author：          cheney.mydream
 * @version：         Ver 0.1
 */
public class SysMesUserDAO extends GenericDAO {

	private static final long serialVersionUID = -3211255263198762483L;
	private static SysMesUserDAO instance;
	private JdbcTemplate jdbcTemplate;
	
	public static SysMesUserDAO getInstance(JdbcTemplate jdbcTemplate) {
        if(instance == null) {
            instance =  new SysMesUserDAO(jdbcTemplate);
        }
        return instance;
    }
	
	private SysMesUserDAO(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public SysMesUser find(String sql,Object[] args){
		List<SysMesUser> list = findAll(sql,args);
		return Validate.collectionNotNull(list) ? list.get(0) : null;
	}

	/**
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public List<SysMesUser> findAll(String sql,Object[] args){
		return jdbcTemplate.query(sql, args, new RowMapper<SysMesUser>(){
			@Override
			public SysMesUser mapRow(ResultSet rs, int rowNum) throws SQLException {
				SysMesUser sysMesUser = new SysMesUser();
				sysMesUser.setId(rs.getString("id"));
				sysMesUser.setReadFlag(rs.getString("readflag"));
//				sysMesUser.setSysMessage(sysMessage)
//				sysMesUser.setUsers(users)
				return sysMesUser;
			}
		});
	}
	
	
	/**
	 * 
	 * @param page
	 * @param sql
	 * @param args
	 * @return
	 */
	public Page<SysMesUser> pagination(final Page<SysMesUser> page,String sql,Object... args){
		page.setAllResult(jdbcTemplate.query(sql, args, new RowMapper<SysMesUser>(){
			@Override
			public SysMesUser mapRow(ResultSet rs, int rowNum) throws SQLException {
				SysMesUser sysMesUser = new SysMesUser();
				sysMesUser.setId(rs.getString("id"));
				sysMesUser.setReadFlag(rs.getString("readflag"));
//				sysMesUser.setSysMessage(sysMessage)
//				sysMesUser.setUsers(users)
				if(rowNum==0)
					page.setRowCount(rs.getInt("rowCount"));
				return sysMesUser;
			}}));
		return page;
	}
	
	/**
	 * 保存
	 * @param sysMesUser
	 * @return
	 */
	public int save(SysMesUser sysMesUser){
		return jdbcTemplate.update(SQLConstant.SYSMESUSER_SAVE_SQL, new Object[]{
			UUIDGenerator.generate(),
			sysMesUser.getUsers().getId(),
			sysMesUser.getSysMessage().getId(),
			sysMesUser.getReadFlag()
		});
	}

	/**
	 * 更新
	 * @param sysMesUser
	 * @return
	 */
	public int update(SysMesUser sysMesUser){
		return jdbcTemplate.update(SQLConstant.SYSMESUSER_UPDATE_SQL, new Object[]{
			sysMesUser.getUsers().getId(),
			sysMesUser.getSysMessage().getId(),
			sysMesUser.getReadFlag(),
			sysMesUser.getId()
		});
	}
}
