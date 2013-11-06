package com.csms.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csms.domain.District;
import com.csms.service.DistrictService;
import com.platform.exception.CRUDException;
import com.platform.web.action.GenericAction;

@Controller
@Scope("prototype")
public class DistrictAction extends GenericAction<District> {

    private static final long serialVersionUID = -4867867482994497216L;

    @Autowired
    private DistrictService districtService;
    private List<District> districtList;
    private District district;
    private String districtId;
    
    
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
    	    page = districtService.listPagination(page);	
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
    		districtService.saveDistrict(district);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    
    public String toUpdate() throws Exception {
    	try{
    		district = districtService.findById(district.getId());
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    
    public String update() throws Exception {
    	try{
    		districtService.updateDistrict(district);
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
    		 districtService.delete(idList);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }
    
    public String findAllDistrict() throws CRUDException{
    	try{
    		districtList = districtService.findAll();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        
        return SUCCESS;
    }

	
}