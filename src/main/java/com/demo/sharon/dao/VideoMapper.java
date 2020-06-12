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

    void update(@Param("id") String id, @Param("field")String field, @Param("value")String value);

    List<Video> selectByLike(@Param("value") String value, @Param("page")Integer page, @Param("limit")Integer limit);

    List<Video> selectByLike(@Param("value") String value);

    List<Video> selectByStatusAndTime(@Param("stu") String stu, @Param("beginTime") String beginTime, @Param("endTime") String endTime);
}
