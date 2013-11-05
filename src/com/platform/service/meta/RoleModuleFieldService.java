package com.platform.service.meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.constants.SQLConstant;
import com.platform.dao.meta.FieldDAO;
import com.platform.dao.meta.RoleModuleFieldDAO;
import com.platform.domain.Role;
import com.platform.domain.RoleModuleField;
import com.platform.domain.RoleUsers;
import com.platform.domain.VmetaField;
import com.platform.exception.CRUDException;
import com.platform.service.IService;
import com.platform.util.Validate;

@Service
public class RoleModuleFieldService implements IService {

    private RoleModuleFieldDAO roleModuleFieldDAO;
    private FieldDAO fieldDAO;
    
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        roleModuleFieldDAO = RoleModuleFieldDAO.getInstance(jdbcTemplate);
        fieldDAO = FieldDAO.getInstance(jdbcTemplate);
    }

    /**
     * 根据角色和模块查找字段
     * 
     * @param moduleId
     * @param roleId
     * @return
     * @throws CRUDException
     */
    public List<RoleModuleField> listRoleModuleFieldByModuleAndRole(String moduleId, String roleId) throws CRUDException {
        return roleModuleFieldDAO.findAll(SQLConstant.ROLEMODULEFIELD_SELECT_BY_MODULE_ROLE_SQL, new String[]{moduleId, roleId});
    }

    /**
     * 根据角色和前台组件ID查找该记录
     * 
     * @param roleId
     * @param webId
     * @return
     * @throws CRUDException
     */
    public RoleModuleField findRoleModuleFieldByRoleAndWebId(String roleId, String webId) throws CRUDException {
        List<RoleModuleField> rmfList = roleModuleFieldDAO.findAll(SQLConstant.ROLEMODULEFIELD_SELECT_BY_ROLE_WEBID_SQL, new String[]{roleId, webId});
        if (Validate.collectionNotNull(rmfList)) {
            return rmfList.get(0);
        } else {
            return null;
        }
    }

    /**
     * 修改字段的约束
     * 
     * @param roleId
     * @param field
     * @param canNull
     * @param typeList
     * @param valueList
     * @param messageList
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void updateRoleModuleField(String roleId, VmetaField field, String canNull, List<String> typeList, List<String> valueList, List<String> messageList) throws CRUDException {
        RoleModuleField rmf = findRoleModuleFieldByRoleAndWebId(roleId, field.getWebId());

        if (Validate.collectionNotNull(typeList)) {
            StringBuffer rules = new StringBuffer();
            rules.append("[");
            rules.append("{type:'canNull', value:'");
            rules.append(canNull);
            rules.append("'}");
            for (int i = 0; i < typeList.size(); i++) {
                if (Validate.notNull(valueList.get(i))) {
                    rules.append(",{type:'");
                    rules.append(typeList.get(i));
                    rules.append("', value:'");
                    rules.append(valueList.get(i));
                    rules.append("', message:'");
                    rules.append(messageList.get(i));
                    rules.append("'}");
                }
            }
            rules.append("]");

            if (Validate.idNotNull(rmf)) {
                rmf.setRules(rules.toString());
                roleModuleFieldDAO.update(rmf);
            } else {
                rmf = new RoleModuleField();

                Role role = new Role();
                role.setId(roleId);

                rmf.setRole(role);
                rmf.setModule(field.getModule());
                rmf.setWebId(field.getWebId());

                rmf.setRules(rules.toString());

                roleModuleFieldDAO.save(rmf);
            }
        }
    }

    /**
     * 根据用户角色用户列表查找字段的约束
     * 
     * @param ruList
     * @return
     * @throws CRUDException
     */
    public HashMap<String, String> findUsersFields(List<RoleUsers> ruList) throws CRUDException {
        HashMap<String, String> fields = new HashMap<String, String>();
        if (Validate.collectionNotNull(ruList)) {
            for (RoleUsers ru : ruList) {
                List<RoleModuleField> rmfList = roleModuleFieldDAO.findAll(SQLConstant.ROLEMODULEFIELD_SELECT_BY_ROLEID_SQL, new String[]{ru.getRole().getId()});
                if (Validate.collectionNotNull(rmfList)) {
                    for (RoleModuleField field : rmfList) {
                        fields.put(field.getWebId(), field.getRules());
                    }
                }
            }
        }
        return fields;
    }

    /**
     * 删除字段约束
     * 
     * @param roleId
     * @param moduleId
     * @param idList
     * @return
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public List<String> deleteRoleModuleField(String roleId, String moduleId, List<String> idList) throws CRUDException {
        List<String> webIdList = new ArrayList<String>();
        if (Validate.collectionNotNull(idList)) {
            for (String fieldId : idList) {
                VmetaField field = fieldDAO.find(SQLConstant.ROLEMODULEFIELD_SELECT_BY_ID,new String[]{fieldId});
                List<RoleModuleField> rmfList = roleModuleFieldDAO.findAll(SQLConstant.ROLEMODULEFIELD_SELECT_BY_ROLE_MODULE_WEBID_SQL,new String[]{roleId, moduleId, field.getWebId()});
                if (Validate.collectionNotNull(rmfList)) {
                    webIdList.add(field.getWebId());
                    roleModuleFieldDAO.deleteByProperty(SQLConstant.ROLEMODULEFIELD_DELETE_BY_ID_SQL,rmfList.get(0).getId());
                }
            }
        }
        return webIdList;
    }
}
