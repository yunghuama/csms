package com.platform.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.platform.constants.IntercomConstant;
import com.platform.constants.SQLConstant;
import com.platform.constants.StringConstant;
import com.platform.dao.AttachedFileDAO;
import com.platform.dao.IntercomDAO;
import com.platform.dao.UsersDAO;
import com.platform.domain.AttachedFile;
import com.platform.domain.Intercom;
import com.platform.exception.CRUDException;
import com.platform.util.LoginBean;
import com.platform.util.SearchUtil;
import com.platform.util.Validate;
import com.platform.vo.Page;

@Service
public class IntercomService implements IService {

    private IntercomDAO intercomDAO;
    private UsersDAO usersDAO;
    private AttachedFileDAO attachedFileDAO;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        intercomDAO = IntercomDAO.getInstance(jdbcTemplate);
        usersDAO = UsersDAO.getInstance(jdbcTemplate);
        attachedFileDAO = AttachedFileDAO.getInstance(jdbcTemplate);
    }

    /**
     * 分页
     * 
     * @param page
     * @param type
     * @return
     * @throws CRUDException
     */
    public Page<Intercom> listPagination(Page<Intercom> page, String type, String searchType, List<String> searchValue) throws CRUDException {
    	String ss = SearchUtil.getString(
				new String[]{"title", "sendTime"},//高级查询条件
				new String[]{SearchUtil.STRING_LIKE, SearchUtil.BETWEEN_DATE},//查询类型
				searchType,//与或类型
				searchValue);//查询值
    	
    	if("send".equals(type)){
    		return intercomDAO.pagination(page, "from Intercom where readFlag = ? and owner = ?" + ss,  "sendTime desc", IntercomConstant.HasSend, LoginBean.getLoginBean().getUser());
    	}else if("delete".equals(type)){
    		return intercomDAO.pagination(page, "from Intercom where readFlag = ? and owner = ?" + ss,  "sendTime desc", IntercomConstant.HasDeleted, LoginBean.getLoginBean().getUser());
    	}else{
    		return intercomDAO.pagination(page, "from Intercom where (readFlag = ? or readFlag = ?) and owner = ?" + ss, "sendTime desc", IntercomConstant.HasRead, IntercomConstant.NoRead, LoginBean.getLoginBean().getUser());
    	}
    	
    	/*
    	if (StringConstant.ALL.equals(type)) {
            return intercomDAO.pagination(page, SQLConstant.INTERCOM_OWNER_PAGE_HQL + ss, "sendTime desc", LoginBean.getLoginBean().getUser());
        } else {
            return intercomDAO.pagination(page, SQLConstant.INTERCOM_PAGE_HQL + ss, "sendTime desc", type, LoginBean.getLoginBean().getUser());
        }
        */
    }

    /**
     * 根据编号查找通信信息
     * 
     * @param intercomId
     * @return
     * @throws Exception
     */
    public Intercom findById(String intercomId) throws Exception {
        return intercomDAO.find(SQLConstant.DICTIONARY_SELECT_BY_ID_SQL,new String[]{intercomId});
    }
    
    /**
     * 根据title查数据
     * 
     */
    public List<Intercom>findByTitle(String title)throws Exception {
    	return intercomDAO.findAll(SQLConstant.INTERCOM_SELECT_BY_TITLE_SQL,new String[]{title});
    }

    /**
     * 查看并标记为已读
     * 
     * @param intercomId
     * @return
     * @throws Exception
     */
    public String updateView(String intercomId) throws Exception {
        Intercom intercom = intercomDAO.find(SQLConstant.INTERCOM_SELECT_BY_ID,new String[]{intercomId});
        if (StringConstant.FALSE.equals(intercom.getReadFlag())) {
            intercom.setReadFlag(StringConstant.TRUE);
            intercomDAO.update(intercom);
        }
        return intercom.getReadFlag();
    }

    /**
     * 查找发送人发送的所有信息
     * 
     * @param senderId
     * @return
     * @throws Exception
     */
    public List<Intercom> findBySenderId(String senderId) throws CRUDException {
        return intercomDAO.findAll(SQLConstant.INTERCOM_SEND_ALL_SQL, new String[]{senderId, senderId});
    }

    /**
     * 根据接收人和已读状态查找信息
     * 
     * @param page
     * @return
     */
    public List<Intercom> findByReadFlag(String readFlag) throws CRUDException {
        return intercomDAO.findAll(SQLConstant.INTERCOM_SELECT_OWNER_READFLAG_SQL, new String[]{LoginBean.getLoginBean().getUser().getId(), readFlag});
    }

    /**
     * 保存通信信息
     * 
     * @param intercom
     * @throws CRUDException
     */
    public void saveIntercom(Intercom intercom, List<AttachedFile> listAttachedFile, String oldIintercomId) throws CRUDException {
        String[] owners = Validate.notNull(intercom.getReplier()) ? intercom.getReplier().split(",") : null;
        String[] copiers = Validate.notNull(intercom.getCopier()) ? intercom.getCopier().split(",") : null;
        intercom.setSender(intercom.getSender()==null?LoginBean.getLoginBean().getUser():intercom.getSender());
        intercom.setOwner(intercom.getOwner()==null?LoginBean.getLoginBean().getUser():intercom.getOwner());
        intercom.setReadFlag(StringConstant.SENDED);
        intercom.setSendTime(new Date());
        intercom.setType(intercom.getType()==null? "0" : intercom.getType());
        
        List<AttachedFile> oldAFList = null;
        if (!oldIintercomId.equals("")) {
            oldAFList = attachedFileDAO.findAll(SQLConstant.ATTACHEDFILE_BY_SUPERID_SQL, new String[]{oldIintercomId});
        }

        if (Validate.collectionNotNull(listAttachedFile) || Validate.collectionNotNull(oldAFList))
            intercom.setAf(StringConstant.TRUE);
        else
            intercom.setAf(StringConstant.FALSE);

        intercomDAO.save(intercom);

        // 保存附件信息
        if (Validate.collectionNotNull(listAttachedFile)) {
            for (AttachedFile af : listAttachedFile) {
                af.setSuperId(intercom.getId());
                af.setCreateTime(new Date());
                attachedFileDAO.save(af);
            }
        }
        // 保存转发的附件信息
        if (Validate.collectionNotNull(oldAFList)) {
            for (AttachedFile af : oldAFList) {
                AttachedFile file = new AttachedFile();
                file.setSuperId(intercom.getId());
                file.setFileName(af.getFileName());
                file.setFilePath(af.getFilePath());
                file.setFileType(af.getFileType());
                file.setContentType(af.getContentType());
                file.setTitle(af.getTitle());
                file.setCreateTime(new Date());
                attachedFileDAO.save(file);
            }
        }
        this.createIntercom(owners, intercom, listAttachedFile, oldAFList);
        this.createIntercom(copiers, intercom, listAttachedFile, oldAFList);
    }

    /**
     * 创建通信信息
     * 
     * @param strInfo
     * @param intercom
     * @throws CRUDException
     */
    private void createIntercom(String[] userInfo, Intercom intercom, List<AttachedFile> listAttachedFile, List<AttachedFile> oldAFList) throws CRUDException {
        if (Validate.arrayNotNull(userInfo)) {
            for (String userId : userInfo) {
                if(Validate.notNull(userId)) {
                    Intercom in = new Intercom();
                    in.setTitle(intercom.getTitle());
                    in.setContents(intercom.getContents());
                    in.setReplier(intercom.getReplier());
                    in.setCopier(intercom.getCopier());
                    in.setSender(intercom.getSender());
                    in.setSendTime(intercom.getSendTime());
                    in.setOwner(usersDAO.find(SQLConstant.USERS_GET_BY_ID,new String[]{userId}));
                    in.setType(intercom.getType());
                    in.setReadFlag(StringConstant.FALSE);
                    in.setInform(StringConstant.FALSE);
                    in.setAf(intercom.getAf());
                    intercomDAO.save(in);
    
                    // 保存附件信息
                    if (Validate.collectionNotNull(listAttachedFile)) {
                        for (AttachedFile af : listAttachedFile) {
                            AttachedFile file = new AttachedFile();
                            file.setSuperId(in.getId());
                            file.setFileName(af.getFileName());
                            file.setFilePath(af.getFilePath());
                            file.setFileType(af.getFileType());
                            file.setContentType(af.getContentType());
                            file.setTitle(af.getTitle());
                            file.setCreateTime(new Date());
                            attachedFileDAO.save(file);
                        }
                    }
                    // 保存转发的附件信息
                    if (Validate.collectionNotNull(oldAFList)) {
                        for (AttachedFile af : oldAFList) {
                            AttachedFile file = new AttachedFile();
                            file.setSuperId(in.getId());
                            file.setFileName(af.getFileName());
                            file.setFilePath(af.getFilePath());
                            file.setFileType(af.getFileType());
                            file.setContentType(af.getContentType());
                            file.setTitle(af.getTitle());
                            file.setCreateTime(new Date());
                            attachedFileDAO.save(file);
                        }
                    }
                }
            }
        }
    }

    /**
     * 修改通信信息
     * 
     * @param intercom
     * @throws CRUDException
     */
    public void updateIntercom(Intercom intercom) throws CRUDException {
        Intercom oldIntercom = intercomDAO.find(SQLConstant.INTERCOM_SELECT_BY_ID,new String[]{intercom.getId()});
        oldIntercom.setTitle(intercom.getTitle());
        oldIntercom.setContents(intercom.getContents());
        oldIntercom.setReplier(intercom.getReplier());
        oldIntercom.setCopier(intercom.getCopier());
        oldIntercom.setSender(intercom.getSender());
        oldIntercom.setSendTime(intercom.getSendTime());
        oldIntercom.setOwner(intercom.getOwner());
        oldIntercom.setReadFlag(intercom.getReadFlag());
        oldIntercom.setInform(intercom.getInform());
        intercomDAO.update(oldIntercom);
    }

    /**
     * 将未读更新成已读状态
     * 
     * @param id
     * @throws CRUDException
     */
    public void updateHasReaded(String id) throws CRUDException {
        Intercom intercom = intercomDAO.find(SQLConstant.INTERCOM_SELECT_BY_ID,new String[]{id});
        this.updateHasReaded(intercom);
    }

    /**
     * 将未读更新成已读状态
     * 
     * @param intercom
     * @throws CRUDException
     */
    public void updateHasReaded(Intercom intercom) throws CRUDException {
        intercom.setReadFlag(StringConstant.TRUE);
        intercomDAO.update(intercom);
    }

    /**
     * 批量将未读更新成已读状态
     * 
     * @param idList
     * @throws CRUDException
     */
    public void updateHasReadedBatch(List<String> idList) throws CRUDException {
        if (Validate.collectionNotNull(idList)) {
            for (String id : idList) {
                this.updateHasReaded(id);
            }
        }
    }

    /**
     * 将所有的未读更新成已读
     * 
     * @throws CRUDException
     */
    public void updateAllHasReaded() throws CRUDException {
        List<Intercom> listIntercom = this.findByReadFlag(StringConstant.FALSE);
        if (Validate.collectionNotNull(listIntercom)) {
            for (Intercom intercom : listIntercom) {
                this.updateHasReaded(intercom);
            }
        }
    }

    /**
     * 修改发送人收到接收人已读的提示状态
     * 
     * @return
     * @throws CRUDException
     */
    public List<String> updateInform(String senderId) throws CRUDException {
        List<String> infoList = new ArrayList<String>();

        List<Intercom> intercomList = intercomDAO.findAll(SQLConstant.INTERCOM_INFORM_SQL, new Object[] { senderId, senderId});
        for (Intercom intercom : intercomList) {
            infoList.add("标题为：" + intercom.getTitle() + "的信息已经被【" + intercom.getOwner().getRealName() + "】打开！");
            intercom.setInform(StringConstant.TRUE);
            intercomDAO.update(intercom);
        }

        return infoList;
    }

    /**
     * 批量删除信息
     * 
     * @param idList
     * @throws Exception
     */
    public void deleteIntercomBatch(List<String> idList) throws CRUDException {
        if (Validate.collectionNotNull(idList)) {
        	for (String id : idList) {
        		intercomDAO.deleteByProperty(SQLConstant.INTERCOM_DELETE_BY_ID_SQL,id);
        		attachedFileDAO.deleteByIdList(SQLConstant.ATTACHEDFILE_DELETE_BY_IDS,attachedFileDAO.findIDS(SQLConstant.ATTACHEDFILE_SELECTIDS_BY_SUPERID, new String[]{id}));
			}
        }
    }

    /**
     * 包装通信信息
     * 
     * @param intercomId
     * @return
     * @throws CRUDException
     */
    public Intercom packageIntercom(String intercomId) throws CRUDException {
        Intercom intercom = intercomDAO.find(SQLConstant.INTERCOM_SELECT_BY_ID,new String[]{intercomId});
        StringBuffer contents = new StringBuffer("<fieldset><legend>" + intercom.getTitle() + "</legend>");
        contents.append(intercom.getContents());
        contents.append("</fieldset>");
        intercom.setContents(contents.toString());
        return intercom;
    }
    
    /**
     * 查找未读数量
     * 
     * @return
     * @throws CRUDException
     */
    public Integer countNoRead() throws CRUDException {
    	return intercomDAO.countNoRead();
    }
    public Integer countNoRead(String type) throws CRUDException {
    	return intercomDAO.countNoRead(type);
    }
    
    /**
     * 查找未读信息
     * 
     * @return
     * @throws CRUDException
     */
    public List<Intercom> findNoRead() throws CRUDException {
    	return intercomDAO.findAll(SQLConstant.INTERCOM_SELECT_OWNER_READFLAG_SQL,new String[]{LoginBean.getLoginBean().getUser().getId(), StringConstant.FALSE});
    }
    public List<Intercom> findAllNoRead() throws CRUDException{
    	return intercomDAO.findAll(SQLConstant.INTERCOM_SELECT_ALL_NOREAD_SQL);
    }
    public List<Intercom> findNoRead(String type) throws CRUDException {
    	return intercomDAO.findAll(SQLConstant.INTERCOM_SELECT_BY_READFLAG_OWNER_TYPE_SQL,new String[]{StringConstant.FALSE, LoginBean.getLoginBean().getUser().getId(), type});
    }
    public void readAll(String userId) throws CRUDException{
    	 List<Intercom> listIntercom = intercomDAO.findAll(SQLConstant.INTERCOM_SELECT_OWNER_READFLAG_SQL,new String[]{userId,StringConstant.FALSE});
         if (Validate.collectionNotNull(listIntercom)) {
             for (Intercom intercom : listIntercom) {
                 this.updateHasReaded(intercom);
             }
         }
    	
    }
    /**
     * 修改旗帜信息
     */
    public void updateFlag(String id,String type) throws CRUDException {
    	if(Validate.notNull(id)){
    		Intercom intercom = intercomDAO.find(SQLConstant.INTERCOM_SELECT_BY_ID,new String[]{id});
    		if(Validate.notNull(type)){
    			intercom.setFlagType(type);
        	}else {
        		intercom.setFlagType(type);
        	}
    		intercomDAO.update(intercom);
    	}
    	
    }
}