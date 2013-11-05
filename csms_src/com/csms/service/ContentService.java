package com.csms.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csms.constants.CSMSSQLConstant;
import com.csms.dao.ContentDAO;
import com.csms.domain.Content;
import com.csms.util.LoginUtils;
import com.platform.exception.CRUDException;
import com.platform.service.IService;
import com.platform.util.LoginBean;
import com.platform.util.Validate;
import com.platform.vo.Page;

@Service
public class ContentService implements IService {

    private ContentDAO contentDAO;
    
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    	Log log = LogFactory.getLog(ContentService.class);
    	log.debug(jdbcTemplate);
        contentDAO = ContentDAO.getInstance(jdbcTemplate);
    }

    /**
     * 分页
     * 
     * @param page
     * @param superId
     * @return
     * @throws CRUDException
     */
    public Page<Content> listPagination(Page<Content> page,String type) throws CRUDException {
    	if(Validate.notNull(type))
    	return contentDAO.pagination(page, CSMSSQLConstant.CONTENT_SELECT_BY_PAGE_STATE_SQL, new Object[]{LoginUtils.getDepartmentId(),type});
    	else
        return contentDAO.pagination(page, CSMSSQLConstant.CONTENT_SELECT_BY_PAGE_SQL ,new Object[]{LoginUtils.getDepartmentId()});
    }
    
    /**
     * 分页
     * 
     * @param page
     * @param superId
     * @return
     * @throws CRUDException
     */
    public Page<Content> listPaginationA(Page<Content> page,String type) throws CRUDException {
    	return contentDAO.pagination(page, CSMSSQLConstant.CONTENT_SELECT_BY_PAGE_UNCHECKED_SQL, new Object[]{"0"});
    }

    public List<Content> findByState(String state){
    	return contentDAO.findAll(CSMSSQLConstant.CONTENT_SELECT_BY_STATE, new String[]{LoginUtils.getDepartmentId(),state});
    }
    
    /**
     * 根据id查询内容
     * 
     * @param id
     * @return
     * @throws Exception
     */
    public Content findById(String id) throws Exception {
        return contentDAO.find(CSMSSQLConstant.CONTENT_SELECT_BY_ID,new String[]{id});
    }
    
    public List<Content> findAll(){
    	return contentDAO.findAll(CSMSSQLConstant.CONTENT_SELECT_ALL_SQL,new Object[]{LoginUtils.getDepartmentId()});
    }

    /**
     * 新建内容
     * 
     * @param department
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void saveContent(Content content) throws CRUDException {
    	content.setCreator(LoginBean.getLoginBean().getUser());
    	content.setState("0");
    	content.setDepartment(LoginUtils.getDepartmentId());
        contentDAO.save(content);
    }

    
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void updateContent(Content content) throws CRUDException {
        contentDAO.update(content);
    }
    
    /**
     * 删除
     * @param idList
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void delete(List<String> idList){
    	contentDAO.deleteByIdList(CSMSSQLConstant.CONTENT_DELETE_BY_IDS,idList);
    }
}