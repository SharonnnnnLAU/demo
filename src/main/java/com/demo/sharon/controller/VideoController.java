package com.demo.sharon.controller;

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
    public List<Video> getAllVideos(Integer page, Integer limit) {
        page = (page - 1)*limit;
        List<Video> videos = videoService.getAllVideos(page,limit);
        return videos;
    }

}
