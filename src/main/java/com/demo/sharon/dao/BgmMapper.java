package com.demo.sharon.dao;

import com.demo.sharon.pojo.Bgm;
import java.util.List;

public interface BgmMapper {
    int deleteByPrimaryKey(String id);

    int insert(Bgm record);

    Bgm selectByPrimaryKey(String id);

    List<Bgm> selectAll();

    int updateByPrimaryKey(Bgm record);
}