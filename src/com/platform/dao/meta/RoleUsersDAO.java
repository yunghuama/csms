package com.platform.dao.meta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.platform.constants.SQLConstant;
import com.platform.dao.GenericDAO;
import com.platform.domain.Role;
import com.platform.domain.RoleUsers;
import com.platform.domain.Users;
import com.platform.exception.CRUDException;
import com.platform.util.UUIDGenerator;
import com.platform.util.Validate;


public class RoleUsersDAO extends GenericDAO {

	private static final long serialVersionUID = 622832120994917754L;
	private static RoleUsersDAO instance;
	private JdbcTemplate jdbcTemplate;
	
	public static RoleUsersDAO getInstance(JdbcTemplate jdbcTemplate) {
        if(instance == null) {
            instance =  new RoleUsersDAO(jdbcTemplate);
        }
        return instance;
    }
	
    /**
     * 构造函数
     * 
     * @param sessionFactory
     */
    private RoleUsersDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
    }
    
    /**
     * 根据用户ID查找用户权限
     * 
     * @param usersId
     * @return
     * @throws CRUDException
     */
    public List<RoleUsers> findUsersRoleByUsers(String usersId) throws CRUDException {
        return findAll(SQLConstant.ROLEUSERS_BY_USERS_SQL, new String[]{usersId});
    }

    /**
     * 
     * @param sql
     * @param args
     * @return
     */
    public RoleUsers find(String sql , Object[] args){
    	List<RoleUsers>  list = findAll(sql,args);
    	return Validate.collectionNotNull(list)? list.get(0) : null;
    }
    
	
	/**
	 * 根据sql和查询条件查询所有
	 * @param sql
	 * @param args
	 * @return
	 */
	public List<RoleUsers> findAll(String sql , Object[] args){
		return jdbcTemplate.query(sql, args, new RowMapper<RoleUsers>(){
			@Override
			public RoleUsers mapRow(ResultSet rs, int rowNum) throws SQLException {
				RoleUsers ru = new RoleUsers();
				ru.setId(rs.getString("id"));
				Role r = new Role();
				r.setId(rs.getString("roleId"));
				ru.setRole(r);
				Users u = new Users();
				u.setId(rs.getString("usersid"));
				ru.setUsers(u);
				return ru;
			}
		});
	}
	
	public List<RoleUsers> findAll(String sql){
		return findAll(sql,null);
	}
    
	/**
	 * 删除集合
	 * @param collection
	 * @return
	 */
	public int deleteAll(Collection<RoleUsers> collection){
		int deleteCount = 0;
		if(!Validate.collectionNotNull(collection)) return deleteCount;
		
		for(RoleUsers roleUsers : collection){
			if(roleUsers!=null&&roleUsers.getId()!=null)
			deleteCount += deleteByProperty(SQLConstant.ROLEUSERS_DELETE_BY_ID,roleUsers.getId());
		}
		return deleteCount;
	}
	
	/**
	 * 
	 * @param roleUsers
	 * @return
	 */
	public int save(RoleUsers roleUsers){
		return jdbcTemplate.update(SQLConstant.ROLEUSERS_SAVE_SQL, new Object[]{
			UUIDGenerator.generate(),
			roleUsers.getRole().getId(),
			roleUsers.getUsers().getId()
		});
	}
	
	
	public int save(String roleId,String usersId){
		return jdbcTemplate.update(SQLConstant.ROLEUSERS_SAVE_SQL, new Object[]{
				UUIDGenerator.generate(),
				roleId,
				usersId
			});
	}
	
	
	/**
	 * 根据角色删除
	 * @param role
	 * @return
	 */
	public int deleteByRole(String roleId){
		if(!Validate.notNull(roleId))
			return 0;
		return jdbcTemplate.update(SQLConstant.ROLEUSERS_DELETE_BY_ROLEID, roleId);
	}
}