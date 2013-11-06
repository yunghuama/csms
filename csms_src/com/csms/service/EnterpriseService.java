package com.csms.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csms.constants.CSMSSQLConstant;
import com.csms.dao.EnterpriseDAO;
import com.csms.domain.Enterprise;
import com.csms.util.LoginUtils;
import com.platform.exception.CRUDException;
import com.platform.service.IService;
import com.platform.util.LoginBean;
import com.platform.util.Validate;
import com.platform.vo.Page;

@Service
public class EnterpriseService implements IService {

    private EnterpriseDAO enterpriseDAO;
    
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    	Log log = LogFactory.getLog(EnterpriseService.class);
    	log.debug(jdbcTemplate);
        enterpriseDAO = enterpriseDAO.getInstance(jdbcTemplate);
    }

    /**
     * 分页
     * 
     * @param page
     * @param superId
     * @return
     * @throws CRUDException
     */
    public Page<Enterprise> listPagination(Page<Enterprise> page) throws CRUDException {
        return enterpriseDAO.pagination(page, CSMSSQLConstant.ENTERPRISE_SELECT_BY_PAGE_SQL,null);
    }
    

    /**
     * 根据id查询内容
     * 
     * @param id
     * @return
     * @throws Exception
     */
    public Enterprise findById(String id) throws Exception {
        return enterpriseDAO.find(CSMSSQLConstant.ENTERPRISE_SELECT_BY_ID,new String[]{id});
    }
    
    public List<Enterprise> findAll(){
    	return enterpriseDAO.findAll(CSMSSQLConstant.ENTERPRISE_SELECT_BY_PAGE_SQL);
    }

    /**
     * 新建内容
     * 
     * @param department
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void saveEnterprise(Enterprise enterprise) throws CRUDException {
        enterpriseDAO.save(enterprise);
    }

    
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void updateEnterprise(Enterprise enterprise) throws CRUDException {
        enterpriseDAO.update(enterprise);
    }
    
    /**
     * 删除
     * @param idList
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void delete(List<String> idList){
    	enterpriseDAO.deleteByIdList(CSMSSQLConstant.ENTERPRISE_DELETE_BY_IDS_SQL,idList);
    }
}