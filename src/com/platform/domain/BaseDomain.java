package com.platform.domain;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 文件名：BaseDomain.java<br>
 * 说明：映射类的公共父类 <br>
 * 作者：马金凯<br>
 * 更改记录： <br>
 * -------------------------------------------------------<br>
 * 改动人 时间 原因<br>
 * -------------------------------------------------------<br>
 * 马金凯 2007-12-17 创建文件<br>
 * -------------------------------------------------------<br>
 */
public abstract class BaseDomain implements Serializable {

    protected String id;
    protected Users creator;
    protected Users editor;
    protected Date createTime;
    protected Date editTime;

    @JSON(serialize = false)
    public Users getCreator() {
        return creator;
    }

    public void setCreator(Users creator) {
        this.creator = creator;
    }

    @JSON(serialize = false)
    public Users getEditor() {
        return editor;
    }

    public void setEditor(Users editor) {
        this.editor = editor;
    }

    @JSON(format = "yyyy-MM-dd HH:mm:ss")
    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JSON(format = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    /**
     * 覆盖toString 
     * @author zhangqiang
     */
    @SuppressWarnings("unchecked")
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		Class clazz = this.getClass();
		Field[] fields = clazz.getDeclaredFields();
		try {
			for (int i = 0; i < fields.length; i++) {
				Field f = fields[i];
				if (Modifier.isStatic(f.getModifiers()))
					continue;
				f.setAccessible(true);
				Object value = f.get(this);
				if (buffer.length() > 0) {
					buffer.append(',');
				}
				if (value == null) {
					buffer.append(f.getName() + "=null");
				} else {
					buffer.append(f.getName() + "=" + value);
				}
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		return buffer.toString();
	}
}