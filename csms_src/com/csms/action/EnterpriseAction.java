package com.csms.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csms.domain.District;
import com.csms.domain.Enterprise;
import com.csms.service.DistrictService;
import com.csms.service.EnterpriseService;
import com.platform.exception.CRUDException;
import com.platform.web.action.GenericAction;

@Controller
@Scope("prototype")
public class EnterpriseAction extends GenericAction<Enterprise> {

    private static final long serialVersionUID = -4867867482994497216L;

    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private DistrictService districtService;
    
    private List<Enterprise> enterpriseList;
    private List<District> districtList;
    private Enterprise enterprise;
    private String enterpriseId;
    
    
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
    	districtList = districtService.findAll();
        return SUCCESS;
    }

    public String listPagination() throws Exception {
    	try{
    	    page = enterpriseService.listPagination(page);	
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
    		enterpriseService.saveEnterprise(enterprise);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    
    public String toUpdate() throws Exception {
    	try{
    		enterprise = enterpriseService.findById(enterprise.getId());
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }
    
    public String update() throws Exception {
    	try{
    		enterpriseService.updateEnterprise(enterprise);
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
    		 enterpriseService.delete(idList);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }
    
    public String findAllEnterprise() throws CRUDException{
    	try{
    		enterpriseList = enterpriseService.findAll();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        
        return SUCCESS;
    }

	public EnterpriseService getEnterpriseService() {
		return enterpriseService;
	}

	public void setEnterpriseService(EnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	public List<Enterprise> getEnterpriseList() {
		return enterpriseList;
	}

	public void setEnterpriseList(List<Enterprise> enterpriseList) {
		this.enterpriseList = enterpriseList;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<District> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<District> districtList) {
		this.districtList = districtList;
	}
	
}