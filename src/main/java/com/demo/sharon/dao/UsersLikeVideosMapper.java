package com.demo.sharon.dao;

import com.demo.sharon.pojo.UsersLikeVideos;
import java.util.List;

public interface UsersLikeVideosMapper {
    int deleteByPrimaryKey(String id);

    int insert(UsersLikeVideos record);

    UsersLikeVideos selectByPrimaryKey(String id);

    List<UsersLikeVideos> selectAll();

    int updateByPrimaryKey(UsersLikeVideos record);
}