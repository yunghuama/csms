package com.csms.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csms.constants.CSMSSQLConstant;
import com.csms.dao.NumberDAO;
import com.csms.domain.CsmsNumber;
import com.csms.util.LoginUtils;
import com.platform.exception.CRUDException;
import com.platform.service.IService;
import com.platform.util.LoginBean;
import com.platform.util.Validate;
import com.platform.vo.Page;

@Service
public class NumberService implements IService {

    private NumberDAO numberDAO;
    
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    	Log log = LogFactory.getLog(NumberService.class);
    	log.debug(jdbcTemplate);
        numberDAO = NumberDAO.getInstance(jdbcTemplate);
    }

    /**
     * 分页
     * 
     * @param page
     * @param superId
     * @return
     * @throws CRUDException
     */
    public Page<CsmsNumber> listPagination(Page<CsmsNumber> page,String depId) throws CRUDException {
    	return numberDAO.pagination(page, CSMSSQLConstant.NUMBER_SELECT_BY_PAGE_DEP_SQL, new Object[]{depId});
    }

    public Page<CsmsNumber> listPaginationA(Page<CsmsNumber> page,String depId) throws CRUDException {
    	return numberDAO.pagination(page, CSMSSQLConstant.NUMBER_SELECT_BY_PAGE_GROUP_SQL, new Object[]{depId});
    }
    
    /**
     * 根据id查询内容
     * 
     * @param id
     * @return
     * @throws Exception
     */
    public CsmsNumber findById(String id) throws Exception {
        return numberDAO.find(CSMSSQLConstant.NUMBER_SELECT_BY_ID_SQL,new String[]{id});
    }
    
    
    public int findCountByNumber(String number) throws Exception {
    	return numberDAO.findCountByNumber(number);
    }
    
    public List<CsmsNumber> findAll(){
    	return numberDAO.findAll(CSMSSQLConstant.NUMBER_SELECT_ALL_SQL,new Object[]{LoginUtils.getDepartmentId()});
    }

    /**
     * 新建
     * 
     * @param department
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void saveNumber(CsmsNumber number) throws CRUDException {
    	number.setCreator(LoginBean.getLoginBean().getUser());
        numberDAO.save(number);
    }
    
    
    /**
     * 
     * 
     * @param department
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void updateNumber(CsmsNumber number) throws CRUDException {
        numberDAO.update(number);
    }
    
    /**
     * 
     * 
     * @param department
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void updateNumberA(CsmsNumber number) throws CRUDException {
        numberDAO.updateA(number);
    }

    /**
     * 删除
     * @param idList
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void delete(List<String> idList){
    	numberDAO.deleteByIdList(CSMSSQLConstant.NUMBER_DELETE_BY_IDS_SQL,idList);
    }
}