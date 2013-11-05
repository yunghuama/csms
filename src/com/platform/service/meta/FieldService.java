package com.platform.service.meta;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.platform.constants.SQLConstant;
import com.platform.dao.meta.FieldDAO;
import com.platform.domain.VmetaField;
import com.platform.exception.CRUDException;
import com.platform.service.IService;
import com.platform.util.Validate;

@Service
public class FieldService implements IService {

    private FieldDAO fieldDAO;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        fieldDAO = FieldDAO.getInstance(jdbcTemplate);
    }

    /**
     * 获得字段
     * 
     * @param id
     * @return
     * @throws CRUDException
     */
    public VmetaField findFieldById(String id) throws CRUDException {
        return fieldDAO.find(SQLConstant.FIELD_SELECT_BY_ID_SQL, new String[]{id});
    }

    /**
     * 删除字段
     * 
     * @param idList
     * @throws CRUDException
     */
    public void deleteFields(List<String> idList) throws CRUDException {
        fieldDAO.deleteByIdList(SQLConstant.FIELD_DELETE_BY_IDS_SQL,idList);
    }

    /**
     * 根据模块查找字段
     * 
     * @param moduleId
     * @return
     * @throws CRUDException
     */
    public List<VmetaField> listFieldByModuleId(String moduleId) throws CRUDException {
        if (Validate.notNull(moduleId))
            return fieldDAO.findAll(SQLConstant.FIELD_SELECT_BY_MODULE_SQL, new String[]{moduleId});
        return null;
    }

}