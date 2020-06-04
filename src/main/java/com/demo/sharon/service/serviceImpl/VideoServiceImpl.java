package com.demo.sharon.service.serviceImpl;

import com.demo.sharon.dao.VideoMapper;
import com.demo.sharon.pojo.Video;
import com.demo.sharon.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

//    @Override
    public List<Video> getAllVideos(Integer page, Integer limit){
        List<Video> videos = videoMapper.selectAll(page, limit);
        return videos;
    }
}
