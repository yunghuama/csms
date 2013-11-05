package com.platform.service.meta;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.constants.SQLConstant;
import com.platform.dao.meta.RoleDAO;
import com.platform.dao.meta.RoleModuleOperateDAO;
import com.platform.domain.Role;
import com.platform.domain.RoleModuleOperate;
import com.platform.domain.VmetaModule;
import com.platform.exception.CRUDException;
import com.platform.service.IService;
import com.platform.util.LoginBean;
import com.platform.util.Validate;

@Service
public class RoleModuleOperateService implements IService {

    private RoleModuleOperateDAO roleModuleOperateDAO;
    private RoleDAO roleDAO;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        roleModuleOperateDAO = RoleModuleOperateDAO.getInstance(jdbcTemplate);
        roleDAO = RoleDAO.getInstance(jdbcTemplate);
    }

    /**
     * 根据角色和模块获得角色的操作
     * 
     * @param moduleId
     * @param roleId
     * @return
     * @throws CRUDException
     */
    public List<RoleModuleOperate> listRoleOperateByModuleAndRole(String moduleId, String roleId) throws CRUDException {
        return roleModuleOperateDAO.findAll(SQLConstant.ROLEOPERATE_SELECT_BY_MODULE_ROLE_SQL, new String[]{moduleId, roleId});
    }

    /**
     * 保存角色的操作组件权限
     * 
     * @param idList
     * @param roleId
     * @param moduleId
     * @param useable
     * @return
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public List<String> saveRoleOperate(List<String> idList, String roleId, String moduleId, String useable) throws CRUDException {
        List<String> webIdList = new ArrayList<String>();
        // 判断集合是否为空
        if (Validate.collectionNotNull(idList)) {
            // 遍历集合
            for (String webId : idList) {
                // 根据webId和roleId查找唯一的角色操作对象
                List<RoleModuleOperate> oldRO = roleModuleOperateDAO.findAll(SQLConstant.ROLEOPERATE_SELECT_BY_WEBID_ROLE_SQL,new String[]{webId, roleId});
                // 如果对象不为空
                if (Validate.collectionNotNull(oldRO)) {
                    // 更新对象
                    RoleModuleOperate ro = oldRO.get(0);
                    ro.setUseable(useable);
                    roleModuleOperateDAO.update(ro);
                    
                    webIdList.add(ro.getWebId());
                } else {
                    // 新建对象
                    Role r = new Role();
                    r.setId(roleId);

                    VmetaModule m = new VmetaModule();
                    m.setId(moduleId);

                    RoleModuleOperate ro = new RoleModuleOperate();
                    ro.setWebId(webId);
                    ro.setModule(m);
                    ro.setRole(r);
                    ro.setUseable(useable);

                    roleModuleOperateDAO.save(ro);
                    webIdList.add(ro.getWebId());
                }
            }

            Role oldRole = roleDAO.find(SQLConstant.ROLE_SELECT_BY_ID,new String[]{roleId});
            oldRole.setEditor(LoginBean.getLoginBean().getUser());
            oldRole.setEditTime(new Date());
            roleDAO.update(oldRole);
        }
        // 返回webId集合
        return webIdList;
    }
}