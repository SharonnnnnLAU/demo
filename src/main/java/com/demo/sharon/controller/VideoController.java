package com.demo.sharon.controller;

import com.demo.sharon.pojo.Result;
import com.demo.sharon.pojo.Video;
import com.demo.sharon.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
