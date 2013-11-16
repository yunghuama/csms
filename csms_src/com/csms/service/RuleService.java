package com.csms.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csms.constants.CSMSSQLConstant;
import com.csms.dao.RuleDAO;
import com.csms.domain.GloRule;
import com.csms.domain.Group;
import com.csms.domain.PreRule;
import com.csms.domain.Rule;
import com.csms.util.LoginUtils;
import com.platform.exception.CRUDException;
import com.platform.service.IService;
import com.platform.util.LoginBean;
import com.platform.vo.Page;

@Service
public class RuleService implements IService {

    private RuleDAO ruleDAO;
    
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    	Log log = LogFactory.getLog(RuleService.class);
    	log.debug(jdbcTemplate);
        ruleDAO = RuleDAO.getInstance(jdbcTemplate);
    }

    /**
     * 分页
     * 
     * @param page
     * @param superId
     * @return
     * @throws CRUDException
     */
    public Page<Rule> listPaginationRule(Page<Rule> page,String type) throws CRUDException {
        return ruleDAO.paginationRule(page, CSMSSQLConstant.RULE_SELECT_BY_PAGE_SQL, new Object[]{LoginUtils.getEnterpriseId()});
    }

    
    /**
     * 获取内容
     * @return
     * @throws CRUDException
     */
    public List<Rule> findAllRule() throws CRUDException {
        List<Rule> list = ruleDAO.findAllRule(CSMSSQLConstant.RULE_SELECT_ALL_SQL,new Object[]{LoginUtils.getEnterpriseId()});
        return list;
    }
    
    /**
     * 根据id查询内容
     * 
     * @param id
     * @return
     * @throws Exception
     */
    public Rule findRuleById(String id) throws Exception {
        return ruleDAO.findRule(CSMSSQLConstant.RULE_SELECT_BY_GROUP_ID,new String[]{id});
    }
    
    /**
     * 更新状态
     * @return
     */
    public int updateState(List<String> idList,String state){
    	for(String id:idList){
    		ruleDAO.updateStateRule(state,id);
    	}
    	return 1;
    }

    /**
     * 新建内容
     * 
     * @param department
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void saveRule(Rule rule,String ids) throws CRUDException {
    	try{
    		if(ids!=null&&!"".equals(ids)){
    			String array[] = ids.split(",");
    			for(int i=0;i<array.length;i++){
    				String id = array[i];
    				if(id.length()==32){
    					//首先删除原来的计划
    		    		ruleDAO.deleteRuleByGroup(id);
    		    		//保存新策略
    		    		Group group = new Group();
    		    		group.setId(id);
    		    		rule.setGroup(group);
    		    		ruleDAO.saveRule(rule);
    				}
    			}
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
       
    }
    
    /**
     * 
     * 更新内容
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void updateRule(Rule rule) throws CRUDException {
        ruleDAO.updateRule(rule);
    }
     

    /**
     * 删除
     * @param idList
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void deleteRule(List<String> idList){
    	ruleDAO.deleteByIdList(CSMSSQLConstant.RULE_DELETE_BY_IDS_SQL,idList);
    }
    
    //--------------全局策略
    
    /**
     * 
     * 全局策略查询
     */
    public Page<GloRule> listPaginationGloRule(Page<GloRule> page,String type) throws CRUDException {
        return ruleDAO.paginationGloRule(page, CSMSSQLConstant.GLORULE_SELECT_SQL, new Object[]{LoginUtils.getEnterpriseId()});
    }

    /**
     * 根据id查询内容
     * 
     * @param id
     * @return
     * @throws Exception
     */
    public GloRule findGloRuleById(String id) throws Exception {
        return ruleDAO.findGloRule(CSMSSQLConstant.GLORULE_SELECT_BY_ID,new String[]{id});
    }
    
    /**
     * 新建全局策略
     * 
     * @param department
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void saveGloRule(GloRule rule) throws CRUDException {
    	rule.setCreator(LoginBean.getLoginBean().getUser());
    	rule.setDepartment(LoginUtils.getEnterpriseId());
        ruleDAO.saveGloRule(rule);
    }
    
    /**
     * 
     * 更新全局策略
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void updateGloRule(GloRule rule) throws CRUDException {
        ruleDAO.updateGloRule(rule);
    }
    
    /**
     * 删除
     * @param idList
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void deleteGloRule(List<String> idList){
    	ruleDAO.deleteByIdList(CSMSSQLConstant.GLORULE_DELETE_BY_IDS_SQL,idList);
    }
    
    //-------------预先策略
    /**
     * 
     * 查询
     */
    public List<PreRule> findAllPreRule(String ruleId) throws CRUDException {
        return ruleDAO.findAllPreRule(CSMSSQLConstant.PRERULE_SELECT_BY_RULE_SQL, new Object[]{ruleId});
    }

    
    /**
     * 新建全局策略
     * 
     * @param 
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void savePreRule(PreRule rule) throws CRUDException {
    	rule.setCreator(LoginBean.getLoginBean().getUser());
        ruleDAO.savePreRule(rule);
    }
    
    
    /**
     * 删除
     * @param idList
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void deletePreRule(List<String> idList){
    	ruleDAO.deleteByIdList(CSMSSQLConstant.PRERULE_DELETE_SQL,idList);
    }
    
}