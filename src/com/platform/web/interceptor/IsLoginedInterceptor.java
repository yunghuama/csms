package com.platform.web.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.platform.constants.StringConstant;

public class IsLoginedInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = 8022747182142597416L;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        if (ActionContext.getContext().getSession().get("LoginBean") == null) {
            return StringConstant.NO_LOGIN;
        } else {
            return invocation.invoke();
        }
    }

}
