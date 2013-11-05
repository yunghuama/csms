package com.csms.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csms.constants.CSMSSQLConstant;
import com.csms.dao.GroupDAO;
import com.csms.domain.Group;
import com.csms.util.LoginUtils;
import com.platform.constants.SQLConstant;
import com.platform.constants.StringConstant;
import com.platform.exception.CRUDException;
import com.platform.service.IService;
import com.platform.util.LoginBean;
import com.platform.util.Meta;
import com.platform.util.Validate;
import com.platform.vo.Page;

@Service
public class GroupService implements IService {

    private GroupDAO groupDAO;
    
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    	Log log = LogFactory.getLog(GroupService.class);
    	log.debug(jdbcTemplate);
    	groupDAO = GroupDAO.getInstance(jdbcTemplate);
    }

    /**
     * 分页
     * 
     * @param page
     * @param superId
     * @return
     * @throws CRUDException
     */
    public Page<Group> listPagination(Page<Group> page,String type) throws CRUDException {
        return groupDAO.pagination(page, CSMSSQLConstant.GROUP_SELECT_BY_PAGE_SQL,new Object[]{LoginUtils.getDepartmentId()});
    }
    
    /**
     * 根据id查询内容
     * 
     * @param id
     * @return
     * @throws Exception
     */
    public Group findById(String id) throws Exception {
        return groupDAO.find(CSMSSQLConstant.GROUP_SELECT_BY_ID,new String[]{id});
    }
    
    public List<Group> findAll(){
    	return findAll(LoginUtils.getDepartmentId());
    }
    
    public List<Group> findAll(String depId){
    	return groupDAO.findAll(CSMSSQLConstant.GROUP_SELECT_ALL_SQL,new Object[]{depId});
    }

    /**
     * 新建内容
     * 
     * @param department
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void save(Group group) throws CRUDException {
    	group.setCreator(LoginBean.getLoginBean().getUser());
    	group.setDepartment(LoginUtils.getDepartmentId());
    	group.setType("1");
        groupDAO.save(group);
    }
    
    /**
     * 新建内容
     * 
     * @param department
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void update(Group group) throws CRUDException {
        groupDAO.update(group);
    }

    /**
     * 删除
     * @param idList
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void delete(List<String> idList){
    	groupDAO.deleteByIdList(CSMSSQLConstant.GROUP_DELETE_BY_IDS_SQL,idList);
    }
}