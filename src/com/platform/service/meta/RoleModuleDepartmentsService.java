package com.platform.service.meta;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.constants.SQLConstant;
import com.platform.constants.StringConstant;
import com.platform.dao.meta.RoleDAO;
import com.platform.dao.meta.RoleModuleDepartmentsDAO;
import com.platform.dao.meta.ScopeDeptVisibleDAO;
import com.platform.domain.Department;
import com.platform.domain.Role;
import com.platform.domain.RoleModuleDepartments;
import com.platform.domain.ScopeDeptVisible;
import com.platform.domain.VmetaModule;
import com.platform.exception.CRUDException;
import com.platform.service.IService;
import com.platform.util.ListHelper;
import com.platform.util.LoginBean;
import com.platform.util.Validate;

@Service
public class RoleModuleDepartmentsService implements IService {

	private RoleDAO roleDAO;
    private RoleModuleDepartmentsDAO roleModuleDepartmentsDAO;
    private ScopeDeptVisibleDAO scopeDeptVisibleDAO;
    
    @Autowired
    RoleCacheService roleChangedService;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    	roleDAO = RoleDAO.getInstance(jdbcTemplate);
        roleModuleDepartmentsDAO = RoleModuleDepartmentsDAO.getInstance(jdbcTemplate);
        scopeDeptVisibleDAO = ScopeDeptVisibleDAO.getInstance(jdbcTemplate);
    }


    /**
     * 根据角色和模块查找
     * 
     * @param roleId
     * @param moduleId
     * @return roleModuleDepartments 对象
     * @throws CRUDException
     */
    public RoleModuleDepartments findRoleModuleDepartments(String roleId, String moduleId) throws CRUDException {
    	List<RoleModuleDepartments> rmdList = roleModuleDepartmentsDAO.findAll(SQLConstant.ROLEMODULEDEPARTMENT_SELECT_BY_ROLE_MODULE_SQL, new String[]{roleId, moduleId});
    	if (Validate.collectionNotNull(rmdList)) {
    		return rmdList.get(0);
    	} else {
    		return null;
    	}
    }
    public boolean findIsPower(String roleId,String departmentId,String moduleId) throws CRUDException {
    	List<RoleModuleDepartments> rmdList = roleModuleDepartmentsDAO.findAll(SQLConstant.ROLEMODULEDEPARTMENT_SELECT_BY_ROLE_MODULE_SQL, new String[]{roleId, moduleId});
    	for(int i=0;i<rmdList.size();i++){
    		if(rmdList.get(i).getDepartments().indexOf(departmentId)!=-1)
    			return true;
    	}
    	return false;
    }
    /**
     * 修改角色模块部门
     * 
     * @param roleId
     * @param moduleId
     * @param departmentId
     * @param useable
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void updateRoleModuleDepartments(String roleId, String moduleId, String departmentId, String useable) throws CRUDException {
        List<RoleModuleDepartments> rmdList = roleModuleDepartmentsDAO.findAll(SQLConstant.ROLEMODULEDEPARTMENT_SELECT_BY_ROLE_MODULE_SQL, new String[]{roleId, moduleId});
        if (Validate.collectionNotNull(rmdList)) {
            RoleModuleDepartments rmd = rmdList.get(0);
            if (StringConstant.TRUE.equals(useable))
                rmd.setDepartments(rmd.getDepartments() + departmentId + ",");
            else
                rmd.setDepartments(rmd.getDepartments().replaceAll(departmentId + ",", ""));
            roleModuleDepartmentsDAO.update(rmd);
        } else {
            Role r = new Role();
            r.setId(roleId);

            VmetaModule m = new VmetaModule();
            m.setId(moduleId);

            RoleModuleDepartments rmd = new RoleModuleDepartments();
            rmd.setRole(r);
            rmd.setModule(m);
            if (StringConstant.TRUE.equals(useable))
                rmd.setDepartments(departmentId + ",");

            roleModuleDepartmentsDAO.save(rmd);
        }
    }
    
    /**
     * 保存角色模块部门
     * @param idList
     * @param roleId
     * @param moduleId
     * @param useable
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void saveRoleModuleDepartments(List<String> idList, String roleId, String moduleId) throws CRUDException {
    		//查找唯一数据，因为findUnique会报错，因此使用find再进行判断
            List<RoleModuleDepartments> rmuList = roleModuleDepartmentsDAO.findAll(SQLConstant.ROLEMODULEDEPARTMENT_SELECT_BY_ROLE_MODULE_SQL, new String[]{roleId, moduleId});
            String departments = ListHelper.joint2String(idList, ",");
            //先删除范围中的操作权限记录
            deleteScopeDeptVisible(roleId,moduleId,departments);
            if (Validate.collectionNotNull(rmuList)) {
                RoleModuleDepartments rmu = rmuList.get(0);
                //如果没有或者全部取消了，就不保存了
                if(Validate.notNull(departments)) {
                	rmu.setDepartments(departments);
                	roleModuleDepartmentsDAO.update(rmu);
                } else {
                	roleModuleDepartmentsDAO.deleteByProperty(SQLConstant.ROLEMODULEDEPARTMENT_DELETE_BY_ID_SQL,rmu.getId());
                }
            } else {
            	if(Validate.notNull(departments)) {
            		Role r = new Role();
                    r.setId(roleId);

                    VmetaModule m = new VmetaModule();
                    m.setId(moduleId);

                    RoleModuleDepartments rmu = new RoleModuleDepartments();
                    rmu.setRole(r);
                    rmu.setModule(m);
                    rmu.setDepartments(departments);
                    roleModuleDepartmentsDAO.save(rmu);
            	}
            }

            Role oldRole = roleDAO.find(SQLConstant.ROLE_SELECT_BY_ID,new String[]{roleId});
            oldRole.setEditor(LoginBean.getLoginBean().getUser());
            oldRole.setEditTime(new Date());
            roleDAO.update(oldRole);
    		
    	
    	
    }
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void deleteScopeDeptVisible(String roleId,String moduleId,String departmentIds) throws CRUDException{
    	if(departmentIds==null)
    		departmentIds="";
    	List<ScopeDeptVisible> sdvList = scopeDeptVisibleDAO.findAll(SQLConstant.SCOPEDEPTVISIBLE_SELECT_BY_ROLE_MODULE_SQL, new String[]{roleId,moduleId});
    	for(int i=0;i<sdvList.size();i++){
    		if(departmentIds.indexOf(sdvList.get(i).getDepartment().getId())==-1)
    			scopeDeptVisibleDAO.deleteByProperty(SQLConstant.SCOPEDEPTVISIBLE_DELETE_BY_ID_SQL,sdvList.get(i).getId());
    	}
    }
    
    /**
     * <p>
     * <b>将所有部门用递归法产生树，然后先序遍历生成JSON</b>
     * 为避免构造过多的集合存储中间变量及结果，暂决定采用JSON形式
     * </p>
     * <p>该JSON将形如:<br/>
     * [{"id":"xxxxxx","name":"A", "deep":"2",...},{...}]
     * </p>
     * @return
     * @throws CRUDException
     */
    @Deprecated
    public final static class DepartmentsJsonMaker{
    	private static List<Department> departments;
    	private static StringBuffer sb;
    	private final static DepartmentsJsonMaker maker = new DepartmentsJsonMaker();
    	private DepartmentsJsonMaker(){}
    	
    	// 根据父节点查找子节点
    	private void findChild(String superId, int deep){
    		for(Department d: departments){
    			if(superId.equals(d.getSuperId())){
    				addJson(d, deep++);
    				findChild(d.getId(), deep--);
    			}
    		} // 无子节点时递归结束
    	}
    	private void addJson(Department d, int deep){
    		sb.append("{\"id\":\""+d.getId()+"\",\"code\":\""+d.getCode()+"\"," +
    				"\"name\":\""+d.getName()+"\",\"deep\":\""+deep+"\"},");
    	}
    	public static String make(List<Department> departments){
    		sb = new StringBuffer(5120);
    		sb.append("[");
    		DepartmentsJsonMaker.departments = departments;
    		maker.findChild(StringConstant.ROOT_ID, 0);
			return sb.substring(0, sb.lastIndexOf(","))+"]";
    	}
    }
    
}