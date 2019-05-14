package com.easysoft.commons.helper;

import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 图片处理 工具类
 * @author IvanHsu
 * @2018年10月15日 上午10:36:39
 */
public class ThumbnailatorHelper {

	/**
	 * 创建不同分辨率的信息
	 * 默认 0.5的分辨率
	 * @param file
	 * @param scale
	 * @return
	 */
	public static String createThumbnailatorImage(File file,Double scale)
	{
		try {
			if(null != file){
				String thumbnailatorUrl =  null;
				System.out.println(file.getName());
				scale = null == scale || scale<= 0 ? 0.5 : scale;
				Thumbnails.of(file).scale(scale).toFile(thumbnailatorUrl);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 创建缩略图 
	 * size(width,height) 若图片横比200小，高比300小，不变
     * 若图片横比200小，高比300大，高缩小到300，图片比例不变 若图片横比200大，高比300小，横缩小到200，图片比例不变
     * 若图片横比200大，高比300大，图片按比例缩小，横为200或高为300
	 * @param file
	 * @param scale
	 * @param width
	 * @param height
	 * @return
         */
	public static String createThumbnailatorImage(File file,int width,int height)
	{
		try {
			if(null != file){
				String thumbnailatorUrl =  null;
				Thumbnails.of(file).size(width, height).toFile(thumbnailatorUrl);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	 
	public static void main(String[] args) {
		createThumbnailatorImage(new File("E:/1.jpg"),0.2);
		createThumbnailatorImage(new File("E:/1.jpg"),200,300);
	}
}
