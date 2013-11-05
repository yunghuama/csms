package com.platform.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import com.platform.domain.SysMessage;


/**
 * <p>程序名称：       SysMessageDAO.java</p>
 * <p>程序说明：       系统消息DAO</p>
 * <p>时间：          2012-12-17 11:17</p>	
 * 
 * @author：         cheney.mydream
 * @version：         Ver 0.1
 */
public class SysMessageDAO extends GenericDAO{

	private static final long serialVersionUID = 9184364375826586670L;
	private static SysMessageDAO instance;
	
	public static SysMessageDAO getInstance(JdbcTemplate jdbcTemplate) {
        if(instance == null) {
            instance =  new SysMessageDAO(jdbcTemplate);
        }
        return instance;
    }
	
	private SysMessageDAO(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

}
