package com.platform.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.platform.constants.StringConstant;
import com.platform.domain.Dictionary;
import com.platform.service.DictionaryService;
import com.platform.util.Validate;

@Controller
@Scope("prototype")
public class DictionaryAction extends GenericAction<Dictionary> {

    private static final long serialVersionUID = 8602178539587010482L;
    @Autowired
    private DictionaryService dictionaryService;

    private List<Dictionary> dictionaryList;
    private Dictionary dictionary;
    private String superId;
    private String syncUpdate;
    private String syncDelete;

    /** 总列表 */
    public String list() throws Exception {
        return SUCCESS;
    }

    /** 树形菜单 */
    public String listTree() throws Exception {
    	try{
    		 dictionaryList = dictionaryService.findDictionaryBySuperId(StringConstant.ROOT_ID);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    /** 列表分页 */
    public String listPagination() throws Exception {
    	try{
		 if (Validate.notNull(azparam))
	            page = dictionaryService.paginationByPY(page, "name", azparam);
	        else
	            page = dictionaryService.listPagination(page, superId);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    /** 预加载新建 */
    public String toSave() throws Exception {
    	try{
    		dictionaryList = dictionaryService.findDictionaryBySuperId(StringConstant.ROOT_ID);
    		//添加根字典
    		Dictionary root = new Dictionary();
    		root.setId(StringConstant.ROOT_ID);
    		root.setName("根字典");
    		root.setSortNum(-1);
    		dictionaryList.add(root);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    /** 新建字典 */
    public String save() throws Exception {
    	try{
    		dictionaryService.saveDictionary(dictionary);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    /** 预加载修改 */
    public String toUpdate() throws Exception {
    	try{
    		dictionary = dictionaryService.findDictionaryById(dictionary.getId());
            dictionaryList = dictionaryService.findDictionaryBySuperId(StringConstant.ROOT_ID);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    /** 修改字典 */
    public String update() throws Exception {
    	try{
    		 dictionaryService.updateDictionary(dictionary, syncUpdate);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    /** 批量删除 */
    public String delete() throws Exception {
    	try{
    		dictionaryService.deleteDictionary(idList, syncDelete);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    /** 预加载排序 */
    public String toUpdateSort() throws Exception {
    	try{
    		dictionaryList = dictionaryService.findDictionaryBySuperId(superId);	
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    /** 排序更新 */
    public String updateSort() throws Exception {
    	try{
    		dictionaryService.updateSortDictionary(idList);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    public String getSuperId() {
        return superId;
    }

    public void setSuperId(String superId) {
        this.superId = superId;
    }

    public List<Dictionary> getDictionaryList() {
        return dictionaryList;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public void setSyncUpdate(String syncUpdate) {
        this.syncUpdate = syncUpdate;
    }

    public void setSyncDelete(String syncDelete) {
        this.syncDelete = syncDelete;
    }
}