package com.platform.service.meta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.platform.constants.SQLConstant;
import com.platform.constants.StringConstant;
import com.platform.dao.meta.ModuleDAO;
import com.platform.domain.VmetaModule;
import com.platform.exception.CRUDException;
import com.platform.service.IService;

@Service
public class ModuleService implements IService {

    private ModuleDAO moduleDAO;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        moduleDAO = ModuleDAO.getInstance(jdbcTemplate);
    }

    /**
     * 根据父级ID查找模块
     * 
     * @param superId
     * @return
     * @throws CRUDException
     */
    public List<VmetaModule> findModuleBySuperId(String superId) throws CRUDException {
        return moduleDAO.findAll(SQLConstant.MODULE_SELECT_BY_SUPERID_SQL, new String[]{superId});
    }

    /**
     * 查找二级模块
     * 
     * @return
     * @throws CRUDException
     */
    public List<VmetaModule> findChildModule() throws CRUDException {
        return moduleDAO.findAll(SQLConstant.MODULE_SELECT_CHILD_SQL, new String[]{StringConstant.ROOT_ID});
    }

    /**
     * 查询全部
     * 
     * @return
     * @throws CRUDException
     */
    public List<VmetaModule> findAllModule() throws CRUDException {
        return moduleDAO.findAll(SQLConstant.MODULE_SELECT_ALL_SQL);
    }

    /**
     * 根据ID查找模块
     * 
     * @param id
     * @return
     * @throws CRUDException
     */
    public VmetaModule findModuleById(String id) throws CRUDException {
        return moduleDAO.find(SQLConstant.MODULE_SELECT_BY_ID_SQL,new String[]{id});
    }
}