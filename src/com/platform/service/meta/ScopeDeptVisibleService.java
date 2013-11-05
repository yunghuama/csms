package com.platform.service.meta;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.constants.SQLConstant;
import com.platform.dao.meta.ScopeDeptVisibleDAO;
import com.platform.domain.Department;
import com.platform.domain.Role;
import com.platform.domain.ScopeDeptVisible;
import com.platform.domain.VmetaModule;
import com.platform.exception.CRUDException;
import com.platform.service.IService;
import com.platform.util.Validate;

@Service
public class ScopeDeptVisibleService implements IService {

    private ScopeDeptVisibleDAO scopeDeptVisibleDAO;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        scopeDeptVisibleDAO = ScopeDeptVisibleDAO.getInstance(jdbcTemplate);
    }

    /**
     * 根据角色、模块、用户ID查找可操作权限
     * 
     * @param roleId
     * @param moduleId
     * @param usersId
     * @return
     * @throws CRUDException
     */
    public List<ScopeDeptVisible> findRoleModuleDepartmentVisible(String roleId, String moduleId, String deptId) throws CRUDException {
        return scopeDeptVisibleDAO.findAll(SQLConstant.SCOPEDEPTVISIBLE_SELECT_BY_ROLE_MOUDLE_DEPT_SQL, new String[]{roleId, moduleId, deptId});
    }

    /**
     * 根据角色、模块、用户ID修改权限
     * 
     * @param roleId
     * @param moduleId
     * @param usersId
     * @param webId
     * @param visible
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public List<String> updateScopeDeptVisible(String roleId, String moduleId, String deptId, List<String> webIdList, String visible) throws CRUDException {
    	for (Iterator iterator = webIdList.iterator(); iterator.hasNext();) {
			String webId = (String) iterator.next();
			List<ScopeDeptVisible> sdvList = scopeDeptVisibleDAO.findAll(SQLConstant.SCOPEDEPTVISIBLE_SELECT_BY_ROLE_MODULE_DEPT_WEBID_SQL, new String[]{roleId, moduleId, deptId, webId});
	        if (Validate.collectionNotNull(sdvList)) {
	            ScopeDeptVisible sdv = sdvList.get(0);
	            sdv.setVisible(visible);
	            scopeDeptVisibleDAO.update(sdv);
	        } else {
	            Role r = new Role();
	            r.setId(roleId);

	            VmetaModule m = new VmetaModule();
	            m.setId(moduleId);

	            Department dept = new Department();
	            dept.setId(deptId);

	            ScopeDeptVisible sdv = new ScopeDeptVisible();
	            sdv.setRole(r);
	            sdv.setModule(m);
	            sdv.setDepartment(dept);
	            sdv.setWebId(webId);
	            sdv.setVisible(visible);
	            scopeDeptVisibleDAO.save(sdv);
	        }
		}
    	return webIdList;
    }
}
