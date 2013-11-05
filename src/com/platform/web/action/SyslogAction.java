package com.platform.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.platform.constants.StringConstant;
import com.platform.domain.Syslog;
import com.platform.service.SyslogService;
import com.platform.util.Validate;

@Controller
@Scope("prototype")
public class SyslogAction extends GenericAction<Syslog> {

    private static final long serialVersionUID = -7670821263734981074L;

    @Autowired
    private SyslogService syslogService;

    private String type;

    /** 预加载列表 */
    public String list() throws Exception {
        return SUCCESS;
    }

    /** 分页 */
    public String listPagination() throws Exception {
    	try{
    		if (!Validate.notNull(type))
                type = StringConstant.ALL;
            page = syslogService.listPagination(page, type);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    /** 删除 */
    public String delete() throws Exception {
    	try{
    		 syslogService.deleteSyslog(idList);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    /** 清空 */
    public String deleteAll() throws Exception {
    	try{
    		syslogService.deleteAllSyslog();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}