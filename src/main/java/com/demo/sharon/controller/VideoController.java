package com.demo.sharon.controller;

import com.demo.sharon.pojo.Result;
import com.demo.sharon.pojo.Video;
import com.demo.sharon.service.VideoService;
import com.demo.sharon.util.DownUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/videos")
public class VideoController {

    // 需要查询所有数据 返回List(Video)
    @Autowired
    private VideoService videoService;
    // 分页：前端给第几页和每页第几条 后端传输对应数据
    // http://localhost:8080/demo/videos/getAllVideos?page=1&limit=10
    // videos/getAllVideos
    @RequestMapping("/getAllVideos")
    @ResponseBody
    public Result getAllVideos(Integer page, Integer limit) {
        List<Video> videos = videoService.getAllVideos(page,limit);
        Integer count = videoService.getCount();
        Result result = new Result();
        result.setData(videos);
        result.setCount(count);
        return result;
    }

    @RequestMapping("/del")
    @ResponseBody
    public Result del(String id) {
        Result result = videoService.del(id);
        return result;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Result delete(String[] ids) {
        // 1st让事务回滚的方式 在controller层捕获异常
        try {
            Result result = videoService.delete(ids);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            Result result = new Result();
            result.setMsg("u try it so fast, plz try it latter...");
            return result;
        }
    }

    @ResponseBody
    @RequestMapping("/update")
    public Result update(String id, String field, String value) {
        Result result = videoService.update(id, field, value);
        return result;
    }

    @ResponseBody
    @RequestMapping("/upload")
    public Result upload(MultipartFile file, HttpServletRequest request) {
        Result result = videoService.upload(file, request);
        return result;
    }

    @ResponseBody
    @RequestMapping("/add")
    public Result add(Video video, HttpServletRequest request) {
        Result result = videoService.add(video, request);
        return result;
    }

    @ResponseBody
    @RequestMapping("/selectByLike")
    public Result selectByLike(String value, Integer page, Integer limit) {
        Result result = videoService.selectByLike(value, page, limit);
        return result;
    }

    @RequestMapping("/selectByStatusAndTime")
    @ResponseBody
    public Result selectByStatusAndTime(String stu,String timeRange){
        Result result = videoService.selectByStatusAndTime(stu,timeRange);
        return result;
    }

    @RequestMapping("/download")
//    @ResponseBody
    public Result download(HttpServletRequest request, HttpServletResponse response, String filename){
        String[] split = filename.split("/");
        String s = split[split.length-1];
        DownUtil.down(request, response, s);

        Result result = new Result();
        return result;
    }
}
