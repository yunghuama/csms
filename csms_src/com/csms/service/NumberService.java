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
import com.csms.dao.NumberDAO;
import com.csms.domain.CsmsNumber;
import com.csms.domain.Group;
import com.csms.util.LoginUtils;
import com.platform.exception.CRUDException;
import com.platform.service.IService;
import com.platform.util.LoginBean;
import com.platform.util.SearchUtil;
import com.platform.vo.Page;

@Service
public class NumberService implements IService {

    private NumberDAO numberDAO;
    private GroupDAO groupDAO;
    
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    	Log log = LogFactory.getLog(NumberService.class);
    	log.debug(jdbcTemplate);
        numberDAO = NumberDAO.getInstance(jdbcTemplate);
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
    public Page<CsmsNumber> listPagination(Page<CsmsNumber> page,String searchType, List<String> searchValue,String depId) throws CRUDException {
    	String ss = SearchUtil.getString(
                new String[]{"n.number","n.name"},//高级查询条件
                new String[]{SearchUtil.STRING_LIKE,SearchUtil.STRING_LIKE},//查询类型
                searchType,//与或类型
                searchValue);//查询值
    	return numberDAO.pagination(page, CSMSSQLConstant.NUMBER_SELECT_BY_PAGE_DEP_SQL +ss, new Object[]{depId});
    }

    public Page<CsmsNumber> listPaginationA(Page<CsmsNumber> page,String searchType, List<String> searchValue,String group) throws CRUDException {
    	String ss = SearchUtil.getString(
                new String[]{"n.number","n.name"},//高级查询条件
                new String[]{SearchUtil.STRING_LIKE,SearchUtil.STRING_LIKE},//查询类型
                searchType,//与或类型
                searchValue);//查询值
    	if(group!=null&&!"".equals(group)){
    		return numberDAO.pagination(page, CSMSSQLConstant.NUMBER_SELECT_BY_PAGE_GROUP_SQL +ss, new Object[]{group});
    	}else {
    		return numberDAO.pagination(page, CSMSSQLConstant.NUMBER_SELECT_BY_PAGE_DEP_SQL +ss, new Object[]{LoginUtils.getEnterpriseId()});
    	}
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
    	return numberDAO.findAll(CSMSSQLConstant.NUMBER_SELECT_ALL_SQL,new Object[]{LoginUtils.getEnterpriseId()});
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
     * 修改号码的组群和备注
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void updateNumber(String number,String group,String name,String remark){
    	//如果group是空，则查询默认部门
    	if("".equals(group)){
    		Group defaultGroup = groupDAO.find(CSMSSQLConstant.GROUP_DEFAULT_SELECT, new Object[]{LoginUtils.getEnterpriseId()});
    		if(defaultGroup!=null)
    		numberDAO.update(number, defaultGroup.getId(), name, remark, LoginUtils.getEnterpriseId());
    	}else {
    		Group defaultGroup = groupDAO.find(CSMSSQLConstant.GROUP_SELECT_NAME_DEPART, new Object[]{group,LoginUtils.getEnterpriseId()});
    		if(defaultGroup!=null)
    		numberDAO.update(number, defaultGroup.getId(), name, remark, LoginUtils.getEnterpriseId());
    	}
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