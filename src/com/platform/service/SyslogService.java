package com.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.platform.constants.SQLConstant;
import com.platform.constants.StringConstant;
import com.platform.dao.SyslogDAO;
import com.platform.domain.Syslog;
import com.platform.exception.CRUDException;
import com.platform.vo.Page;

@Service
public class SyslogService implements IService {

    private SyslogDAO syslogDAO;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate  jdbcTemplate) {
        syslogDAO = SyslogDAO.getInstance(jdbcTemplate);
    }

    /**
     * 分页
     * 
     * @param page
     * @return
     * @throws CRUDException
     */
    public Page<Syslog> listPagination(Page<Syslog> page, String type) throws CRUDException {
        // 如果没有规定类型,则查找所有日志
        if (StringConstant.ALL.equals(type))
            page = syslogDAO.pagination(page, SQLConstant.SYSLOG_PAGE_SQL, null);
        else
            page = syslogDAO.pagination(page, SQLConstant.SYSLOG_BY_TYPE_PAGE_SQL, new String[]{type});
        return page;
    }

    /**
     * 删除系统日志
     * 
     * @param idList
     */
    public void deleteSyslog(List<String> idList) throws CRUDException {
        syslogDAO.deleteByIdList(SQLConstant.SYSLOG_DELETE_BY_IDS, idList);
    }

    /**
     * 清空系统日志
     * 
     * @param idList
     */
    public void deleteAllSyslog() throws CRUDException {
        syslogDAO.deleteAll(SQLConstant.SYSLOG_DELETE_ALL);
    }

}
