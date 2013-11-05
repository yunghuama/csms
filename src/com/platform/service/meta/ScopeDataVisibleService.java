package com.platform.service.meta;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.constants.SQLConstant;
import com.platform.dao.meta.ScopeDataVisibleDAO;
import com.platform.domain.Role;
import com.platform.domain.ScopeDataVisible;
import com.platform.domain.Users;
import com.platform.domain.VmetaModule;
import com.platform.exception.CRUDException;
import com.platform.service.IService;
import com.platform.util.Validate;

@Service
public class ScopeDataVisibleService implements IService {

    private ScopeDataVisibleDAO scopeDataVisibleDAO;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        scopeDataVisibleDAO = ScopeDataVisibleDAO.getInstance(jdbcTemplate);
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
    public List<ScopeDataVisible> findRoleModuleUsersScopeDataVisible(String roleId, String moduleId, String usersId) throws CRUDException {
        return scopeDataVisibleDAO.findAll(SQLConstant.SCOPEDATAVISIBLE_SELECT_BY_ROLE_MODULE_USERS_SQL, new String[]{roleId, moduleId, usersId});
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
    public List<String> updateUsersScopeDataVisible(String roleId, String moduleId, String usersId, List<String> webIdList, String visible) throws CRUDException {
    	for (Iterator iterator = webIdList.iterator(); iterator.hasNext();) {
			String webId = (String) iterator.next();
			List<ScopeDataVisible> sdvList = scopeDataVisibleDAO.findAll(SQLConstant.SCOPEDATAVISIBLE_BY_ROLE_MODULE_USERS_WEBID_SQL, new String[]{roleId, moduleId, usersId, webId});
	        if (Validate.collectionNotNull(sdvList)) {
	            ScopeDataVisible sdv = sdvList.get(0);
	            sdv.setVisible(visible);
	            scopeDataVisibleDAO.update(sdv);
	        } else {
	            Role r = new Role();
	            r.setId(roleId);

	            VmetaModule m = new VmetaModule();
	            m.setId(moduleId);

	            Users u = new Users();
	            u.setId(usersId);

	            ScopeDataVisible sdv = new ScopeDataVisible();
	            sdv.setRole(r);
	            sdv.setModule(m);
	            sdv.setUsers(u);
	            sdv.setWebId(webId);
	            sdv.setVisible(visible);
	            scopeDataVisibleDAO.save(sdv);
	        }
		}
    	return webIdList;
    }
}
