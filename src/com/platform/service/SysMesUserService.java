package com.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.platform.constants.SQLConstant;
import com.platform.constants.StringConstant;
import com.platform.dao.SysMesUserDAO;
import com.platform.domain.SysMesUser;
import com.platform.exception.CRUDException;
import com.platform.util.LoginBean;
import com.platform.vo.Page;

@Service
public class SysMesUserService implements IService {

    private SysMesUserDAO sysMesUserDAO;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        sysMesUserDAO = SysMesUserDAO.getInstance(jdbcTemplate);
    }

    /**
     * 查找未读系统消息
     * 
     * @return
     * @throws CRUDException
     */
    public List<SysMesUser> findSysMessageByNoRead() throws CRUDException {
        return sysMesUserDAO.findAll(SQLConstant.SYSMESUSER_BY_READFLAG_SQL, new String[]{StringConstant.FALSE, LoginBean.getLoginBean().getUser().getId()});
    }

    /**
     * 分页系统消息
     * 
     * @param page
     * @param readFlag
     * @return
     * @throws CRUDException
     */
    public Page<SysMesUser> listPagination(Page<SysMesUser> page, String readFlag) throws CRUDException {
        if (StringConstant.ALL.equals(readFlag))
            return sysMesUserDAO.pagination(page, SQLConstant.SYSMESUSER_PAGE_SQL, null, LoginBean.getLoginBean().getUser());
        else
            return sysMesUserDAO.pagination(page, SQLConstant.SYSMESUSER_SELECT_BY_READFLAG_PAGE_SQL,readFlag, LoginBean.getLoginBean().getUser());
    }

    /**
     * 批量删除
     * 
     * @param idList
     * @throws CRUDException
     */
    public void delete(List<String> idList) throws CRUDException {
        sysMesUserDAO.deleteByIdList(SQLConstant.SYSMESUSER_DELETE_BY_IDS,idList);
    }

    /**
     * 清空系统消息
     * 
     * @throws CRUDException
     */
    public void deleteAll() throws CRUDException {
        sysMesUserDAO.deleteByProperty(SQLConstant.SYSMESUSER_DELETE_BY_USER_SQL, LoginBean.getLoginBean().getUser().getId());
    }

    /**
     * 批量删除
     * 
     * @param idList
     * @throws CRUDException
     */
    public void updateReadFlag(List<String> idList) throws CRUDException {
        for (String id : idList) {
            SysMesUser smu = sysMesUserDAO.find(SQLConstant.SYSMESUSER_SELECT_BY_ID,new String[]{id});
            smu.setReadFlag(StringConstant.TRUE);
            sysMesUserDAO.update(smu);
        }
    }

    /**
     * 清空系统消息
     * 
     * @throws CRUDException
     */
    public void updateReadFlagAll() throws CRUDException {
        List<SysMesUser> smuList = sysMesUserDAO.findAll(SQLConstant.SYSMESUSER_SELECT_BY_USER_SQL,new String[]{LoginBean.getLoginBean().getUser().getId()});
        for (SysMesUser smu : smuList) {
            smu.setReadFlag(StringConstant.TRUE);
            sysMesUserDAO.update(smu);
        }
    }

}
