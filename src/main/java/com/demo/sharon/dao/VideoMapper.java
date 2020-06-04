package com.demo.sharon.dao;

import com.demo.sharon.pojo.Video;
import java.util.List;

public interface VideoMapper {
    int deleteByPrimaryKey(String id);

    int insert(Video record);

    Video selectByPrimaryKey(String id);

    List<Video> selectAll();

    int updateByPrimaryKey(Video record);
}
