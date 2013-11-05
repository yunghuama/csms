package com.platform.web.ajax;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.platform.constants.StringConstant;
import com.platform.domain.RoleUsers;
import com.platform.domain.Users;
import com.platform.service.RoleUsersService;
import com.platform.service.UsersService;
import com.platform.util.LoginBean;
import com.platform.vo.UserVO;
import com.platform.web.action.GenericAction;

@Controller
@Scope("prototype")
public class UsersAjaxAction extends GenericAction<Users> {

    private static final long serialVersionUID = -6403417740622732138L;

    @Autowired
    private UsersService usersService;
    @Autowired
    private RoleUsersService roleUsersService;

    private String userId;
    private String state;
    private List<UserVO> userList;
    private List<String> idList;

    private String accountName;
    private String password;
    private String passwordRe;
    private String passwordReRe;
    private String realName;
    
    private String moduleId;
    private String inputId;
    private String contentId;
    
    private String inDep;

    private String nbSelect;
    
    /**
     * 根据条件获取人员
     * 
     * @return
     * @throws Exception
     */
    public String listUsersByCondition() throws Exception {
        userList = new ArrayList<UserVO>();
        List<Users> list = null;
        if(inDep!=null){ //仅当前部门的
        	Users currentUser = LoginBean.getLoginBean().getUser();
        	String depId = currentUser.getDepartment().getId();
        	list = usersService.findUsersLikeRealName(realName, depId);
        	if("T".equals(nbSelect)){
        		List<RoleUsers> ruList = roleUsersService.findUsersRoleByUsers(currentUser.getId());
        		for(RoleUsers ru : ruList){
        			if(ru.getRole().getName()!=null&&ru.getRole().getName().contains("总经理")||ru.getRole().getName().contains("副总")){
        				list = usersService.findUsersLikeRealName(realName);
        				break;
        			}
        		}
        	}
        }else{
        	list = usersService.findUsersLikeRealName(realName);
        }
       
     
        
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Users user = (Users) iter.next();
            UserVO vo = new UserVO();
            vo.setUserId(user.getId());
            vo.setRealName(user.getRealName());
            vo.setAccountName(user.getAccountName());
            vo.setSex(user.getSex());
            vo.setDeptId(user.getDepartment().getId());
            vo.setDeptName(user.getDepartment().getName());
            vo.setState(user.getState());
            vo.setBigImage(user.getBigImage());
            vo.setNormalImage(user.getNormalImage());
            vo.setSmallImage(user.getSmallImage());
            userList.add(vo);
        }
        return Action.SUCCESS;
    }
    
    public String listMetaUsers() throws Exception {
    	userList = new ArrayList<UserVO>();
    	List<Users> list = usersService.findMetaUsersLikeRealName(realName, moduleId);
    	for (Iterator iter = list.iterator(); iter.hasNext();) {
            Users user = (Users) iter.next();
            UserVO vo = new UserVO();
            vo.setUserId(user.getId());
            vo.setRealName(user.getRealName());
            vo.setAccountName(user.getAccountName());
            vo.setSex(user.getSex());
            vo.setDeptId(user.getDepartment().getId());
            vo.setDeptName(user.getDepartment().getName());
            vo.setState(user.getState());
            vo.setBigImage(user.getBigImage());
            vo.setNormalImage(user.getNormalImage());
            vo.setSmallImage(user.getSmallImage());
            userList.add(vo);
        }
    	return Action.SUCCESS;
    }

    /**
     * 关联用户角色
     * @return
     * @throws Exception
     */
    public String saveUsersRole() throws Exception {
        roleUsersService.saveUsersRole(idList, userId, state);
        return Action.SUCCESS;
    }

    /**
     * 验证帐号是否存在
     * @return
     * @throws Exception
     */
    public String validateAccountName() throws Exception {
        if (usersService.accountNameExist(accountName))
            state = StringConstant.FALSE;
        else
            state = StringConstant.TRUE;
        return Action.SUCCESS;
    }
    
    /**
     * 修改密码
     * @return
     * @throws Exception
     */
    public String changePassword() throws Exception {
    	if(!LoginBean.getLoginBean().getUser().getPassword().equals(password)) {
    		state = "2";
    	} else if(!passwordRe.equals(passwordReRe)) {
    		state = "3";
    	} else {
    		state = "1";
    		LoginBean.getLoginBean().getUser().setPassword(passwordReRe);
    		usersService.updateUsers(LoginBean.getLoginBean().getUser(), null, null);
    	}
    	return Action.SUCCESS;
    }
    /**
     * 用户选择列表
     * @return
     * @throws Exception
     */
    public String listPagination() throws Exception {
        
        page = usersService.listPagination(page, searchType, searchValue, null);
        
        return SUCCESS;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<UserVO> getUserList() {
        return userList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public List<String> getIdList() {
        return idList;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getState() {
        return state;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPasswordRe(String passwordRe) {
		this.passwordRe = passwordRe;
	}

	public void setPasswordReRe(String passwordReRe) {
		this.passwordReRe = passwordReRe;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

    public String getInputId() {
        return inputId;
    }

    public void setInputId(String inputId) {
        this.inputId = inputId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }
	public String getInDep() {
		return inDep;
	}
	public void setInDep(String inDep) {
		this.inDep = inDep;
	}

	public String getNbSelect() {
		return nbSelect;
	}

	public void setNbSelect(String nbSelect) {
		this.nbSelect = nbSelect;
	}
	
}