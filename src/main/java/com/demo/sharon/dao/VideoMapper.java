package com.demo.sharon.dao;

import com.demo.sharon.pojo.Video;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoMapper {
    int deleteByPrimaryKey(String id);

    int insert(Video record);

    Video selectByPrimaryKey(String id);

    List<Video> selectAll(@Param("page")Integer page, @Param("limit")Integer limit);

    int updateByPrimaryKey(Video record);

    Integer getCount();
}
