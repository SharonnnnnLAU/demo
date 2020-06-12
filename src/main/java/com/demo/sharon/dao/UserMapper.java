package com.demo.sharon.dao;

import com.demo.sharon.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    User selectByPrimaryKey(String id);

    List<User> selectAll(@Param("page")Integer page, @Param("limit") Integer limit);

    int updateByPrimaryKey(User record);

    User selectByUsername(String username);

    Integer getCount();

    List<User> selectNameByLike(@Param("value") String value, @Param("page")Integer page, @Param("limit")Integer limit);


}
