package com.platform.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipException;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

/**
 * 文件名：FileHelper.java<br>
 * 说明：用于XXX的类 <br>
 * 作者：马金凯<br>
 * 更改记录： <br>
 * -------------------------------------------------------<br>
 * 改动人 时间 原因<br>
 * -------------------------------------------------------<br>
 * 马金凯 2008-1-14 创建文件<br>
 * -------------------------------------------------------<br>
 */
public class FileHelper {

	public static final String SEPARATOR = "/";
	
	/**
	 * 获得文件扩展名
	 * @param filePath
	 * @return
	 */
	public static String getFileExtension(String filePath) {
		if(filePath != null && !filePath.equals("")) {
			String[] sp = filePath.split("\\.");
			return sp[sp.length-1];
		} else {
			return "";
		}
	}
	
	/**
	 * 获得文件夹下的文件名
	 * @param folderPath
	 * @param suffix
	 * @return
	 */
	public static List<String> listFileNames(String folderPath, final String suffix) {
		List<String> fileNames = new ArrayList<String>();
		File folder = new File(folderPath);
		if(folder.exists() && folder.isDirectory()) {
			String[] files = folder.list(new FilenameFilter(){
				public boolean accept(File dir, String name) {
					if(suffix == null) {
						return true;
					} else {
						return name.toLowerCase().endsWith(suffix);
					}
				}
			});
			if(Validate.arrayNotNull(files)) {
				for (int i = 0; i < files.length; i++) {
					fileNames.add(files[i].substring(0, files[i].length()-suffix.length()));
				}
			}
		}
		return fileNames;
	}
	
    /**
     * 新建目录
     * 
     * @param folderPath
     *            String 如 c:/fqf
     * @return boolean
     */
    public static void newFolder(String folderPath) {
        try {
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            if (!myFilePath.exists()) {
                myFilePath.mkdir();
            }
        } catch (Exception e) {
            System.out.println("新建目录操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 新建文件
     * 
     * @param filePathAndName
     *            String 文件路径及名称 如c:/fqf.txt
     * @param fileContent
     *            String 文件内容
     * @return boolean
     */
    public static void newFile(String filePathAndName, String fileContent) {

        try {
            String filePath = filePathAndName;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            if (!myFilePath.exists()) {
                myFilePath.createNewFile();
            }
            FileWriter resultFile = new FileWriter(myFilePath);
            PrintWriter myFile = new PrintWriter(resultFile);
            String strContent = fileContent;
            myFile.println(strContent);
            resultFile.close();

        } catch (Exception e) {
            System.out.println("新建目录操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 删除文件
     * 
     * @param filePathAndName
     *            String 文件路径及名称 如c:/fqf.txt
     * @param fileContent
     *            String
     * @return boolean
     */
    public static void delFile(String filePathAndName) {
        try {
            String filePath = filePathAndName;
            filePath = filePath.toString();
            File myDelFile = new File(filePath);
            myDelFile.delete();

        } catch (Exception e) {
            System.out.println("删除文件操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 删除文件夹
     * 
     * @param filePathAndName
     *            String 文件夹路径及名称 如c:/fqf
     * @param fileContent
     *            String
     * @return boolean
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); // 删除空文件夹

        } catch (Exception e) {
            System.out.println("删除文件夹操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 删除文件夹里面的所有文件
     * 
     * @param path
     *            String 文件夹路径 如 c:/fqf
     */
    public static void delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);// 再删除空文件夹
            }
        }
    }

    /**
     * 复制单个文件
     * 
     * @param oldPath
     *            String 原文件路径 如：c:/fqf.txt
     * @param newPath
     *            String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { // 文件存在时
                InputStream inStream = new FileInputStream(oldPath); // 读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; // 字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 复制整个文件夹内容
     * 
     * @param oldPath
     *            String 原文件路径 如：c:/fqf
     * @param newPath
     *            String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public static void copyFolder(String oldPath, String newPath) {
        try {
            (new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {// 如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 获得文件夹大小
     * 
     * @param file
     * @return
     * @throws Exception
     */
    public static long recursionFileList(File file) {
        long size = 0;
        File flist[] = file.listFiles();
        for (int i = 0; flist != null && i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + recursionFileList(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    /**
     * 获得文件大小
     * 
     * @param file
     * @return
     */
    public static String getFileSize(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            Float size = Float.parseFloat(fis.available() + "");
            fis.close();
            DecimalFormat df = new DecimalFormat("#.##");
            return df.format(size / 1024) + "K";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 压缩
     * 
     * @param files
     * @param zipFileName
     */
    public static void zip(List<String> srcPathName, String zipFileName) {
        File zipFile = new File(zipFileName);
        Zip zip = new Zip();
        Project prj = new Project();
        zip.setProject(prj);
        zip.setDestFile(zipFile);
        for (String path : srcPathName) {
            File srcdir = new File(path);
            if (!srcdir.exists())
                throw new RuntimeException(srcPathName + "不存在！");
            FileSet fileSet = new FileSet();
            fileSet.setProject(prj);
            fileSet.setExcludes("*.bmp");// 排除缩略图
            fileSet.setDir(srcdir);
            // fileSet.setIncludes("*.java"); 包括哪些文件或文件夹
            // eg:zip.setIncludes("*.java");
            zip.addFileset(fileSet);
        }
        zip.execute();
    }

    /**
     * 解压ZIP(包括文件夹)
     * 
     * @param filePath
     * @param unZipPath
     */
    public static void unZip(String filePath, String unZipPath) {
        try {
            int BUFFER = 2048;
            ZipFile zipFile = new ZipFile(filePath);
            Enumeration emu = zipFile.getEntries();
            File unZipFolder = new File(unZipPath);
            unZipFolder.mkdirs();
            while (emu.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) emu.nextElement();
                // 会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
                if (entry.isDirectory()) {
                    new File(unZipPath, entry.getName()).mkdirs();
                    continue;
                }
                BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
                File file = new File(unZipPath, entry.getName());
                // 加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
                // 而这个文件所在的目录还没有出现过，所以要建出目录来。
                File parent = file.getParentFile();
                if (parent != null && (!parent.exists())) {
                    parent.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER);
                int count;
                byte data[] = new byte[BUFFER];
                while ((count = bis.read(data, 0, BUFFER)) != -1) {
                    bos.write(data, 0, count);
                }
                bos.flush();
                bos.close();
                bis.close();
            }
            zipFile.close();
        } catch (ZipException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解压ZIP(不包括文件夹)
     * 
     * @param filePath
     * @param unZipPath
     */
    public static void unZipWithoutFolder(String filePath, String unZipPath) {
        try {
            int BUFFER = 2048;
            ZipFile zipFile = new ZipFile(filePath);
            Enumeration emu = zipFile.getEntries();
            File unZipFolder = new File(unZipPath);
            unZipFolder.mkdirs();
            while (emu.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) emu.nextElement();
                if (!entry.isDirectory()) {
                    BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
                    String[] name = entry.getName().split("/");
                    if (name.length == 1)
                        name = entry.getName().split("\\\\");
                    File file = new File(unZipPath + File.separator + name[name.length - 1]);
                    FileOutputStream fos = new FileOutputStream(file);
                    BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER);
                    int count;
                    byte data[] = new byte[BUFFER];
                    while ((count = bis.read(data, 0, BUFFER)) != -1) {
                        bos.write(data, 0, count);
                    }
                    bos.flush();
                    bos.close();
                    bis.close();
                }
            }
            zipFile.close();
        } catch (ZipException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
