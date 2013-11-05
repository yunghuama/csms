package com.platform.web.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.platform.util.UploadHelper;

/**
 * <p>程序名称：       FileUploadAction.java</p>
 * <p>程序说明：       用于上传文件的类</p>
 * <p>版权信息：       Copyright 深圳市维远泰克科技有限公司</p>
 * <p>时间：          Jan 14, 2011 1:09:17 PM</p>	
 * 
 * @author：          Marker.King
 * @version：         Ver 0.1
 */
@SuppressWarnings("unchecked")
@Controller
@Scope("prototype")
public class FileUploadAction extends GenericAction {

	private static final long serialVersionUID = -6467542593013848930L;
	private String callback;
	private UploadHelper uploadHelper;
	
	/**
	 * 临时上传
	 * @return
	 * @throws Exception
	 */
	public String tempUpload() throws Exception {
		uploadHelper = new UploadHelper(upload, uploadFileName, uploadTitle, uploadContentType, savePath, UploadHelper.UID);
		return SUCCESS;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public UploadHelper getUploadHelper() {
		return uploadHelper;
	}

}