package com.csms.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csms.constants.CSMSSQLConstant;
import com.csms.dao.DistrictDAO;
import com.csms.domain.District;
import com.platform.exception.CRUDException;
import com.platform.service.IService;
import com.platform.vo.Page;

@Service
public class DistrictService implements IService {

    private DistrictDAO districtDAO;
    
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    	Log log = LogFactory.getLog(DistrictService.class);
    	log.debug(jdbcTemplate);
        districtDAO = districtDAO.getInstance(jdbcTemplate);
    }

    /**
     * 分页
     * 
     * @param page
     * @param superId
     * @return
     * @throws CRUDException
     */
    public Page<District> listPagination(Page<District> page,String parentId) throws CRUDException {
    	if(parentId==null)
        return districtDAO.pagination(page, CSMSSQLConstant.DISTRICT_SELECT_ALL,null);
    	else 
    	return districtDAO.pagination(page, CSMSSQLConstant.DISTRICT_SELECT_ALL_BY_PARENT,new Object[]{parentId});	
    }
    

    /**
     * 根据id查询内容
     * 
     * @param id
     * @return
     * @throws Exception
     */
    public District findById(String id) throws Exception {
        return districtDAO.find(CSMSSQLConstant.DISTRICT_SELECT_BY_ID,new String[]{id});
    }
    
    public List<District> findAll(){
    	return districtDAO.findAll(CSMSSQLConstant.DISTRICT_SELECT_ALL);
    }

    public List<District> findByType(String type){
    	return districtDAO.findAll(CSMSSQLConstant.DISTRICT_SELECT_BY_TYPE,new Object[]{type});
    }
    
    /**
     * 新建内容
     * 
     * @param department
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void saveDistrict(District district) throws CRUDException {
        districtDAO.save(district);
    }

    
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void updateDistrict(District district) throws CRUDException {
        districtDAO.update(district);
    }
    
    /**
     * 删除
     * @param idList
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void delete(List<String> idList){
    	districtDAO.deleteByIdList(CSMSSQLConstant.DISTRICT_DELETE_BY_IDS,idList);
    }
}