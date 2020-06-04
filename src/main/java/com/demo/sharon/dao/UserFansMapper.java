package com.demo.sharon.dao;

import com.demo.sharon.pojo.UserFans;
import java.util.List;

public interface UserFansMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserFans record);

    UserFans selectByPrimaryKey(String id);

    List<UserFans> selectAll();

    int updateByPrimaryKey(UserFans record);
}