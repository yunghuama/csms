package com.platform.dao;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.platform.constants.SQLConstant;
import com.platform.domain.AttachedFile;
import com.platform.exception.CRUDException;
import com.platform.util.UUIDGenerator;
import com.platform.util.Validate;
import com.platform.vo.Page;

/**
 * <p>程序名称：       AttachedFileDAO.java</p>
 * <p>程序说明：       附件DAO</p>
 * <p>时间：          2012-12-14 16:53</p>	
 * 
 * @author：          cheney.mydream
 * @version：         Ver 0.1
 */
public class AttachedFileDAO extends GenericDAO{

	private static final long serialVersionUID = 5009503854192117528L;
	
	private JdbcTemplate jdbcTemplate;
	private static AttachedFileDAO instance;
	
	public static AttachedFileDAO getInstance(JdbcTemplate jdbcTemplate) {
        if(instance == null) {
        	instance =  new AttachedFileDAO(jdbcTemplate);
        }
        return instance;
    }
	
	public AttachedFileDAO(JdbcTemplate jdbcTemplate){
		super(jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 删除附件
	 * @param af
	 * @param f
	 * @throws CRUDException
	 */
	public void deleteAF(AttachedFile af, File f) throws CRUDException{
       deleteByProperty(SQLConstant.ATTACHEDFILE_DELETE_BY_ID_SQL,af.getId());
        if (f.exists()) {
            f.delete();
        }
    }
	
	
	/**
	 * 保存
	 * @param af
	 * @return
	 */
	public int save(AttachedFile af){
		return jdbcTemplate.update(SQLConstant.ATTACHEDFILE_SAVE_SQL, new Object[]{
			UUIDGenerator.generate(),
			af.getTitle(),
			af.getFileName(),
			af.getFilePath(),
			af.getFileType(),
			af.getContentType(),
			af.getSuperId(),
			af.getCreateTime(),
			af.getExtendType()
		});
	}
	
	/**
	 * 保存
	 * @param af
	 * @return
	 */
	public int update(AttachedFile af){
		return jdbcTemplate.update(SQLConstant.ATTACHEDFILE_UPDATE_SQL, new Object[]{
				af.getTitle(),
				af.getFileName(),
				af.getFilePath(),
				af.getFileType(),
				af.getContentType(),
				af.getSuperId(),
				af.getExtendType(),
				af.getId()
			});
	}
	
	/**
	 * 查询用户信息
	 * @param sql 要查询的sql
	 * @param args sql的匹配条件
	 * @return
	 */
	public AttachedFile find(String sql,Object[] args){
		List<AttachedFile> list  = findAll(sql,args);
		if(Validate.collectionNotNull(list)){
			return list.get(0);
		}
		
		return null;
	}
	
	/**
	 * 根据sql查询用户
	 * @param sql
	 * @return
	 */
	public AttachedFile find(String sql){
		return find(sql,null);
	}
	
	/**
	 * 根据sql和查询条件查询所有
	 * @param sql
	 * @param args
	 * @return
	 */
	public List<AttachedFile> findAll(String sql , Object[] args){
		return jdbcTemplate.query(sql, args, new RowMapper<AttachedFile>(){
			@Override
			public AttachedFile mapRow(ResultSet rs, int rowNum) throws SQLException {
				AttachedFile af = new AttachedFile();
				af.setId(rs.getString("id"));
				af.setFileName(rs.getString("filename"));
				af.setFilePath(rs.getString("filepath"));
				af.setFileType(rs.getString("filetype"));
				af.setSuperId(rs.getString("superid"));
				af.setExtendType(rs.getString("extendtype"));
				return af;
			}
		});
	}
	
	public List<AttachedFile> findAll(String sql){
		return findAll(sql,null);
	}
	
	
	/**
	 * 分页查询
	 * @param page
	 * @param sql
	 * @param args
	 * @return
	 */
	public Page<AttachedFile> pagination(final Page<AttachedFile> page,String sql,Object... args){
		page.setAllResult(jdbcTemplate.query(sql, args, new RowMapper<AttachedFile>(){
			@Override
			public AttachedFile mapRow(ResultSet rs, int rowNum) throws SQLException {
				AttachedFile af = new AttachedFile();
				af.setId(rs.getString("id"));
				af.setFileName(rs.getString("filename"));
				af.setFilePath(rs.getString("filepath"));
				af.setFileType(rs.getString("filetype"));
				af.setSuperId(rs.getString("superid"));
				af.setExtendType(rs.getString("extendtype"));
				if(rowNum==0)
					page.setRowCount(rs.getInt("rowCount"));
				return af;
			}}));
		return page;
	}
	
	
	public List<String> findIDS(String sql,Object[] args){
		return jdbcTemplate.query(sql, args, new RowMapper<String>(){
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("id");
			}
		});
	}
}
