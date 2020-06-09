package com.demo.sharon.service.serviceImpl;

import com.demo.sharon.dao.VideoMapper;
import com.demo.sharon.pojo.Result;
import com.demo.sharon.pojo.Video;
import com.demo.sharon.service.VideoService;
import com.demo.sharon.util.UpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
}
