package com.platform.web.ajax;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.platform.service.DeleteValidateService;

@Controller
@Scope("prototype")
public class DeleteValidateAjaxAction {

    @Autowired
    private DeleteValidateService deleteValidateService;

    private List<String> idList;
    private String tableName;
    private List<String> cantDeleteList;

    /**
     * 验证是否可删
     * 
     * @return
     * @throws Exception
     */
    public String deleteValidate() throws Exception {
    	try{
    		cantDeleteList = deleteValidateService.findCannotDeleteList(idList, tableName).get(1);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        
        return Action.SUCCESS;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getCantDeleteList() {
        return cantDeleteList;
    }
}