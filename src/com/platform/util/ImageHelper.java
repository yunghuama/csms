package com.platform.util;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * <p>
 * 程序名称： ImageHelper.java
 * </p>
 * <p>
 * 程序说明： 图片操作类
 * </p>
 * <p>
 * 版权信息： Copyright 深圳市维远泰克科技有限公司
 * </p>
 * <p>
 * 时间： Jan 17, 2011 9:36:15 AM
 * </p>
 * 
 * @author： Marker.King
 * @version： Ver 0.1
 */
public class ImageHelper {
	
	public static final String GIF = "gif";
	public static final String JPG = "jpg";
	public static final String JPEG = "jpeg";
	public static final String PNG = "png";
	
	/**
	 * 缩放图片
	 * @param filePath 图片物理路径
	 * @param targetPath 目标文件夹物理路径
	 * @param max 最大尺寸
	 * @return
	 */
	public static String scaleResize(String filePath, String targetPath, int max) {
		try {
			//获得扩展名
			String ext = FileHelper.getFileExtension(filePath).toLowerCase();
			if(ImageHelper.GIF.equals(ext) || ImageHelper.PNG.equals(ext)) {
				ext = ImageHelper.PNG;
			} else {
				ext = ImageHelper.JPG;
			}
			
			File srcFile = new File(filePath);
			BufferedImage srcImage = ImageIO.read(srcFile);
			int width = srcImage.getWidth(),
			    height = srcImage.getHeight(),
			    new_width,
			    new_height;
			
			//计算高度和宽度
			if(width < max && height < max) {
				new_width = width;
				new_height = height;
			} else if(width > height) {
				new_width = max;
				new_height = max * height / width;
			} else {
				new_height = max;
				new_width = max * width / height;
			}
			//计算缩放比例
			double scaleX = (double)new_width / (double)width;
			double scaleY = (double)new_height / (double)height;
			
			//生成图片
			BufferedImage new_image = new BufferedImage(new_width, new_height, BufferedImage.SCALE_SMOOTH);
			
			AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(scaleX, scaleY), null);
			new_image = ato.filter(srcImage, null);
			
			StringBuffer new_path = new StringBuffer();
			if(Validate.notNull(targetPath)) {
				File target_folder = new File(targetPath);
				if(!target_folder.exists()) {
					target_folder.mkdir();
				}
				new_path.append(targetPath);
				new_path.append(File.separator);
				new_path.append(srcFile.getName().substring(0, srcFile.getName().length() - ext.length()));
			} else {
				new_path.append(filePath.substring(0, filePath.length() - ext.length()));
			}
			new_path.append(max);
			new_path.append(".");
			new_path.append(ext);
			
			File new_file = new File(new_path.toString());
			ImageIO.write(new_image, ext, new_file);
			
			return new_file.getPath();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
}
