package com.platform.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;

import com.platform.domain.AttachedFile;

/**
 * 说明：上传文件抽象帮助类<br>
 * 作者：马金凯<br>
 * 更改记录： <br>
 * -------------------------------------------------------<br>
 * 改动人 时间 原因<br>
 * -------------------------------------------------------<br>
 * 马金凯 2007-12-1 创建文件<br>
 * -------------------------------------------------------<br>
 */
public class UploadHelper {

    private List<File> files; // 文件集合
    private List<String> realNames; // 原文件名集合
    private List<String> filePaths; // 上传文件后生成的文件所在服务器的完全路径集合
    private List<String> titles; // 自定义文件名集合
    private List<String> contentTypes;
    public static final int UID = 0;
    public static final int NAME_UUID = 1;
    public static final int NAME_UNIX_TIME = 2;
    public static final int NAME = 3;

    public static final String S = "_";

    /**
     * 上传方法
     * 
     * @param upload
     *            文件集合
     * @param uploadFileName
     *            原文件名集合
     * @param uploadTitle
     *            自定义文件名集合
     * @param savePath
     *            保存路径
     * @param fileNameType
     *            文件名生成方式
     * @throws IOException
     */
    public UploadHelper(List<File> upload, List<String> uploadFileName, List<String> uploadTitle, List<String> uploadContentType, String savePath, int fileNameType) throws IOException {
        this.files = upload;
        this.realNames = uploadFileName;
        this.titles = uploadTitle;
        this.contentTypes = uploadContentType;

        filePaths = upload(files, realNames, uploadContentType, savePath, fileNameType);
    }

    /**
     * 获得文件信息数据库的实体对象
     * 
     * @return List<AttachedFile>
     */
    public List<AttachedFile> getAttachedFiles() {
        List<AttachedFile> list = new ArrayList<AttachedFile>();
        if (Validate.collectionNotNull(files)) {
            for (int i = 0; i < files.size(); i++) {
                if (Validate.notNull((String) realNames.get(i))) {
                    String[] splitName = realNames.get(i).split("\\.");

                    AttachedFile attachedFile = new AttachedFile();
                    attachedFile.setFileType(splitName[splitName.length - 1]);
                    attachedFile.setFileName(realNames.get(i));
                    attachedFile.setFilePath(filePaths.get(i));
                    attachedFile.setContentType(contentTypes.get(i));
                    attachedFile.setIsConvert("0");
                    if (Validate.collectionNotNull(titles))
                        attachedFile.setTitle(titles.get(i));
                    else
                        attachedFile.setTitle(splitName[0]);

                    list.add(attachedFile);
                }
            }
        }
        return list;
    }

    /**
     * 上传文件方法
     * 
     * @param List
     *            <File> files 文件集合
     * @param List
     *            <String> realNames 真实文件名集合
     * @param String
     *            savePath 保存路径地址
     * @param int
     *            fileNameType 文件名生成方式
     * @return List<String> filePaths 保存文件后的路径、文件名组合的地址集合
     * @throws IOException
     */
    private List<String> upload(List<File> files, List<String> realNames, List<String> contentType, String savePath, int fileNameType) throws IOException {
        List<String> filePaths = new ArrayList<String>();
        if (Validate.collectionNotNull(files)) {
            for (int i = 0; i < files.size(); i++) {
                String filePath = "";
                if (Validate.notNull(realNames.get(i))) {
                    // 拆分文件名
                    String[] splitName = realNames.get(i).split("\\.");
                    String fileType = splitName[splitName.length - 1];
                    StringBuffer fileOldName = new StringBuffer();
                    for (int j = 0; j < splitName.length; j++) {
                        // 不要后缀名
                        if (j == (splitName.length - 1))
                            break;

                        // 以"."拆分的,所以要加上"."
                        if (j == 0)
                            fileOldName.append(splitName[j]);
                        else
                            fileOldName.append("." + splitName[j]);
                    }

                    // 根据规则组合一个文件名
                    String fileName = "";
                    if (fileNameType == UID)
                        fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileType;
                    else if (fileNameType == NAME_UUID)
                        fileName = fileOldName.toString() + S +  UUID.randomUUID().toString().replaceAll("-", "") + "." + fileType;
                    else if (fileNameType == NAME_UNIX_TIME)
                        fileName = fileOldName.toString() + S + new Date().getTime() + "." + fileType;
                    else if (fileNameType == NAME)
                        fileName = realNames.get(i);

                    // 创建文件夹
                    String realFolder = ServletActionContext.getServletContext().getRealPath(savePath);
                    File folder = new File(realFolder);
                    if (!folder.exists())
                        folder.mkdir();
                    
                    FileOutputStream fos = null;
                    FileInputStream fis = null;
                    try{
                    // 建立文件输出流
                    fos = new FileOutputStream(realFolder + FileHelper.SEPARATOR + fileName);
                    // 建立文件输入流
                    fis = new FileInputStream(files.get(i));
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = fis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    }catch(IOException e){
                    	System.out.println("上传出错或上传了一个空文件!");
                    }finally{
                    	if(fos!=null){
                    		fos.flush();
                            fos.close();
                    	}
                    	if(fis!=null)
                            fis.close();
                    }
                    filePath = savePath + FileHelper.SEPARATOR + fileName;
                    filePaths.add(filePath);
                }
            }
        }
        return filePaths;
    }
}