package com.platform.util;

import com.opensymphony.xwork2.ActionContext;
import com.platform.domain.Users;

/**
 * 文件名：LoginBean.java<br>
 * 说明：登入封装 <br>
 * 作者：马金凯<br>
 * 更改记录： <br>
 * -------------------------------------------------------<br>
 * 改动人 时间 原因<br>
 * -------------------------------------------------------<br>
 * 马金凯 2008-1-10 创建文件<br>
 * -------------------------------------------------------<br>
 */
public class LoginBean {

    /** 帐户信息 */
    private Users user = new Users();

    /**
     * 获得session用户
     * 
     * @return LoginBean
     */
    public static LoginBean getLoginBean() {
        LoginBean loginBean = (LoginBean) ActionContext.getContext().getSession().get("LoginBean");
        synchronized (loginBean) {
            if (loginBean == null) {
                loginBean = new LoginBean();
                Users user = new Users();
                user.setId("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                user.setAccountName("admin");
                loginBean.setUser(user);
            }
            return loginBean;
        }
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}