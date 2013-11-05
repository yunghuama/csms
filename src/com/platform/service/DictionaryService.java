package com.platform.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.constants.SQLConstant;
import com.platform.constants.StringConstant;
import com.platform.dao.DictionaryDAO;
import com.platform.domain.Dictionary;
import com.platform.exception.CRUDException;
import com.platform.util.LoginBean;
import com.platform.util.Meta;
import com.platform.util.TFHelper;
import com.platform.util.Validate;
import com.platform.vo.Page;

@Service
public class DictionaryService implements IService {

    private DictionaryDAO dictionaryDAO;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        dictionaryDAO = DictionaryDAO.getInstance(jdbcTemplate);
    }

    /**
     * 新建字典
     * 
     * @param dictionary
     * @throws CRUDException
     */
    public void saveDictionary(Dictionary dictionary) throws CRUDException {
        dictionary.setCreator(LoginBean.getLoginBean().getUser());
        dictionary.setCreateTime(new Date());
        dictionary.setSortNum(dictionaryDAO.findMaxSortNum(dictionary.getSuperId()));
        dictionaryDAO.save(dictionary);
    }

    /**
     * 删除字典
     * 
     * @param idList
     * @param syncDelete
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void deleteDictionary(List<String> idList, String syncDelete) throws CRUDException {
        if (Validate.collectionNotNull(idList)) {
            if (TFHelper.trueOrFalse(syncDelete)) {
                for (String id : idList) {
                    Dictionary oldDictionary = dictionaryDAO.find(SQLConstant.DICTIONARY_SELECT_BY_ID_SQL,new String[]{id});
                    // 同步清空
                    dictionaryDAO.syncUpdate(oldDictionary.getSuperId(), "", oldDictionary.getName());
                    dictionaryDAO.deleteByProperty(SQLConstant.DICTIONARY_DELETE_BY_ID_SQL,oldDictionary.getId());
                }
            } else {
                for (String id : idList) {
                    Dictionary oldDictionary = dictionaryDAO.find(SQLConstant.DICTIONARY_SELECT_BY_ID_SQL,new String[]{id});
                    dictionaryDAO.deleteByProperty(SQLConstant.DICTIONARY_DELETE_BY_ID_SQL,oldDictionary.getId());
                }
            }
        }
    }

    /**
     * 字典分页
     * 
     * @param page
     * @param superId
     * @return
     * @throws CRUDException
     */
    public Page<Dictionary> listPagination(Page<Dictionary> page, String superId) throws CRUDException {
        if (Validate.notNull(superId))
            return dictionaryDAO.pagination(page, SQLConstant.DICTIONARY_PAGE_SQL + SQLConstant.getUsersAndDeptWhere("d.creator","u.departmentid",Meta.getUsers(StringConstant.DICTIONARY_ID),Meta.getDepartments(StringConstant.DICTIONARY_ID))," order by sortnum ", new String[]{superId});
        else
            return page;
    }

    /**
     * 查找某字典下的所有字典项
     * 
     * @param superId
     * @return
     * @throws CRUDException
     */
    public List<Dictionary> findDictionaryBySuperId(String superId) throws CRUDException {
        return dictionaryDAO.findAll(SQLConstant.DICTIONARY_SELECT_CHILD_SQL, new String[]{superId});
    }

    /**
     * 更新字典顺序
     * 
     * @param idList
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void updateSortDictionary(List<String> idList) throws CRUDException {
        if (Validate.collectionNotNull(idList)) {
            for (int i = 0; i < idList.size(); i++) {
                Dictionary dictionary = dictionaryDAO.find(SQLConstant.DICTIONARY_SELECT_BY_ID_SQL,new String[]{idList.get(i)});
                dictionary.setSortNum(i + 1);
                dictionary.setEditor(LoginBean.getLoginBean().getUser());
                dictionary.setEditTime(new Date());
                dictionaryDAO.update(dictionary);
            }
        }
    }

    /**
     * 根据ID查找字典
     * 
     * @param id
     * @return
     * @throws CRUDException
     */
    public Dictionary findDictionaryById(String id) throws CRUDException {
        return dictionaryDAO.find(SQLConstant.DICTIONARY_SELECT_BY_ID_SQL,new String[]{id});
    }

    /**
     * AZ查询
     * 
     * @param page
     * @param property
     * @param azparam
     * @return
     * @throws CRUDException
     */
    public Page<Dictionary> paginationByPY(Page<Dictionary> page, String property, String azparam) throws CRUDException {
        return dictionaryDAO.pagination(page, SQLConstant.getAZHQL("Dictionary", property, azparam),null,null);
    }

    /**
     * 修改字典
     * 
     * @param dictionary
     * @param syncUpdate
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void updateDictionary(Dictionary dictionary, String syncUpdate) throws CRUDException {
        Dictionary oldDictionary = dictionaryDAO.find(SQLConstant.DICTIONARY_SELECT_BY_ID_SQL,new String[]{dictionary.getId()});
        // 同步更新
        if (TFHelper.trueOrFalse(syncUpdate)) {
            dictionaryDAO.syncUpdate(oldDictionary.getSuperId(), dictionary.getName(), oldDictionary.getName());
        }

        oldDictionary.setName(dictionary.getName());
        oldDictionary.setRemark(dictionary.getRemark());
        oldDictionary.setEditTime(new Date());
        oldDictionary.setEditor(LoginBean.getLoginBean().getUser());

        dictionaryDAO.update(oldDictionary);
    }
    
    /**
     * 判断同类下是否有相同的名称
     * 
     * @param name
     * @param superId
     * @return
     * @throws CRUDException
     */
    public boolean nameExist(String dictId, String name, String superId) throws CRUDException {
    	if(Validate.notNull(dictId)) {
    		return dictionaryDAO.queryForInt(SQLConstant.DICTIONARY_UPDATE_VALIDATE_NAME_SQL, new String[]{dictId, name, superId}) >0;
    	} else {
    		return dictionaryDAO.queryForInt(SQLConstant.DICTIONARY_VALIDATE_NAME_SQL, new String[]{name, superId}) >0;
    	}
    }
    
    /**
     * 按属性查找对象列表<br/> 示例代码<br/> xxx.findByProperty("name", "Lucy");
     * 
     * @param propertyName
     *            属性名
     * @param value
     *            属性值
     * @return
     * @throws CRUDException
     */
    public List<Dictionary> findByProperty(String propertyName, Object value) throws CRUDException {
        return dictionaryDAO.findAll("select * from dictionary where "+propertyName+" = ?", new Object[]{value});
    }
    
    /**
     * 按属性查找对象列表<br/> 示例代码<br/> xxx.findByProperty("name", "Lucy");
     * 
     * @param propertyName
     *            属性名
     * @param value
     *            属性值
     * @return
     * @throws CRUDException
     */
    public Dictionary findByPropertyOne(String propertyName, Object value) throws CRUDException {
        return dictionaryDAO.find("select * from dictionary where "+propertyName+" = ?", new Object[]{value});
    }
}