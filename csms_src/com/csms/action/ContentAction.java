package com.csms.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csms.domain.Content;
import com.csms.service.ContentService;
import com.platform.exception.CRUDException;
import com.platform.web.action.GenericAction;

@Controller
@Scope("prototype")
public class ContentAction extends GenericAction<Content> {

    private static final long serialVersionUID = -4867867482994497216L;

    @Autowired
    private ContentService contentService;
    private List<Content> contentList;
    private Content content;
    private String contentId;
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
    	    page = contentService.listPagination(page,type);	
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    public String listPaginationA() throws Exception {
    	try{
    	    page = contentService.listPaginationA(page,type);	
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
    		contentService.saveContent(content);
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
    		 
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }
    
    public String toUpdateA() throws Exception {
    	try{
    		content = contentService.findById(content.getId());
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
//    		contentService.updateDepartment(content);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }
    
    public String updateA() throws Exception {
    	try{
    		contentService.updateContent(content);
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
    		 contentService.delete(idList);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }
    
    public String findAllContent() throws CRUDException{
    	try{
    		contentList = contentService.findAll();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        
        return SUCCESS;
    }

	public ContentService getContentService() {
		return contentService;
	}

	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}

	public List<Content> getContentList() {
		return contentList;
	}

	public void setContentList(List<Content> contentList) {
		this.contentList = contentList;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
    
}