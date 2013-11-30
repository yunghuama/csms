package com.platform.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

import com.csms.domain.District;
import com.csms.domain.Enterprise;

public class Users extends BaseDomain {

    private static final long serialVersionUID = -7094766838338441306L;
    private Department department; // 部门对象
    private String accountName; // 账户名
    private String realName; // 真实姓名
    private String password; // 密码
    private String sex; // 性别
    private Date birthday; // 生日
    private String edu; // 学历
    private String area; //地域(暂时济南1，深圳0)
    private String state; // 帐户可用状态(T:可用,F:停用)
    private String cellNo;//手机号
    private String bigImage;
    private String normalImage;
    private String smallImage;
    private String remark;
    private Enterprise enterprise;
    private Role role;
    private District district;
    private String roleId;
    private Set sysMesUsers = new HashSet(0);
    private Set roleUsers = new HashSet(0);
    
    public static final int BIG_IMAGE = 168;
    public static final int NORMAL_IMAGE = 50;
    public static final int SMALL_IMAGE = 16;
    
    public static final String PIC_FOLDER = "userhead";

    @JSON(serialize = false)
    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getRealName() {
        return this.realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @JSON(serialize = false)
    public Set getSysMesUsers() {
        return this.sysMesUsers;
    }

    public void setSysMesUsers(Set sysMesUsers) {
        this.sysMesUsers = sysMesUsers;
    }

    @JSON(serialize = false)
    public Set getRoleUsers() {
        return roleUsers;
    }

    public void setRoleUsers(Set roleUsers) {
        this.roleUsers = roleUsers;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEdu() {
		return edu;
	}

	public void setEdu(String edu) {
		this.edu = edu;
	}

	public String getBigImage() {
		return bigImage;
	}

	public void setBigImage(String bigImage) {
		this.bigImage = bigImage;
	}

	public String getNormalImage() {
		return normalImage;
	}

	public void setNormalImage(String normalImage) {
		this.normalImage = normalImage;
	}

	public String getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

    public String getCellNo() {
        return cellNo;
    }

    public void setCellNo(String cellNo) {
        this.cellNo = cellNo;
    }

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

}