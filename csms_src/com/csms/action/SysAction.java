package com.csms.action;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csms.domain.Warn;
import com.csms.service.SysService;
import com.platform.web.action.GenericAction;

@Controller
@Scope("prototype")
public class SysAction extends GenericAction<Warn> {

    private static final long serialVersionUID = -4867867482994497216L;

    @Autowired
    private SysService sysService;


    /**
     * 保存报警信息
     * @return
     */
	public String saveWarn(){
       File file = new File("/temp/resource.res");
       return SUCCESS;
    }

    public String listPagination(){
    	page = sysService.listPagination(page);
        return SUCCESS;
    }
}