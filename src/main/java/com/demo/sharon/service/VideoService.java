package com.demo.sharon.service;

import com.demo.sharon.pojo.Video;

import java.util.List;

public interface VideoService {
    List<Video> getAllVideos(Integer page, Integer limit);
}
