package com.csms.action.ajax;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csms.domain.Content;
import com.csms.domain.PreRule;
import com.csms.service.ContentService;
import com.csms.service.RuleService;
import com.platform.web.action.GenericAction;

@Controller
@Scope("prototype")
public class PreRuleAction extends GenericAction<PreRule> {

    private static final long serialVersionUID = -4867867482994497216L;

    @Autowired
    private RuleService ruleService;
    @Autowired
    private ContentService contentService;
    private List<PreRule> ruleList;
    private List<Content> contentList;
    private PreRule rule;
    private String ruleId;
    private String flag;

    public String findAll() throws Exception {
    	try{
    	    ruleList = ruleService.findAllPreRule(ruleId);
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
    		ruleService.savePreRule(rule);
    		flag = "success";
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
    public void delete() throws Exception {
    	try{
    		 ruleService.deletePreRule(idList);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
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

	public List<PreRule> getRuleList() {
		return ruleList;
	}

	public void setRuleList(List<PreRule> ruleList) {
		this.ruleList = ruleList;
	}

	public List<Content> getContentList() {
		return contentList;
	}

	public void setContentList(List<Content> contentList) {
		this.contentList = contentList;
	}

	public PreRule getRule() {
		return rule;
	}

	public void setRule(PreRule rule) {
		this.rule = rule;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}


	public String getFlag() {
		return flag;
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}

}