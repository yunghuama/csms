package com.csms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csms.dao.SysDAO;
import com.csms.domain.Warn;
import com.platform.service.IService;
import com.platform.vo.Page;

@Service
public class SysService implements IService {

    private SysDAO sysDAO;
    
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        sysDAO = SysDAO.getInstance(jdbcTemplate);
    }
     

    /**
     * 保存警告
     * @param idList
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public int saveWarn(double cpu,long memusage,int storage,long memtotal){
        return sysDAO.saveWarn(cpu,memusage,storage,memtotal);
    }
    
    public Page<Warn> listPagination(Page page){
    	return sysDAO.paginationWarn(page);
    }
}