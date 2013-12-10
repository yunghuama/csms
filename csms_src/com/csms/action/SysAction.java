package com.csms.action;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csms.domain.SmsRule;
import com.csms.domain.Warn;
import com.csms.service.SysService;
import com.csms.util.SmsRuleContext;
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
    
    
    /**
     * 开启短信处理
     * @return
     */
    public String startSmsHandler(){
    	
    	return SUCCESS;
    }
    
    /**
     * 生成号码规则
     */
    public String refreshRule(){
    	
    	//设置号码规则
    	SmsRuleContext.setContextMap(sysService.listSmsRule());
    	
    	return SUCCESS;
    }
}