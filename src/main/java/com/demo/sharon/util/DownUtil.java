package com.demo.sharon.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

public class DownUtil {

    public static void down(HttpServletRequest request, HttpServletResponse response,String filename){

//        String s = filename.substring(filename.indexOf("."));
//        if (s.equals(".mp4")){ }
//        else if (s.equals(".jpg")) {}


        //当你需要下载的文件在不同的陌路下面是,需要在这里添加对filename的判断来实现不同目录的切换
        String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/file/"+filename);
        File file = new File(realPath);
        try {
            filename= URLEncoder.encode(filename,"UTF-8");
            // 设置响应头
            response.addHeader("Content-Disposition","attachment;filename="+filename);
            response.setContentType("multipart/form-data");
            // 文件流
            FileInputStream fileInputStream = new FileInputStream(file);
            OutputStream outputStream = response.getOutputStream();
            
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = fileInputStream.read(bytes))>0){
                outputStream.write(bytes,0,len);
            }
            fileInputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
