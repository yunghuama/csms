package com.platform.web.ajax;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.platform.constants.StringConstant;
import com.platform.domain.Intercom;
import com.platform.service.IntercomService;
import com.platform.util.DateTimeHelper;
import com.platform.util.Validate;
import com.platform.vo.IntercomVO;

@Controller
@Scope("prototype")
public class IntercomAjaxAction {

    @Autowired
    private IntercomService intercomService;

    private String intercomId;
    private String result;
    private Integer noread;
    private List<IntercomVO> intercomList;
    
    private String type;

    /**
     * 将未读更新为已读,并返回其状态html代码
     * 
     * @return
     * @throws Exception
     */
    public String updateIntercomToRead() throws Exception {
        result = StringConstant.INTERCOM_SELECT.get(intercomService.updateView(intercomId));
//        //把缓存中的记录改为已读
//        Map<String,List<MessageVO>> messageMap=(Map<String,List<MessageVO>>)CacheUtil.get(RoleCache.CACHE_NAME, "VOAMessages");
//    	List<MessageVO> list = messageMap.get(LoginBean.getLoginBean().getUser().getId());
//    	for(int i=0;i<list.size();i++){
//    		MessageVO vo = list.get(i);
//    		if(vo.getId().equals(intercomId)){
//    			list.remove(i);
//    			break;
//    		}
//    	}
        return Action.SUCCESS;
    }
    
    /**
     * 统计未读信息
     * 
     * @return
     * @throws Exception
     */
    public String findNoReadCount() throws Exception {
    	if(type!=null && type.trim().length()!=0){
    		noread = intercomService.countNoRead(type);
    	}else{
    		noread = intercomService.countNoRead();
    	}
    	return Action.SUCCESS;
    }
    
    /**
     * 查找未读信息
     * 
     * @return
     * @throws Exception
     */
    public String findNoRead() throws Exception {
    	List<Intercom> list = intercomService.findNoRead();
    	intercomList = new ArrayList<IntercomVO>();
    	for (Intercom intercom : list) {
    		IntercomVO vo = new IntercomVO();
    		vo.setId(intercom.getId());
    		vo.setSender(intercom.getSender().getRealName());
    		vo.setTitle(intercom.getTitle());
    		vo.setSendTime(DateTimeHelper.formatDateTime("yyyy-MM-dd HH:mm:ss", intercom.getSendTime()));
    		intercomList.add(vo);
		}
    	return Action.SUCCESS;
    }
    
    /**
     * 更改消息旗帜
     * @return
     * @throws Exception
     */
    public String updateFlag() throws Exception {
    	intercomService.updateFlag(intercomId, type);
    	return Action.SUCCESS;
    }
    
    
    public String findNoReadByType() throws Exception {
    	List<Intercom> list = intercomService.findNoRead(type);
    	if(Validate.collectionNotNull(list)){
    		StringBuffer sb = new StringBuffer();
    		sb.append("[");
    		for(Iterator<Intercom> it=list.iterator(); it.hasNext();){
    			Intercom intercom = it.next();
    			sb.append("{\"id\":\"");
    			sb.append(intercom.getId());
    			sb.append("\",\"title\":\"");
    			sb.append(URLEncoder.encode(intercom.getTitle(), "UTF-8"));
    			sb.append("\"}");
    			if(it.hasNext()){
    				sb.append(",");
    			}
    		}
    		sb.append("]");
    		result = sb.toString();
    	}else{
    		result = "";
    	}
    	return Action.SUCCESS;
    }

    public String getIntercomId() {
        return intercomId;
    }

    public void setIntercomId(String intercomId) {
        this.intercomId = intercomId;
    }

    public String getResult() {
        return result;
    }

	public Integer getNoread() {
		return noread;
	}

	public List<IntercomVO> getIntercomList() {
		return intercomList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}