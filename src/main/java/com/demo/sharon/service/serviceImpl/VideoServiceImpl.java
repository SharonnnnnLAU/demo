package com.demo.sharon.service.serviceImpl;

import com.demo.sharon.dao.VideoMapper;
import com.demo.sharon.pojo.Result;
import com.demo.sharon.pojo.Video;
import com.demo.sharon.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        for (int i = 0; i < ids.length; i++) {
            videoMapper.deleteByPrimaryKey(ids[i]);
        }
        return result;
    }
}
