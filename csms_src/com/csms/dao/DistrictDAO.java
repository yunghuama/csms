package com.csms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.csms.constants.CSMSSQLConstant;
import com.csms.domain.District;
import com.platform.dao.GenericDAO;
import com.platform.util.Meta;
import com.platform.util.PageHelper;
import com.platform.util.UUIDGenerator;
import com.platform.vo.Page;

/**
 * <p>程序名称：       ContentDAO.java</p>
 * <p>程序说明：       集团管理DAO</p>
 * <p>时间：          2013-1-13 16:53 雨夹雪</p>	
 * 
 * @author：          cheney.mydream
 * @version：         Ver 0.1
 */
public class DistrictDAO extends GenericDAO{

	private static final long serialVersionUID = 376038639038947152L;
	private static DistrictDAO instance;
	private JdbcTemplate jdbcTemplate;
	
	public static DistrictDAO getInstance(JdbcTemplate jdbcTemplate) {
        if(instance == null) {
        	instance =  new DistrictDAO(jdbcTemplate);
        }
        return instance;
    }
	
	public DistrictDAO(JdbcTemplate jdbcTemplate){
		super(jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 分页获取内容
	 */
	public Page<District> pagination(final Page<District> page,String sql,Object[] args){
		Object[] preparedArgs = Meta.addPageToParameters(args, page);
		page.setList(jdbcTemplate.query(sql + CSMSSQLConstant.LIMIT, preparedArgs, new RowMapper<District>(){
			@Override
			public District mapRow(ResultSet rs, int rowNum) throws SQLException {
				District district = new District();
				district.setId(rs.getString("id"));
				district.setParentId(rs.getString("parentId"));
				district.setName(rs.getString("name"));
				return district;
			}}));
		int rowCount = queryForInt(Meta.getRowCountSQL(CSMSSQLConstant.DISTRICT_ROWCOUNT_SQL , sql),args);
		page.setRowCount(rowCount);
		page.setMaxPage(PageHelper.getMaxPage(rowCount, page.getPageSize()));
		return page;
	}
	
	
	
	/**
	 * 根据sql 和 args 查询
	 * 
	 */
	public List<District> findAll(String sql,Object[] args){
		return jdbcTemplate.query(sql, args, new RowMapper<District>(){
			@Override
			public District mapRow(ResultSet rs, int rowNum) throws SQLException {
				District district = new District();
				district.setId(rs.getString("id"));
				district.setParentId(rs.getString("parentId"));
				district.setName(rs.getString("name"));
				return district;
			}
		});
	}
	
	/**
	 * 根据sql 查询
	 * 
	 */
	public List<District> findAll(String sql){
		return findAll(sql,null);
	}
	
	/**
	 * 保存部门数据
	 * @param department
	 * @return
	 */
	public int save(District district){
     return jdbcTemplate.update(CSMSSQLConstant.DISTRICT_SAVE_SQL, new Object[]{
			UUIDGenerator.generate(),
			district.getName(),
			district.getParentId(),
			new Date()
		});
	}
	
	
	public int update(District district){
	     return jdbcTemplate.update(CSMSSQLConstant.DISTRICT_UPDATE_SQL, new Object[]{
	    		district.getName(),
	    		district.getParentId(),
	 			district.getId()
			});
		}
	
	/**
	 * 根据ID 查询部门
	 * @param id
	 */
	public District find(String sql,Object[] args){
		List<District> list = findAll(sql,args);
		if(list!=null&&list.size()>0)
			return list.get(0);
		return null;
	}

}