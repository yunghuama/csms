package com.csms.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csms.domain.Content;
import com.csms.domain.GloRule;
import com.csms.service.ContentService;
import com.csms.service.RuleService;
import com.platform.exception.CRUDException;
import com.platform.util.LoginBean;
import com.platform.web.action.GenericAction;

@Controller
@Scope("prototype")
public class GloRuleAction extends GenericAction<GloRule> {

    private static final long serialVersionUID = -4867867482994497216L;

    @Autowired
    private RuleService ruleService;
    @Autowired
    private ContentService contentService;
    private List<GloRule> ruleList;
    private List<Content> contentList;
    private GloRule rule;
    private String type;
    
    public String list() throws Exception {
    	
        return SUCCESS;
    }
    
    /**
     * 部门树
     * 
     * @return
     * @throws Exception
     */
    public String listTree() throws Exception {
    	
        return SUCCESS;
    }

    public String listPagination() throws Exception {
    	try{
    	    page = ruleService.listPaginationGloRule(page,type);	
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }
    

    /**
     * 预新建
     * 
     * @return
     * @throws Exception
     */
    public String toSave() throws Exception {
    	contentList = contentService.findByState("1");
        return SUCCESS;
    }

    /**
     * 新建
     * 
     * @return
     * @throws Exception
     */
    public String save() throws Exception {
    	try{
    		if(!"0".equals(rule.getState()))
    			rule.setState("1");
    		rule.setDepartment(LoginBean.getLoginBean().getUser().getDepartment().getId());
    		ruleService.saveGloRule(rule);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    /**
     * 预修改
     * 
     * @return
     * @throws Exception
     */
    public String toUpdate() throws Exception {
    	try{
    		contentList = contentService.findByState("1");
    		rule = ruleService.findGloRuleById(rule.getId());
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    /**
     * 修改
     * 
     * @return
     * @throws Exception
     */
    public String update() throws Exception {
    	try{
    		if(!"0".equals(rule.getState()))
    			rule.setState("1");
    		ruleService.updateGloRule(rule);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    /**
     * 删除
     * 
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {
    	try{
    		 ruleService.deleteGloRule(idList);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

	public RuleService getRuleService() {
		return ruleService;
	}

	public void setRuleService(RuleService ruleService) {
		this.ruleService = ruleService;
	}

	public ContentService getContentService() {
		return contentService;
	}

	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}

	public List<GloRule> getRuleList() {
		return ruleList;
	}

	public void setRuleList(List<GloRule> ruleList) {
		this.ruleList = ruleList;
	}

	public List<Content> getContentList() {
		return contentList;
	}

	public void setContentList(List<Content> contentList) {
		this.contentList = contentList;
	}

	public GloRule getRule() {
		return rule;
	}

	public void setRule(GloRule rule) {
		this.rule = rule;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}