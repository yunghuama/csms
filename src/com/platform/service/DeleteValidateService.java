package com.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.platform.constants.SQLConstant;
import com.platform.dao.DeleteValidateDAO;
import com.platform.dao.GenericDAO;
import com.platform.exception.CRUDException;

@Service
public class DeleteValidateService implements IService {

    private DeleteValidateDAO deleteValidateDAO;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    	deleteValidateDAO = DeleteValidateDAO.getInstance(jdbcTemplate);
    }

    /**`
     * 查找不能被删除的集合
     * 
     * @param idList
     * @param tableName
     * @return
     * @throws CRUDException
     */
    public List<List<String>> findCannotDeleteList(List<String> idList, String tableName) throws CRUDException {
        return deleteValidateDAO.findCannotDeleteList(idList, SQLConstant.DELETE_TABLE_VALIDATE.get(tableName));
    }

}
