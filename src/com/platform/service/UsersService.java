package com.platform.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;
import com.platform.constants.SQLConstant;
import com.platform.constants.StringConstant;
import com.platform.dao.UsersDAO;
import com.platform.domain.Users;
import com.platform.exception.CRUDException;
import com.platform.util.FileHelper;
import com.platform.util.ImageHelper;
import com.platform.util.LoginBean;
import com.platform.util.Meta;
import com.platform.util.SearchUtil;
import com.platform.util.Uuid;
import com.platform.util.Validate;
import com.platform.vo.Page;

@Service
public class UsersService implements IService {

    private UsersDAO usersDAO;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        usersDAO = UsersDAO.getInstance(jdbcTemplate);
    }

    /**
     * 用户登录
     * 
     * @param accountName
     * @param password
     * @return
     * @throws CRUDException
     */
    public Users saveLogin(String accountName, String password) throws CRUDException {
        return usersDAO.find(SQLConstant.USERS_LOGIN_SELECT_SQL, new String[]{accountName, password});
    }

    /**
     * 登出
     * 
     * @throws CRUDException
     */
    public void saveLogout() throws CRUDException {
        ActionContext.getContext().getSession().remove("LoginBean");
    }

    /**
     * 验证用户名是否存在(存在:true,不存在:false)
     * 
     * @param accountName
     * @return
     * @throws CRUDException
     */
    public boolean accountNameExist(String accountName) throws CRUDException {
        int size = usersDAO.queryForInt(SQLConstant.USERS_SELECT_EXITS_SQL, new String[]{accountName});
        return size >0;
    }

    /**
     * 查找所有用户
     * 
     * @return
     * @throws CRUDException
     */
    public List<Users> findAllUsers() throws CRUDException {
        return usersDAO.findAll(SQLConstant.USERS_SELECT_ALL_SQL);
    }
    
    /**
     * 根据真实姓名模糊查找用户
     * 
     * @param realName
     * @return
     * @throws CRUDException
     */
    public List<Users> findUsersLikeRealName(String realName) throws CRUDException {
        if (Validate.notNull(realName)) {
        	String like = "%" + realName + "%";
            return usersDAO.findAll(SQLConstant.USERS_LIKE_REALNAME_SQL, new String[]{like,like});
        } else {
            return usersDAO.findAll(SQLConstant.USERS_SELECT_ALL_SQL);
        }
    }
    
    /**
     * 根据真实姓名和部门查找用户
     * @param realName
     * @param depId
     * @return
     * @throws CRUDException
     */
    public List<Users> findUsersLikeRealName(String realName, String depId) throws CRUDException {
        if (Validate.notNull(realName)) {
        	String like = "%" + realName + "%";
            return usersDAO.findAll(SQLConstant.USERS_LIKE_REALNAME_AND_DEP_SQL,new String[]{like,like,depId});
        } else {
            return usersDAO.findAll(SQLConstant.USERS_ALL_AND_DEP_SELECT_SQL,new String[]{depId});
        }
    }
    
    public List<Users> findMetaUsersLikeRealName(String realName, String moduleId) throws CRUDException {
        if (Validate.notNull(realName)) {
            return usersDAO.findAll(SQLConstant.USERS_LIKE_REALNAME_SQL + SQLConstant.getSQLInString(Meta.getUsers(moduleId), "id", "and"), new String[]{"%" + realName + "%","%" + realName + "%"});
        } else {
            return usersDAO.findAll(SQLConstant.USERS_ALL_SELECT_SQL + SQLConstant.getSQLInString(Meta.getUsers(moduleId), "id", "and"));
        }
    }

    /**
     * 查找真实姓名,以","分隔显示
     * 
     * @param userIds
     * @return
     * @throws CRUDException
     */
    public String findUserByAccountNames(String userIds) throws CRUDException {
        if (Validate.notNull(userIds)) {
            StringBuffer realNames = new StringBuffer();
            String[] splitIds = userIds.split(",");
            for (int i = 0; i < splitIds.length; i++) {
                Users user = usersDAO.find(SQLConstant.USERS_GET_BY_ID,new String[]{splitIds[i]});
                if (user != null) {
                    if (i == 0) {
                        realNames.append(user.getRealName());
                    } else {
                        realNames.append(",");
                        realNames.append(user.getRealName());
                    }
                }
            }
            return realNames.toString();
        } else {
            return "";
        }
    }

    /**
     * 拼接前台用户展现
     * 
     * @param userIds
     * @return
     * @throws CRUDException
     */
    public String findUserLinks(String userIds) throws CRUDException {
        if (Validate.notNull(userIds)) {
            StringBuffer realNames = new StringBuffer();
            
            /**
            realNames.append("<div class=\"link_content_inner\">");
            String[] splitIds = userIds.split(",");
            for (int i = 0; i < splitIds.length; i++) {
                Users user = usersDAO.get(splitIds[i]);
                if (user != null) {
                    realNames.append("<div><a href=\"javascript:void(0)\">");
                    realNames.append(user.getRealName());
                    realNames.append("</a></div>");
                }
            }
            realNames.append("</div>");
            **/
            
            //realNames.append("<a href=\"javascript:void(0)\">");
            String[] splitIds = userIds.split(",");
            for (int i = 0; i < splitIds.length; i++) {
                Users user = usersDAO.find(SQLConstant.USERS_GET_BY_ID,new String[]{splitIds[i]});
                if (user != null) {
                    if (i == 0) {
                        realNames.append(user.getRealName());
                    }else {
                        realNames.append(" " + user.getRealName());
                    }
                }
            }
            //realNames.append("</a>");
            return realNames.toString();
        } else {
            return "&nbsp;";
        }
    }

    /**
     * 查找除自己以外的所有用户
     * 
     * @return
     * @throws CRUDException
     */
    public List<Users> findAllUsersNotSelf() throws CRUDException {
        return usersDAO.findAll(SQLConstant.USERS_NOSELF_ALL_SQL, new String[]{LoginBean.getLoginBean().getUser().getId()});
    }

    /**
     * 根据首字符拼音查询
     * 
     * @param page
     * @param property
     * @param azparam
     * @return
     * @throws CRUDException
     */
    public Page<Users> paginationByPY(Page<Users> page, String property, String azparam) throws CRUDException {
        return usersDAO.pagination(page, SQLConstant.getAZHQL("Users", property, azparam), null);
    }

    /**
     * 根据ID查找用户
     * 
     * @param userId
     * @return
     * @throws Exception
     */
    public Users findById(String userId) throws CRUDException {
        return usersDAO.find(SQLConstant.USERS_GET_BY_ID, new String[]{userId});
    }

    /**
     * 修改用户信息
     * 
     * @param user
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void updateUsers(Users user, String imagePath, String targetPath) {
        Users oldUser = usersDAO.find(SQLConstant.USERS_GET_BY_ID, new String[]{user.getId()});
        oldUser.setPassword(user.getPassword());
        oldUser.setSex(user.getSex());
        oldUser.setBirthday(user.getBirthday());
        oldUser.setState(user.getState());
        oldUser.setArea(user.getArea());
        oldUser.setRealName(user.getRealName());
        oldUser.setDepartment(user.getDepartment());
        oldUser.setEditor(LoginBean.getLoginBean().getUser());
        oldUser.setEditTime(new Date());
        oldUser.setCellNo(user.getCellNo());
        if(Validate.notNull(imagePath)) {
            oldUser.setBigImage(getImagePath(imagePath, targetPath, Users.BIG_IMAGE));
            oldUser.setNormalImage(getImagePath(imagePath, targetPath, Users.NORMAL_IMAGE));
            oldUser.setSmallImage(getImagePath(imagePath, targetPath, Users.SMALL_IMAGE));
        }
        usersDAO.update(oldUser);
        
    }
    
    /**
     * 生成图片，获得文件路径
     * @param imagePath
     * @param targetPath
     * @param imageSize
     * @return
     */
    
    private String getImagePath(String imagePath, String targetPath, int imageSize) {
        String path = ImageHelper.scaleResize(imagePath, targetPath, imageSize);
        if(Validate.notNull(path)) {
            String shortPath = path.split(Users.PIC_FOLDER)[1];
            return FileHelper.SEPARATOR + Users.PIC_FOLDER + FileHelper.SEPARATOR + shortPath.substring(1, shortPath.length());
        } else {
            return "";
        }
    }

    /**
     * 修改密码
     * 
     * @param id
     * @param newPassword
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void updateUsersPassword(String id, String newPassword) throws CRUDException {
        Users user = usersDAO.find(SQLConstant.USERS_GET_BY_ID,new String[]{id});
        user.setEditor(LoginBean.getLoginBean().getUser());
        user.setEditTime(new Date());
        user.setPassword(newPassword);
        usersDAO.update(user);
    }

    /**
     * 修改用户状态
     * 
     * @param id
     * @param state
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void updateUsersState(String id, String state) throws CRUDException {
        Users user = usersDAO.find(SQLConstant.USERS_GET_BY_ID,new String[]{id});
        user.setState(state);
        user.setEditor(LoginBean.getLoginBean().getUser());
        user.setEditTime(new Date());
        usersDAO.update(user);
    }

    /**
     * 删除
     * 
     * @param idList
     * @throws Exception
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void deleteUsers(List<String> idList) throws CRUDException {
        if (Validate.collectionNotNull(idList)) {
            for (String id : idList) {
            	if(Validate.notNull(id))
                usersDAO.updateDelete(id);
            }
        }
    }

    /**
     * 分页
     * 
     * @param page
     * @return
     * @throws CRUDException
     */
    public Page<Users> listPagination(Page<Users> page, String searchType, List<String> searchValue, String deptId) throws CRUDException {
        String ss = SearchUtil.getString(
                new String[]{"u1.realName"},//高级查询条件
                new String[]{SearchUtil.STRING_LIKE},//查询类型
                searchType,//与或类型
                searchValue);//查询值
        if (Validate.notNull(deptId))
            return usersDAO.pagination(page, SQLConstant.USERS_DEPT_SQL + ss + SQLConstant.getUsersAndDeptWhere(" u1.creator "," u2.departmentid ",Meta.getUsers(StringConstant.DEPARTMENT_ID),Meta.getDepartments(StringConstant.DEPARTMENT_ID)),new Object[]{deptId});
        else
            return usersDAO.pagination(page,SQLConstant.USERS_ALL_MORE_SELECT+ss + SQLConstant.getUsersAndDeptWhere(" u1.creator "," u2.departmentid ",Meta.getUsers(StringConstant.DEPARTMENT_ID),Meta.getDepartments(StringConstant.DEPARTMENT_ID)),null);
    }
    
    /**
     * 创建新用户
     * 
     * @param user
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void saveUsers(Users user, String imagePath, String targetPath) throws CRUDException {
        user.setCreator(LoginBean.getLoginBean().getUser());
        user.setCreateTime(new Date());
        user.setState(user.getState());
        if(Validate.notNull(imagePath)) {
            user.setBigImage(getImagePath(imagePath, targetPath, Users.BIG_IMAGE));
            user.setNormalImage(getImagePath(imagePath, targetPath, Users.NORMAL_IMAGE));
            user.setSmallImage(getImagePath(imagePath, targetPath, Users.SMALL_IMAGE));
        }
        usersDAO.save(user);
    }
    
    /**
     * 根据属性查找用户
     * 
     * @param userId
     * @return
     * @throws Exception
     */
    public Users findByProperty(String propertyName, String value) throws CRUDException {
       return usersDAO.find("select * from users where "+propertyName+" = ?", new String[]{value});
    }
    
    /**
     * 查找部门下的所有用户 like 'deptCode%'
     * @param deptCode 部门code
     * @return 
     * @throws CRUDException
     */
    public List<Users> findByDeptCode(String deptCode) throws CRUDException {
        return usersDAO.findAll(SQLConstant.USERS_BY_DEPARTCODE_SQL,new String[]{deptCode+"%"});
    }
}