package com.csms.action;

import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csms.domain.CsmsNumber;
import com.csms.domain.District;
import com.csms.domain.Enterprise;
import com.csms.service.DistrictService;
import com.csms.service.EnterpriseService;
import com.csms.service.NumberService;
import com.platform.domain.AttachedFile;
import com.platform.domain.Users;
import com.platform.exception.CRUDException;
import com.platform.service.RoleUsersService;
import com.platform.service.UsersService;
import com.platform.util.UploadHelper;
import com.platform.web.action.GenericAction;

@Controller
@Scope("prototype")
public class EnterpriseAction extends GenericAction<Enterprise> {

    private static final long serialVersionUID = -4867867482994497216L;

    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private DistrictService districtService;
    @Autowired
    private NumberService numberService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private RoleUsersService roleUsersService;
    
    private List<Enterprise> enterpriseList;
    private List<District> districtList;
    private Enterprise enterprise;
    private String enterpriseId;
    private Users users;
    private String districtId;
    private String errorInfo;
    private CsmsNumber number;
    
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
    	    page = enterpriseService.listPagination(page,districtId);	
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
    	districtList = districtService.findAll();
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
    		districtList = districtService.findAll();
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
    
    /**
     * 预更新企业管理员
     * @return
     */
    public String toUpdateUsers(){
    	users = usersService.findUsersByEnterprise(enterprise.getId());
    	return SUCCESS;
    }

    /**
     * 保存企业管理员
     * @return
     * @throws Exception
     */
    public String updateUsers() throws Exception{
    	if(users.getId()!=null&&users.getId().length()==32){
    		usersService.updateUsers(users,null,null);
    	}else {
    		//保存企业管理员
    		users.setEnterprise(enterprise);
    		usersService.saveUsers(users,null,null);
    		//添加角色
    		List<String> idList = new ArrayList<String>();
    		idList.add(users.getId());
    		roleUsersService.saveRoleUsers(idList, "ff8080813c55b78c013c55e0649d0042", "T");
    	}
    	return SUCCESS;
    }
    
    public String toImport() throws CRUDException{
        return SUCCESS;
    }
    
    /**
     * 导入
     * 
     * @return
     * @throws Exception
     */
    public String importXls() throws Exception {
    	StringBuilder sb = new StringBuilder();
    	try{
    		UploadHelper helper = new UploadHelper(upload, uploadFileName, uploadTitle, uploadContentType, "upload",UploadHelper.NAME_UUID);
    		List<AttachedFile> list = helper.getAttachedFiles();
    		
    		if(list!=null&&list.size()>0){
    			String path = ServletActionContext.getServletContext().getRealPath("/");
    			AttachedFile af = list.get(0);
    			FileInputStream f = new FileInputStream(path+"/"+af.getFilePath());
    			//如果是Excel2003格式
    			if("xls".equals(af.getFileType())){
    				HSSFWorkbook wb = new HSSFWorkbook(f);
    				HSSFSheet sheet = wb.getSheetAt(0);
    				int rowNum = sheet.getLastRowNum();
    				for(int i=1;i<=rowNum;i++){
    					HSSFRow row = sheet.getRow(i);
    					HSSFCell numberCell = row.getCell((short)0);
    					HSSFCell remarkCell = row.getCell((short)1);
    					String num = "";
    					
    					if(numberCell==null)
    						continue;
    					
    					//判断是否是合法号码
    				    if(numberCell.getCellType() == HSSFCell.CELL_TYPE_STRING){
    						num = numberCell.getStringCellValue();
    					}else if(numberCell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
    						double n = numberCell.getNumericCellValue();
    						DecimalFormat df = new DecimalFormat("0"); 
    						num = df.format(n);
    					}else{
    						sb.append("行:["+(i)+",1],");
    						continue;
    					}
    				    
    				    if(i>0&&(num==""||num.length()!=11)){
    				    	sb.append("行:["+(i)+",1],");
    				    	continue;
    				    }
    				    
    				    //如果有备注
    				    if(remarkCell!=null){
    				    if(remarkCell.getCellType() == HSSFCell.CELL_TYPE_STRING){
    						remarkCell.getStringCellValue();
    					}else {
    						sb.append("行:["+(i)+",2],");
    						continue;
    					}
    				    }
    				    
    				    //判断号码是否存在
    				    if(numberService.findCountByNumber(num)>0){
    				    	sb.append("行:"+(i)+"已经存在,");
    				    }
    					
    				}
    				//如果没有错误,则进行导入
    				if(sb.length()==0){
    					for(int i=1;i<=rowNum;i++){
        					HSSFRow row = sheet.getRow(i);
        					HSSFCell numberCell = row.getCell((short)0);
        					HSSFCell remarkCell = row.getCell((short)1);
        					String num = "";
        					if(numberCell.getCellType() == HSSFCell.CELL_TYPE_STRING){
        						num = numberCell.getStringCellValue();
        					}else if(numberCell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
        						double n = numberCell.getNumericCellValue();
        						DecimalFormat df = new DecimalFormat("0"); 
        						num = df.format(n);
        					}
        					CsmsNumber number1 = new CsmsNumber();
        					//导入
        					number1.setNumber(num);
        					number1.setGroup(number.getGroup());
        					if(remarkCell!=null)
        					number1.setRemark(remarkCell.getStringCellValue());
        					number1.setDepartment(enterpriseId);
        					//判断是否存在
        					if(numberService.findCountByNumber(num)==0)
        					numberService.saveNumber(number1);
        				}
    				}
    			}else if("xlsx".equals(af.getFileType())){
    			//如果是Excel2007格式
    				XSSFWorkbook hssfWorkbook = new XSSFWorkbook(f);
        			XSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        			int rowNum = hssfSheet.getLastRowNum();
        			for(int i=1;i<=rowNum;i++){
        				XSSFRow row = hssfSheet.getRow(i);
        				XSSFCell numberCell = row.getCell(0);
        				XSSFCell reamrkCell = row.getCell(1);
        				String num = "";
        				
        				if(numberCell.getCellType() == XSSFCell.CELL_TYPE_STRING){
        					num = numberCell.getStringCellValue();
        				}else if(numberCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
        					double n = numberCell.getNumericCellValue();
    						DecimalFormat df = new DecimalFormat("0"); 
    						num = df.format(n);
        				} else{
        					sb.append("行:["+(i)+",1],");
    						continue;
        				}
        				
        				 if(num==""||num.length()!=11){
      				    	sb.append("行:["+(i)+",1],");
      				    	continue;
      				    }
        				
        				 //如果有备注
     				    if(reamrkCell!=null){
     				    if(reamrkCell.getCellType() == XSSFCell.CELL_TYPE_STRING){
     				    	reamrkCell.getStringCellValue();
     					}else {
     						sb.append("行:["+(i)+",2],");
     						continue;
     					}
     				    }
        				 
     				    //判断号码是否存在
     				    if(numberService.findCountByNumber(num)>0){
     				    	sb.append("行:"+(i)+"已经存在,");
     				    }
        			}
        			//如果没有错误,则进行导入
    				if(sb.length()==0){
    					for(int i=1;i<=rowNum;i++){
    						XSSFRow row = hssfSheet.getRow(i);
            				XSSFCell numberCell = row.getCell(0);
            				XSSFCell remarkCell = row.getCell(1);
            				String num = "";
        					if(numberCell.getCellType() == XSSFCell.CELL_TYPE_STRING){
        						num = numberCell.getStringCellValue();
        					}else if(numberCell.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
        						double n = numberCell.getNumericCellValue();
        						DecimalFormat df = new DecimalFormat("0"); 
        						num = df.format(n);
        					}
        					//导入
        					CsmsNumber number1 = new CsmsNumber();
        					number1.setNumber(num);
//        					number1.setGroup(number.getGroup());
        					if(remarkCell!=null)
        					//设置电话
        					number1.setRemark(remarkCell.getStringCellValue());
        					//设置集团
        					number1.setDepartment(enterprise.getId());
        					if(numberService.findCountByNumber(num)==0)
        					numberService.saveNumber(number1);
        				}
    				}
        			
    			}
    			
    			
    		}
    		if(sb.length()>0)
    		sb.append("格式不对");
    		//numberService.saveNumber(number);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	if(sb.length()>0){
    		errorInfo = sb.toString();
    		return ERROR;
    	}
    	System.out.println(sb.toString());
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

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}
	
}