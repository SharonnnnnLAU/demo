package com.demo.sharon.service;

import com.demo.sharon.pojo.Result;
import com.demo.sharon.pojo.Video;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public interface VideoService {
    /**
     * 分页
     * @param page
     * @param limit
     * @return
     */
    List<Video> getAllVideos(Integer page, Integer limit);

    Integer getCount();

    /**
     * 删除单个视频
     * @param id
     * @return
     */
    Result del(String id);

    /**
     * 批量删除视频
     * @param ids
     * @return
     */
    Result delete(String[] ids);

    Result update(String id, String field, String value);

    public Result upload(MultipartFile file, HttpServletRequest request);

    Result add(Video video, HttpServletRequest request);

    Result selectByLike(String value, Integer page, Integer limit);
}
