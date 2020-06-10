package com.demo.sharon.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;


public class UpUtils {

	public static void upfile(MultipartFile file, HttpServletRequest request) {

		// 获取服务器路径
		String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/video/");
		System.out.println(realPath);
		// 获取本地路径
		String basePath="D:\\Ⅲ\\JavaEE\\demo\\src\\main\\webapp\\WEB-INF\\templates\\";

		// 创建文件对象
		File file1 = new File(realPath);
		if (!file1.exists()) {
			file1.mkdirs();
		}
		File file2 = new File(basePath);
		if(!file2.exists()) {
             file2.mkdirs();
		}

		// 获取视频文件名
		String orgName = file.getOriginalFilename();
		// 文件的IO流操作
		try {
		    // 文件输出流    细节：文件拼接要加//     append：后上传的文件不会被之前上传的文件覆盖
			FileOutputStream fos = new FileOutputStream(realPath+orgName,true);
			FileOutputStream fos1 = new FileOutputStream(basePath+orgName,true);
			fos.write(file.getBytes());
			fos1.write(file.getBytes());
			fos1.flush();
			fos.flush();
			fos1.close();
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
