package com.platform.service;

import java.io.File;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.constants.OAStringConstant;
import com.platform.constants.SQLConstant;
import com.platform.dao.AttachedFileDAO;
import com.platform.domain.AttachedFile;
import com.platform.exception.CRUDException;
import com.platform.util.DateTimeHelper;
import com.platform.util.SearchUtil;
import com.platform.util.Validate;
import com.platform.vo.Page;

@Service
public class AttachedFileService implements IService {

    private AttachedFileDAO attachedFileDAO;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        attachedFileDAO = AttachedFileDAO.getInstance(jdbcTemplate);
    }

    /**
     * 根据对象ID查找附件
     * 
     * @param superId
     * @return
     * @throws CRUDException
     */
    public List<AttachedFile> findAttachedFileBySuperId(String superId) throws CRUDException {
        return attachedFileDAO.findAll(SQLConstant.ATTACHEDFILE_BY_SUPERID_SQL, new String[]{superId});
    }
    
    /**
     * 根据对象ID和类型查找附件
     * 
     * @param superId
     * @return
     * @throws CRUDException
     */
    public List<AttachedFile> findAttachedFileBySuperIdAndExtendType(String superId, String hasCostDoc) throws CRUDException {
    	if("F".equals(hasCostDoc)){
    		 return attachedFileDAO.findAll(SQLConstant.ATTACHEDFILE_BY_SUPERID_SQL, new String[]{superId});
    	} else{
    		return attachedFileDAO.findAll(SQLConstant.ATTACHEDFILE_BY_SUPERID_AND_EXTENDTYPE_SQL,new String[]{superId,"quot"});
    	}
       
    }

    /**
     * 根据对象ID和类型查找附件
     * 
     * @param superId
     * @return
     * @throws CRUDException
     */
    public List<AttachedFile> findAttachedFileBySuperIdAndExtendType1(String superId, String hasCostDoc) throws CRUDException {
    	if("F".equals(hasCostDoc)){
    		 return attachedFileDAO.findAll(SQLConstant.ATTACHEDFILE_BY_SUPERID_AND_EXTENDTYPE_SQL,new String[]{superId,"tech"});
    	} else{
    		return attachedFileDAO.findAll(SQLConstant.ATTACHEDFILE_BY_SUPERID_AND_EXTENDTYPE_SQL,new String[]{superId,"comm"});
    	}
       
    }
    
    /**
     * 删除附件
     * 
     * @param id
     * @throws CRUDException
     */
    public void delete(String id) throws CRUDException {
        AttachedFile af = this.findFileById(id);
        File f = new File(ServletActionContext.getServletContext().getRealPath(af.getFilePath()));
        attachedFileDAO.deleteByProperty(SQLConstant.ATTACHEDFILE_DELETE_BY_ID_SQL, af.getId());
        if (f.exists()) {
            f.delete();
        }
    }

    /**
     * 根据ID查找文件
     * 
     * @param id
     * @return
     * @throws CRUDException
     */
    public AttachedFile findFileById(String id) throws CRUDException {
        return attachedFileDAO.find(SQLConstant.ATTACHEDFILE_SELECT_BY_ID_SQL,new String[]{id});
    }
    
    /**
     * 分页
     * 
     * @param page
     * @param type
     * @return
     * @throws CRUDException
     */
    public Page<AttachedFile> listPagination(Page<AttachedFile> page, String searchType, List<String> searchValue) throws CRUDException {
        String ss = SearchUtil.getString(
                new String[]{"title"},//高级查询条件
                new String[]{SearchUtil.STRING_LIKE},//查询类型
                searchType,//与或类型
                searchValue);//查询值
        return attachedFileDAO.pagination(page, SQLConstant.ATTACHEDFILE_BY_SUPERID_SQL + ss, "createTime desc", OAStringConstant.DOCUMENTS_SUPERID);
    }
    /**
     * 保存附件
     * 
     * @param listAttachedFile
     * @param superId
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void save(List<AttachedFile> listAttachedFile, String superId) throws CRUDException {
        // 保存附件信息
        if (Validate.collectionNotNull(listAttachedFile)) {
            for (AttachedFile af : listAttachedFile) {
                af.setSuperId(superId);
                af.setCreateTime(DateTimeHelper.getDateTimeD());
                attachedFileDAO.save(af);
            }
        }
    }
    
    /**
     * 批量删除
     * 
     * @param idList
     * @throws Exception
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void deleteBatch(List<String> idList) throws CRUDException {
        if (Validate.collectionNotNull(idList)) {
            for (String id : idList) {
                AttachedFile af = this.findFileById(id);
                File f = new File(ServletActionContext.getServletContext().getRealPath(af.getFilePath()));
                attachedFileDAO.deleteAF(af,f);
            }
        }
    }
    
    /**
     * 更新附件
     * 
     * @param intercom
     * @throws CRUDException
     */
    public void update(AttachedFile attachedFile) throws CRUDException {
       attachedFileDAO.update(attachedFile);
    }
}
