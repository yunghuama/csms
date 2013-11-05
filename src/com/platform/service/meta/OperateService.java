package com.platform.service.meta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.platform.constants.SQLConstant;
import com.platform.dao.meta.OperateDAO;
import com.platform.domain.VmetaOperate;
import com.platform.exception.CRUDException;
import com.platform.service.IService;
import com.platform.util.Validate;

@Service
public class OperateService implements IService {

    private OperateDAO operateDAO;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        operateDAO = OperateDAO.getInstance(jdbcTemplate);
    }

    /**
     * 查找模块操作
     * 
     * @param superId
     * @return
     * @throws CRUDException
     */
    public List<VmetaOperate> listOperateByModuleId(String superId) throws CRUDException {
        if (Validate.notNull(superId))
            return operateDAO.findAll(SQLConstant.OPERATE_SELECT_BY_MODULE_SQL, new String[]{superId});
        else
            return null;
    }

    /**
     * 查找可用操作
     * 
     * @param moduleId
     * @return
     * @throws CRUDException
     */
    public List<VmetaOperate> listOperateByModuleIdAndVisible(String moduleId) throws CRUDException {
        if (Validate.notNull(moduleId))
            return operateDAO.findAll(SQLConstant.OPERATE_SELECT_BY_MODULE_VISIBLE_SQL, new String[]{moduleId});
        else
            return null;
    }

    /**
     * 获得操作
     * 
     * @param id
     * @return
     * @throws CRUDException
     */
    public VmetaOperate findOperateById(String id) throws CRUDException {
        return operateDAO.find(SQLConstant.OPERATE_SELECT_BY_ID_SQL,new String[]{id});
    }

    /**
     * 删除操作
     * 
     * @param idList
     * @throws CRUDException
     */
    public void deleteOperates(List<String> idList) throws CRUDException {
        operateDAO.deleteByIdList(SQLConstant.OPERATE_DELETE_BY_IDS_SQL, idList);
    }
}