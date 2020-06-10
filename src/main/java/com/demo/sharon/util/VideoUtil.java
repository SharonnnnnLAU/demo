package com.demo.sharon.util;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * 视频解析工具类
 */
public class VideoUtil {
    // 都是静态方法
    public static MultimediaInfo video(HttpServletRequest request, String name) {
        // 获取服务器中存放文件的目录
        String serverPath = request.getSession().getServletContext().getRealPath("/WEB-INF/videos/");
        // 拼接具体文件路径
        String path = serverPath + name;
        // 创建一个文件对象
        File file = new File(path);
        // 专门用来解析视频的
        Encoder encoder = new Encoder();
        // 告诉encoder应该去解析哪一个视频
        try {
            // info：将视频转换成java对象
            MultimediaInfo info = encoder.getInfo(file);
            // 时间ms 转换成秒  /1000
            long duration = info.getDuration();
            return info;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

