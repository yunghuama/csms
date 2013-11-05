package com.platform.web.ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.platform.constants.StringConstant;
import com.platform.domain.Users;
import com.platform.service.UsersService;

@Controller
@Scope("prototype")
public class WebServiceLoginAjaxAction {

	@Autowired
	private UsersService usersService;

	private String okFlag;
	private String errorMes;

	/**
	 * 验证登陆
	 * 
	 * @return
	 * @throws Exception
	 */
	public String checkLogin(String accountName, String password) throws Exception {
		Users user = usersService.saveLogin(accountName, password);
		if (user == null) {
			errorMes = "用户名或密码错误，请重新登录";
			okFlag="1";
		} else if (!StringConstant.TRUE.equals(user.getState())) {
			errorMes = "用户已被禁用，无法登录";
			okFlag="1";
		} else {
			okFlag="0";
		}
		return Action.SUCCESS;
	}

	public String getErrorMes() {
		return errorMes;
	}

	public void setErrorMes(String errorMes) {
		this.errorMes = errorMes;
	}

	public String getOkFlag() {
		return okFlag;
	}

	public void setOkFlag(String okFlag) {
		this.okFlag = okFlag;
	}

}