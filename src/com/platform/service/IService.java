package com.platform.service;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Service接口
 * 
 * @author cheney.mydream
 *
 */
public interface IService {

    /**
     * 注入所需jdbcTemplate,以实例化DAO
     * 
     * @param jdbcTemplate
     */
    public abstract void setJdbcTemplate(JdbcTemplate jdbcTemplate);
}