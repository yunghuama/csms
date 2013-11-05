package com.platform.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.platform.constants.StringConstant;
import com.platform.domain.SysMesUser;
import com.platform.service.SysMesUserService;
import com.platform.util.Validate;

@Controller
@Scope("prototype")
public class SysMesUserAction extends GenericAction<SysMesUser> {

    private static final long serialVersionUID = 8116551950197940222L;

    @Autowired
    private SysMesUserService sysMesUserService;

    private String readFlag;

    /** 分页 */
    public String listPagination() throws Exception {
        if (!Validate.notNull(readFlag))
            readFlag = StringConstant.ALL;
        page = sysMesUserService.listPagination(page, readFlag);
        return SUCCESS;
    }

    /** 删除 */
    public String delete() throws Exception {
        sysMesUserService.delete(idList);
        return SUCCESS;
    }

    /** 清空 */
    public String deleteAll() throws Exception {
        sysMesUserService.deleteAll();
        return SUCCESS;
    }

    /** 标记已读 */
    public String updateReadFlag() throws Exception {
        sysMesUserService.updateReadFlag(idList);
        return SUCCESS;
    }

    /** 全部标记已读 */
    public String updateReadFlagAll() throws Exception {
        sysMesUserService.updateReadFlagAll();
        return SUCCESS;
    }

    public String getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(String readFlag) {
        this.readFlag = readFlag;
    }
}
