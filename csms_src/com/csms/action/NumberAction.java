package com.csms.action;

import java.io.FileInputStream;
import java.text.DecimalFormat;
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
import com.csms.domain.Group;
import com.csms.service.EnterpriseService;
import com.csms.service.GroupService;
import com.csms.service.NumberService;
import com.platform.domain.AttachedFile;
import com.platform.domain.Department;
import com.platform.service.DepartmentService;
import com.platform.util.UploadHelper;
import com.platform.web.action.GenericAction;

@Controller
@Scope("prototype")
public class NumberAction extends GenericAction<CsmsNumber> {

    private static final long serialVersionUID = -4867867482994497216L;

    @Autowired
    private NumberService numberService;

    @Autowired
    private GroupService groupService;
    
    @Autowired
    private EnterpriseService enterpriseService;
    
    private List<CsmsNumber> numberList;
    private List<Enterprise> departmentList;
    private List<Group> groupList;
    private CsmsNumber number;
    private String numberid;
    private String type;
    private String enterpriseId;
    private String errorInfo;
    
    public String list() throws Exception {
    	
        return SUCCESS;
    }
    
    public String listA() throws Exception {
    	
        return SUCCESS;
    }
    
    public String listTree() throws Exception {
    	departmentList = enterpriseService.findAll();
        return SUCCESS;
    }

    
    public String listTreeA() throws Exception {
    	groupList = groupService.findAll();
        return SUCCESS;
    }


    public String listPagination() throws Exception {
    	try{
    	    page = numberService.listPagination(page,enterpriseId);	
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }
    
    public String listPaginationA() throws Exception {
    	try{
    	    page = numberService.listPaginationA(page,enterpriseId);	
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
    	try{
    		groupList = groupService.findAll(enterpriseId);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
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
    		number.setDepartment(enterpriseId);
    		numberService.saveNumber(number);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }
    
    /**
     * 预导入
     * @return
     * @throws Exception
     */
    public String toImport() throws Exception {
    	//获得组群
    	groupList = groupService.findAll(enterpriseId);
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
        					number1.setGroup(number.getGroup());
        					if(remarkCell!=null)
        					number1.setRemark(remarkCell.getStringCellValue());
        					number1.setDepartment(enterpriseId);
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

    /**
     * 预修改
     * 
     * @return
     * @throws Exception
     */
    public String toUpdate() throws Exception {
    	try{
    		groupList = groupService.findAll(enterpriseId);
    		number = numberService.findById(number.getId());
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }
    
    public String toUpdateA() throws Exception {
    	try{
    		groupList = groupService.findAll();
    		number = numberService.findById(number.getId());
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
    public String toUpdateGroup() throws Exception {
    	try{
    		 
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
    		numberService.updateNumber(number);
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
    public String updateA() throws Exception {
    	try{
    		numberService.updateNumberA(number);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    public String updateGroup() throws Exception {
    	
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
    		 numberService.delete(idList);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

	public NumberService getNumberService() {
		return numberService;
	}

	public void setNumberService(NumberService numberService) {
		this.numberService = numberService;
	}


	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public List<CsmsNumber> getNumberList() {
		return numberList;
	}

	public void setNumberList(List<CsmsNumber> numberList) {
		this.numberList = numberList;
	}

	

	public List<Enterprise> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<Enterprise> departmentList) {
		this.departmentList = departmentList;
	}

	public List<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}

	public CsmsNumber getNumber() {
		return number;
	}

	public void setNumber(CsmsNumber number) {
		this.number = number;
	}

	public String getNumberid() {
		return numberid;
	}

	public void setNumberid(String numberid) {
		this.numberid = numberid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	

}