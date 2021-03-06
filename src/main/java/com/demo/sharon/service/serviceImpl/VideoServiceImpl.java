package com.demo.sharon.service.serviceImpl;

import com.demo.sharon.dao.VideoMapper;
import com.demo.sharon.pojo.Result;
import com.demo.sharon.pojo.Video;
import com.demo.sharon.service.VideoService;
import com.demo.sharon.util.UpUtils;
import com.demo.sharon.util.VideoUtil;
import it.sauronsoftware.jave.MultimediaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

//    @Override
    public List<Video> getAllVideos(Integer page, Integer limit){
        page = (page-1) * limit;
        List<Video> videos = videoMapper.selectAll(page, limit);
        return videos;
    }

    public Integer getCount() {
        Integer count = videoMapper.getCount();
        return count;
    }

    public Result del(String id) {
        Result result = new Result();
        // 删除前先判断数据是否存在
        Video video = videoMapper.selectByPrimaryKey(id);
        if (video != null) {
            int del = videoMapper.deleteByPrimaryKey(id);
            result.setMsg("Delete success.");
        } else {
            result.setCode(500);
            result.setMsg("System is busy, plz try it later.....");
        }
        return result;
    }

    @Transactional  // 添加事务
    // SQL事务：做某件事的时候，要么全部成功，要么全部失败
    public Result delete(String[] ids) {
        // iter：增强for循环
        // fori：普通循环
        Result result = new Result();
        try {
            for (int i = 0; i < ids.length; i++) {
                videoMapper.deleteByPrimaryKey(ids[i]);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            // 因为在服务层进行了异常的捕获，事务不会自动回滚
            // 解决方式：在服务层手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setMsg("Error");
            return result;
        }

    }

//    @Override
    public Result update(String id, String field, String value) {
        Result result = new Result();
        try {
            videoMapper.update(id, field, value);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setMsg("Error");
            result.setCode(500);
            return result;
        }
    }

    public Result upload(MultipartFile file, HttpServletRequest request) {
        Result result = new Result();
        try {
            UpUtils.upfile(file, request);
            String filename = file.getOriginalFilename();
            result.setMsg(filename);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Result add(Video video, HttpServletRequest request) {
        String id = UUID.randomUUID().toString();
        String authorID = "";
        Result result = new Result();
        MultimediaInfo info = VideoUtil.video(request, video.getVideoPath());
        try {
            long duration = info.getDuration();
            Float videoSeconds = Float.valueOf(duration/1000);
            Integer width = info.getVideo().getSize().getWidth();
            Integer height = info.getVideo().getSize().getHeight();
            // 构造函数构建video  视频路径是否需要编辑
            Video v = new Video(id, authorID, "", videoSeconds, width, height, 0l, 0, new Date());
            video.setVideoDesc(v.getVideoDesc());
            String path = v.getVideoPath();
            String videoPath = "video/" + path;
            v.setVideoPath(videoPath);

            // 调用dao层实现添加操作
            videoMapper.insert(v);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg("Error");
            result.setCode(0);
            return result;
        }
    }

    public Result selectByLike(String value, Integer page, Integer limit) {
        Result result = new Result();
        page = (page-1) * limit;
        List<Video> videos = videoMapper.selectByLike(value, page, limit);
        List<Video> list = videoMapper.selectByLike(value);
        result.setCount(list.size());
        result.setData(videos);
        return result;
    }

    public Result selectByStatusAndTime(String stu, String timeRange) {

        //时间和状态
        Result result = new Result();
        if(stu!=null && stu.equals("已发布")){
            stu = "1";
        }else if(stu != null && stu.equals("未发布")){
            stu = "0";
        }
        String beginTime="";
        String endTime ="";
        if(timeRange!=null&&timeRange!=""){
            String[] split = timeRange.split("~");
            beginTime= split[0];
            endTime = split[1];
        }
        List<Video> videos = videoMapper.selectByStatusAndTime(stu,beginTime,endTime);
        result.setCount(videos.size());
        result.setData(videos);
        return result;

    }
}
